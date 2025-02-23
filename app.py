from flask import Flask, request, jsonify
from pandas import DataFrame
import pandas as pd
import os

app = Flask(__name__)
lat,long=0,0

crime_df = pd.read_csv("csv_2017_2022.csv", low_memory=False)

# Crime severity weighting
severity_weights = {
    "ASSAULT": 5, "ROBBERY": 4, "BURGLARY": 3, "LARCENY": 2, "TRESPASS": 2, "VANDALISM": 1, "WARRANT": 1
}
def assign_severity(description):
    description = description.upper()  # Convert to uppercase for case-insensitive matching
    for crime, weight in severity_weights.items():
        if crime in description:
            return weight
    return 1
crime_df["NEW_OFFENSE"]=crime_df["OFFENSE_DESCRIPTION"].apply(assign_severity)
def normalize_crime_score(crime_score, max_crime_score=2000):
    """
    Normalizes the crime score to a range of 0-100.
    """
    normalized_score = (crime_score / max_crime_score) * 100
    return min(normalized_score, 100)
def calculate_safety_score(street, district):
    """
    Given a street and district, calculates a safety score based on crime density and severity.
    """
    # Filter crimes based on user input
    filtered_crimes = crime_df[(crime_df["DISTRICT"] == district) & (crime_df["STREET"].str.contains(street, case=False, na=False))]
    lat,long=filtered_crimes["Lat"][0],filtered_crimes["Long"][0]
   
    # Calculate crime score using severity weights
    crime_score = sum(crime for crime in filtered_crimes["NEW_OFFENSE"])

    normalized_crime_score = normalize_crime_score(crime_score)
    safety_score = 100 - normalized_crime_score

    if 90 <= safety_score <= 100:
        safety_level = "🟢 Dark Green (Very Safe)"
    elif 80 <= safety_score < 90:
        safety_level = "🟢 Green (Safe)"
    elif 70 <= safety_score < 80:
        safety_level = "🟢 Light Green (Slightly Safe)"
    elif 60 <= safety_score < 70:
        safety_level = "🟡 Yellow-Green (Moderate Risk)"
    elif 50 <= safety_score < 60:
        safety_level = "🟡 Yellow (Low Risk)"
    elif 40 <= safety_score < 50:
        safety_level = "🟠 Light Orange (Unsafe)"
    elif 30 <= safety_score < 40:
        safety_level = "🟠 Orange (Risky)"
    elif 20 <= safety_score < 30:
        safety_level = "🔴 Light Red (Very Unsafe)"
    elif 10 <= safety_score < 20:
        safety_level = "🔴 Red (Dangerous)"
    else:
        safety_level = "🔴 Dark Red (Super Unsafe)"

    return safety_score, safety_level,lat,long
@app.route("/")
def home():
    return "Flask API is running!"
@app.route("/safety-index", methods=["GET"])
def get_safety_index():
    street = request.args.get("street")
    district = request.args.get("district")

    if not street or not district:
        return jsonify({"error": "Please provide both street and district"}), 400

    safety_score, safety_level,lat,long = calculate_safety_score(street, district)

    return jsonify({
        "street": street,
        "district": district,
        "safety_score": safety_score,
        "safety_level": safety_level,
        "latitude": lat,
        "longitude": long
    })

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))
    app.run(host="0.0.0.0", port=5000, debug=True)
