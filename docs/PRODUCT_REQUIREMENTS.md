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
|# | Requirement | Priority | Status                | 
|:----|:-----|:----|:----------------------|
|FR-01|Start a new classic minesweeper game / board | Must-Have | <input type="checkbox" checked> | 
|FR-02|Generate a valid minesweeper board| Must-have | Must-Have | <input type="checkbox" checked> | 
|FR-03|The system shall ensure the first click does not reveal a mine| Must-Have | <input type="checkbox" checked> | 
|FR-04|The system shall display the number of adjacenet mines when a non-mine cell is revealed | Must-Have | <input type="checkbox" checked> | 
|FR-05|The system shall automatically reveal adjacent cells if a revealed cell has zero adjacent mines (flood fill behaviour) | Must-Have | <input type="checkbox" checked> | 
|FR-06|The system allows players to flag and unflag cells | Must-Have | <input type="checkbox" checked> | 
|FR-07|A flagged cell cannot be revealed when clicked | Must-Have | <input type="checkbox" checked> | 
|FR-08|The system will declare a 'win' when all non-mine tiles are revealed |Must-Have | <input type="checkbox" checked> | 
|FR-09|The system will declare a 'lose' when a mine is revealed | Must-Have | <input type="checkbox" checked> | 
|FR-10|Help Message or window will pop up when Help Button is clicked on | Nice-to-Have | <input type="checkbox" checked> | 
|FR-11|Save game state on every move; reload on app reopen (Continue Game) | Nice-to-Have | <input type="checkbox" checked> | 
|FR-12|Allow player to restart the game by clicking on the restart button| Nice-to-Have | <input type="checkbox" checked> | 
|FR-13|The system records the puzzle's completion time when the player wins|Nice-to-Have | <input type="checkbox" checked> | 
|FR-14|The player can access the leaderboard window which shows the persistent leaderboard sorted by fastest time | Nice-to-Have | <input type="checkbox" checked> | 
|FR-15|The player can press a hint button to reveal a safe cell|Nice-to-Have | <input type="checkbox" checked> | 
|FR-16|Play again from Lose dialog|Nice-to-Have | <input type="checkbox" checked> | 
|FR-17|Back to Home from Lose dialog |Nice-to-Have | <input type="checkbox" checked> | 
|FR-18|User accounts with scores tied to account | Nice-to-Have | <input type="checkbox" checked> | 

(Havent included unlikely-to-have)


*** 

## Non-Functional Requirements



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



