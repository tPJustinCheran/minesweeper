# 📠 System Architecture Document 

## Architecture Overview
![Alt Text](images/SystemArchitecture.png)

| UI Layer                                                                                   | Logic Layer                      | Storage Layer                                                                                                                    | 
|:---------------------------------------------------------------------------------------------|:---------------------------------|:---------------------------------------------------------------------------------------------------------------------------------| 
| HomePage <br>GamePage<br>HelpPage<br>LeaderboardPage<br>WinPage<br>LosePage<br>ResourceManager | Gameboard<br>Box<br>CustomeTimer| Storage<br>-----------<br>data/game.txt<br>data/solution.txt<br>data/time.txt<br>data/hint.txt<br>data/leaderboard.txt           |

***

## UML Diagram 
![Alt Text](images/UMLDiagram.png)

*** 

## Sequence Diagram 
- Gameboard, Storage, Timer Sequential Diagram 
![Alt Text](images/GameboardStorageTimerSequential.png)
