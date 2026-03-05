import React, { useState } from 'react';

const ProductCatalogForm = ({ onSubmit }) => {
    const [productName, setProductName] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState('');
    const [price, setPrice] = useState('');
    const [image, setImage] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        const productData = {
            productName,
            description,
            category,
            price,
            image,
        };
        onSubmit(productData);
        resetForm();
    };

    const resetForm = () => {
        setProductName('');
        setDescription('');
        setCategory('');
        setPrice('');
        setImage(null);
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Product Catalog</h2>
            <div>
                <label>Product Name:</label>
                <input
                    type="text"
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Description:</label>
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />
            </div>
            <div>
                <label>Category:</label>
                <input
                    type="text"
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                />
            </div>
            <div>
                <label>Price:</label>
                <input
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                />
            </div>
            <div>
                <label>Product Image:</label>
                <input
                    type="file"
                    onChange={(e) => setImage(e.target.files[0])}
                />
            </div>
            <button type="submit">Add Product</button>
        </form>
    );
};

export default ProductCatalogForm;