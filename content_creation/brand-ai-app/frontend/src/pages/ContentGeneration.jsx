import React, { useState } from 'react';
import WorkflowA from '../components/WorkflowA';
import WorkflowB from '../components/WorkflowB';
import WorkflowC from '../components/WorkflowC';

const ContentGeneration = () => {
    const [selectedWorkflow, setSelectedWorkflow] = useState('');

    const handleWorkflowSelection = (event) => {
        setSelectedWorkflow(event.target.value);
    };

    return (
        <div className="content-generation">
            <h1>Content Generation</h1>
            <div>
                <label htmlFor="workflow-select">Select Workflow:</label>
                <select id="workflow-select" onChange={handleWorkflowSelection}>
                    <option value="">--Choose a Workflow--</option>
                    <option value="workflowA">Text-Only Generation</option>
                    <option value="workflowB">Visual + Text Generation</option>
                    <option value="workflowC">Context-Aware Iteration</option>
                </select>
            </div>
            {selectedWorkflow === 'workflowA' && <WorkflowA />}
            {selectedWorkflow === 'workflowB' && <WorkflowB />}
            {selectedWorkflow === 'workflowC' && <WorkflowC />}
        </div>
    );
};

export default ContentGeneration;