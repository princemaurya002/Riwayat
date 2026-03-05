const express = require('express');
const { IncomingForm } = require('formidable');
const fs = require('fs');
const { v4: uuidv4 } = require('uuid');

const FirebaseService = require('./FirebaseService');
const GenAIService = require('./GenAIService');

// --- Configuration ---
const serviceAccount = require('./firebase-service-account.json');
const GEMINI_API_KEY = "AQ.Ab8RN6JOb2p0cSSt4ChPJCXoG3U_We9AW2EwzO8C89RUeSjgbQ";
const BUCKET_NAME = "gs://riwayat-be2a5.firebasestorage.app";

const firebase = new FirebaseService(serviceAccount, BUCKET_NAME);
const ai = new GenAIService(GEMINI_API_KEY);

const app = express();
const port = 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// --- Unified API Endpoints ---

/**
 * Mobile: Parse Natural Language Voice Commands
 */
app.post('/api/mobile/parse-voice', async (req, res) => {
    const { transcript, userId } = req.body;
    if (!transcript || !userId) return res.status(400).json({ error: 'Missing transcript or userId' });

    const systemInstruction = "You are a business parser for an artisan app. Extract 'actionType' (SALE, EXPENSE, INVENTORY), 'product', and 'amount' from the transcript. Return JSON.";
    try {
        const result = await ai.generateContent('gemini-1.5-flash', transcript, systemInstruction);
        res.json(JSON.parse(result));
    } catch (error) {
        res.status(500).json({ error: 'Failed to parse voice command.' });
    }
});

/**
 * Shared: Generate Ad Creative (Web & Mobile)
 */
app.post('/api/ad/generate', (req, res) => {
    const form = new IncomingForm({ multiples: false });

    form.parse(req, async (err, fields, files) => {
        if (err) return res.status(500).json({ error: 'Form processing failed.' });

        try {
            const inputs = JSON.parse(fields.inputs[0]);
            const { userId, productId } = inputs;

            // 1. Generate Master Prompt via AI
            const masterPrompt = await ai.generateContent('gemini-1.5-flash', `Create a professional ad prompt for: ${inputs.productDescription}`);

            // 2. Process Visuals
            const imageFile = files.imageFile ? files.imageFile[0] : null;
            let generatedVisual = null;

            if (imageFile) {
                const fileBuffer = await fs.promises.readFile(imageFile.filepath);
                const base64Image = fileBuffer.toString('base64');
                const visualData = await ai.generateAdCreative(inputs, masterPrompt, base64Image, imageFile.mimetype);

                const imageDataPart = visualData.candidates?.[0]?.content?.parts?.find(p => p.inlineData);
                if (imageDataPart) {
                    generatedVisual = await firebase.uploadImage(imageDataPart.inlineData.data, imageDataPart.inlineData.mimeType, `ads/${uuidv4()}.png`);
                }
            }

            // 3. Generate Caption
            const caption = await ai.generateContent('gemini-1.5-flash', `Generate a short social media caption for this ad: ${masterPrompt}`);

            // 4. Save to Unified Firestore (SCHEMA.md alignment)
            const adDoc = {
                productId,
                visualUrl: generatedVisual,
                caption,
                createdAt: new Date(),
                inputs: inputs
            };

            await firebase.getMarketingRef(userId).add(adDoc);

            res.json({ visual: generatedVisual, text: caption });
        } catch (error) {
            console.error('Generation Flow Error:', error);
            res.status(500).json({ error: 'Failed to generate content.' });
        }
    });
});

app.listen(port, () => {
    console.log(`Riwayat Scalable Backend running on http://localhost:${port}`);
});
