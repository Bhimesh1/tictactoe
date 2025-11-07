# Architecture

## Overview

This project is organized around a clear separation of concerns:

* **Library layer (core):** Implements the rules, state, and mechanics of Tic-Tac-Toe. Contains no input/output.
* **User interface layers:** Provide different ways to play the game (CLI and Swing). These depend on the library but do not modify it.

This design ensures the game logic is testable and independent of any specific UI.

## Package Structure

```
dev.bhim.tictactoe.core
    Board         // 3x3 grid representation and win/draw checks
    Player        // Immutable record for player identity and symbol
    Game          // Manages turns, delegates to Board, exposes game status
    GameStatus    // IN_PROGRESS, X_WINS, O_WINS, DRAW

dev.bhim.tictactoe.cli
    Main          // Terminal-based UI

dev.bhim.tictactoe.gui
    SwingMain     // Desktop window UI using Swing
```

## Core Components

### Board

* Stores a 3x3 `char` grid.
* Valid values: `'X'`, `'O'`, `' '` (blank).
* Responsibilities:

    * Return board cell values (`get`)
    * Place moves with validation (`place`)
    * Detect full board (`isFull`)
    * Detect winner (`winner`)
    * Support simple visual display (`print`) for CLI

### Player

* Immutable data structure.
* Fields:

    * `name`: Human-readable identifier
    * `symbol`: `'X'` or `'O'`

### Game

* Holds:

    * The `Board`
    * Two players
    * Current turn
* Responsibilities:

    * Apply moves (`playMove`)
    * Swap turns
    * Report game progress and final status via `GameStatus`

### GameStatus

Indicates the state of the game:

* `IN_PROGRESS`
* `X_WINS`
* `O_WINS`
* `DRAW`

## Data Flow

1. UI requests move coordinates from a player.
2. UI calls:

   ```java
   game.playMove(row, col);
   ```
3. `Game` delegates to `Board.place()` and updates the current player.
4. UI queries the board state and displays it.
5. UI queries `game.status()` to determine if play continues or ends.

## Separation of Concerns

* The library does **not** read input or print output.
* The CLI and GUI rely on the same library API.
* This makes the code easier to test and maintain.

## Extensibility

New frontends can be added without modifying the core:

* Web UI
* Kotlin Notebook demonstration
* Computer-controlled player (random or strategy-based)

These would interact with `Game` and `Board` just like the existing UIs.
