const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        unique: true,
    },
    password: {
        type: String,
        required: true,
    },
    brand: {
        name: {
            type: String,
            required: true,
        },
        logo: {
            type: String, // URL or path to the logo image
            required: true,
        },
        persona: {
            type: String,
            required: true,
        },
    },
    productCatalog: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Product',
    }],
}, { timestamps: true });

const User = mongoose.model('User', userSchema);

module.exports = User;