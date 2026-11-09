# Jetpack Compose Migration Plan

Migrate the Matrix calculation Android application from legacy XML layouts and Android Activities to modern Jetpack Compose (Material 3).

## User Review Required

> [!IMPORTANT]
> This migration will transition the UI layer from XML views and traditional Activities to Jetpack Compose while preserving core matrix calculation logic.

## Proposed Changes

### Build Configuration
#### [MODIFY] [app/build.gradle.kts](file:///Users/andreguedes/AndroidStudioProjects/matrix/app/build.gradle.kts)
- Enable Compose build features (`buildFeatures { compose = true }`).
- Add Jetpack Compose dependencies (Compose BOM, UI, Material 3, Activity Compose).

### UI & Activities Migration
#### [MODIFY] [MainActivity.java](file:///Users/andreguedes/AndroidStudioProjects/matrix/app/src/main/java/br/com/arsgapps/matrizes/MainActivity.java) or convert to Kotlin/Compose Composable
- Replace XML layout rendering with Jetpack Compose UI (`setContent`).
- Implement Material 3 buttons and layout for operation selection.

#### [MODIFY] Other Activities & UI screens
- Migrate `OrdemMatriz`, `SomaSubtracaoMatriz`, `MultiConstante`, `MultiMatrizes`, and result screens to Jetpack Compose composables.

## Verification Plan

### Automated Tests
- Run `gradle_assemble_all` to verify successful compilation.

### Manual Verification
- Deploy and verify app navigation, matrix operations (addition, subtraction, scalar multiplication, matrix multiplication), and input handling on device/emulator.
