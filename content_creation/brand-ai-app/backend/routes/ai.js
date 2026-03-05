const express = require('express');
const router = express.Router();
const aiController = require('../controllers/aiController');

// Workflow A: Text-Only Generation
router.post('/text-generation', aiController.textOnlyGeneration);

// Workflow B: Visual + Text Generation
router.post('/visual-text-generation', aiController.visualTextGeneration);

// Workflow C: Context-Aware Iteration
router.post('/context-aware-iteration', aiController.contextAwareIteration);

module.exports = router;