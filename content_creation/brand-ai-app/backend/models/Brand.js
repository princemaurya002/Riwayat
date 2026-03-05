const mongoose = require('mongoose');

const brandSchema = new mongoose.Schema({
    brandName: {
        type: String,
        required: true,
        trim: true
    },
    logo: {
        type: String,
        required: true
    },
    persona: {
        type: String,
        required: true,
        enum: ['rustic', 'playful', 'elegant', 'modern', 'classic'], // Example personas
        default: 'modern'
    },
    productCatalog: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Product'
    }]
}, { timestamps: true });

const Brand = mongoose.model('Brand', brandSchema);

module.exports = Brand;