import firebase_admin
from firebase_admin import credentials, firestore
import json
import time
import random

# --- CONFIGURATION ---
# Replace with the path to your service account key JSON file
SERVICE_ACCOUNT_KEY = '/home/shivam/Desktop/riyawat/server/firebase-service-account.json'

# Replace with the appId you are using in your API and client code
APP_ID = 'content-dev'

# --- INITIALIZATION ---
try:
    with open(SERVICE_ACCOUNT_KEY) as json_file:
        data = json.load(json_file)
        cred = credentials.Certificate(data)
    firebase_admin.initialize_app(cred)
    print("Firebase Admin SDK initialized successfully.")
except Exception as e:
    print(f"Error initializing Firebase: {e}")
    exit()

db = firestore.client()

# --- DUMMY DATA ---
def get_dummy_data():
    timestamp = int(time.time() * 1000)
    user_id = f"dummy_user_{timestamp}"
    project_id_1 = f"project_alpha_{timestamp}"
    project_id_2 = f"project_beta_{timestamp}"

    brand_profile = {
        'brandName': "Rustic Woodworks",
        'brandTone': "rustic, warm, earthy",
        'logoUrl': "https://firebasestorage.googleapis.com/v0/b/riwayat-be2a5.firebasestorage.app/o/images%2Fimages.jpeg?alt=media&token=64356155-23e6-4048-be9f-13fb3489eb16",
        'products': [
            {'name': 'Hand-carved Wooden Mug', 'description': 'A beautiful, ergonomic mug carved from a single piece of reclaimed oak.'},
            {'name': 'Walnut Cutting Board', 'description': 'A durable and elegant cutting board with a rich grain pattern.'}
        ]
    }

    project_history_1 = [
        {
            'prompt': '{"productDescription": "Hand-carved Wooden Mug", "contentType": "Social Media Caption", "targetPlatform": "Instagram"}',
            'response': 'The perfect companion for your morning coffee. ☕️ Each mug is hand-carved with passion and purpose. #Handmade #Woodworking #Artisan',
            'type': 'text-only'
        },
        {
            'prompt': '{"productDescription": "Hand-carved Wooden Mug", "adSize": "Facebook Post", "heading": "Crafted to Perfection"}',
            'response': '{"visual": "https://firebasestorage.googleapis.com/v0/b/riwayat-be2a5.firebasestorage.app/o/images%2FGemini_Generated_Image_j28rqjj28rqjj28r.png?alt=media&token=fd19b865-458a-4141-bccf-a8fba65cc6ca", "text": "Crafted to Perfection. Find your next favorite mug here."}',
            'type': 'visual+text'
        },
    ]

    project_history_2 = [
        {
            'prompt': '{"productDescription": "Walnut Cutting Board", "adSize": "Instagram Post", "additionalDetails": "Place on a white kitchen counter"}',
            'response': '{"visual": "https://firebasestorage.googleapis.com/v0/b/riwayat-be2a5.firebasestorage.app/o/images%2FGemini_Generated_Image_d5h5nxd5h5nxd5h5.png?alt=media&token=4dc35848-d562-4cec-a291-4057bc56740e", "text": "Elevate your kitchen with our stunning walnut cutting board. #KitchenGoals #Walnut"}',
            'type': 'visual+text'
        }
    ]

    return {
        'user_id': user_id,
        'brand_profile': brand_profile,
        'projects': [
            {'id': project_id_1, 'title': 'My First Ad Campaign', 'history': project_history_1},
            {'id': project_id_2, 'title': 'Cutting Board Promotion', 'history': project_history_2}
        ]
    }

# --- SCRIPT LOGIC ---
def add_data_to_firestore():
    data = get_dummy_data()
    user_id = data['user_id']
    
    print(f"\nAdding data for new dummy user: {user_id}")
    
    # Add dummy brand profile
    brand_profile_ref = db.collection(f"artifacts/{APP_ID}/users/{user_id}/metadata").document('brandProfile')
    brand_profile_ref.set(data['brand_profile'])
    print("   ✅ Brand profile added.")
    
    # Add dummy projects
    for project in data['projects']:
        project_id = project['id']
        project_data = {
            'title': project['title'],
            'history': project['history'],
            'createdAt': firestore.firestore.SERVER_TIMESTAMP,
            'updatedAt': firestore.firestore.SERVER_TIMESTAMP
        }
        project_ref = db.collection(f"artifacts/{APP_ID}/users/{user_id}/projects").document(project_id)
        project_ref.set(project_data)
        print(f"   ✅ Project '{project['title']}' added with ID: {project_id}")
    
    print("\nDummy data setup complete!")
    print(f"You can now use the following user ID for testing: {user_id}")
    
if __name__ == '__main__':
    add_data_to_firestore()
