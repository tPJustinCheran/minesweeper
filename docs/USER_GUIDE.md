# Minesweeper

Minesweeper is a ...

![Ui](Ui.png)

---

## Download
[Latest Release](https://github.com/tPJustinCheran/minesweeper/releases/latest)

---

## Getting Started

### Prerequisites
- Java 21 ([Download here](https://adoptium.net/temurin/releases/?version=21))
- Note that Jave versions higher than 21 may experience issues due to incompatibility with Gradle.

### Running the app
```
java -jar minesweeper.jar
```

---

## Features
put some screenshots per feature.

### Starting a New Game

```
edit
```

### Continuing a Saved Game

### Left Click - Revealing a Cell
Left Click to reveal a number

Left Click to reveal a bomb

Left Click to floodfill


### Right Click - Flag/Unflag
Right Click on an unrevealed cell to Flag

Right Click on a cell with a Flag to Unflag


### Timer & First Click Safety
The timer only starts when the user reveals their first cell, either by Left Clicking or using a Hint.

The First revealed cell can never be a bomb.

### Hint Button
The user can use a Hint to reveal a random unrevealed, non-Bomb cell.

### Chording
When the number of flags surrounding a revealed cell is equal to the revealed cell's number, the user can click on the revealed cell to trigger `Chording`.  

`Chording` will reveal all unrevealed cell surrounding the clicked cell, allowing users to reveal the board much faster.

### Win Condition & Leaderboard Entry
When all non-Bomb cells are revealed, the user Wins the game.  

Upon winning the game, the user can enter their name inside the leaderboard.

### Lose Condition
When the user reveals a Bomb, the user Lose the game.

### Leaderboard Page
The Leaderboard page shows the records of Won games, sorted by time.

### Help Page
The Help page gives guidance on how to play minesweeper.

---

## FAQ
`[edit this pls]`

---

## For Developers

### Developer Guide
Can be found in `[edit this pls]`.

### Tech Stack
`[edit this pls]`
- Java 21
- JavaFX 21
- JUnit 5
- Gradle with Shadow plugin for fat JAR

---

## Acknowledgments & Credits

### Character & Aesthetic
* **Visual Assets:** Taken from...

### Technical Credits
This project was developed as part of a software engineering module at the **National University of Singapore (NUS)**.

---

## License
This project is for educational purposes. ... `[edit this pls]` something abt original minesweeper
