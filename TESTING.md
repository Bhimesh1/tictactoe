# Testing

## Purpose

The goal of testing in this project is to ensure that the core game logic behaves correctly and remains stable as new features and user interfaces are added. All tests are focused on the library layer (`dev.bhim.tictactoe.core`) and do not involve input/output.

## What Is Tested

The tests verify the rules of Tic-Tac-Toe at the data and game state level.

### Board Tests

Located in `src/test/java/dev/bhim/tictactoe/core/BoardTest.java`

These tests cover:

* **Initial State**: A new board starts empty.
* **Placing Marks**: A player can place an `X` or `O` in an empty cell.
* **Invalid Placement**: Attempting to place a symbol in an occupied cell results in an exception.
* **Full Board Detection**: The board correctly reports when all cells are filled.
* **Winner Detection**:

    * Horizontal wins
    * Vertical wins
    * Diagonal wins
    * No-win scenarios

### Game Tests

Located in `src/test/java/dev/bhim/tictactoe/core/GameTest.java`

These tests confirm:

* **Turn Order**: `X` always moves first.
* **Turn Switching**: Turns alternate correctly after each move.
* **Status Evaluation**: `status()` returns the correct `GameStatus` value (`IN_PROGRESS`, `X_WINS`, `O_WINS`, `DRAW`).

## What Is Not Tested (By Design)

* **CLI Input/Output**: Tested through manual interaction.
* **GUI (Swing)**: Visual output is subjective, and correctness relies on the core library, which is already tested.

This approach keeps tests focused on deterministic logic, where expected outputs are clear.

## How to Run Tests

Linux/Mac:

```
./mvnw test
```

Windows PowerShell:

```
./mvnw.cmd test
```

Test reports are written to:

```
target/surefire-reports/
```


