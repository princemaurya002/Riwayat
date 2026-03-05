# Brand AI App

## Overview
The Brand AI App is a comprehensive platform designed to assist users in defining their brand identity and generating AI-powered content. The application is divided into two main phases: Onboarding & Brand Foundation and AI-Powered Content Generation Workflows.

## Project Structure
The project is organized into two main directories: `backend` and `frontend`.

### Backend
The backend is built using Node.js and Express. It handles user authentication, brand management, product catalog operations, and AI content generation.

- **controllers/**: Contains the logic for handling requests related to authentication, brands, products, workflows, and AI interactions.
- **models/**: Defines the MongoDB schemas for User, Brand, Product, and Project History.
- **routes/**: Contains the route definitions for the API endpoints.
- **middleware/**: Includes middleware functions for authentication.
- **utils/**: Contains utility functions for AI interactions.
- **app.js**: Sets up the Express application with middleware and routes.
- **index.js**: Entry point for the backend application.

### Frontend
The frontend is built using React. It provides a user-friendly interface for onboarding and content generation.

- **public/**: Contains the main HTML file for the React application.
- **src/**: Contains the React components, pages, services, and styles.
  - **components/**: Reusable components for onboarding, authentication, and content generation workflows.
  - **pages/**: Main pages of the application.
  - **services/**: Functions for making API calls to the backend.
  - **styles/**: CSS styles for the application.
- **App.js**: Main application component that sets up routing and global state management.
- **index.js**: Entry point for the frontend application.

## Features

### Phase 1: Onboarding & Brand Foundation
- **Guided Onboarding Wizard**: A step-by-step process to collect essential brand information.
- **Brand Identity Definition**: Users can input their brand name and upload a logo.
- **Brand Persona**: Users define their brand's tone of voice.
- **Product Catalog**: Users can input details about their products.

### Phase 2: AI-Powered Content Generation Workflows
- **Workflow A**: Text-only content generation based on user inputs.
- **Workflow B**: Visual + text content generation, including image uploads and automated caption generation.
- **Workflow C**: Context-aware iteration for refining previous outputs.

## Getting Started

### Prerequisites
- Node.js
- MongoDB

### Installation
1. Clone the repository:
   ```
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```
   cd brand-ai-app
   ```
3. Install backend dependencies:
   ```
   cd backend
   npm install
   ```
4. Install frontend dependencies:
   ```
   cd ../frontend
   npm install
   ```

### Running the Application
1. Start the backend server:
   ```
   cd backend
   node index.js
   ```
2. Start the frontend application:
   ```
   cd frontend
   npm start
   ```

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.