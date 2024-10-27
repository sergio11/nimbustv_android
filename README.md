# NimbusTV 🌐📺: Your Gateway to Effortless M3U Streaming on Android TV

<img width="auto" height="200px" align="left" src="doc/main_logo.png" />

Welcome to **NimbusTV** 🎉, your all-in-one solution for experiencing M3U playlist content on Android TV without the usual hassle. Designed with simplicity and power in mind, NimbusTV lets you forget about complicated setups and file management, making it easy to dive straight into your favorite channels 📺✨.

With NimbusTV, you can effortlessly manage multiple M3U playlists 📂, giving you the freedom to organize your channels by theme—sports 🏆, movies 🎬, news 📰, music 🎶, and more. Switch seamlessly between playlists and enjoy diverse formats with support for HLS 📡, DASH 🎥, Clear Key-encrypted channels 🔐, and even radio streams 📻.

NimbusTV doesn’t stop at playlists. Bringing in EPGs 📅 for each of your channels, it keeps you informed with real-time programming schedules. Imagine instantly knowing what’s on, what’s up next, and never having to wonder if you've missed a must-watch show 🕒. Set custom reminders 🔔 for your favorite programs, so you’ll always be in the know when the action begins!

Crafted with **Jetpack Compose for TV** 🖥️, NimbusTV shines on the big screen, delivering a smooth, elegant experience for Android TV. Built on the reliable **Clean Architecture** 🧩 and **MVI** pattern foundations, it’s designed to be fast, scalable, and visually immersive. Thanks to the [**🍮 Fudge**](https://github.com/sergio11/fudge_tv_compose_library) UI Kit, NimbusTV combines a sleek design with easy navigation, putting total control in your hands 🎛️.

A big thank you to the [JetFit](https://github.com/TheChance101/tv-samples/tree/JetFit/JetFit) repository for inspiring NimbusTV with a solid base for Jetpack Compose for TV 🛠️.

Explore NimbusTV today, and bring a world of channels right to your living room—effortless, organized, and always ready when you are. 🌐✨📺

## Technologies Used 🛠️

NimbusTV harnesses a variety of technologies to deliver a seamless streaming experience on Android TV. 🌟

- **Kotlin** 🦺: The preferred language for developing Android applications, offering modern syntax and powerful features to enhance productivity. 🚀

- **Room** 🗄️: Implements a local database schema to persist information about M3U playlists, channels, EPG data, and program entries linked to each user profile. Each user profile in NimbusTV maintains its own independent playlists and EPGs, ensuring personalized content management. 🔐📂

- **Jetpack DataStore** 💾: A modern data storage solution used to store application preferences shared across profiles. It allows users to enable different EPG viewing modes, such as "Channel Overview" or "Now and Schedule," and activate channel search options. 🔍✨

- **Coil** 🖼️: An image loading library for Android that simplifies the process of loading images from the internet, ensuring smooth and efficient image handling in the app. 📸🌐

- **WorkManager** 🕰️: Used to schedule background tasks, such as updating EPG data daily and sending reminders for upcoming shows, ensuring that users never miss their favorite content. 📅⏰

- **Coroutines** 🌀: Simplifies asynchronous programming and efficiently manages background tasks, enhancing app responsiveness. ⏱️⚡

- **Clean Architecture** 🏗️: Promotes a well-structured and scalable app design by separating concerns into distinct layers, enhancing maintainability and testability. 🔍

- **MVI (Model-View-Intent)** 📈: Implements a unidirectional data flow pattern, ensuring a clear separation between UI components and business logic. 🔄

- **Jetpack Compose for TV** 📺: Utilizes Jetpack Compose to build modern, responsive UIs tailored for TV screens, optimizing the interface for large displays. 🎨

- **Jetpack Compose Navigation** 🗺️: Facilitates in-app navigation and screen transitions with a clear API, supporting deep linking and complex navigation flows effortlessly. 🚦

- **Material Design 3** 🎨: Applies the latest Material Design guidelines to create a visually appealing and intuitive user interface. 🖌️

- **🍮 Fudge** 🍰: Fudge is a Jetpack Compose UI Kit for TV apps, providing pre-built components and tools to craft engaging experiences on the big screen. 🎬🚀

- **Media3 for Media Playback** 🎥: 
  - **Media3 ExoPlayer** 🎬: Part of the Media3 library, ExoPlayer supports various media formats, providing advanced features for high-quality playback. 📻🍿
    - **HLS (HTTP Live Streaming)** 📺: This plugin allows the playback of HLS streams, enabling adaptive streaming for different network conditions. It dynamically adjusts the quality of the video based on the user's internet speed, ensuring smooth playback with minimal buffering. 🎥🌐
    - **DASH (Dynamic Adaptive Streaming over HTTP)** 📡: This plugin enables the playback of DASH streams, another adaptive streaming format. Similar to HLS, DASH adjusts video quality in real-time, providing a seamless viewing experience by optimizing bandwidth usage and improving playback performance. 📺⚡
  - **Media3 UI** 🎨: Provides UI components for integrating media playback controls into your app’s interface. 🕹️🎶

- **Dagger Hilt** 🧩: A dependency injection library simplifying the management of dependencies and enhancing modularity in your app. 🔧💡

- **Mapper Pattern** 🔄: Facilitates conversion between different data models, ensuring data consistency across application components. 📐

## Architecture Overview 🏛️

NimbusTV is designed with a robust architecture for maintainability, testability, and flexibility. The architecture leverages several design patterns and principles:

**Clean Architecture**  
Clean Architecture focuses on separating concerns into distinct layers:
- **Presentation Layer**: Handles UI and user interactions using Jetpack Compose for modern interfaces. 🖥️✨
- **Domain Layer**: Contains business logic and use cases, independent of external frameworks. 🧠🔗
- **Data Layer**: Manages data sources and repositories, abstracting data retrieval and storage. 📦🔒

**Data Sources**  
Data sources fetch and manage data from various origins, including:
- **Remote Data Sources** 🌐: Interact with cloud services or web APIs (e.g., Firebase Firestore). ☁️🔄
- **Local Data Sources** 💻: Handle local data storage (e.g., Room and Jetpack DataStore). 💾🗃️

**Repository Pattern**  
The repository pattern provides a unified interface for data access, decoupling data retrieval from the rest of the application for easier testing and maintenance. 📚⚙️

**Use Cases**  
In the Domain Layer, Use Cases represent specific actions, encapsulating business logic and interacting with repositories to retrieve or modify data. 🧩🔍

**Inversion of Control (IoC)** 🔄  
IoC inverts control flow, allowing dependencies to be injected rather than hardcoded, promoting modularity and reducing boilerplate code. 🔧🔄

**SOLID Principles** 📏  
We apply SOLID principles to ensure our codebase remains clean and maintainable:
- **Single Responsibility Principle (SRP)** ✅: Each class has one responsibility. 
- **Open/Closed Principle (OCP)** 🔓: Classes are open for extension but closed for modification. 
- **Liskov Substitution Principle (LSP)** 🔄: Subtypes must be substitutable for their base types. 
- **Interface Segregation Principle (ISP)** 🚫: Clients should not depend on interfaces they do not use. 
- **Dependency Inversion Principle (DIP)** 📏: High-level modules depend on abstractions.

**MVI (Model-View-Intent)** 📈  
MVI manages state and interactions, ensuring a predictable unidirectional data flow. 🔄📊

This architecture ensures that NimbusTV is well-structured, easy to maintain, and scalable, adhering to best practices and design principles. 🌈✨

