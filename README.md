# tP-Minesweeper
Team Project for CS2103DE

## v0.7 - Release

#### Refactor: UI/Storage Separation
- [ ] Audit all UI classes (`GamePage`, `HomePage`, `WinPage`, `LosePage`, `LeaderboardPage`, `HelpPage`) for any direct `Storage` references
- [ ] Move all `Storage` interactions out of UI layer. Only `logic/` classes should call `Storage` methods
- [ ] `GamePage` currently holds a `Storage` reference and passes it to `Gameboard` methods. Route all storage calls through `Gameboard` instead
- [ ] Verify `HomePage` only reads save existence via `Gameboard` or a dedicated method, not `Storage` directly
- [ ] Run full regression test after refactor to ensure no behaviour change

#### Extension 1: Bomb Counter on GamePage
- [ ] Add a live bomb counter to the `GamePage` header showing remaining unflagged bombs
- [ ] Counter decrements when a flag is placed, increments when a flag is removed
- [ ] `getUnflaggedBombCount()` already exists in `Gameboard`. Wire it to a header label
- [ ] Update `updateDisplay()` to refresh the counter after every flag action and board reset

#### User Guide
- Complete all sections + 1 GIF each feature

#### Developer Guide
- [ ] Architecture overview diagram: show `ui`, `logic`, `storage` package interaction
- [ ] Design section: explain method injection decision, callback pattern for win/lose flow
- [ ] Implementation section: sequence diagrams for first click safety, hint, chording, save/continue
- [ ] Update class diagram in DrawIO with current class list (if needed)
- [ ] Testing section: link to docs for UI test MP4s, unit test summary
- [ ] Appendix: Requirements: paste PRD

#### UI Testing
- [ ] Record MP4/GIF for each major flow: new game, continue, win, lose, hint, chording, leaderboard, help
- [ ] Test on other type of devices (linux/macos?)