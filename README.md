# 🌤 WeatherTracker App

This is a simple weather tracking app developed for the Android Internship 2025 at Instabug. The app provides:

- 📍 Current location-based weather
- 🗓 5-day weather forecast
- 🔄 refresh support
- 📴 Offline support with cached data
- ⚠️ Error handling with user-friendly messages

---

## 🚀 Features

| Feature | Status |
|--------|--------|
| Get user location via GPS | ✅ Done |
| Fetch current weather | ✅ Done |
| Fetch 5-day forecast | ✅ Done |
| Refresh data manually | ✅ Done |
| Handle offline mode | ✅ Done |
| Cache last weather data | ✅ Done |

---

## 💡 Tech Used

- Android SDK (No third-party libraries)
- HttpURLConnection for networking
- SharedPreferences for caching
- Jetpack Compose UI
- Manual permissions handling
- ViewModel

---

## 🔧 Setup

1. Clone the repository.
2. Generate an API key from [WeatherAPI](https://www.visualcrossing.com/).
3. Add your API key.
4. Run the app on a physical or virtual device with location enabled.

---

## 📁 Project Structure

- ui/ - Composable UI Screens
- viewmodel/ - ViewModel logic
- model/ - Data models
- network/ - HTTP and parsing logic
- cache/ - SharedPreferences helpers
- utils/ - Permissions, location, network checks

---

## 📸 ## Screenshots

| Current Weather                                     | Forecast                                            |
|-----------------------------------------------------|-----------------------------------------------------|
| <img src="screenshots/screenshot1.jpg" width="300"> | <img src="screenshots/screenshot2.jpg" width="300"> |

## 📬 Contact

For inquiries or questions, contact: habiba.abdeldayem.dev@gmail.com

---

## 📌 Notes

✅ No third-party libraries were used as per task requirements.  
✅ Fully supports offline access using cached data.