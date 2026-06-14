## 🏗️ Project Architecture

This application follows a modern MVVM (Model-View-ViewModel) architecture and feature-based packaging to separate the UI, business logic, and hardware integrations.

```text
com.example.disastermanagementapp
│
├── MainActivity.kt                # 🚀 Entry point that starts your Compose UI
│
├── ui/                            # 🎨 Screens, Layouts, and Styling
│   ├── theme/
│   │   ├── Color.kt               # Reassurance Theme (Teals, soft corals, charcoal text)
│   │   ├── Theme.kt               # Light/Dark mode implementation
│   │   └── Type.kt                # Typography configurations
│   │
│   ├── navigation/
│   │   └── AppNavGraph.kt         # Controls routing (Onboarding -> Auth -> Dashboard)
│   │
│   └── screens/                   
│       ├── onboarding/
│       │   └── WelcomeScreen.kt   # "Get Started" screen
│       ├── auth/
│       │   └── AuthScreen.kt      # Login / Register screen
│       ├── dashboard/
│       │   ├── DashboardScreen.kt # Live telemetry monitoring screen
│       │   └── DashboardViewModel.kt
│       ├── emergency/
│       │   ├── EmergencyScreen.kt # 30-second crash countdown screen
│       │   └── EmergencyViewModel.kt
│       └── assistant/
│           ├── RoadsideAssistantScreen.kt # AI Voice Triage Chat screen
│           └── AssistantViewModel.kt
│
├── data/                          # ⚙️ Hardware Sensors & API Integrations
│   ├── sensors/
│   │   └── ImuManager.kt          # Phone Accelerometer logic (>4.5g impact checks)
│   ├── wear/
│   │   └── WatchConnection.kt     # Samsung Health SDK / Wear OS biometric sync
│   └── network/
│       ├── LlmApiClient.kt        # Connection to Claude/OpenAI for voice processing
│       └── DispatcherApi.kt       # Pushes incident cards to your Supabase/Firebase web panel
│
└── domain/                        # 🧠 Shared Data Objects & Business Logic
    └── models/
        ├── CrashEvent.kt          # Stores G-force details, GPS tags, and timestamps
        ├── Biometrics.kt          # Stores real-time heart rate data from the watch
        └── TriageMessage.kt       # Stores individual user and AI message objects