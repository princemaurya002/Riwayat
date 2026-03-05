const express = require('express');
const router = express.Router();
const workflowController = require('../controllers/workflowController');

// Workflow A: Text-Only Generation
router.post('/text-only', workflowController.generateTextOnly);

// Workflow B: Visual + Text Generation
router.post('/visual-text', workflowController.generateVisualText);

// Workflow C: Context-Aware Iteration
router.post('/context-aware', workflowController.iterateContextAware);

module.exports = router;