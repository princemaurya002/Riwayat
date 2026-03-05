const express = require('express');
const router = express.Router();
const { generateTextOnly, generateVisualText, iterateContext } = require('../utils/aiUtils');

// Workflow A: Text-Only Generation
router.post('/text-only', async (req, res) => {
    const { primaryProduct, contentType, targetPlatform } = req.body;

    if (!primaryProduct || !contentType) {
        return res.status(400).json({ error: 'Primary product and content type are required.' });
    }

    try {
        const generatedContent = await generateTextOnly(primaryProduct, contentType, targetPlatform);
        res.status(200).json({ content: generatedContent });
    } catch (error) {
        res.status(500).json({ error: 'Error generating text content.' });
    }
});

// Workflow B: Visual + Text Generation
router.post('/visual-text', async (req, res) => {
    const { primaryProduct, adType, imageInput, template, heading, subHeading } = req.body;

    if (!primaryProduct || !adType) {
        return res.status(400).json({ error: 'Primary product and ad type are required.' });
    }

    try {
        const { image, caption } = await generateVisualText(primaryProduct, adType, imageInput, template, heading, subHeading);
        res.status(200).json({ image, caption });
    } catch (error) {
        res.status(500).json({ error: 'Error generating visual and text content.' });
    }
});

// Workflow C: Context-Aware Iteration
router.post('/iterate', async (req, res) => {
    const { prompt } = req.body;

    if (!prompt) {
        return res.status(400).json({ error: 'Prompt is required for iteration.' });
    }

    try {
        const refinedContent = await iterateContext(prompt);
        res.status(200).json({ content: refinedContent });
    } catch (error) {
        res.status(500).json({ error: 'Error iterating content.' });
    }
});

module.exports = router;