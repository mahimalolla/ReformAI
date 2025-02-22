# ReformAI – Recidivism Prediction & Safety by Area Index  
**"Predict. Prevent. Rebuild Lives."**  

ReformAI is an **AI-powered Android application** designed to help **law enforcement, NGOs, and policymakers** predict **recidivism risk** (likelihood of reoffending) and recommend **rehabilitation programs**. It also includes a **Safety by Area Index**, which allows users to check the safety levels of different locations based on crime data.

---

## 🚀 Features  
### 1️⃣ Recidivism Prediction  
- 📊 **Predicts the risk level (1-10) of a released offender reoffending.**  
- 🛠 **Uses crime history, employment status, drug use, and gang affiliation** to assess risk.  
- 📢 **Suggests rehabilitation programs** (Job placement, mental health therapy, or intensive rehab).  

### 2️⃣ Safety by Area Index  
- 🌍 **Displays safety levels for different areas using a color-coded system:**  
  - 🟢 **Safe Area (Green)**  
  - 🟠 **Unsafe Area (Orange)**  
  - 🔴 **Very Unsafe Area (Red)**  
- 🔍 **Uses real-time crime data (FBI API, Kaggle datasets, local police reports).**  
- 📍 **Users can enter a location to check crime density & severity.**  

### 3️⃣ Case History Tracking  
- 🗂️ **Stores past risk assessments & safety index checks.**  
- 📊 **Allows law enforcement & NGOs to track high-risk individuals.**  

---

## 📌 Tech Stack  
### 📱 Android App  
- **Language:** Java & XML  
- **Networking:** Retrofit for API calls  
- **UI Components:** RecyclerView, CardView, Google Maps API  

### 🖥️ Backend (Flask API)  
- **Machine Learning:** Scikit-Learn (Random Forest/XGBoost)  
- **Database:** PostgreSQL/MongoDB (For case history tracking)  
- **Crime Data Sources:** FBI API, Kaggle, OpenCrimeData  


---

## 📊 How It Works  
### 1️⃣ Recidivism Prediction  
1. User enters **offender details** (age, crime type, prior convictions, employment status, etc.).  
2. ReformAI **sends data to Flask API**.  
3. ML model **calculates risk score (1-10)**.  
4. ReformAI **suggests rehabilitation programs**.  

### 2️⃣ Safety by Area Index  
1. User **enters a location** (city, zip code).  
2. ReformAI **fetches crime data** from APIs (FBI, Kaggle).  
3. ReformAI **calculates safety score**:  
   ```
   Safety Score = 100 - (w1 × Crime Rate + w2 × Criminal Density + w3 × Crime Severity)
   ```
4. ReformAI **displays safety level** (Green, Orange, Red).  

---

## 🌍 API Endpoints  
| **Endpoint** | **Method** | **Description** |  
|-------------|-----------|----------------|  
| `/predict` | `POST` | Predicts recidivism risk based on user data. |  
| `/safety-index` | `GET` | Fetches safety score for a given area. |  

---

## 📌 Future Improvements  
✅ **Google Maps Integration** → Display crime heatmaps.  
✅ **Real-time Notifications** → Alert users if an area becomes unsafe.  
✅ **Bias Mitigation** → Ensure fairness in recidivism predictions.  

---

## 👨‍💻 Contributors  
- Mahima Lolla
- Siddharth Shukla
- Sahil Satramani
- Ujjwal Pandit
- Suresh Palaparthi

---

## 📩 Contact  
For any issues or contributions, please open a GitHub **issue** or email **lolla.m@northeastern.edu**.  

---

