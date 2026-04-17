# Minesweeper - User Guide

🏠 [Home](./README.md) | 📖 [User Guide](#) |️️ 🛠️ [Developer's Guide](./DEVELOPERS_GUIDE.md)

Minesweeper is a classic logic puzzle game where players must clear a grid of hidden cells while avoiding bombs. Using clues provided by numbers on revealed cells, players deduce the positions of bombs and mark them with flags. The objective is to reveal all non-bomb cells in the shortest possible time.

---

## Table of Contents

- [Download](#download)
- [Getting Started](#getting-started)
- [Features](#features)
  - [Starting a New Game](#starting-a-new-game)
  - [Continuing a Saved Game](#continuing-a-saved-game)
  - [Left Click - Revealing a Cell](#left-click---revealing-a-cell)
  - [Right Click - Flag/Unflag](#right-click---flagunflag)
  - [Timer & First Click Safety](#timer--first-click-safety)
  - [Hint Button](#hint-button)
  - [Chording](#chording)
  - [Win Condition & Leaderboard Entry](#win-condition--leaderboard-entry)
  - [Lose Condition](#lose-condition)
  - [Leaderboard Page](#leaderboard-page)
  - [Help Page](#help-page)
- [FAQ](#faq)
- [For Developers](#for-developers)
- [Acknowledgments & Credits](#acknowledgments--credits)
- [License](#license)

![UI](images/userguide/MinesweeperUI.png)

Refer to the demonstration video here: ![Demo](demo/DEMO_VIDEO.mp4)

---
 
## Download
[Latest Release](https://github.com/tPJustinCheran/minesweeper/releases/latest)
 
---
 
## Getting Started
 
### Prerequisites

- Java 21 ([Download here](https://adoptium.net/temurin/releases/?version=21))
- Note that Java versions higher than 21 may experience issues due to incompatibility with Gradle.

### Running the app

1. Download the JAR folder [here] 
2. Move the JAR File to an empty folder. 
3. Run the JAR File by double-clicking on the `minesweeper.jar` file. 

Alternatively, after navigating into the folder with the JAR file in your terminal, run:  

```bash
java -jar minesweeper.jar
```
 
---
 
## Features
 
---
 
### Starting a New Game
 
From the Home page, click the **New Game** button to start a fresh Minesweeper board.
 
A new 10x10 grid will be generated with bombs placed randomly. The timer will remain at 0 until the first move is made.
 
![New Game](images/userguide/NewGame.gif)
 
---
 
### Continuing a Saved Game
 
If a previous game was not completed, click **Continue Game** on the Home page to resume where you left off.
 
The board state, flags, revealed cells, hints remaining, and timer progress will all be restored automatically.
 
![Continue Game](images/userguide/Continue.gif)
 
---
 
### Left Click - Revealing a Cell
 
Left clicking on a cell reveals its contents.
 
Possible outcomes:
 
- If the cell contains a number, the number indicates how many bombs are in the surrounding 8 cells.  
- If the cell contains a bomb, the game is lost.  
- If the cell contains no adjacent bombs, the game automatically reveals surrounding cells using a flood fill behaviour.

Flood fill helps reveal large safe areas quickly.
 
![Reveal Box](images/userguide/RevealBox.gif)
 
![Floodfill](images/userguide/Floodfill.gif)
 
---
 
### Right Click - Flag/Unflag
 
Right clicking allows players to mark suspected bomb locations.
 
- Right click on an unrevealed cell to place a Flag.  
- Right click on a flagged cell to remove the Flag.

Flags help track where bombs are likely located, but placing flags does not affect the win condition unless used correctly.
 
![Flag and Unflag](images/userguide/FlagUnflag.gif)
 
---
 
### Timer & First Click Safety
 
The game timer starts only after the first move is made.
 
This first move can be:
 
- Left clicking a cell  
- Using the Hint button  

The first revealed cell is guaranteed to never contain a bomb, ensuring the game always starts fairly.
 
The timer continues running until the player wins or loses.
 
---
 
### Hint Button
 
Players can press the **Hint** button to reveal a safe cell automatically.
 
Hints reveal a random unrevealed cell that does not contain a bomb.
 
Hints are limited, so they should be used strategically.
 
The remaining number of hints is displayed on the Hint button.
 
![Hint](images/userguide/Hint.gif)
 
---
 
### Chording
 
Chording is an advanced feature that speeds up gameplay.
 
When the number of Flags surrounding a revealed numbered cell matches the number shown on that cell, the player can left click the revealed cell again to reveal all remaining surrounding cells automatically.
 
Example:  
If a revealed cell shows **2**, and exactly **2 flags** are placed around it, clicking the revealed cell will reveal all other adjacent cells.
 
Chording allows experienced players to clear large sections quickly.
 
![Chording](images/userguide/Chord.gif)
 
---
 
### Win Condition & Leaderboard Entry
 
The player wins when all non-bomb cells are revealed.
 
After winning:
 
- The timer stops  
- The player can enter their name  
- The result is saved to the leaderboard  

Leaderboard rankings are based on fastest completion time.
 
![Win Page](images/userguide/WinPage.gif)
 
---
 
### Lose Condition
 
If a bomb is revealed, the game ends immediately.
 
All bomb locations are revealed on the board, allowing players to see where the mistakes occurred.
 
Players can choose to:
 
- Play again  
- Return to the Home page  

![Lose Page](images/userguide/LosePage.gif)
 
---
 
### Leaderboard Page
 
The Leaderboard displays records of completed games sorted by completion time.
 
Each entry includes:
 
- Player name  
- Completion time  

Players can view past achievements and compare results.
 
![Leaderboard Page](images/userguide/LeaderboardPage.gif)
 
---
 
### Help Page
 
The Help page provides an overview of how to play Minesweeper, including explanations of controls and game mechanics.
 
It is useful for new players unfamiliar with Minesweeper rules.
 
![Help Page](images/userguide/HelpPage.gif)
 
---
 
## FAQ
 
### Why does the first click never hit a bomb?
The game ensures the first revealed cell is always safe by regenerating the board if necessary.
 
### How many bombs are on the board?
The game uses a random amount of bombs (5-20) distributed randomly across the 10x10 grid.
 
### Does placing flags affect the win condition?
Flags do not directly affect winning. The game is won when all non-bomb cells are revealed.
 
### Does the timer pause when the window is closed?
If the game is continued later, the saved timer value will resume.
 
### Can I play without using hints?
Yes. Hints are optional and intended as assistance for difficult situations.
 
---
 
## For Developers
 
### Developer Guide
The developer documentation can be found in the project repository:
 
[Developer's Guide](./DEVELOPERS_GUIDE.md)
 
This guide explains:
 
- Architecture design  
- Class responsibilities  
- Storage structure  
- Testing approach  

---
 
### Tech Stack
 
- Java 21
- JavaFX 21
- JUnit 5
- Mockito
- Gradle
- Shadow plugin (fat JAR packaging)

---
 
## Acknowledgments & Credits
 
### Character & Aesthetic

**Visual Assets:** Icons and visual elements were sourced from open asset libraries and adapted for educational use.

### Technical Credits

This project was developed as part of a software engineering module at the **National University of Singapore (NUS)**.
 
Minesweeper is inspired by the original game popularised by Microsoft Windows.
 
---
 
## License
 
This project is for educational purposes only.
 
Minesweeper is an original game concept historically distributed with Microsoft Windows.  
This implementation is an independent recreation developed for learning software engineering principles and does not contain any proprietary Microsoft code.
 
All original code in this repository is released for academic use.