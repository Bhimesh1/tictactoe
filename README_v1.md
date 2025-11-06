# Tic-Tac-Toe (Java 21, Maven)

A simple, clean Tic-Tac-Toe **library** with a minimal **CLI** that uses it.

## Build

```bash
mvn -q -DskipTests package

Run CLI
mvn -q exec:java -Dexec.mainClass=dev.bhim.tictactoe.cli.Main
```

## Structure

* `dev.bhim.tictactoe.core` — pure game engine (no I/O)
* `dev.bhim.tictactoe.cli` — command-line UI using the engine

## Roadmap

* Core engine + tests
* CLI game loop (names, moves, board, result)
* (Bonus) computer player / AI
* (Bonus) Kotlin notebook demo

---

# Dev Log

## Phase 1 — Project scaffold (Java 21, Maven)

* Created single-module Maven project.
* Set up packages for core (library) and cli (UI).
* Added minimal CLI main to verify run.
* Rationale: keep it simple; separate logic from UI; add features incrementally.
