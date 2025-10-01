# Notary App

A mobile application designed to simplify the notarization process.  
The app allows users to scan government IDs, auto-extract essential details, and complete notarization forms with accuracy. It also supports manual input and correction.

---

## Features

- Camera integration to scan IDs.
- OCR & autofill for:
    - Full Name
    - Address
    - Date of Birth
    - License Number
- Editable input fields to correct or enter data manually.
- ID type selector with options:
    - Driver License
    - Passport
    - Employment Authorization Document (EAD)
    - Green Card (GC)
- Notarization type selector.
- Form validation for required fields.
- Fragment-based design with integrated camera action.

---

## Tech Stack

- **UI/Frontend**: Android (Kotlin, Fragments)
- **OCR & Scanning**: CameraX, ML Kit Text Recognition
- **State Management**: ViewModel, LiveData
- **Backend**: Flask API

---

## Getting Started

### Prerequisites
- Android Studio Ladybug or newer
- JDK 17+
- Gradle 8+

### Installation
```bash
# Clone the repository
git clone git@github.com:your-username/notary-app.git

# Navigate to the project
cd notary-app
