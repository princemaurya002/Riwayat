import React, { useState } from 'react';

const BrandIdentityForm = ({ onSubmit }) => {
    const [brandName, setBrandName] = useState('');
    const [logo, setLogo] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        const brandData = {
            brandName,
            logo,
        };
        onSubmit(brandData);
    };

    const handleLogoChange = (e) => {
        setLogo(e.target.files[0]);
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="brandName">Brand Name:</label>
                <input
                    type="text"
                    id="brandName"
                    value={brandName}
                    onChange={(e) => setBrandName(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="logo">Brand Logo:</label>
                <input
                    type="file"
                    id="logo"
                    accept="image/*"
                    onChange={handleLogoChange}
                />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
};

export default BrandIdentityForm;