# RecipesAI 🍳

An intelligent Android application that leverages Generative AI to help users discover, create, and manage recipes based on available ingredients.

---

## 🚀 Technologies & Architecture

This project is built with a modern Android tech stack, focusing on performance, modularity, and cutting-edge AI integration.

### Core Stack
* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** **Jetpack Compose** (100% declarative UI with Material 3)
* **Architecture:** Clean Architecture + MVI
* **Dependency Injection:** **Koin**
* **Networking:** **Ktor Client** with OkHttp engine and Content Negotiation for JSON.
* **Image Loading:** **Coil 3**

### AI Integration
* **Google Gemini AI:** Integrated via the **Firebase AI SDK**.
* **Generative Models:** Utilizing `gemini-1.5-flash` for high-speed, low-latency recipe generation and ingredient analysis.

### Android Jetpack & Tools
* **Navigation 3:** Exploring the latest experimental Navigation-Compose integration.
* **Lifecycle & ViewModels:** Modern state management with `lifecycle-runtime-ktx` and `lifecycle-viewmodel-navigation3`.
* **Coroutines & Flow:** Asynchronous programming and reactive data streams.
* **Firebase:** Centralized management of Firebase dependencies (Generative AI, Analytics, etc.).

---

## ✨ Key Features

- **AI Ingredients Detection from photo:** Obtain ingredients using AI Vision feature from a frige photo.
- **AI Recipe Generation:** Transforms a list of ingredients into detailed culinary instructions using Google's most capable generative models.
- **Modern UI:** Fully responsive design using Material Design 3 tokens and smooth Compose animations.

---

## 🛠️ Project Structure

The project follows **Clean Architecture** principles to ensure separation of concerns aplying feature-layer architecture:

- **`:app`**: Main Android module.
- **`:domain`**: Pure Kotlin module containing Business Logic, Entities, and Repository Interfaces.
- **`:data`**: Implementation of repositories, DataSources (Gemini, Local JSON), and API Models.
- **`:presentation`**: UI components, ViewModels, and State management using Compose.
