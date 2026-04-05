# 📖 Product Requirement Document

## Product Overview 
**Product Goal:** Develop a lightweight, locally-hosted minesweeper game that improves on the classic Windows XP experience. 

**Target Audience:** Solo Players on Windows / macOS / Linux desktops running Java 17+. Suitable for all ages!

**Minimum-Viable Product (MVP):** Fully Playable JavaFX Application with progress saving, a hint system, and a leaderboard. 

*** 
- [Problem](#problem-)
- [Proposed Solution](#proposed-solution)
- [User Flow](#user-flow-)
- [Functional Requirements](#functional-requirements-)
- [Non-Functional Requirements](#non-functional-requirements)
- [Timeline](#timeline-)
- [Constraints and Limitations](#constraints-and-limitations)

***

## Problem 
- The classic Windows XP Minesweeper has no progress-saving feature — closing the app loses all progress. 
- Standard Minesweeper games offer no hint system, forcing players who are stuck to guess blindly. 
- The Windows XP version has no leaderboard, so completion times cannot be compared across sessions.

*** 

## Proposed Solution
Offer a lightweight, desktop-based Minesweeper game built in Java (JavaFX) that addresses the shortcomings 
of the classic version while remaining accessible and easy to play.

Our Unique Selling Points:
- Saves progress automatically on every move and ability to resume exactly where you left off. 
- Provides up to 3 hints per game to assist players who are stuck. 
- Records completion times on a persistent leaderboard sorted by fastest time. 
- First-click safety guarantee. The first cell clicked is never a bomb.

*** 

## User Flow 
![Alt text](images/UserFlow.png)

*** 

## Functional Requirements 
| #     | Requirement | Priority                          | Status          | 
|:------|:-----|:----------------------------------|:----------------|
| FR-01 |Start a new classic minesweeper game / board | Must-Have                         | ✅               | 
| FR-02 |Generate a valid minesweeper board| Must-have                         | Must-Have       | ✅ | 
| FR-03 |The system shall ensure the first click does not reveal a mine| Must-Have                         | ✅               | 
| FR-04 |The system shall display the number of adjacenet mines when a non-mine cell is revealed | Must-Have                         | ✅               | 
| FR-05 |The system shall automatically reveal adjacent cells if a revealed cell has zero adjacent mines (flood fill behaviour) | Must-Have                         | ✅               | 
| FR-06 |The system allows players to flag and unflag cells | Must-Have                         | ✅               | 
| FR-07 |A flagged cell cannot be revealed when clicked | Must-Have                         | ✅               | 
| FR-08 |The system will declare a 'win' when all non-mine tiles are revealed | Must-Have                         | ✅               | 
| FR-09 |The system will declare a 'lose' when a mine is revealed | Must-Have                         | ✅               | 
| FR-10 |Help Message or window will pop up when Help Button is clicked on | Nice-to-Have                      | ✅               | 
| FR-11 |Save game state on every move; reload on app reopen (Continue Game) | Nice-to-Have                      | ✅               | 
| FR-12 |Allow player to restart the game by clicking on the restart button| Nice-to-Have                      | ✅               | 
| FR-13 |The system records the puzzle's completion time when the player wins| Nice-to-Have                      | ✅               | 
| FR-14 |The player can access the leaderboard window which shows the persistent leaderboard sorted by fastest time | Nice-to-Have                      | ✅               | 
| FR-15 |The player can press a hint button to reveal a safe cell| Nice-to-Have                      | ✅               | 
| FR-16 |Play again from Lose dialog| Nice-to-Have                      | ✅               | 
| FR-17 |Back to Home from Lose dialog | Nice-to-Have                      | ✅               | 
| FR-18 |User accounts with scores tied to account | Unlikely-to-Have                  | Not Planned     | 
| FR-19 |Extension 1: The player can click a button on a revealed numbered cell that has adjacent flags equal its own number to reveal all of its non-revealed, non-flag adjacent cells. (chording) | Unlikely-to-Have                  | ✅     | 
|FR-20|Extension 1: The player can change the number of rows, columns, bombs, and hints in a setting. Default is [10, 10, 5-20, 3] respectively | Unlikely-to-Have | Not Planned |
|FR-21|Extension 2: Provide the option for the player to start the minesweeper board with gimmicks | Unlikely-to-Have                  | Not Planned     |
|FR-22|Extension 2: The player can use a shield (shared usage with hints) to protect them from losing in the next cell reveal. | Unlikely-to-Have                  | Not Planned |
|FR-23|Extension 2: The system designates fog tiles to safe tiles randomly, which hides the number of adjacent mines to it when displayed.| Unlikely-to-Have                  | Not Planned |
|FR-24|Extension 2: The system generates super mines counts as 2 mines on an adjacent safe cell display. | Unlikely-to-Have                  | Not Planned     |

*** 

## Non-Functional Requirements
| #      | Requirement | Notes                                                                                            | 
|:-------|:-----|:-------------------------------------------------------------------------------------------------|
| NFR-01 |Good coding standards for maintainability| Javadoc on all public methods; consistent naming conventions observed in codebase                | 
| NFR-02 |Consistent and clear documentation| Javadoc present; User Guide and Developer Guide to be finalised by Wk 13                         | 
| NFR-03 |Intuitive UI| JavaFX GUI with colour-coded buttons, clear labels, and modal dialogs                            | 
| NFR-04 |Game does not crash| Exception Hierarchy                                                                              | 
| NFR-05| Platform Compatibility| Java 17+; runs on Windows, macOS, Linux via JAR launcher                                         | 
|NFR-06| Local Data Storage | All saves stored in a local data/ folder (game.txt, solution.txt, time.txt, hint.txt, leaderboard| 



*** 

## Timeline
| Week | Release                                                                     | Tasks                                                                                |
|:-----|:----------------------------------------------------------------------------|:-------------------------------------------------------------------------------------|
| 5-7  | -                                                                           | Formation of Problem Statement<br>Defining of Functional/Non-Functional Requirements |
| 8-9  | -                                                                           | Development of Backend / CLI Code                                                    |
| 9-11 | -                                                                           | Development of Frontend / GUI                                                        | 
|      | [v0.1](https://github.com/tPJustinCheran/minesweeper/releases/tag/v0.1)     | Implement GamePage GUI and Integration with Game Logic                               | 
|      | [v0.1.1](https://github.com/tPJustinCheran/minesweeper/releases/tag/v0.1.1) | First fully playable version of minesweeper with a JavaFX GUI                        | 
|      | [v0.2](https://github.com/tPJustinCheran/minesweeper/releases/tag/v0.2)     | Leaderboard & Post-Game flow                                                         | 
|      | [v0.3](https://github.com/tPJustinCheran/minesweeper/releases/tag/v0.3)     | Bug Fixes & Hint Improvements                                                        | 
|      | [v0.4](https://github.com/tPJustinCheran/minesweeper/releases/tag/v0.4)     | UI Polish, Config, Package Restructure                                               |
|      | [v0.5](https://github.com/tPJustinCheran/minesweeper/releases/tag/v0.5)     | Code Cleanup, Unit Testing, Documentations  
|      | [v0.6]     | To-be finalised

*** 

## Constraints and Limitations
- Single-player only. No multiplayer, online, or account system. 
- Save files are stored locally in a data/ directory adjacent to the JAR. 
- Requires Java 17+ and JavaFX runtime. 
- Board size is fixed at 10×10. 
- Mine count is randomised between 5 and 20 per new game. 
- Maximum of 3 hints can be used per game session. 
- Leaderboard entries are stored as plain text (leaderboard.txt), no database. 
- The hint system reveals a safe cell but does not guarantee the cell is strategically optimal.



