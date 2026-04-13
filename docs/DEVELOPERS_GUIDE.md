# Minesweeper - Developer's Guide
[User Guide](./USER_GUIDE.md) | [Developer's Guide](#)

This is a stub developer's guide.

Sections to write:

---

## 1. Acknowledgements: any libraries used (JavaFX, JUnit, Mockito)

---

## 2. Setting Up: how to clone, build and run (./gradlew run), IDE setup

---

## 3. Design

- Architecture overview: describe the 3 packages (ui, logic, storage) and how they interact at a high level, with a simple architecture diagram
- minesweeper.logic: Gameboard, Box, CustomTimer.
- minesweeper.storage: Storage, Config.
- minesweeper.ui: GamePage, HomePage, WinPage, LosePage, LeaderboardPage, HelpPage, ResourceManager.
^ explain all parts if possible

---

## 4. Implementation: explain non-obvious features with sequence diagrams:
- First click safety (the regeneration loop)
- Hint system (auto-reveal with floodfill)
- Chording
- Save/continue game flow

---

## 5. Class Diagram

---

## 6. Testing

Unit Testing  

See [UI Testing](./UI_TESTING.md) for the full UI test checklist and recordings.

---

## 7. Requirements: link PRD content

See [Product Requirements Document](./PRODUCT_REQUIREMENTS.md).

---
