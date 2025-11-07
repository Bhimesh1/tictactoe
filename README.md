# Tic-Tac-Toe (Java 21, Maven)

This project implements Tic-Tac-Toe as a **reusable and UI‑independent game library**. The core module contains all game logic, including board state, turn handling, and win/draw evaluation, without any user interface or input/output code. This separation allows the logic to be thoroughly tested and reused in different presentation layers.

A reusable Tic-Tac-Toe **library** with three example user interfaces:

* **CLI** (terminal-based)
* **Swing GUI** (desktop application)
* **Web UI** (browser-based, runnable via Docker Compose)

The core game logic is fully separated in a library module (`dev.bhim.tictactoe.core`).
Each UI is just a thin layer on top.

---

## Why Three UIs?

I built the UIs in stages during development:

1. **CLI first** – to verify the game rules and flow quickly.
2. **Swing GUI** – to provide a more interactive playing experience.
3. **Web UI** – to make the game easier to run and demonstrate, especially for interviewers, with *no local setup* required (just Docker Compose).

Each UI uses the **same underlying game library**.
No logic is duplicated.

---

## Requirements

* Java 21
* Maven Wrapper (included)
* Optional: Docker + Docker Compose (for Web UI)

---

## Project Structure

```
src/
  main/
    java/
      dev/bhim/tictactoe/core/   --> core library (Board, Game, Player, GameStatus)
      dev/bhim/tictactoe/cli/    --> CLI entrypoint (Main)
      dev/bhim/tictactoe/gui/    --> Swing GUI entrypoint (SwingMain)
      dev/bhim/tictactoe/web/    --> Web UI entrypoint (WebServer)
  test/
    java/
      dev/bhim/tictactoe/core/   --> unit tests for the library
```

---

## Build

Linux/macOS:

```bash
./mvnw -DskipTests package
```

Windows PowerShell:

```powershell
./mvnw.cmd -DskipTests package
```

---

## Running the Game

The simplest way to run and try the game is via the **Web UI** using Docker Compose:

```bash
docker compose --profile web up -d
```

Then open:

```
http://localhost:8060
```

To stop:

```bash
docker compose --profile web down
```

For other run options (CLI, Swing GUI, local runs), see the dedicated guide:

**→ See: RUNNING.md**

---

## Tests

Run all tests:

```bash
./mvnw test
```

Windows:

```powershell
./mvnw.cmd test
```

---

## Design Notes

* The core library contains **no I/O** → fully testable.
* UIs depend on the core module but the core does **not** depend on UIs.
* This separation allows easily adding:

  * AI players
  * WebSocket multiplayer
  * Kotlin Notebook demos

---

## Optional Next Enhancements

* Add a computer-controlled player
* Add a stronger game AI
* Add a Kotlin Notebook showcasing game interaction

---

## Additional Documentation

* **ARCHITECTURE.md** – High-level structure of the core library and UIs.
* **DESIGN_DECISIONS.md** – Rationale behind key implementation choices.
* **RUNNING.md** – Detailed instructions for running CLI, Swing, and Web UI.
* **TESTING.md** – Testing approach and instructions.