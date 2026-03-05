import React, { useState } from 'react';
import axios from 'axios';

const WorkflowC = () => {
    const [prompt, setPrompt] = useState('');
    const [previousOutputs, setPreviousOutputs] = useState([]);
    const [newOutput, setNewOutput] = useState('');

    const handlePromptChange = (e) => {
        setPrompt(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/workflow/context-aware', { prompt, previousOutputs });
            setNewOutput(response.data.output);
        } catch (error) {
            console.error('Error generating new output:', error);
        }
    };

    return (
        <div className="workflow-c">
            <h2>Context-Aware Iteration</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="prompt">New Prompt:</label>
                    <input
                        type="text"
                        id="prompt"
                        value={prompt}
                        onChange={handlePromptChange}
                        required
                    />
                </div>
                <button type="submit">Generate</button>
            </form>
            {newOutput && (
                <div className="output">
                    <h3>Generated Output:</h3>
                    <p>{newOutput}</p>
                </div>
            )}
        </div>
    );
};

export default WorkflowC;