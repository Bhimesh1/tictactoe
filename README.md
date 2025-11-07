# Tic-Tac-Toe

This project implements Tic-Tac-Toe as a **reusable and UI‑independent game library**. The core module contains all game logic, including board state, turn handling, and win/draw evaluation, without any user interface or input/output code. This separation allows the logic to be thoroughly tested and reused in different presentation layers.

The project includes three example user interfaces built on top of the same core **library**:

* **CLI** (terminal-based)
* **Swing GUI** (desktop application)
* **Web UI** (browser-based, runnable via Docker Compose)

The core game logic is fully separated in a library module (`dev.bhim.tictactoe.core`).
Each UI is just a thin layer on top.

---

## Why Three UIs?

I built the UIs in stages during development:

1. **CLI first** - to verify the game rules and flow quickly.
2. **Swing GUI** - to provide a more interactive playing experience.
3. **Web UI** - to make the game easier to run and demonstrate, especially for interviewers, with *no local setup* required (just Docker Compose).

Each UI uses the **same underlying game library**.
No logic is duplicated.


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
http://localhost:8090
```

To stop:

```bash
docker compose --profile web down
```

For other run options (CLI, Swing GUI, local runs), see the dedicated guide:

**→ See: RUNNING.md**

---

**Why Docker and Docker Compose?**

The Web UI is packaged in a Docker container so that it can run **without requiring Java to be installed** on the machine. Docker provides a consistent environment for running the application.

Docker Compose is used to define the run configuration (port mapping, environment variables, and startup command) so the Web UI can be started easily with **a single command.**



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



## Kotlin Notebook Demo

A Kotlin Notebook is included to **interactively explore** the game logic.

Open:

```
notebooks/TicTacToeDemo.ipynb
```

In IntelliJ IDEA:

1. Open the notebook
2. In the top‑right toolbar, set **Run in IDE Process** (or Use Project Classpath)
3. In the dependencies dropdown → select **tictactoe** library
4. Click **Run All**

This allows running and visualizing the game **without any UI**, ideal for explaining and experimenting with the logic.



---


## Additional Documentation

| File                              | Purpose                                                   |
| --------------------------------- | --------------------------------------------------------- |
| **ARCHITECTURE.md**               | High‑level structure of the core library and UIs.         |
| **DESIGN_DECISIONS.md**           | Reasoning behind key implementation choices.              |
| **RUNNING.md**                    | Detailed instructions for running CLI, Swing, and Web UI. |
| **TESTING.md**                    | Testing strategy and how to run tests.                    |
| **notebooks/TicTacToeDemo.ipynb** | Interactive Kotlin Notebook demo of core logic.           |

---

The architecture ensures:

* The game logic is **fully isolated and testable**.
* UIs are **replaceable and extendable**.
* New interfaces such as **AI players**, **WebSocket multiplayer**, or **mobile UIs** can be added without changing the core.
