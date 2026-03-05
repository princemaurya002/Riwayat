import React, { useState } from 'react';
import OnboardingWizard from '../components/OnboardingWizard';
import BrandIdentityForm from '../components/BrandIdentityForm';
import BrandPersonaForm from '../components/BrandPersonaForm';
import ProductCatalogForm from '../components/ProductCatalogForm';

const Onboarding = () => {
    const [step, setStep] = useState(1);
    const [brandData, setBrandData] = useState({
        brandName: '',
        logo: null,
        persona: '',
        products: []
    });

    const handleNextStep = (data) => {
        setBrandData(prevData => ({ ...prevData, ...data }));
        setStep(prevStep => prevStep + 1);
    };

    const handlePreviousStep = () => {
        setStep(prevStep => prevStep - 1);
    };

    return (
        <div className="onboarding-container">
            {step === 1 && <OnboardingWizard onNext={handleNextStep} />}
            {step === 2 && (
                <BrandIdentityForm 
                    brandData={brandData} 
                    onNext={handleNextStep} 
                    onPrevious={handlePreviousStep} 
                />
            )}
            {step === 3 && (
                <BrandPersonaForm 
                    brandData={brandData} 
                    onNext={handleNextStep} 
                    onPrevious={handlePreviousStep} 
                />
            )}
            {step === 4 && (
                <ProductCatalogForm 
                    brandData={brandData} 
                    onPrevious={handlePreviousStep} 
                />
            )}
        </div>
    );
};

export default Onboarding;