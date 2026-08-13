# Task List - Injaazh Islamic App

## Phase 1: Foundation & Infrastructure
- [x] Dependency Setup (Hilt, Navigation, Adhan, Lottie, Google Fonts)
- [x] Hilt Application Class Setup
- [x] Theme & Color Scheme Refinement (Material 3 - Deep Emerald & Gold)
- [x] Navigation Graph Implementation
- [x] Splash Screen Implementation
- [x] Localization Infrastructure (Strings.xml English/Bengali)
- [x] Font Integration (Tiro Bangla via Google Fonts)

## Phase 2: Core Features - Prayer Times & Qibla
- [x] Location Services Helper
- [x] Prayer Time Repository & UseCases
- [x] Premium Dashboard UI (Glassmorphism & Widgets)
- [x] Qibla Compass Logic & UI
- [x] Adhan Notification System (WorkManager & AlarmManager)
    - [x] Notification Channel Setup (Custom Sound)
    - [x] Broadcast Receivers (Adhan & Boot)
    - [x] Prayer Scheduling Logic
    - [x] Permission Handling UI

## Phase 3: Premium Features (New)
- [x] Interactive Prayer Tracker Screen (Charts & Stats)
- [x] Local Database (Room) for Tracking
- [/] Quran Memorizer (Hifz) Progress UI
    - [ ] Hifz Entity & Dao
    - [ ] Hifz Tracker Screen with Progress Rings
- [/] Premium Education Hub Cards
    - [ ] Asmaul Husna Data & Grid UI
    - [ ] Kalima & Ayatul Kursi Cards
    - [ ] Sunnah & Wudu Guides

## Phase 4: Quran & Knowledge Base
- [x] Quran API Integration (Retrofit Models & Service)
- [x] Surah List Screen (Premium UI)
- [x] Ayah View Screen (with translations)
- [ ] Offline Caching for Chapters
- [x] Audio Player Implementation (Media3 ExoPlayer)

## Phase 5: Utilities & Polish
- [x] Hisnul Muslim (Duas & Azkar)
    - [x] Dua Entities & Dao Setup
    - [x] Local JSON Data Asset
    - [x] Dua Category Grid UI
    - [x] Dua List & Detail UI
    - [ ] Counter/Tasbih functionality for specific Azkar
- [x] Digital Tasbih Counter
    - [x] DataStore Setup for persistence
    - [x] Tasbih Screen UI (Modern Circle)
    - [x] Haptic Feedback & Animations
- [x] Zakat Calculator
    - [x] String Resources for Assets & Liabilities
    - [x] Zakat Calculation Logic (ViewModel)
    - [x] Zakat Screen UI (Premium Cards)
    - [x] Integration into Navigation
- [x] Settings & Language Switcher
    - [x] Infrastructure Update (AppCompatActivity)
    - [x] Settings DataStore for notifications
    - [x] Settings Screen UI
    - [x] Language switching logic (Android 13+ standard)
