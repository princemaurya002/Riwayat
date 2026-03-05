import React, { useState } from 'react';

const BrandPersonaForm = ({ onSubmit }) => {
    const [toneOfVoice, setToneOfVoice] = useState('');
    const [description, setDescription] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ toneOfVoice, description });
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Define Your Brand Persona</h2>
            <div>
                <label htmlFor="toneOfVoice">Tone of Voice:</label>
                <input
                    type="text"
                    id="toneOfVoice"
                    value={toneOfVoice}
                    onChange={(e) => setToneOfVoice(e.target.value)}
                    required
                />
            </div>
            <div>
                <label htmlFor="description">Description:</label>
                <textarea
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
};

export default BrandPersonaForm;