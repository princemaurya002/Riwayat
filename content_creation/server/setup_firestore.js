const admin = require('firebase-admin');
const serviceAccount = require('./firebase-service-account.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function setupSchema() {
    console.log("🚀 Starting Riwayat Database Setup...");

    // 1. Setup a dummy user
    const userId = "artisan_001";
    await db.collection('users').doc(userId).set({
        name: "Ramesh Kumar",
        businessName: "Banarasi Silk Wonders",
        brandTone: "elegant",
        createdAt: admin.firestore.FieldValue.serverTimestamp()
    });
    console.log("✅ Users collection initialized.");

    // 2. Setup a dummy product
    await db.collection('inventory').doc(userId).collection('products').add({
        name: "Handwoven Silk Saree",
        description: "Pure mulberry silk with gold zari work.",
        price: 15000,
        stock: 5,
        category: "Apparel"
    });
    console.log("✅ Inventory collection initialized.");

    // 3. Setup community marketplace
    await db.collection('community').doc('marketplace').collection('items').add({
        name: "Natural Indigo Dye",
        price: "₹1,200/liter",
        source: "Tamil Nadu",
        supplierContact: "+91-9876543210"
    });
    console.log("✅ Community Marketplace initialized.");

    console.log("🎉 Database Setup Complete!");
    process.exit();
}

setupSchema().catch(console.error);
