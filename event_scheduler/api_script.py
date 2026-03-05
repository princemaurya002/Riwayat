from flask import Flask, request, render_template_string, jsonify
import pandas as pd
import numpy as np

app = Flask(__name__)

df = pd.read_csv("geocoded.csv")

# HTML page
HTML_PAGE = """
<!DOCTYPE html>
<html>
<head>
    <title>Get My Location</title>
</head>
<body>
    <h2>API </h2>
    <p id="status">Waiting for permission...</p>
    <script>
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    fetch('/send_location', {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify({
                            lat: position.coords.latitude,
                            lon: position.coords.longitude
                        })
                    }).then(response => response.json())
                      .then(data => {
                          let html = `Your location:<br>Latitude: ${data.lat}<br>Longitude: ${data.lon}<br><br>`;
                          html += "<h3>Nearest 5 Locations:</h3><ul>";
                          data.nearest.forEach(loc => {
                              html += "<li>";
                              html += `Type of Event: ${loc['Type of Event']}<br>`;
                              html += `Event Details: ${loc['Event Details']}<br>`;
                              html += `Event Date: ${loc['Event Date']}<br>`;
                              html += `Apply Closing Date: ${loc['Apply Closing Date']}<br>`;
                              html += `Distance: ${loc.distance_km.toFixed(2)} km`;
                              html += "</li><br>";
                          });
                          html += "</ul>";
                          document.getElementById("status").innerHTML = html;
                      });
                },
                function (error) {
                    document.getElementById("status").innerHTML =
                        "Could not get location. Please allow access.";
                }
            );
        } else {
            document.getElementById("status").innerHTML = "Geolocation not supported in this browser.";
        }
    </script>
</body>
</html>
"""

# Haversine distance formula (vectorized)
def haversine_vectorized(lat1, lon1, lats, lons):
    R = 6371.0  # km
    lat1, lon1, lats, lons = map(np.radians, [lat1, lon1, lats, lons])
    dlat = lats - lat1
    dlon = lons - lon1
    a = np.sin(dlat/2)**2 + np.cos(lat1) * np.cos(lats) * np.sin(dlon/2)**2
    c = 2 * np.arctan2(np.sqrt(a), np.sqrt(1 - a))
    return R * c

@app.route('/')
def index():
    return render_template_string(HTML_PAGE)

@app.route('/send_location', methods=['POST'])
def receive_location():
    data = request.get_json()
    user_lat = data["lat"]
    user_lon = data["lon"]

    # Vectorized distance calculation
    distances = haversine_vectorized(
        user_lat, user_lon,
        df['latitude'].values, df['longitude'].values
    )
    df['distance_km'] = distances

    # Select top n nearest
    nearest = df.nsmallest(10, 'distance_km')

    # Only keep first 4 columns + distance
    nearest_result = nearest.iloc[:, :4].copy()
    nearest_result['distance_km'] = nearest['distance_km']

    return jsonify({
        "lat": user_lat,
        "lon": user_lon,
        "nearest": nearest_result.to_dict(orient='records')
    })

def parse_datetime(col, date_only=False):
    """Try multiple formats safely"""
    dt = pd.to_datetime(col, dayfirst=True, errors="coerce")
    if dt.isna().any():  # try ISO fallback
        dt = pd.to_datetime(col, errors="coerce")
    return dt 

df["Apply Closing Date"] = parse_datetime(df["Apply Closing Date"])
df["Event Start Date"] = parse_datetime(df["Event Start Date"], date_only=True)
df["Event End Date"] = parse_datetime(df["Event End Date"], date_only=True) 


@app.route("/events", methods=["GET"])
def get_events():
    filtered_df = df.copy()

    # Filter by Type of Event
    type_of_event = request.args.get("type")
    if type_of_event:
        filtered_df = filtered_df[filtered_df["Type of Event"].str.contains(type_of_event, case=False, na=False)]

    # Filter by Event Date range
    start_date = request.args.get("start_date")  # format: YYYY-MM-DD
    end_date = request.args.get("end_date")
    if start_date:
        filtered_df = filtered_df[filtered_df["Event Start Date"] >= pd.to_datetime(start_date)]
    if end_date:
        filtered_df = filtered_df[filtered_df["Event End Date"] <= pd.to_datetime(end_date)]

    # Filter by Closing Date
    closing_after = request.args.get("closing_after")
    closing_before = request.args.get("closing_before")
    if closing_after:
        filtered_df = filtered_df[filtered_df["Apply Closing Date"] >= pd.to_datetime(closing_after)]
    if closing_before:
        filtered_df = filtered_df[filtered_df["Apply Closing Date"] <= pd.to_datetime(closing_before)]


    for col in ["Apply Closing Date", "Event Start Date", "Event End Date"]:
        if col in filtered_df.columns:
            filtered_df[col] = filtered_df[col].dt.strftime("%d/%m/%Y %H:%M").fillna("")

    return jsonify(filtered_df.to_dict(orient="records"))
    

@app.route("/events/type/<event_type>", methods=["GET"])
def get_events_by_type(event_type):
    filtered_df = df[df["Type of Event"].str.contains(event_type, case=False, na=False)].copy()

    for col in ["Apply Closing Date", "Event Start Date", "Event End Date"]:
        if col in filtered_df.columns:
            if col == "Apply Closing Date":
                filtered_df[col] = filtered_df[col].dt.strftime("%d/%m/%Y %H:%M").fillna("")
            else:
                filtered_df[col] = filtered_df[col].dt.strftime("%d/%m/%Y").fillna("")

    return jsonify(filtered_df.to_dict(orient="records"))

@app.route("/events/closing", methods=["GET"])
def get_events_by_closing():
    closing_after = request.args.get("after")   # YYYY-MM-DD
    closing_before = request.args.get("before")

    filtered_df = df.copy()

    if closing_after:
        filtered_df = filtered_df[filtered_df["Apply Closing Date"] >= pd.to_datetime(closing_after)]
    if closing_before:
        filtered_df = filtered_df[filtered_df["Apply Closing Date"] <= pd.to_datetime(closing_before)]

    for col in ["Apply Closing Date", "Event Start Date", "Event End Date"]:
        if col in filtered_df.columns:
            if col == "Apply Closing Date":
                filtered_df[col] = filtered_df[col].dt.strftime("%d/%m/%Y %H:%M").fillna("")
            else:
                filtered_df[col] = filtered_df[col].dt.strftime("%d/%m/%Y").fillna("")

    return jsonify(filtered_df.to_dict(orient="records"))
    #http://127.0.0.1:5000/events/closing?after=2025-10-01&before=2025-11-15


if __name__ == '__main__':
    app.run(debug=True)
