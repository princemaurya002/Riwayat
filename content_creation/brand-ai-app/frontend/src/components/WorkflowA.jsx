import React, { useState } from 'react';

const WorkflowA = () => {
    const [primaryProduct, setPrimaryProduct] = useState('');
    const [contentType, setContentType] = useState('');
    const [targetPlatform, setTargetPlatform] = useState('');
    const [generatedContent, setGeneratedContent] = useState('');

    const handleGenerateContent = async () => {
        // Call the backend API to generate content
        const response = await fetch('/api/workflow/generate-text', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                primaryProduct,
                contentType,
                targetPlatform,
            }),
        });

        const data = await response.json();
        setGeneratedContent(data.content);
    };

    return (
        <div className="workflow-a">
            <h2>Text-Only Content Generation</h2>
            <form onSubmit={(e) => { e.preventDefault(); handleGenerateContent(); }}>
                <div>
                    <label>Primary Product/Service (Required):</label>
                    <input
                        type="text"
                        value={primaryProduct}
                        onChange={(e) => setPrimaryProduct(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Content Type:</label>
                    <input
                        type="text"
                        value={contentType}
                        onChange={(e) => setContentType(e.target.value)}
                    />
                </div>
                <div>
                    <label>Target Platform:</label>
                    <input
                        type="text"
                        value={targetPlatform}
                        onChange={(e) => setTargetPlatform(e.target.value)}
                    />
                </div>
                <button type="submit">Generate Content</button>
            </form>
            {generatedContent && (
                <div className="generated-content">
                    <h3>Generated Content:</h3>
                    <p>{generatedContent}</p>
                </div>
            )}
        </div>
    );
};

export default WorkflowA;