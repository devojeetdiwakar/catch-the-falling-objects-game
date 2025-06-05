// Import required libraries
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;
// Main game class extending JPanel and implementing ActionListener
public class CatchGame extends JPanel implements ActionListener {
    private Timer timer; // Timer to update game frame
    private ArrayList<Rectangle> fallingObjects;// Lists for different object types
    private ArrayList<Rectangle> bonusObjects;
    private ArrayList<Rectangle> obstacleObjects;
    private Rectangle basket; // Player's basket
    private int score, lives; // Score and remaining lives
    private Random rand; // Random generator
    private boolean isRunning; // Game state flag
    private int difficulty; // 0: Easy, 1: Medium, 2: Hard
    private int objectSpeed, spawnRate; // Controls for game difficulty
    private boolean showHint; // Whether to show hint box
    private Rectangle hintObject; // Hint rectangle
    private String playerName; // Player's name
    private DatabaseManager dbManager = new DatabaseManager(); // Score database manager
// Constructor
    public CatchGame(String playerName) {
        this.playerName = playerName;
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.CYAN);
        this.setFocusable(true);

        // Initialize player basket and game state
        this.basket = new Rectangle(275, 500, 50, 30);
        this.fallingObjects = new ArrayList<>();
        this.bonusObjects = new ArrayList<>();
        this.obstacleObjects = new ArrayList<>();
        this.score = 0;
        this.lives = 20;
        this.rand = new Random();
        this.isRunning = true;

