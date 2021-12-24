
# Exam online

Built with AndroidX Support

Requires Android Studio 4.1 or greater.

Current Kotlin Version 1.4.30


### SDK Versions

compileSdkVersion 30

buildToolsVersion "30.0.1"

minSdkVersion 24

targetSdkVersion 30


### Libraries

1. Retrofit- REST API Call
https://square.github.io/retrofit/
3. Material Design Components - Google's latest Material Components.
https://material.io/develop/android
4. koin - Dependency Injection
https://insert-koin.io/
### App Navigation

### Package Structure


```
├── appcomponents       
│ ├── di                 - Dependency Injection Components 
│ │ └── MyApp.kt
│ ├── network            - REST API Call setup
│ │ └── RetrofitProvider.kt
│ └── ui                 - Data Binding Utilities
│     └── CustomBindingAdapter.kt
├── extensions           - Kotlin Extension Function Files
│ ├── AlertDialogHelper.kt
│ ├── CustomProgressDialog.kt
│ ├── NetworkUtils.kt
│ ├── ViewExtensions.kt
│ └── Strings.kt
├── modules              - Application Specific code
│ └── example            - A module of Application
│  ├── ui                - UI handling classes
│  └── data              - Data Handling classes
│    ├── viewmodel       - ViewModels for the UI
│    └── model           - Model for the UI
└── network              - REST API setup
  ├── models             - Request/Response Models
  ├── repository         - Network repository
  ├── resources          - Common classes for API
  └── RetrofitService.kt