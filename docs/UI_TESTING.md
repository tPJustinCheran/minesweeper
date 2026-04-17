# UI Testing

🏠 [Home](../README.md) | 📖 [User Guide](./USER_GUIDE.md) |️️ 🛠️ [Developer's Guide](./DEVELOPERS_GUIDE.md)

## 1. Normal UI Tests
Start the UI tests from a fresh build (no saves available)

### 1.1 Home Page

| Item | Working as Intended | Link |
|------|-------------------|------|
| Launch app | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Title shows "Minesweeper" | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| New Game, Continue, Leaderboard, Help buttons visible | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Version visible at bottom left corner | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Continue is greyed out (no save exists) | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 1.2 Leaderboard Page (empty)

| Item | Working as Intended | Link |
|------|-------------------|------|
| From Home, click Leaderboard | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| "No entries yet" message shown | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Click ← Home, verify returns to Home | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 1.3 Leaderboard Page (with entries)

| Item | Working as Intended | Link |
|------|-------------------|------|
| Win a game and submit a name | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Win another game and submit a different name with a faster time | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Entries are sorted fastest first | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Top 3 have gold, silver, bronze styling | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 1.4 Help Page

| Item | Working as Intended | Link |
|------|-------------------|------|
| From Home, click Help | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| All sections visible: GOAL, CONTROLS, NUMBERS, HINTS, WIN, LOSE | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Scroll works if content overflows | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Click ← Home, verify help page closes | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 1.5 GamePage: Basic Navigation

| Item | Working as Intended | Link |
|------|-------------------|------|
| Click New Game | ✓ |  [▶](ui_test/UI_TEST_2_13042026.mp4) |
| 10x10 grid shown, timer at 00:00.000 | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Bomb counter shows total bomb count | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Hint button shows "Hint (3 left)" | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click ← Home, returns to Home | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Continue is greyed out (no game started) | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 1.6 GamePage: Save and Continue

| Item | Working as Intended | Link |
|------|-------------------|------|
| Left click a cell: timer starts | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Right click 2 cells to flag: bomb counter decrements by 2 | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Hint: cell revealed, hint shows "Hint (2 left)" | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click ← Home: Continue enabled on Home | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Continue: board state restored (flags, revealed cells, timer, hint count) | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Open more cells, flag one more, click X to close app | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Relaunch app: Continue is enabled | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Continue: board state fully restored | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 1.7 GamePage: Continue with Pre-Made Data

B|B|B|B|B|B|B|B|B|2|  
B|8|B|7|B|6|B|5|B|2|  
B|B|B|4|B|3|1|2|1|1|  
2|3|2|2|1|1| | | | |  
 | | | | | | | | | |  
 | | | | | | | | | |  
 | | | | | | | | | |  
 | | | | | | | | | |  
 | | | | | | | | | |  
 | | | | | | | | | |  

| Item | Working as Intended | Link |
|------|-------------------|------|
| Overwrite data/game.txt and data/solution.txt with pre-made data | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| Launch app, click Continue | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| Cells 1-8 display correctly (numbers with correct colours) | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| Flagged cells show flag icon with grey background | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| Bomb counter is correct | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| Timer resumes | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |

### 1.8 GamePage: Right Click on Revealed Cell

| Item | Working as Intended | Link |
|------|-------------------|------|
| Start new game, reveal a cell | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Right click the revealed cell: nothing happens (no flag placed) | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 1.9 GamePage: Over-Flagging

| Item | Working as Intended | Link |
|------|-------------------|------|
| Place more flags than there are bombs | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Bomb counter goes negative | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Game does not crash | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 1.10 GamePage: Hint Exhaustion

| Item | Working as Intended | Link |
|------|-------------------|------|
| Click Hint 3 times: hint button shows "Hint (0 left)" | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Hint again: error alert "No more hints remaining!" | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 1.11 GamePage: Name Validation on WinPage

| Item | Working as Intended | Link |
|------|-------------------|------|
| Win a game | ✓ | [▶](ui_test/UI_TEST_3_13042026.mp4) |
| Enter name containing a `&#124;` character: error message shown | ✓ | [▶](ui_test/UI_TEST_3_13042026.mp4) |
| Clear name, submit empty: "Anonymous" is used | ✓ | [▶](ui_test/UI_TEST_3_13042026.mp4) |

---

## 2. Lose UI Tests

### 2.1 Lose → Back to Home

| Item | Working as Intended | Link |
|------|-------------------|------|
| Start new game, flag some cells | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click a bomb: board reveals all bombs | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| LosePage shows correct time and unflagged bomb count | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Back to Home: Continue is greyed out | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 2.2 Lose → Play Again

| Item | Working as Intended | Link |
|------|-------------------|------|
| Click a bomb: LosePage appears | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Play Again: fresh board, timer resets, hints reset to 3, bomb counter resets | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 2.3 Lose → Play Again → Lose Again

| Item | Working as Intended | Link |
|------|-------------------|------|
| Use 2 hints, then click a bomb | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| LosePage appears | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Click Play Again: hints reset to 3 on new game | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 2.4 Lose → Close X Button

| Item | Working as Intended | Link |
|------|-------------------|------|
| Click a bomb: LosePage appears | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Close LosePage with X button: Play Again behaviour triggers (board resets) | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 2.5 Chord Triggering Lose

| Item | Working as Intended | Link |
|------|-------------------|------|
| Reveal a numbered cell, place flags equal to its number | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Left click the numbered cell: adjacent unflagged bomb revealed | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| LosePage appears | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

---

## 3. Win UI Tests

### 3.1 Win → Close WinPage with X → Home → Leaderboard

| Item | Working as Intended | Link |
|------|-------------------|------|
| Win a game | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Close WinPage with X button: returns to Home, Continue greyed out | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Click Leaderboard: no entry was added | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 3.2 Win → Submit Name → Leaderboard → X → Verify Board Resets

| Item | Working as Intended | Link |
|------|-------------------|------|
| Win a game, submit name on WinPage | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Leaderboard appears: entry shown | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Close Leaderboard with X: GamePage shows fresh empty board | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Timer at 00:00.000, hints reset to 3 | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 3.3 Win → Submit Name → Leaderboard → Home → Continue Greyed Out

| Item | Working as Intended | Link |
|------|-------------------|------|
| Win a game, submit name | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| On Leaderboard, click ← Home | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |
| Home shows Continue greyed out | ✓ | [▶](ui_test/UI_TEST_1_13042026.mp4) |

### 3.4 Win via Hint Only

| Item | Working as Intended | Link |
|------|-------------------|------|
| Keep clicking Hint until all safe cells are revealed | ✓ | [▶](ui_test/UI_TEST_3_13042026.mp4) |
| WinPage appears automatically after hint triggers win | ✓ | [▶](ui_test/UI_TEST_3_13042026.mp4) |

### 3.5 Win via Continue

| Item | Working as Intended | Link |
|------|-------------------|------|
| Close app mid-game, overwrite save with pre-made near-win state | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| Continue game, reveal last safe cell | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |
| WinPage appears | ✓ | [▶](ui_test/UI_TEST_4_13042026.mp4) |

---

## 4. Logic Tests

### 4.1 Flag Behaviour

| Item | Working as Intended | Link |
|------|-------------------|------|
| Right click unrevealed cell: flag appears, bomb counter decrements | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Right click flagged cell: flag removed, bomb counter increments | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Left click flagged cell: nothing happens (no reveal) | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Reveal a cell, right click it: nothing happens | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 4.2 Floodfill Behaviour

| Item | Working as Intended | Link |
|------|-------------------|------|
| Click a cell with 0 adjacent bombs: all connected empty cells revealed | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Flagged cells adjacent to empty area are NOT revealed by floodfill | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Flagged cells can still be unflagged after floodfill | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

### 4.3 Chording Behaviour

| Item | Working as Intended | Link |
|------|-------------------|------|
| Reveal a numbered cell, place flags equal to its number, left click: adjacent cells revealed | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Place fewer flags than the number, left click: nothing happens | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |
| Place flags on wrong cells, chord: lose triggers if unflagged bomb adjacent | ✓ | [▶](ui_test/UI_TEST_2_13042026.mp4) |

---

## Extreme UI Tests

`⚠️ Run these separately on a disposable copy. These tests are likely to crash or break the app.`

### E1. Bomb Count Edge Cases
*(Modify `Config.MIN_BOMBS` and `Config.MAX_BOMBS`)*

| Item | Working as Intended | Link |
|------|-------------------|------|
| MIN=0, MAX=1: app handles 0 bomb board | | |
| MIN=98, MAX=99: near-full bomb board works | | |
| MIN=100, MAX=101: fully bombed board (no safe cells) | | |

### E2. Grid Size Edge Cases
*(Modify `Config.BOARD_SIZE_ROW` and `Config.BOARD_SIZE_COL`)*

| Item | Working as Intended | Link |
|------|-------------------|------|
| 1x1 grid: app does not crash on floodfill | | |
| 2x2 grid: basic gameplay works | | |
| 5x10 grid: non-square grid renders correctly | | |
| 8x4 grid: asymmetric grid works | | |
| 12x12 grid: larger grid fits in window | | |
| 50x50 grid: performance and window overflow | | |

### E3. Timer File Edge Cases
*(Modify `data/time.txt` directly)*

| Item | Working as Intended | Link |
|------|-------------------|------|
| Set -10000: timer handles negative time gracefully | | |
| Set 0: timer starts from 00:00.000 | | |
| Set 10000: timer loads and resumes from 00:00.010 | | |
| Set 999999999999999999 (near Long.MAX_VALUE): no overflow crash | | |
| Set abc (non-numeric): parse error handled gracefully, defaults to 0 | | |