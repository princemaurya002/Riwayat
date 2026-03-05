

const admin = require('firebase-admin');
const serviceAccount = require('./firebase-service-account.json');
const axios = require('axios');
const { v4: uuidv4 } = require('uuid');

// --- Configuration ---
const BUCKET_NAME = "gs://riwayat-be2a5.firebasestorage.app";

// Initialize Firebase Admin SDK
try {
    admin.initializeApp({
        credential: admin.credential.cert(serviceAccount)
    });

    const storage = admin.storage();
    const bucket = storage.bucket(BUCKET_NAME);
    
    console.log("Firebase Admin SDK initialized successfully.");
    console.log(`Successfully connected to bucket: ${bucket.name}`);

    const originalImageUrl = 'https://firebasestorage.googleapis.com/v0/b/riwayat-be2a5.firebasestorage.app/o/images%2FGemini_Generated_Image_d5h5nxd5h5nxd5h5.png?alt=media&token=4dc35848-d562-4cec-a291-4057bc56740e';
    
    // Fetches an image from a public URL and returns it as a Base64 string
    const fetchImageFromUrl = async (url) => {
        try {
            console.log(`- Fetching image from URL: ${url}`);
            const response = await axios.get(url, { responseType: 'arraybuffer' });
            const mimeType = response.headers['content-type'];
            const base64Data = Buffer.from(response.data, 'binary').toString('base64');
            console.log('   ✅ Image fetched and converted to Base64.');
            return { mimeType, data: base64Data };
        } catch (error) {
            console.error('   ❌ Failed to fetch image:', error.message);
            return null;
        }
    };

    // Utility to upload base64 image data to Firebase Storage
    const uploadBase64Image = async (base64Data, mimetype, filename) => {
        const buffer = Buffer.from(base64Data, 'base64');
        const file = bucket.file(filename);

        console.log(`- Uploading image to storage: ${filename}`);
        await file.save(buffer, {
            metadata: { contentType: mimetype },
            public: true,
            validation: 'md5'
        });
        console.log('   ✅ Image uploaded successfully.');

        // Get the signed URL for the uploaded file
        const [url] = await file.getSignedUrl({
            action: 'read',
            expires: '03-09-2491' // A long-lasting URL for testing purposes
        });
        
        return url;
    };

    async function testImageWorkflow() {
        console.log(`\n--- Starting Image Workflow Test (No Deletion) ---`);
        
        // Step 1: Fetch the image from the provided URL
        const imageData = await fetchImageFromUrl(originalImageUrl);
        if (!imageData) {
            console.log('Test aborted: Could not fetch image.');
            return;
        }

        // Step 2: Re-upload the Base64 data with a new name
        const newFilename = `images/reuploaded_${uuidv4()}.png`;
        const newPublicUrl = await uploadBase64Image(imageData.data, imageData.mimeType, newFilename);
        
        // Step 3: Print the public URL for the newly uploaded file
        console.log('\n--- Test Complete ---');
        console.log('New Public URL:', newPublicUrl);
    }
    
    testImageWorkflow().catch(error => {
        console.error('\n--- Image Workflow Test Failed ---');
        console.error(error.message);
    });

} catch (error) {
    console.error("Failed to initialize or connect to Firebase:", error.message);
    process.exit(1);
} 