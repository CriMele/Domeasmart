#  Domeasmart (Work in Progress)

**Domeasmart** is an Android application for monitoring and controlling a smart home environment.

The application communicates with a custom **FastAPI backend** through REST APIs and provides a mobile interface for monitoring sensors, rooms, doors and other connected devices.
It also features an integrated video monitoring system through cameras implemented within the FastAPI server.
The project was developed using **Kotlin** and **Jetpack Compose**, following a modular architecture based on MVVM, Repository Pattern and Dependency Injection.

---

##  Features

*  Smart home dashboard
*  Environmental sensor monitoring
*  Door status monitoring
*  Live camera streaming
*  PIR / motion sensor monitoring
*  Sensor data visualization
*  Room management ( In progress )
*  Sensor management ( In progress )
*  Additional smart-home features (In progress )
*  Camera interface
*  Application settings
*  Local settings persistence
*  Communication with a REST API

---

##  Technologies

### Android

* **Kotlin**
* **Jetpack Compose**
* **Android SDK**
* **MVVM**
* **Repository Pattern**
* **Retrofit**
* **Kotlin Coroutines**
* **DataStore**
* **Dependency Injection**

### Backend

The Android application communicates with a custom backend developed with:

* **Python**
* **FastAPI**
* **REST API**

The backend is hosted separately from the Android application.

---

## Architecture

The application follows a layered architecture designed to separate the UI, business logic and data access layers.

```text
┌───────────────────────────────┐
│          Jetpack Compose      │
│             UI                │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│           ViewModels          │
│        UI / State Logic       │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│          Repositories         │
│       Data Management         │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│        Retrofit / API         │
│          ApiService           │
└───────────────┬───────────────┘
                │
                │ HTTP / REST
                ▼
┌───────────────────────────────┐
│        FastAPI Backend        │
└───────────────────────────────┘
```

### Project structure

```text

├── MainActivity.kt
├── DomeasmartApplication.kt
│
├── data/
│   ├── remote/
│   │   ├── ApiService.kt
│   │   └── dto/
│   │       ├── DoorStateDto.kt
│   │       ├── GenericResponseDto.kt
│   │       ├── PirStateDto.kt
│   │       ├── SensorDataDto.kt
│   │       ├── SensorUpdateDto.kt
│   │       ├── ServerStatDto.kt
│   │       ├── StanzaDto.kt
│   │       └── ValoreSensoreDto.kt
│   │
│   └── repository/
│       ├── DomeasmartRepo.kt
│       ├── DomeasmartRepoImpl.kt
│       └── SettingsRepo.kt
│
├── di/
│   ├── DataStoreModule.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
│
├── ui/
│   ├── navigation/
│   │   └── NavGraph.kt
│   │
│   ├── screens/
│   │   ├── CameraScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── SensoriScreen.kt
│   │   ├── SettingsScreen.kt
│   │   └── StanzeScreen.kt
│   │
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   │
│   └── viewmodel/
│       ├── CameraViewModel.kt
│       ├── HomeViewModel.kt
│       ├── SensoriViewModel.kt
│       ├── SettingsViewModel.kt
│       └── StanzeViewModel.kt
│
└── utils/
    └── DataUtils.kt

```

---

##  API Configuration

The application does not contain a fixed backend address.

The API endpoint can be configured by the user through the application settings.

This allows the Android application to connect to different instances of the Domeasmart backend without modifying the source code.

Example:

```text
http://<server-address>:<port>
```

> **Note:** No production server credentials, API keys or private infrastructure information are included in this repository.

---

##  Screenshots

Screenshots of the application can be added here.

### Home

![Home Screen](screenshots/home.png)

### Sensors

![Sensors Screen](screenshots/sensors.png)

### Rooms

![Rooms Screen](screenshots/rooms.png)

### Settings

![Settings Screen](screenshots/settings.png)

---

## 🔒 Security & Privacy

This repository contains the Android client source code.

No passwords, API keys or private server credentials are stored in the source code.

Local configuration files and generated Android/Gradle files are excluded through `.gitignore`.
---

##  Project Goals

The main goals of Domeasmart are:

* develop a modern Android application using Kotlin and Jetpack Compose;
* communicate with a custom REST backend;
* implement a clean separation between UI, business logic and data access;
* manage application state using ViewModels;
* persist local settings using Android DataStore;
* provide a flexible interface for monitoring a smart home environment.

---

## Roadmap
* Complete room management
* Complete sensor management
* Add further automation features
* Improve UI/UX
* Testing and performance improvements

--- 
## License

Copyright © 2026 CriMele.

This project is provided for portfolio and educational purposes.
The source code may be viewed for reference, but may not be copied,
modified, redistributed, or used commercially without permission.
---

## Author

**Cristian Mele**

Full Stack Developer

[GitHub](https://github.com/CriMele)
