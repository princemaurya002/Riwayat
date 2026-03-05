const mongoose = require('mongoose');

const ProjectHistorySchema = new mongoose.Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    projectName: {
        type: String,
        required: true
    },
    outputs: [
        {
            content: {
                type: String,
                required: true
            },
            createdAt: {
                type: Date,
                default: Date.now
            }
        }
    ],
    createdAt: {
        type: Date,
        default: Date.now
    }
});

const ProjectHistory = mongoose.model('ProjectHistory', ProjectHistorySchema);

module.exports = ProjectHistory;