        // Set difficulty
        String[] options = { "Easy", "Medium", "Hard" };
        difficulty = JOptionPane.showOptionDialog(null, "Select Difficulty Level", "Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        setDifficulty(difficulty);

        this.timer = new Timer(20, this);
        this.timer.start();
        
        // Key controls
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && basket.x > 0) {
                    basket.x -= 15;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && basket.x < getWidth() - basket.width) {
                    basket.x += 15;
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                }
            }
        });

        spawnObject();
    }

    // Set difficulty level parameters
    private void setDifficulty(int level) {
        this.difficulty = level;
        switch (level) {
            case 0: // Easy
                objectSpeed = 3;
                spawnRate = 30; // 1 in 30 chance
                basket.setSize(70, 30); // Bigger basket
                break;
            case 1: // Medium
                objectSpeed = 5;
                spawnRate = 20; // 1 in 20 chance
                basket.setSize(50, 30); // Normal basket
                break;
            case 2: // Hard
                objectSpeed = 7;
                spawnRate = 10; // 1 in 10 chance
                basket.setSize(40, 30); // Smaller basket
                break;
        }
    }

    // Spawn falling, bonus, and obstacle objects
    private void spawnObject() {
        int x = rand.nextInt(550);
        fallingObjects.add(new Rectangle(x, 0, 40, 40));
        if (rand.nextInt(5) == 0) { // 20% chance to spawn a bonus object
            bonusObjects.add(new Rectangle(x, 0, 40, 40));
        }
        if (rand.nextInt(10) == 0) { // 10% chance to spawn an obstacle
            obstacleObjects.add(new Rectangle(x, 0, 40, 40));
        }
    }

    /*private boolean isObjectFalling = false; // Flag to track if an object is falling

private void spawnObject() {
    if (!isObjectFalling) { // Only spawn if no object is currently falling
        int x = rand.nextInt(550);
        fallingObjects.add(new Rectangle(x, 0, 40, 40));
        isObjectFalling = true; // Set the flag to true
        if (rand.nextInt(5) == 0) { // 20% chance to spawn a bonus object
            bonusObjects.add(new Rectangle(x, 0, 40, 40));
        }
        if (rand.nextInt(10) == 0) { // 10% chance to spawn an obstacle
            obstacleObjects.add(new Rectangle(x, 0, 40, 40));
        }
    }
}*/

    

    // Paint game elements on screen
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(basket.x, basket.y, basket.width, basket.height);

        g.setColor(Color.RED);
        for (Rectangle obj : fallingObjects) {
            g.fillRect(obj.x, obj.y, obj.width, obj.height);
        }

        g.setColor(Color.YELLOW);
        for (Rectangle bonus : bonusObjects) {
            g.fillRect(bonus.x, bonus.y, bonus.width, bonus.height);
        }

        g.setColor(Color.BLACK);
        for (Rectangle obstacle : obstacleObjects) {
            g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }

        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
        g.drawString("Difficulty: " + (difficulty == 0 ? "Easy" : difficulty == 1 ? "Medium" : "Hard"), 10, 60);

        if (!isRunning) {
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.drawString("Game Over! Press R to Restart", 115, 300);
            displayTopScores();
        }

        if (showHint && hintObject != null) {
            g.setColor(Color.BLUE);
            g.drawRect(hintObject.x, hintObject.y, hintObject.width, hintObject.height);
        }
    }
    // Main game loop

    public void actionPerformed(ActionEvent e) {
        if (!isRunning) {
            return;
        }
            // Handle falling objects
        for (int i = 0; i < fallingObjects.size(); i++) {
            Rectangle obj = fallingObjects.get(i);
            obj.y += objectSpeed;

            if (obj.y > getHeight()) {
                playSound("/Users/devojeetdiwakar/Desktop/CatchTheFallingObjects/miss.wav");
                lives--;
                fallingObjects.remove(i);
                i--;
               // isObjectFalling = false; // Reset the flag when the object goes off-screen
                if (lives <= 0) {
                    isRunning = false;
                    timer.stop();
                }
            } else if (obj.intersects(basket)) {
                playSound("/Users/devojeetdiwakar/Desktop/CatchTheFallingObjects/catch.wav");
                score++;
                fallingObjects.remove(i);
                i--;
               // isObjectFalling = false; // Reset the flag when the object is caught
            }
        }
        // Bonus objects
        for (int i = 0; i < bonusObjects.size(); i++) {
            Rectangle bonus = bonusObjects.get(i);
            bonus.y += objectSpeed;

            if (bonus.y > getHeight()) {
                bonusObjects.remove(i);
                i--;
            } else if (bonus.intersects(basket)) {
                score += 5; // Bonus points
                bonusObjects.remove(i);
                i--;
            }
        }
        // Obstacle objects
        for (int i = 0; i < obstacleObjects.size(); i++) {
            Rectangle obstacle = obstacleObjects.get(i);
            obstacle.y += objectSpeed;

            if (obstacle.y > getHeight()) {
                obstacleObjects.remove(i);
                i--;
            } else if (obstacle.intersects(basket)) {
                score -= 3; // Penalty points
                obstacleObjects.remove(i);
                i--;
            }
        }

        // Spawn new object
        if (rand.nextInt(spawnRate) == 0) {
            spawnObject();
        }
        
        // Show hint occasionally
        showHint = rand.nextInt(100) < 5; // 5% chance to show hint
        if (showHint) {
            hintObject = new Rectangle(rand.nextInt(350), 0, 30, 30);
        } else {
            hintObject = null;
        }

        repaint();
    }
    // Restart the game
    private void restartGame() {
        dbManager.saveScore(playerName, score); // Save score before restarting
        this.score = 0;
        this.lives = 10;
        this.fallingObjects.clear();
        this.bonusObjects.clear();
        this.obstacleObjects.clear();
        this.basket.setLocation(175, 500);
        this.isRunning = true;
        this.timer.start();
        repaint();
    }
     // Play sound
    private void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                System.err.println("Audio file not found: " + soundFilePath);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Display top 10 scores in console
    private void displayTopScores() {
        List<String> topScores = dbManager.getTopScores();
        System.out.println("Top Scores:");
        for (String entry : topScores) {
            System.out.println(entry);
        }
    }
    // Entry point
    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog("Enter your name:");
        JFrame frame = new JFrame("Catch the Falling Objects");
        CatchGame game = new CatchGame(playerName);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}
// Manages saving and retrieving scores
class DatabaseManager {
    private static final String URL = "jdbc:sqlite:leaderboard.db";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS scores (name TEXT, score INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// Save score to DB
    public void saveScore(String playerName, int score) {
        String sql = "INSERT INTO scores (name, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Retrieve top 10 scores
    public List<String> getTopScores() {
        List<String> scores = new ArrayList<>();
        String sql = "SELECT name, score FROM scores ORDER BY score DESC LIMIT 10";
        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                scores.add(rs.getString("name") + ": " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}





