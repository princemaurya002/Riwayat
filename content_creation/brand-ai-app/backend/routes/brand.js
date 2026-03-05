const express = require('express');
const router = express.Router();
const brandController = require('../controllers/brandController');
const authMiddleware = require('../middleware/authMiddleware');

// Route to create a new brand
router.post('/', authMiddleware, brandController.createBrand);

// Route to get brand information
router.get('/:id', authMiddleware, brandController.getBrand);

// Route to update brand information
router.put('/:id', authMiddleware, brandController.updateBrand);

// Route to delete a brand
router.delete('/:id', authMiddleware, brandController.deleteBrand);

module.exports = router;