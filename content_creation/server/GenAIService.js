const axios = require('axios');

class GenAIService {
    constructor(apiKey) {
        this.apiKey = apiKey;
        this.baseUrl = 'https://generativelanguage.googleapis.com/v1beta/models';
    }

    async generateContent(model, prompt, systemInstruction = null) {
        const payload = {
            contents: [{ parts: [{ text: prompt }] }]
        };

        if (systemInstruction) {
            payload.systemInstruction = { parts: [{ text: systemInstruction }] };
        }

        try {
            const response = await axios.post(`${this.baseUrl}/${model}:generateContent?key=${this.apiKey}`, payload);
            return response.data.candidates?.[0]?.content?.parts?.[0]?.text;
        } catch (error) {
            console.error(`Gemini API Error (${model}):`, error.response ? error.response.data : error.message);
            throw error;
        }
    }

    async generateAdCreative(inputs, masterPrompt, base64Image = null, mimetype = null) {
        const model = 'gemini-1.5-flash-latest'; // Robust model for visual+text
        const contents = [{ role: 'user', parts: [{ text: masterPrompt }] }];

        if (base64Image && mimetype) {
            contents[0].parts.push({ inlineData: { mimeType: mimetype, data: base64Image } });
        }

        const payload = {
            contents: contents,
            generationConfig: { responseModalities: ['TEXT', 'IMAGE'] },
            systemInstruction: { parts: [{ text: `You are a world-class ad creative director. Your task is to seamlessly integrate the given text and product into the provided image. The final image should look like a professional, cohesive ad.` }] }
        };

        try {
            const response = await axios.post(`${this.baseUrl}/${model}:generateContent?key=${this.apiKey}`, payload);
            return response.data;
        } catch (error) {
            console.error('Gemini Visual API Error:', error.response ? error.response.data : error.message);
            throw error;
        }
    }
}

module.exports = GenAIService;
