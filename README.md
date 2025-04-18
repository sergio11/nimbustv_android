 # NimbusTV 📺🌐 – A Personal Learning Project for Android TV Development

<img width="auto" height="300px" align="left" src="doc/nimbustv_logo.webp" />

**NimbusTV** is a personal project I developed to apply and enhance my skills in **Android TV development** 📺, **Jetpack Compose for TV** 🖥️, **Clean Architecture** 🏛️, and **MVI** 🔄. The goal was to build an efficient, user-friendly streaming app that simplifies channel management and gives users easy access to their favorite TV content.

This project allowed me to dive deep into Android TV design patterns, exploring how to create smooth, intuitive user interfaces and ensuring scalability for larger applications. The app supports a variety of content types 📂, seamlessly allowing users to organize and switch between categories—such as sports 🏆, movies 🎬, news 📰, and music 🎶—while handling modern streaming formats like HLS 📡, DASH 🎥, and encrypted channels 🔐.

I also incorporated **EPG (Electronic Program Guides)** 📅 to provide real-time schedules, allowing users to plan their viewing experience and set **custom reminders** 🔔 for their favorite shows.

Through this project, I further honed my knowledge of **Jetpack Compose** and how to create a responsive, visually appealing interface for Android TV. **NimbusTV** leverages my understanding of **Clean Architecture** and **MVI** to ensure that the app is not only functional but also well-structured, making future improvements and scaling easier.

Working on **NimbusTV** has been a great way for me to deepen my understanding of Android TV development and design best practices, and I'm proud to showcase this project as a way of documenting my learning journey.

