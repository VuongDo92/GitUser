# Git User App Demo
This repository contains a demo Android application built for the Tymex company interview process. The app demonstrates the use of Jetpack Compose, Koin for dependency injection, Room for local database, Ktor for remote data, and modern Android development practices by implementing a two-screen interface that interacts with the GitHub API.

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Tech Stack](#tech-stack)
- [Architect Model](#architect-model)
- [Screens](#screen)
- [Navigation](#navigation)
- [Installation and Setup](#installation-and-setup)
- [How to Use](#how-to-use)
- [Contact](#contact)

## Features
- **Git User List Screen**: Displays a list of GitHub users fetched from the GitHub API..
- **Git User Detail Screen**: Shows detailed information about a selected GitHub user.

## Screenshots
<div style="display: flex; justify-content: space-between;">
<img src="screenshots/1-git-user-list.png" alt="git-user-list" width="120" />
<img src="screenshots/2-git-user-detail.png" alt="git-user-detail" width="120" />
</div>

## Tech Stack
- **Kotlin**: The main language used for app development.
- **Jetpack Compose**: The UI toolkit for building declarative UIs.
- **Koin**: Dependency injection framework.
- **ViewModel & LiveData**: For managing UI-related data in a lifecycle-conscious way.
- **Coroutines & Flow**: For handling asynchronous tasks
- **Ktor**: Networking library to fetch data from the GitHub API
- **Room**: Local Database management

## Architect Model
In Git User app, I have implemented a modularized project, including core and gituser modules, using the MVVM (Model-View-ViewModel) design pattern. The key components of the MVVM pattern are:

***Model***
- This layer consists of the data repository (GitUserRepository) and data models (GitUser). It handles fetching data from the network or database and provides it to the ViewModel.
- This layer involves modules like core.database, core.data, core.domain, gituser.domain, and gituser.data for handling local data with Room, network operations, and repository logic.

****ViewModel****
- Acts as a bridge between the View and Model, fetching data from the repository, exposing it to the View, and handling UI-related logic. For instance, the GitUserListViewModel and GitUserDetailViewModel in the app act as the intermediary between the Model and View. They fetch data from the repository, manage the app’s state, and handle logic like refreshing or loading more Git users.

****View****
- The UI components (composables) observe changes in the ViewModel and update the UI accordingly including core.presentation.ui, core.presentation.designsystem, components in gituser module.

By separating the app into different modules (core and gituser), you have enhanced modularity and reusability, making the project more maintainable and scalable. Each module has a clear responsibility, reducing coupling between the different layers of the app.

****Unit Test****:
- JUnit 5 has been integrated to enhance unit testing capabilities. A dedicated test utility module that is designed to simplify the testing of coroutines by providing a custom test dispatcher has also been created to centralize and streamline common testing functionalities.
- The test module setup will allows for more organized and maintainable test code, leveraging JUnit 5's advanced features to improve test flexibility and readability. By utilizing this structure, you can ensure a robust testing process that supports high-quality code development in the GitUser app.


## Screens
1. **Git User List Screen**:

<img src="screenshots/1-git-user-list.png" alt="git-user-list" width="120" />

- Displays a list of GitHub users.
- Clicking on a user navigates to the detail screen.

2. **Git User Detail Screen**:

<img src="screenshots/2-git-user-detail.png" alt="git-user-detail" width="120" />

- Shows detailed information of the selected user such as their login, follower count, following count, and blog URL.
- Includes a back button to navigate back to the user list.

### Navigation
The app uses Jetpack Compose's NavHost to handle navigation between the two screens.

- GitUserListScreen → Navigate to GitUserDetailScreen with the user's login passed as a parameter.
- State and data persistence are handled using ViewModel.

## Installation and Setup
1. Clone this repository:
```bash
git clone https://github.com/VuongDo92/GitUser.git
```
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or physical device.

## How to Use
- Launch the app to view a list of GitHub users.
- Tap on any user to view more details about them.
- Use the back button to return to the user list.

## Contact
If you have any questions or feedback, feel free to reach out at:

- Email: vuongdt92@gmail.com or vuongdotuan@gmail.com
- GitHub: https://github.com/VuongDo92
- Linkedin: https://www.linkedin.com/in/vuongdotuan/

##

This README provides an overview of the app’s functionality, tech stack, and usage instructions. Adjust the repository link and contact details as necessary.

