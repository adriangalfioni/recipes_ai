
https://github.com/user-attachments/assets/ecb97a28-8098-45c2-b0ca-561bd3238df2


# ChefLab AI 🍳

An intelligent Android application that leverages Generative AI to help users discover, create, and manage recipes based on available ingredients.

---

## 📲 Download
**Available now on the Google Play Store:** [**Get it on Google Play**](https://play.google.com/store/apps/details?id=agalfioni.recipesai&hl=en)

---

## 🚀 Technologies & Architecture

This project is built with a modern Android tech stack, focusing on performance, modularity, and cutting-edge AI integration.

### Core Stack
* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** **Jetpack Compose** (100% declarative UI with Material 3)
* **Architecture:** Clean Architecture + MVI. Adopting a "Single Source of Truth" pattern in ViewModels for state consistency and predictable UI updates.
* **Dependency Injection:** **Koin**
* **Image Loading:** **Coil 3**

### AI Integration
* **Google Gemini AI:** Integrated via the **Firebase AI Logic SDK**.
* **Generative Models:** Utilizing `gemini-2.0-flash` for high-speed, low-latency recipe generation and ingredient analysis.

### Data & Persistence
* **Room Database:** Robust local persistence for saved recipes, ingredients, and offline support.
* **Paging 3:** Efficient loading and caching of large recipe collections with seamless `LazyRow`/`LazyColumn` integration.
* **WorkManager:** Background processing for syncing AI-generated content and long-running data maintenance tasks.

### Android Jetpack & Tools
* **Navigation 3:** Exploring the latest experimental Navigation-Compose integration for type-safe routing.
* **Lifecycle & ViewModels:** Modern state management using `StateFlow` and `lifecycle-viewmodel-navigation3`.
* **Coroutines & Flow:** Asynchronous programming and reactive data streams (utilizing `combine` to merge multiple state sources).

### Testing & Quality
* **Unit Testing:** Comprehensive business logic verification using **JUnit 5** and **MockK**.
* **Turbine:** Specialized testing for **Kotlin Flows**, ensuring reactive state transitions and emissions are verified accurately.

---

## ✨ Key Features

- **AI Ingredient Detection (Vision):** Extract ingredients directly from fridge or pantry photos using Google's AI Vision capabilities.
- **AI Recipe Generation:** Transform a list of ingredients into detailed culinary instructions using the latest Generative AI.
- **Efficient Browsing:** Smooth, paginated list of recipes powered by Paging 3 to ensure a low memory footprint even with large datasets.
- **Offline Reliability:** Access and manage your favorite recipes without an internet connection via Room persistence.
- **Background Operations:** WorkManager handles background tasks ensuring data consistency without interrupting the user experience.

---

## 🛠️ Project Structure

The project follows **Clean Architecture** principles applying a feature-layered architecture to ensure scalability:

- **`:domain`**: Pure Kotlin module containing Business Logic, Entities, and Repository Interfaces.
- **`:data`**: Implementation of repositories, Room DAOs, DataSources (Gemini, Local JSON), and API Models.
- **`:presentation`**: UI components (Compose), ViewModels, and MVI state management.
- **`:core`**: Common utilities, theme definitions, and shared components used across the app.

---

## 🧪 Testing Approach

- **State Verification:** Utilizing **Turbine** to assert that the `uiState` transitions correctly in response to user intents.
- **Repository Testing:** Mocking data sources with **MockK** or using Fakes to isolate business logic.
- **Migration Testing:** Automated Room migration tests to ensure data integrity across schema versions.
