import React from 'react';
import { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import { getUserDashboardData } from '../services/api';

const Dashboard = () => {
    const [dashboardData, setDashboardData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDashboardData = async () => {
            try {
                const data = await getUserDashboardData();
                setDashboardData(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchDashboardData();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <Navbar />
            <h1>Dashboard</h1>
            {dashboardData ? (
                <div>
                    <h2>Welcome, {dashboardData.userName}</h2>
                    <h3>Your Brand: {dashboardData.brandName}</h3>
                    <h4>Recent Activities:</h4>
                    <ul>
                        {dashboardData.activities.map((activity, index) => (
                            <li key={index}>{activity}</li>
                        ))}
                    </ul>
                </div>
            ) : (
                <p>No data available.</p>
            )}
        </div>
    );
};

export default Dashboard;