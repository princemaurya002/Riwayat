const axios = require('axios');

const AI_API_URL = 'https://api.example.com/ai'; // Replace with your actual AI API URL

// Function to generate text-only content
const generateTextContent = async (productService, contentType, targetPlatform) => {
    try {
        const response = await axios.post(`${AI_API_URL}/generate-text`, {
            productService,
            contentType,
            targetPlatform,
        });
        return response.data;
    } catch (error) {
        throw new Error('Error generating text content: ' + error.message);
    }
};

// Function to generate visual + text content
const generateVisualTextContent = async (productService, adType, imageInput, template, heading, subHeading) => {
    try {
        const response = await axios.post(`${AI_API_URL}/generate-visual-text`, {
            productService,
            adType,
            imageInput,
            template,
            heading,
            subHeading,
        });
        return response.data;
    } catch (error) {
        throw new Error('Error generating visual + text content: ' + error.message);
    }
};

// Function to iterate on previous content
const iterateContent = async (previousOutput, newPrompt) => {
    try {
        const response = await axios.post(`${AI_API_URL}/iterate`, {
            previousOutput,
            newPrompt,
        });
        return response.data;
    } catch (error) {
        throw new Error('Error iterating content: ' + error.message);
    }
};

module.exports = {
    generateTextContent,
    generateVisualTextContent,
    iterateContent,
};