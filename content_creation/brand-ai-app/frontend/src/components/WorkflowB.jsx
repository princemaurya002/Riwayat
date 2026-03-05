import React, { useState } from 'react';

const WorkflowB = () => {
    const [productService, setProductService] = useState('');
    const [adType, setAdType] = useState('');
    const [imageInput, setImageInput] = useState(null);
    const [template, setTemplate] = useState(null);
    const [heading, setHeading] = useState('');
    const [subHeading, setSubHeading] = useState('');

    const handleImageChange = (e) => {
        setImageInput(e.target.files[0]);
    };

    const handleTemplateChange = (e) => {
        setTemplate(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        const inputs = {
            productDescription: productService,
            adSize: adType,
            heading: heading,
            subHeading: subHeading,
            userId: "artisan_001", // Placeholder
            productId: "prod_001"    // Placeholder
        };

        formData.append('inputs', JSON.stringify(inputs));
        if (imageInput) formData.append('imageFile', imageInput);
        if (template) formData.append('templateFile', template);

        try {
            const response = await fetch('http://localhost:3000/api/ad/generate', {
                method: 'POST',
                body: formData,
            });
            const data = await response.json();
            console.log("Generated Ad:", data);
            alert("Ad Generated Successfully!");
        } catch (error) {
            console.error("Error generating ad:", error);
            alert("Failed to generate ad.");
        }
    };

    return (
        <div>
            <h2>Visual + Text Content Generation</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Primary Product/Service (Required):</label>
                    <input
                        type="text"
                        value={productService}
                        onChange={(e) => setProductService(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Ad Type/Size (Required):</label>
                    <input
                        type="text"
                        value={adType}
                        onChange={(e) => setAdType(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Image Input (Optional):</label>
                    <input type="file" onChange={handleImageChange} />
                </div>
                <div>
                    <label>Template (Optional):</label>
                    <input type="file" onChange={handleTemplateChange} />
                </div>
                <div>
                    <label>Heading (Optional):</label>
                    <input
                        type="text"
                        value={heading}
                        onChange={(e) => setHeading(e.target.value)}
                    />
                </div>
                <div>
                    <label>Sub-heading (Optional):</label>
                    <input
                        type="text"
                        value={subHeading}
                        onChange={(e) => setSubHeading(e.target.value)}
                    />
                </div>
                <button type="submit">Generate Content</button>
            </form>
        </div>
    );
};

export default WorkflowB;