Crafted with **Jetpack Compose for TV** 🖥️, NimbusTV shines on the big screen, delivering a smooth, elegant experience for Android TV. Built on the reliable **Clean Architecture** 🧩 and **MVI** pattern foundations, it’s designed to be fast, scalable, and visually immersive. Thanks to the [**🍮 Fudge**](https://github.com/sergio11/fudge_tv_compose_library) UI Kit, NimbusTV combines a sleek design with easy navigation, putting total control in your hands 🎛️.

A huge thank you to the creators of **[JetFit](https://github.com/TheChance101/tv-samples/tree/JetFit/JetFit)** and the contributors to **[PR #183](https://github.com/android/tv-samples/pull/183)** for providing an invaluable foundation for **NimbusTV**. This is an attempt to provide an open-source implementation of JetFit. Licensed under **Apache 2.0** (implementation) and **CC BY 4.0** (design), JetFit served as a key reference for building a seamless **Jetpack Compose for TV** experience. We encourage you to visit the PR, review the effort, and give the deserved recognition. For more details and attributions, please check the **"Credits"** section below. 🚀

To handle M3U playlist parsing, I’ve integrated and customized the [M3u parser](https://github.com/BjoernPetersen/m3u-parser) library. Modifications were made to extend the functionality, allowing NimbusTV to support even more complex M3U structures, while accommodating the varied and sometimes inconsistent formats found in IPTV playlists. A copy of the original license can be found in the [`LICENSE-M3UPARSER`](LICENSE-M3UPARSER) file.

**NimbusTV is for educational purposes only 🎓**, and I am not responsible for the use or content viewed 🚫.  Usage of any media content must comply with local laws and regulations. This project does not endorse the use of unauthorized streams.

Please review the full **Disclaimer** section below for more information 📜.

This app, **Nimbustv**, includes images and resources designed by [Freepik](https://www.freepik.com). We would like to acknowledge and thank Freepik for their incredible design assets. The images used in the app are provided with attribution, as required by Freepik's licensing terms. For more information on Freepik's resources, please visit [www.freepik.com](https://www.freepik.com).

<p align="center">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white" />
</p>

<p align="center">
  <img width="400px" src="doc/screenshots/previewed/image1.jpeg" />
  <img width="400px" src="doc/screenshots/previewed/image2.jpeg" />
  <img width="400px" src="doc/screenshots/previewed/image3.jpeg" />
  <img width="400px" src="doc/screenshots/previewed/image4.jpeg" />
  <img width="400px" src="doc/screenshots/previewed/image5.jpeg" />
</p>

Slides are built using the  template from [Previewed](https://previewed.app/template/AFC0B4CB). I extend my gratitude to them for their remarkable work and contribution.

## ⚠️ Disclaimer  

For demonstration and testing purposes, **NimbusTV** utilizes publicly available IPTV playlists from the [IPTV-Org](https://iptv-org.github.io/) project. These playlists offer a diverse collection of international channels in M3U format, enabling users to explore and experience a wide range of content directly from the app.  

To handle M3U playlist parsing, we’ve integrated and customized the [M3u parser](https://github.com/BjoernPetersen/m3u-parser) library. Modifications were made to extend the functionality, allowing **NimbusTV** to support even more complex M3U structures while accommodating the varied and sometimes inconsistent formats found in IPTV playlists.  

The name **NimbusTV** was generated using **ChatGPT** as part of a creative brainstorming process. Any resemblance to existing brands, products, or services is purely coincidental.  

The **NimbusTV** logo was also generated with **ChatGPT** and is entirely free of copyright restrictions. It may be used freely for personal and open-source projects without any attribution requirements.  


**NimbusTV** does not host, distribute, or promote any copyrighted or illegal content. The user is solely responsible for the playlists they load and how the app is used. This project is for educational purposes only.

## Overview 🌐

NimbusTV is a personal project I created to improve my skills in Android TV development. It aims to offer an intuitive and seamless way for users to enjoy content on their Android TVs, with a focus on simplicity and ease of use. Through this project, I explored features like M3U playlist management, customizable reminders, and EPG integration, all while learning to implement best practices in UI design and app architecture.

<p align="center">
  <img src="doc/screenshots/picture_57.gif" />
</p>

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

<p align="center">
  <img src="doc/screenshots/picture_30.png" />
</p>

## Architecture Overview 🏛️

NimbusTV is designed with a robust architecture for maintainability, testability, and flexibility. The architecture leverages several design patterns and principles:

### **Clean Architecture**  
Clean Architecture focuses on separating concerns into distinct layers:
- **Presentation Layer**: Handles UI and user interactions using Jetpack Compose for modern interfaces. 🖥️✨
- **Domain Layer**: Contains business logic and use cases, independent of external frameworks. 🧠🔗
- **Data Layer**: Manages data sources and repositories, abstracting data retrieval and storage. 📦🔒

### **Data Sources**  
Data sources fetch and manage data from various origins, including:
- **Remote Data Sources** 🌐: Interact with cloud services or web APIs (e.g., Firebase Firestore). ☁️🔄
- **Local Data Sources** 💻: Handle local data storage (e.g., Room and Jetpack DataStore). 💾🗃️

### **Repository Pattern**  
The repository pattern provides a unified interface for data access, decoupling data retrieval from the rest of the application for easier testing and maintenance. 📚⚙️

### **Use Cases**  
In the Domain Layer, Use Cases represent specific actions, encapsulating business logic and interacting with repositories to retrieve or modify data. 🧩🔍

### **Inversion of Control (IoC)** 🔄  
IoC inverts control flow, allowing dependencies to be injected rather than hardcoded, promoting modularity and reducing boilerplate code. 🔧🔄

### **SOLID Principles** 📏  
We apply SOLID principles to ensure our codebase remains clean and maintainable:
- **Single Responsibility Principle (SRP)** ✅: Each class has one responsibility. 
- **Open/Closed Principle (OCP)** 🔓: Classes are open for extension but closed for modification. 
- **Liskov Substitution Principle (LSP)** 🔄: Subtypes must be substitutable for their base types. 
- **Interface Segregation Principle (ISP)** 🚫: Clients should not depend on interfaces they do not use. 
- **Dependency Inversion Principle (DIP)** 📏: High-level modules depend on abstractions.

### **MVI (Model-View-Intent)** 📈  
MVI manages state and interactions, ensuring a predictable unidirectional data flow. 🔄📊

This architecture ensures that NimbusTV is well-structured, easy to maintain, and scalable, adhering to best practices and design principles. 🌈✨

## App Screenshots 📸

Explore **NimbusTV** and its user-friendly design through these screenshots, offering a sneak peek into the app's key features!

### Onboarding and Getting Started 🎬

The journey with **NimbusTV** begins by creating a profile. This simple step allows you to personalize your experience by organizing your favorite EPGs and playlists. There's no need for online accounts or logins—everything is handled locally, ensuring a smooth and self-contained experience. I built this feature to learn how to structure and manage app data efficiently while focusing on a seamless user experience.

<p align="center">
  <img src="doc/screenshots/picture_1.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_2.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_43.png" />
</p>

### Managing Your Profiles 👤

Step into the **Profiles** section, where you can customize **NimbusTV** to suit your viewing preferences. Here’s what makes the Profiles section so versatile:

<p align="center">
  <img src="doc/screenshots/picture_44.png" />
</p>

- **Profile Selection**: Choose between different profiles in the **Profile Selection** screen. Each profile allows for custom EPGs and playlists, ensuring a unique setup for each viewer.

<p align="center">
  <img src="doc/screenshots/picture_21.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_58.gif" />
</p>

- **Creating Profiles**: Add up to four profiles, perfect for household members who want their own playlists and EPGs. Customize each with a unique name and avatar, making NimbusTV adaptable and fun for everyone!

<p align="center">
  <img src="doc/screenshots/picture_22.png" />
</p>  

<p align="center">
  <img src="doc/screenshots/picture_24.png" />
</p>  

<p align="center">
  <img src="doc/screenshots/picture_25.png" />
</p>  

- **Editing Profiles**: Update profile details, including name, avatar, and security PIN, all while keeping your data secure and your experience personalized.

<p align="center">
  <img src="doc/screenshots/picture_23.png" />
</p>  

<p align="center">
  <img src="doc/screenshots/picture_26.png" />
</p>  

- **Deleting Profiles**: When a profile is no longer needed, delete it to keep **NimbusTV** clean and focused on what matters most.

<p align="center">
  <img src="doc/screenshots/picture_27.png" />
</p>  

<p align="center">
  <img src="doc/screenshots/picture_28.png" />
</p>  

The **Profiles** section makes NimbusTV adaptable to every user’s viewing needs, ensuring a personalized experience on a single device.

<p align="center">
  <img src="doc/screenshots/picture_29.png" />
</p>  

### Home Screen 🏠📺

Welcome to the **Home** screen of **NimbusTV**, your central hub for exploring and enjoying your playlists and channels:

<p align="center">
  <img src="doc/screenshots/picture_9.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_6.png" />
</p>


- **Channel Preview** 🔍: Hover over any channel to get a quick preview of what's currently playing—ideal for browsing without fully tuning in.

<p align="center">
  <img src="doc/screenshots/picture_62.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_63.png" />
</p>

- **Favorites** ⭐: Mark your favorite channels to keep them easily accessible for quick viewing.

- **Smooth Playlist Switching** 🔄: Effortlessly switch between playlists to explore channels organized by theme or preference, perfect if you like variety.

<p align="center">
  <img src="doc/screenshots/picture_5.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_4.png" />
</p>

- **Playlist Management** 🎛️: From the main screen, access the **Manage Playlist** button to open the playlist management view, where you can:
  - **Add New Playlists** ➕: Add new M3U playlists to expand your channel lineup.
  - **Delete Playlists** 🗑️: Remove playlists that are no longer needed.
  - **Update Playlists** 🔄: Refresh playlists to keep content up-to-date.
  - **Explore Channels and Favorites** 📋: View each playlist's channels, mark favorites, or play them instantly.

<p align="center">
  <img src="doc/screenshots/picture_10.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_11.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_12.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_13.png" />
</p>

With NimbusTV’s **Home** screen, you’re in control of all your playlists and favorite channels, providing a fully personalized TV experience. 🎉

### Channel Search Screen 🔎📺

The **Channel Search** feature in **NimbusTV** is your go-to tool for quickly finding channels across all imported playlists! Accessible from the **Side Menu** (if enabled in **Preferences**), this screen allows you to:

- **Quickly Search Channels** 🔍: Type a keyword to search through all registered channels, making it easy to find exactly what you’re looking for in seconds.

- **Intuitive On-Screen Keyboard** ⌨️: Use the keyboard on the left side of the screen to input search terms, with the results instantly updating as you type.

- **Results Display** 📜: Found channels are shown in a convenient list on the right side of the screen. Browse through and explore results based on your search term.

- **Channel Actions** 💬: Selecting a channel in the search results opens a dialog with two options:
  - **Play Now** ▶️: Instantly tune in to the channel.
  - **Add to Favorites** ⭐: Save the channel for quick access from your favorites list.

<p align="center">
  <img src="doc/screenshots/picture_15.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_16.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_14.png" />
</p>

The **Channel Search** screen streamlines navigation and lets you customize your experience by finding and favoriting channels across your playlists.

### Favorites Section ⭐

The **Favorites** section in **NimbusTV** is designed for quick access to the channels you love most. Here’s what makes it a perfect tool for streamlining your viewing experience:

- **Easy Access to Favorites** 📜: Your favorite channels are all listed in one place, making it effortless to start watching right away.

- **Instant Playback** ▶️: Simply select a channel from the list to begin playback, no need to navigate through different playlists.

- **Manage Favorites** 🗑️: Want to declutter? Easily remove channels from your favorites list with a single action, keeping only the content you truly enjoy.

The **Favorites** section keeps your top channels just a click away, making it your personalized hub for entertainment on NimbusTV!

<p align="center">
  <img src="doc/screenshots/picture_19.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_20.png" />
</p>


### EPG (Electronic Program Guide) 📅

The **Electronic Program Guide (EPG)** in **NimbusTV** lets each profile import, view, and manage channel lists and programming schedules. Here’s an overview of its key features:

<p align="center">
  <img src="doc/screenshots/picture_41.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_42.png" />
</p>

#### EPG Viewing Modes 🎛️

**NimbusTV** offers two EPG display modes, configurable from **Settings**:

- **Now & Schedule Mode** ⏰:  
  This mode displays the EPG in a two-column format:
  - The first column shows all channels with their currently airing programs.
  - The second column lists the full schedule for each channel, indicating whether the event is past, live, or upcoming.  
  You can select any upcoming event to open a dialog and set a reminder, which will notify you 5 minutes before the show starts.

<p align="center">
  <img src="doc/screenshots/picture_49.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_7.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_59.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_60.gif" />
</p>

- **Single-Column Mode** 📜:  
  This format organizes the EPG into a single column, with each channel appearing in an individual row alongside its full schedule for the day. Just like in **Now & Schedule Mode**, you can interact with events and set reminders for future shows.


<p align="center">
  <img src="doc/screenshots/picture_56.gif" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_40.png" />
</p>

Both modes provide intuitive navigation through each channel’s programming, making it quick and easy to keep track of your favorite shows.

#### EPG Source Management 🔧

Each profile can access **EPG Source Management**, where you can:

<p align="center">
  <img src="doc/screenshots/picture_46.png" />
</p>

- **Add New EPGs** ➕: Import custom EPG sources to load channel programming schedules.

<p align="center">
  <img src="doc/screenshots/picture_47.png" />
</p>

- **Delete Existing EPGs** 🗑️: Remove any EPG sources that you no longer need.

<p align="center">
  <img src="doc/screenshots/picture_48.png" />
</p>

- **Daily Auto-Updates** 🔄: NimbusTV schedules a daily update for each active EPG, refreshing programming data so you always have the latest information.

With these flexible options, each profile in **NimbusTV** can tailor its own program guide, ensuring a viewing experience that’s convenient and perfectly suited to your preferences. Stay organized, informed, and on top of your favorite channels’ schedules!

<p align="center">
  <img src="doc/screenshots/picture_45.png" />
</p>

### Full-Screen Player 🎥

The **Full-Screen Player** in **NimbusTV** provides an immersive viewing experience, allowing you to enjoy channels in full detail. Here’s what you can do within the player:

- **Full-Screen Playback** 📺: Each channel can be viewed in full-screen mode, giving you a clear and focused experience of your selected content.

- **Add to Favorites** ⭐: Want quick access to a channel? Add it to your favorites directly from the player screen with just one click.

- **Access System Settings** ⚙️: Open the system settings panel without leaving the player, allowing for quick adjustments to enhance your viewing experience.

<p align="center">
  <img src="doc/screenshots/picture_17.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_18.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_64.png" />
</p>

<p align="center">
  <img src="doc/screenshots/picture_65.png" />
</p>

#### Audio-Only Player for Music & Radio 📻

For music or radio channels, **NimbusTV** launches a special **Audio-Only Player**:

- **Music & Radio Display** 🎶: When tuning into a radio or music channel, the audio-only player displays station or channel information, delivering a streamlined audio experience without video, ideal for listening enjoyment.

With both full-screen video playback and an optimized audio-only mode, **NimbusTV**’s player is designed to adapt to your viewing and listening needs, making every channel as engaging as possible.

<p align="center">
  <img src="doc/screenshots/picture_66.png" />
</p>


### Settings Screen ⚙️

The **Settings** screen in **NimbusTV** offers you a range of customizable options to tailor your viewing experience. Here’s how you can enhance your interaction with the app:

<p align="center">
  <img src="doc/screenshots/picture_31.png" />
</p>

### Channel Search Settings 🔍

The **Channel Search** option allows you to enable or disable the feature to search for channels across your playlists, providing you with flexibility in navigation.

- **Toggle Search Visibility** ⚙️: Enable or disable the channel search feature easily. When activated, the search icon will appear in the side menu, making it simple to find your favorite channels at any time.

<p align="center">
  <img src="doc/screenshots/picture_32.png" />
</p>

- **EPG View Mode Selection** 📅: Choose how you want to view the Electronic Program Guide (EPG). You can opt for a **"Now & Schedule"** format with two columns for an overview of what's currently airing and what's coming up, or a **unified format** with a single column displaying all programming for the current day in one row per channel.

<p align="center">
  <img src="doc/screenshots/picture_33.png" />
</p>

### Additional Settings 🛠️

- **Access AndroidTV Settings** 📱: Quickly navigate to the AndroidTV system settings for further customization and adjustments.

- **Legal Information** 📜: View essential documents like **Terms and Conditions**, ensuring you understand the usage of the app.

<p align="center">
  <img src="doc/screenshots/picture_35.png" />
</p>

- **Help Section** ❓: Need assistance? Access the help section for FAQs and troubleshooting tips to enhance your experience.

<p align="center">
  <img src="doc/screenshots/picture_36.png" />
</p>

- **About Us** ℹ️: Learn more about **NimbusTV** and our mission in the "About NimbusTV" section.

- **Log Out** 🚪: Easily log out of your account when you’re done, ensuring your privacy and security.

<p align="center">
  <img src="doc/screenshots/picture_37.png" />
</p>

With these settings, **NimbusTV** empowers you to create a viewing environment that fits your preferences, making your entertainment experience as enjoyable as possible!

## Contribution
Contributions to NimbusTV Android are highly encouraged! If you're interested in adding new features, resolving bugs, or enhancing the project's functionality, please feel free to submit pull requests.

## 🎖️ Recognition & Credits  

NimbusTV is developed and maintained by **Sergio Sánchez Sánchez (Dream Software)**. Special thanks to the open-source community and the contributors who have made this project possible. If you have any questions, feedback, or suggestions, feel free to reach out at [dreamsoftware92@gmail.com](mailto:dreamsoftware92@gmail.com).

---

### Acknowledgments

#### 1. **JetFit Project**
A huge thank you to the creators of **[JetFit](https://github.com/TheChance101/tv-samples/tree/JetFit/JetFit)** and the contributors to **[PR #183](https://github.com/android/tv-samples/pull/183)** for providing an invaluable foundation for **NimbusTV**. **JetFit** has served as a reference for both the **Jetpack Compose for TV UI framework** and the app’s design structure.

- **License**:
  - **Apache 2.0** (Implementation)
  - **CC BY 4.0** (Design)

The **JetFit** project significantly influenced the development of NimbusTV. Its **Apache 2.0**-licensed implementation was used to build out the **Jetpack Compose for TV** framework, while its **CC BY 4.0**-licensed design inspired the visual aspects of NimbusTV.

---

### 📌 Key Resources:
- **[JetFit GitHub Repository](https://github.com/TheChance101/tv-samples/tree/JetFit/JetFit)**
- **[JetFit Figma Design](https://www.figma.com/community/file/1237433831695839696/jetfit-fitness-app)**
- **[JetFit Case Study & Guidelines](https://developer.android.com/design/ui/tv/samples/jet-fit?hl=es-419)**
- **[Google TV Design Kit (Figma)](https://developer.android.com/design/ui/tv/guidelines)**

These resources, examples, and insights provided in the **JetFit repository** have played a crucial role in accelerating our development process and deepening our understanding of building seamless TV applications with **Jetpack Compose**. We deeply appreciate the contributions of the JetFit team and their commitment to open-source development.

---

#### 2. **IPTV-Org Project**
For demonstration and testing purposes, **NimbusTV** utilizes publicly available IPTV playlists from the **[IPTV-Org](https://iptv-org.github.io/)** project. These playlists provide a wide collection of international channels in M3U format, offering users the ability to explore diverse content directly from the app.

---

#### 3. **M3u Parser Library**
NimbusTV uses and customizes the **[M3u Parser](https://github.com/BjoernPetersen/m3u-parser)** library to handle M3U playlist parsing. Modifications were made to extend its functionality and accommodate more complex M3U structures, allowing the app to handle the varied formats found in IPTV playlists.

- A copy of the original MIT license is included in the [`LICENSE-M3UPARSER`](LICENSE-M3UPARSER) file.

---

#### 4. **ComposeTv Repository**
🙏 Special thanks to **[Umair Khalid](https://github.com/UmairKhalid786)** for his excellent work on the **[ComposeTv](https://github.com/UmairKhalid786/ComposeTv)** repository. This repository served as a vital resource and starting point for the NimbusTV project. The clean documentation and high-quality code of **ComposeTv** were invaluable in the learning and development process.

---

#### 5. **Design Resources**
We would like to express our deep appreciation to **[Freepik](https://www.freepik.es/)** for providing the icons and images used in this project.

- **Icons and images taken from** [Freepik](https://www.freepik.com) and [Flaticon](https://www.flaticon.es).

## Disclaimer

NimbusTV is provided for educational and learning purposes only 🎓. The project has been developed to facilitate access and viewing of content through M3U playlists 📂, organized in an efficient and accessible manner on Android TV devices 📺. NimbusTV is not responsible for the use made of the app or the content viewed through it 🚫.

Users are responsible for ensuring that the content they access through the app does not violate any copyright laws or other applicable regulations in their jurisdiction ⚖️. NimbusTV uses public IPTV sources such as IPTV org 🌐, which provide freely accessible and public domain channels 📡. NimbusTV does not control or guarantee the availability or legality of the TV channels available through these sources 🔍.

By using NimbusTV, you acknowledge that the use of the app is at your own risk ⚠️, and the development team assumes no responsibility for any copyright violations, legal issues, or damages resulting from accessing content through the app 💼.

To handle M3U playlist parsing, we’ve integrated and customized the [M3u parser](https://github.com/BjoernPetersen/m3u-parser) library. Modifications were made to extend the functionality, allowing **NimbusTV** to support even more complex M3U structures while accommodating the varied and sometimes inconsistent formats found in IPTV playlists.  

The name **NimbusTV** was generated during a brainstorming session with AI. Any resemblance to existing brands, products, or services is purely coincidental.  

The **NimbusTV** logo included in this repository is an original creation and is free to use for personal and open-source projects.

**NimbusTV** does not host, distribute, or promote any copyrighted or illegal content. The user is solely responsible for the playlists they load and how the app is used. This project is for educational purposes only.


## Visitors Count

<img width="auto" src="https://profile-counter.glitch.me/nimbustv_android/count.svg" />

 
 ## Please Share & Star the repository to keep me motivated.
  <a href = "https://github.com/sergio11/nimbustv_android/stargazers">
     <img src = "https://img.shields.io/github/stars/sergio11/nimbustv_android" />
  </a>

## License ⚖️

This project is licensed under the **Apache License 2.0**, a permissive open-source software license that allows developers to freely use, modify, and distribute the software. 🚀 This includes both personal and commercial use, with some conditions for distribution and modification. 📜

Key terms of the Apache License 2.0:

- You are allowed to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the software. 💻
- If you modify and distribute the software, you must include the original copyright notice, provide a copy of the Apache 2.0 license, and indicate any modifications made. 📝
- You are not allowed to use the name of the project or its contributors to promote derived works without permission. ✋
- The software is provided "as is," without any warranties, express or implied. 🚫🛡️

Please see the full license text below for more detailed terms.

```
Apache License Version 2.0, January 2004 http://www.apache.org/licenses/

Copyright (c) 2024 Dream software - Sergio Sánchez

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
```
