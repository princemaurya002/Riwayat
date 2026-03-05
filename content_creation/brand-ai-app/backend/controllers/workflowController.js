const Workflow = require('../models/Workflow'); // Assuming a Workflow model exists
const ProjectHistory = require('../models/ProjectHistory');

// Workflow A: Text-Only Generation
exports.generateTextOnly = async (req, res) => {
    const { primaryProduct, contentType, targetPlatform } = req.body;

    if (!primaryProduct || !contentType) {
        return res.status(400).json({ message: 'Primary product and content type are required.' });
    }

    // Logic to generate text content based on inputs
    const generatedContent = `Generated content for ${primaryProduct} as a ${contentType} for ${targetPlatform}.`;

    // Save to project history if needed
    await ProjectHistory.create({ content: generatedContent });

    res.status(200).json({ content: generatedContent });
};

// Workflow B: Visual + Text Generation
exports.generateVisualAndText = async (req, res) => {
    const { primaryProduct, adType, imageInput, template, heading, subHeading } = req.body;

    if (!primaryProduct || !adType) {
        return res.status(400).json({ message: 'Primary product and ad type are required.' });
    }

    // Logic to generate image and text content
    const generatedImage = `Generated image for ${primaryProduct} as a ${adType}.`;
    const generatedText = `Generated caption for ${primaryProduct}.`;

    // Save to project history if needed
    await ProjectHistory.create({ content: generatedText });

    res.status(200).json({ image: generatedImage, text: generatedText });
};

// Workflow C: Context-Aware Iteration
exports.iterateContextAware = async (req, res) => {
    const { prompt } = req.body;

    if (!prompt) {
        return res.status(400).json({ message: 'Prompt is required for iteration.' });
    }

    // Logic to retrieve previous outputs and generate new content
    const previousOutputs = await ProjectHistory.find(); // Fetch previous outputs
    const newContent = `Refined content based on previous outputs and prompt: ${prompt}`;

    // Save to project history if needed
    await ProjectHistory.create({ content: newContent });

    res.status(200).json({ content: newContent });
};