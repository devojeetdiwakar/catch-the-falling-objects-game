# 🎮 Catch the Falling Objects - Java Game

A fun arcade-style game built in Java using Swing, where the player catches falling objects with a basket while avoiding obstacles and collecting bonuses. The game supports multiple difficulty levels, sound effects, hints, and stores high scores using a local SQLite database.

## 🚀 Features

- 🧺 Player-controlled basket to catch falling objects
- 🔥 Difficulty selection (Easy, Medium, Hard)
- 🎯 Bonus items (+5 points) and obstacle items (−3 points)
- 💡 Hint system with visual hints
- 🔊 Sound effects for catch and miss events
- 🕹️ Keyboard controls:
  - **Left Arrow** → Move basket left
  - **Right Arrow** → Move basket right
  - **R** → Restart the game
- 💾 High score saving with SQLite
- 🏆 Displays top 10 scores in the console after game over

---

## 🖼️ Game Preview

> You catch falling red blocks, collect yellow bonuses, and avoid black obstacles. Earn points and survive with limited lives.

---

🛠️ Tech Stack
Tech	Purpose
Java (Swing)	UI and game mechanics
SQLite	Store player high scores
Java Audio	Play .wav sound effects
JDBC	Database connectivity

---

🚀 How to Run
Clone or download the repository.
Open in any Java IDE (Eclipse, IntelliJ, VS Code).
Make sure catch.wav and miss.wav are correctly placed.
Run the CatchGame.java file.
🎉 Start playing!

javac CatchGame.java
java CatchGame

---

🏆 Leaderboard Sample

Top Scores:
Alice: 102
Bob: 85
Charlie: 78
...
Scores are saved in an SQLite database leaderboard.db and displayed in the console when the game ends.

---

🧠 Game Mechanics
Object	Color	Effect
Falling	Red	+1 point
Bonus	Yellow	+5 points
Obstacle	Black	−3 points
Hint Box	Blue	Shows where to move next

Lives: 🧡 10 to 20 (based on difficulty)
Game Over: When lives drop to 0
Restart: Press R

📂 Project Structure

CatchTheFallingObjects/
├── CatchGame.java         # Main game + UI
├── DatabaseManager.java   # Score handling (SQLite)
├── catch.wav              # Catch sound effect
├── miss.wav               # Miss sound effect
├── screenshot.png         # Game preview (optional)
└── leaderboard.db         # Auto-generated database

---


---

## 🛠️ Requirements

- Java 8 or later
- SQLite JDBC Driver (included in Java standard library for embedded)
- Compatible with IntelliJ, Eclipse, or any Java-supporting IDE

---

## 🧑‍💻 How to Run

1. **Clone or Download** this repository.
2. Open the project in your preferred Java IDE.
3. Make sure the `catch.wav` and `miss.wav` files are placed in the correct path.
4. Run the `CatchGame.java` file.
5. Enter your name, select a difficulty level, and start playing!

---

## 🧠 Gameplay Logic

- **Falling Objects (Red):** +1 point if caught, −1 life if missed.
- **Bonus Objects (Yellow):** +5 points when caught.
- **Obstacles (Black):** −3 points if caught.
- **Hints:** Occasionally show upcoming object positions in blue.
- **Game Over:** Triggered when lives reach 0. Press `R` to restart.

---

## 📊 Leaderboard

- Scores are stored in `leaderboard.db`.
- Top 10 scores are printed to the console after each game ends.
- Uses an embedded SQLite database with the table schema:

   sql
CREATE TABLE IF NOT EXISTS scores (
    name TEXT,
    score INTEGER
);

---

👨‍💻 Author
Devojeet Diwakar
🎓 B.Tech Student, Amity University
💡 Java Developer | Blockchain & IoT Enthusiast
