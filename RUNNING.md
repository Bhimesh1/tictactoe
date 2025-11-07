# Running the Project

This project supports three user interfaces/run modes:

* **CLI Game** (terminal-based)
* **Web UI Game** (browser-based)
* **Swing GUI** (desktop application)

All can be run locally. The CLI and Web UI also have Docker/Compose options.

---

## 1. Running the CLI Game

### Using Docker Compose (recommended)

```bash
docker compose run --rm tictactoe-cli
```

### Running Locally (without Docker)

```bash
./mvnw exec:java -Dexec.mainClass=dev.bhim.tictactoe.cli.Main
```

#### Windows PowerShell

```powershell
.\mvnw.cmd exec:java "-Dexec.mainClass=dev.bhim.tictactoe.cli.Main"
```

---

## 2. Running the Web UI Game

### Using Docker Compose (recommended)

```bash
docker compose --profile web up -d
```

Visit in browser:

```
http://localhost:8060
```

Stop the service:

```bash
docker compose --profile web down
```

### Running Locally (without Docker)

```bash
./mvnw exec:java -Dexec.mainClass=dev.bhim.tictactoe.web.WebServer
```

Visit in browser:

```
http://localhost:8090
```

---

## 3. Running the Swing GUI (Desktop)

The Swing GUI is a desktop application and is intended to run on your host machine, not inside Docker. It requires access to the operating system's window environment.

### Run from IntelliJ IDEA

* Open the file: `src/main/java/dev/bhim/tictactoe/gui/SwingMain.java`
* Run the `main` method.

### Run Locally (without Docker)

```bash
./mvnw exec:java -Dexec.mainClass=dev.bhim.tictactoe.gui.SwingMain
```

#### Windows PowerShell

```powershell
.\mvnw.cmd exec:java "-Dexec.mainClass=dev.bhim.tictactoe.gui.SwingMain"
```

---

## 4. Rebuilding Docker Images

```bash
docker compose build
```

Without cache:

```bash
docker compose build --no-cache
```

---

## 5. Running Tests

```bash
./mvnw test
```

---

## 6. Troubleshooting

| Issue                                                | Cause                              | Solution                                                                             |
| ---------------------------------------------------- | ---------------------------------- | ------------------------------------------------------------------------------------ |
| Browser shows blank page                             | Wrong port opened                  | Use `http://localhost:8060` when using Docker Compose                                |
| `Bind for 0.0.0.0:PORT failed`                       | Host port already in use           | Change the **left** side of `ports:` in `docker-compose.yml` to a free host port     |
| CLI exits immediately in `compose up`                | `compose up` is not interactive    | Use `docker compose run --rm tictactoe-cli` instead                                  |
| PowerShell: Unknown lifecycle phase `.mainClass=...` | `-D` property parsed by PowerShell | Quote the property or use `--%`, e.g. `."mvnw.cmd" exec:java "-Dexec.mainClass=..."` |
