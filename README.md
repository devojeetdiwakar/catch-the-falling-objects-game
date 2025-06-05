# catch-the-falling-objects-game
A Java-based "Catch the Falling Objects" game with single and multiplayer modes, difficulty selection, sound effects, score saving via SQLite, and real-time UI with score/lives tracking. Built using Java Swing.

# 🎮 Catch the Falling Objects - Java Game

This is a fun and interactive Java-based desktop game where players catch falling objects using a movable basket. The game supports **single-player** and **two-player** modes, has **multiple difficulty levels**, **sound effects**, **real-time score/lives display**, and saves high scores using an embedded SQLite database.

## 🕹️ Features

- 🧍‍♂️ Single Player and 🧍‍♂️🧍‍♂️ Two-Player Modes (split-screen style)
- 💪 Difficulty Levels: Easy, Medium, Hard
- 🎯 Catch falling items to score points
- ⚠️ Bonus and Obstacle objects for power-ups and penalties
- 🔊 Sound effects for catching or missing objects
- ❤️ Lives and Score displayed with icons
- 💾 Score saving using SQLite database
- 🔁 Press `R` to restart game anytime
- 👀 Hint system for predicting object spawn
- 🎨 GUI built using Java Swing

## 📸 Screenshots

*(Include screenshots here if available)*

## 🧱 Requirements

- Java JDK 8 or higher
- SQLite JDBC driver (included in most setups)
- IntelliJ IDEA / VS Code / Eclipse (any IDE with Java support)

## 🚀 How to Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/catch-game-java.git
   cd catch-game-java
Compile the game:

javac CatchGame.java

Run the game:

java CatchGame

The game will prompt for your name and then ask for difficulty. Select one to begin.

Controls:

Single Player:

Move Left: ← or A

Move Right: → or D

Multiplayer (split screen):

Player 1: A (left), D (right)

Player 2: ← and → keys

Restart: R

🗃️ File Structure

CatchGame.java         // Main game logic
DatabaseManager.java   // SQLite database logic
resources/
├── catch.wav          // Sound when object is caught
├── miss.wav           // Sound when object is missed
├── ...
💾 Score Saving
Scores are saved locally using a lightweight SQLite database (leaderboard.db). The top 10 scores are printed in the console after each game over.

🛠️ Customization
You can:

Replace colored rectangles with images for basket and objects.

Adjust game speed or difficulty in setDifficulty() method.

Add more object types or power-ups.

📚 License
This project is open-source and available under the MIT License.

🙌 Acknowledgements
Developed by [Your Name], inspired by classic arcade-style games.
Sound effects and icons are sourced from royalty-free resources.

---

Let me know if you want:
- A version with image/icon integration instructions.
- GitHub Actions CI setup (for educational Java projects).
- Java `.jar` packaging instructions.
- Scoreboard UI in a separate panel/window.

I'll tailor it to your project style.
