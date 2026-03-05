const Brand = require('../models/Brand');

// Create a new brand
exports.createBrand = async (req, res) => {
    try {
        const { brandName, logo, persona } = req.body;
        const newBrand = new Brand({ brandName, logo, persona });
        await newBrand.save();
        res.status(201).json({ message: 'Brand created successfully', brand: newBrand });
    } catch (error) {
        res.status(500).json({ message: 'Error creating brand', error: error.message });
    }
};

// Update existing brand information
exports.updateBrand = async (req, res) => {
    try {
        const { brandId } = req.params;
        const updates = req.body;
        const updatedBrand = await Brand.findByIdAndUpdate(brandId, updates, { new: true });
        if (!updatedBrand) {
            return res.status(404).json({ message: 'Brand not found' });
        }
        res.status(200).json({ message: 'Brand updated successfully', brand: updatedBrand });
    } catch (error) {
        res.status(500).json({ message: 'Error updating brand', error: error.message });
    }
};

// Get brand information
exports.getBrand = async (req, res) => {
    try {
        const { brandId } = req.params;
        const brand = await Brand.findById(brandId);
        if (!brand) {
            return res.status(404).json({ message: 'Brand not found' });
        }
        res.status(200).json({ brand });
    } catch (error) {
        res.status(500).json({ message: 'Error retrieving brand', error: error.message });
    }
};