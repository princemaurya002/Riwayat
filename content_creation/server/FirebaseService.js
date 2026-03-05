const admin = require('firebase-admin');

class FirebaseService {
    constructor(serviceAccount, bucketName) {
        if (!admin.apps.length) {
            admin.initializeApp({
                credential: admin.credential.cert(serviceAccount)
            });
        }
        this.db = admin.firestore();
        this.storage = admin.storage();
        this.bucket = this.storage.bucket(bucketName);
    }

    // Standardized data paths based on SCHEMA.md
    getUserRef(userId) { return this.db.collection('users').doc(userId); }
    getInventoryRef(artisanId) { return this.db.collection('inventory').doc(artisanId).collection('products'); }
    getMarketingRef(artisanId) { return this.db.collection('marketing').doc(artisanId).collection('ads'); }
    getLedgerRef(artisanId) { return this.db.collection('ledger').doc(artisanId).collection('transactions'); }

    async uploadImage(base64Data, mimetype, filename) {
        const buffer = Buffer.from(base64Data, 'base64');
        const file = this.bucket.file(filename);

        await file.save(buffer, {
            metadata: { contentType: mimetype },
            public: true
        });

        const [url] = await file.getSignedUrl({
            action: 'read',
            expires: '03-09-2491'
        });
        return url;
    }
}

module.exports = FirebaseService;
