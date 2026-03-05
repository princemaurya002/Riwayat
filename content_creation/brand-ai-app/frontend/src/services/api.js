import axios from 'axios';

const API_URL = 'http://localhost:5000/api'; // Adjust the URL as needed

// Function to handle user signup
export const signup = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/auth/signup`, userData);
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to handle user login
export const login = async (credentials) => {
    try {
        const response = await axios.post(`${API_URL}/auth/login`, credentials);
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to create a new brand
export const createBrand = async (brandData, token) => {
    try {
        const response = await axios.post(`${API_URL}/brand`, brandData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to add a product to the catalog
export const addProduct = async (productData, token) => {
    try {
        const response = await axios.post(`${API_URL}/product`, productData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to generate content using Workflow A
export const generateTextContent = async (contentData, token) => {
    try {
        const response = await axios.post(`${API_URL}/workflow/a`, contentData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to generate visual + text content using Workflow B
export const generateVisualTextContent = async (contentData, token) => {
    try {
        const response = await axios.post(`${API_URL}/workflow/b`, contentData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

// Function to iterate on previous content using Workflow C
export const iterateContent = async (iterationData, token) => {
    try {
        const response = await axios.post(`${API_URL}/workflow/c`, iterationData, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};