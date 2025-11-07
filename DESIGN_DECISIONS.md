# Design Decisions

## Objective

The purpose of this project is to implement the core logic of Tic-Tac-Toe in a way that is independent from any input/output mechanism. This allows the same game logic to be used in different environments (CLI, GUI, notebook, web, etc.) without modification.

---

## Core and UI Separation

**Decision:** The core game logic is placed in the `dev.bhim.tictactoe.core` package, and the user interfaces (CLI, GUI) are placed in separate packages.

**Reasoning:**

* The rules of the game should not depend on how the player interacts with it.
* Maintaining UI and logic separately avoids duplicated game logic across interfaces.
* This makes the code easier to test because the core has no I/O.

**Alternative considered:** Mix input/output directly into game logic.

* **Rejected** because it makes testing difficult and prevents reusing the logic.

---

## Data Representation

**Decision:** The board is represented as a 3x3 `char` array with values `'X'`, `'O'`, or `' '`.

**Reasoning:**

* Simple and efficient.
* Easy to print for CLI.
* Easy to index from UI code.
* Fits naturally into winner detection logic.

**Alternative considered:** Immutable board objects returning new instances on each move.

* **Rejected for this project** due to additional complexity. Could be beneficial in a functional or concurrency-heavy environment.

---

## Responsibility Allocation

**Board**: State and rules for move legality + winner detection.
**Game**: Turn management and interpretation of board state into game progress.
**Player**: Simple value structure to associate name and symbol.

**Why?**

* `Board` should not know about players.
* `Game` should not know how output is displayed.
* UI should not perform any game rule logic.

---

## Turn Handling

**Decision:** `Game` swaps the active player after each valid move.

**Reasoning:**

* Keeps turn state localized.
* Avoids embedding turn logic into UI or Board.

---

## Testing Strategy

**Decision:** All rule logic is tested through unit tests in `dev.bhim.tictactoe.core`.

**Reasoning:**

* The core is deterministic and can be tested without UIs.
* The UI code is intentionally thin; manual testing is sufficient for this context.

**Alternative considered:** UI automation testing.

* **Out of Scope** for this project size.

---

## Extensibility

This design allows adding new features without modifying the core:

* Computer players (random or AI)
* Web UI
* Kotlin Notebook demonstrations
* Plugin embedding

**Reasoning:**
The `Game` public API exposes enough functionality to drive gameplay from any environment.

---

## Summary

The architecture prioritizes clarity, separation of concerns, and testability. The core remains stable and reusable, while UI layers are free to vary or be replaced entirely in the future.
