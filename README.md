<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" alt="Matrix (Matrix Calculator) App Icon" width="120" height="120">
</p>
<h1 align="center">Matrix (Matrix Calculator)</h1>

<p align="center">
  <a href="https://developer.android.com"><img src="https://img.shields.io/badge/Compile%20SDK-37-brightgreen.svg" alt="Android SDK"></a>
  <a href="https://developer.android.com"><img src="https://img.shields.io/badge/Min%20SDK-23-blue.svg" alt="Min SDK"></a>
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/Kotlin-2.4-blueviolet.svg" alt="Kotlin"></a>
  <a href="https://developer.android.com/jetpack/compose"><img src="https://img.shields.io/badge/Jetpack%20Compose-Material%203-green.svg" alt="Compose"></a>
  <a href="https://developer.android.com/topic/architecture"><img src="https://img.shields.io/badge/Architecture-MVVM-orange.svg" alt="Architecture"></a>
  <a href="#internationalization-i18n"><img src="https://img.shields.io/badge/i18n-PT%20%7C%20EN%20%7C%20ES-blue.svg" alt="Languages"></a>
</p>

A modern and intuitive Android application for **Linear Algebra** calculations and matrix operations, built 100% with **Jetpack Compose** and **Material Design 3**.

---

## 🚀 Features

- **Comprehensive Matrix Operations**:
  - ➕ **Matrix Addition**: $A_{m \times n} + B_{m \times n}$
  - ➖ **Matrix Subtraction**: $A_{m \times n} - B_{m \times n}$
  - 🔢 **Scalar Multiplication**: $k \cdot A_{m \times n}$
  - ✖️ **Matrix Multiplication**: $A_{m \times n} \cdot B_{n \times p}$
- **Direct Grid Input & Mathematical Visualization**:
  - Direct cell data entry with styled matrix brackets.
- **Step-by-Step Resolution**:
  - Detailed display of intermediate calculation steps for academic tracking and learning.
- **Flexible Dimension Configuration**:
  - Dynamic dimension adjustments from $1 \times 1$ up to $4 \times 4$ with compatibility rules and automatic dimension synchronization.
- **Edge-to-Edge & Responsive Design**:
  - Full support for **Edge-to-Edge** (`WindowInsets`).
  - Native support for **Light Mode** and **Dark Mode**.
  - Adaptive layout for both **Portrait** and **Landscape** orientations without UI cutoffs.
- **Integrated System Navigation**:
  - Physical back button and system gesture handling via `BackHandler`.

---


## 📥 Get the App on Google Play

You can download the official release directly from the Google Play Store:
[🔗 Matrix on Google Play](https://play.google.com/store/apps/details?id=br.com.arsgapps.matrizes)

---

## 🌍 Internationalization (i18n)

The app features full multi-language localization support:
- 🇧🇷 **Portuguese** (Default)
- 🇺🇸 **English**
- 🇪🇸 **Spanish**

---

## 🛠️ Tech Stack & Architecture

The project is structured according to Google's recommended modern Android architecture guidelines:

- **Language**: [Kotlin](https://kotlinlang.org)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declarative UI)
- **Design System**: [Material Design 3 (M3)](https://m3.material.io)
- **Architecture Pattern**: MVVM (*Model-View-ViewModel*) with `StateFlow` and `Compose State`
- **State Management**: `AndroidViewModel` / `ViewModel`
- **Edge-to-Edge Support**: `enableEdgeToEdge()` and `WindowInsets`
- **Splash Screen**: `androidx.core:core-splashscreen`

---

## 🤝 Contributing

We love contributions from the open-source community! Whether it's reporting a bug, improving documentation, or proposing a new feature, your help is welcome.

### Git Flow & Contribution Workflow

We follow standard Git Flow practices to ensure a clean and collaborative development process:

1. **Fork the Repository**
   Click the **Fork** button at the top right of this repository to create a copy under your GitHub account.

2. **Clone Your Fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/matrizes.git
   cd matrizes
   ```

3. **Create a Feature Branch**
   Always create your branch from `main` (or `develop`):
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/your-feature-name
   ```
   *Branch naming convention:*
    - `feature/short-description` (for new features)
    - `fix/short-description` (for bug fixes)
    - `docs/short-description` (for documentation updates)

4. **Make Your Changes**
    - Implement your changes following Kotlin and Jetpack Compose best practices.
    - Ensure all hardcoded UI strings are added to `strings.xml` across all locales (`values`, `values-en`, `values-es`).

5. **Run Tests and Build**
   Verify that your code compiles and all unit tests pass successfully:
   ```bash
   ./gradlew testDebugUnitTest
   ./gradlew assembleDebug
   ```

6. **Commit Your Changes**
   Use clear, descriptive commit messages following the Conventional Commits style:
   ```bash
   git add .
   git commit -m "feat: add matrix inverse calculation feature"
   ```
   *Prefixes:* `feat:`, `fix:`, `docs:`, `refactor:`, `test:`, `chore:`

7. **Push to Your Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

8. **Open a Pull Request (PR)**
    - Go to the original repository and open a Pull Request against the `main` branch.
    - Provide a clear description of your changes, referencing any related issues.
    - **PR Reviewer:** [@andrersguedes](https://github.com/andrersguedes) will review your contributions.

---

## 👥 Maintainers & Reviewers

- **Lead Maintainer & PR Reviewer:** [@andrersguedes](https://github.com/andrersguedes)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
