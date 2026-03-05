import React, { useState } from 'react';
import BrandIdentityForm from './BrandIdentityForm';
import BrandPersonaForm from './BrandPersonaForm';
import ProductCatalogForm from './ProductCatalogForm';

const OnboardingWizard = () => {
    const [step, setStep] = useState(1);
    const [brandData, setBrandData] = useState({
        brandName: '',
        logo: null,
        persona: '',
        products: []
    });

    const handleNext = (data) => {
        setBrandData(prevData => ({ ...prevData, ...data }));
        setStep(prevStep => prevStep + 1);
    };

    const handlePrevious = () => {
        setStep(prevStep => prevStep - 1);
    };

    const handleSubmit = () => {
        // Submit the brand data to the backend
        console.log('Submitting brand data:', brandData);
        // Add API call here to send brandData to the backend
    };

    return (
        <div className="onboarding-wizard">
            {step === 1 && (
                <BrandIdentityForm onNext={handleNext} />
            )}
            {step === 2 && (
                <BrandPersonaForm onNext={handleNext} onPrevious={handlePrevious} />
            )}
            {step === 3 && (
                <ProductCatalogForm onSubmit={handleSubmit} onPrevious={handlePrevious} />
            )}
        </div>
    );
};

export default OnboardingWizard;