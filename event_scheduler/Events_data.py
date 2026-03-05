import pandas as pd
import requests
import time
from datetime import datetime
import re
import os
from dotenv import load_dotenv

load_dotenv()  # load values from .env
GOOGLE_API_KEY =  os.getenv("GOOGLE_API_KEY")

data=pd.read_csv(r"events.csv")
df=pd.DataFrame(data)

# Apply Closing Date to datetime
df["Apply Closing Date"] = pd.to_datetime(df["Apply Closing Date"], format="%d/%m/%Y %H:%M")
df["Closing Date"] = df["Apply Closing Date"].dt.date
df["Closing Time"] = df["Apply Closing Date"].dt.time

df[["Event Start Date", "Event End Date"]] = df["Event Date"].str.split(" to ", expand=True)

# converting both new columns to datetime
df["Event Start Date"] = pd.to_datetime(df["Event Start Date"], format="%d/%m/%Y")
df["Event End Date"] = pd.to_datetime(df["Event End Date"], format="%d/%m/%Y")

df=df.drop(["Event Date","Apply Closing Date"],axis=1)

def get_location(row):
    if row["Type of Event"] == "Dilli Haat":
        return "Delhi"
    elif "Gandhi Shilp Bazar" in row["Type of Event"]:
        details = str(row["Event Details"])
        # Match patterns like "GSB (State) at X" or "GSB (District)-X"
        match = re.search(r'at\s+(.+)', details, re.IGNORECASE)
        if match:
            return match.group(1).strip()
        match = re.search(r'-\s*(.+)', details)
        if match:
            return match.group(1).strip()
    return row.get("location", None)

# Apply function
df["location"] = df.apply(get_location, axis=1)

LOCATION_COLUMN = "location"         

def geocode_location(location):
    """Return (lat, lon) for a given location string using Google Geocoding API."""
    url = "https://maps.googleapis.com/maps/api/geocode/json"
    params = {"address": location, "key": GOOGLE_API_KEY}
    r = requests.get(url, params=params).json()
    if r['status'] == 'OK':
        loc = r['results'][0]['geometry']['location']
        return loc['lat'], loc['lng']
    return None, None


latitudes = []
longitudes = []

for i, loc in enumerate(df[LOCATION_COLUMN]):
    lat, lon = geocode_location(loc)
    latitudes.append(lat)
    longitudes.append(lon)
    
    # Optional: small delay to avoid hitting rate limits
    time.sleep(0.05)
    
    print(f"[{i+1}/{len(df)}] {loc} -> {lat}, {lon}")

df['latitude'] = latitudes
df['longitude'] = longitudes


df.to_csv("events_updated.csv", index=False)
