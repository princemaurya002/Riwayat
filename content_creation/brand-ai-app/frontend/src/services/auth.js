import axios from 'axios';

const API_URL = 'http://localhost:5000/api/auth'; // Adjust the URL as necessary

// Function to handle user signup
export const signup = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/signup`, userData);
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to handle user login
export const login = async (credentials) => {
    try {
        const response = await axios.post(`${API_URL}/login`, credentials);
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to get the current user
export const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem('user'));
};

// Function to logout the user
export const logout = () => {
    localStorage.removeItem('user');
};