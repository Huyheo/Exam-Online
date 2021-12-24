
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
2. Picasso - Image downloading and caching.
https://github.com/square/picasso
3. Material Design Components - Google's latest Material Components.
https://material.io/develop/android
4. koin - Dependency Injection
https://insert-koin.io/
5. WeekView - View event in week
https://github.com/jlurena/revolvingweekview

### App Navigation
<img src="https://user-images.githubusercontent.com/45762333/147344085-105c1b60-fcb4-4a6b-8eda-031d97523364.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147344890-b66d3d9d-066b-4cac-ad40-a2467f47852f.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345362-ceecb5d6-02ab-4b31-8927-75ad85d012d1.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345378-6ad54c21-abc9-49b5-b7df-0446dd8ba495.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345374-1f45f8fe-6f32-4803-84ba-504963c12d4b.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345395-1cdd595c-960c-4bea-bce6-e8826122ace9.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147346348-a71b6f30-8cd1-422c-8382-2afee10c4724.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345416-58508dc7-6626-49b5-b197-8ea723ca0f5f.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147346217-6e37dfed-bf1c-4b67-9ff2-c273a8d03bfa.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345424-6a11daf7-fa97-4ce6-bcc3-a0f374ac014c.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147345715-317ea22c-dd00-43d7-b7a9-847c5ac2350c.jpg" width="200"/>
<img src="https://user-images.githubusercontent.com/45762333/147346393-1ed57da4-dbb3-4b79-95a0-0a454be5faa5.jpg" width="200"/>

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
  └── Retrof![Screenshot_2021-12-19-03-09-33-23_ed85c2b5b45c96b91a8b41314711ff30](https://user-images.githubusercontent.com/45762333/147345436-0bf0ee16-0073-49c8-ba75-e7c8d7e2e0ca.jpg)
itService.kt
