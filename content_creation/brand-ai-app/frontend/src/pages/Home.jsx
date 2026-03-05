import React from 'react';
import Navbar from '../components/Navbar';

const Home = () => {
    return (
        <div>
            <Navbar />
            <header className="home-header">
                <h1>Welcome to Brand AI</h1>
                <p>Your one-stop solution for AI-powered content generation.</p>
            </header>
            <main className="home-content">
                <section>
                    <h2>Onboard Your Brand</h2>
                    <p>Start by defining your brand identity and persona.</p>
                    <a href="/onboarding" className="btn">Get Started</a>
                </section>
                <section>
                    <h2>Generate Content</h2>
                    <p>Utilize our AI workflows to create engaging content for your brand.</p>
                    <a href="/content-generation" className="btn">Explore Workflows</a>
                </section>
            </main>
        </div>
    );
};

export default Home;