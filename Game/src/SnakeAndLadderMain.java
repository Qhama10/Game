import java.util.Random;
import java.util.Scanner;

// Inheritance + Information hiding //
class Game {
    private String gameName;   
    private int numberOfPlayers;

    // Constructor
    public Game(String gameName, int numberOfPlayers) {
        this.gameName = gameName;
        this.numberOfPlayers = numberOfPlayers;
    }

    // Getter
    public String getGameName() {
        return gameName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    
    public void startGame() {
        System.out.println("Starting " + gameName + " with " + numberOfPlayers + " players...");
    }

    public void showRules() {
        System.out.println("General game rules apply.");
    }
}


class Player {
    private String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0; // start at beginning
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void move(int steps) {
        if (position + steps <= 100) { // must land exactly on 100
            position += steps;
        }
    }

    public void setPosition(int newPosition) {
        this.position = newPosition;
    }
}

// Inheritance + Arrays + Loops //
class SnakeAndLadder extends Game {
    private int[] board;        
    private Player[] players;   
    private Random dice;        

    // Constructor
    public SnakeAndLadder(Player[] players) {
        super("Snakes and Ladders", players.length);
        this.players = players;
        this.board = new int[101]; // positions 1–100
        this.dice = new Random();
        setupBoard();
    }

    // Initialize snakes and ladders
    private void setupBoard() {
        // Ladders
        board[4] = 14;
        board[9] = 31;
        board[20] = 38;
        board[28] = 84;
        board[40] = 59;
        board[63] = 81;
        board[71] = 91;

        // Snakes
        board[17] = 7;
        board[54] = 34;
        board[62] = 19;
        board[64] = 60;
        board[87] = 24;
        board[93] = 73;
        board[95] = 75;
        board[99] = 10;
    }

    private int rollDice() {
        return dice.nextInt(6) + 1; // 1–6
    }

    // Override startGame()
    @Override
    public void startGame() {
        System.out.println(" Welcome to " + getGameName() + "!");
        System.out.println("Number of players: " + getNumberOfPlayers());
        System.out.println("First to reach 100 wins!\n");

        Scanner scanner = new Scanner(System.in);
        boolean won = false;

        while (!won) { // loop until someone wins
            for (Player player : players) {
                System.out.println("\n" + player.getName() + "'s turn. Press Enter to roll dice...");
                scanner.nextLine();

                int roll = rollDice();
                System.out.println(player.getName() + " rolled a " + roll);

                player.move(roll);
                int pos = player.getPosition();

                // Check for snake or ladder
                if (board[pos] != 0) {
                    int newPos = board[pos];
                    if (newPos > pos) {
                        System.out.println("Ladder! " + player.getName() + " climbs from " + pos + " to " + newPos);
                    } else {
                        System.out.println("Snake! " + player.getName() + " slides from " + pos + " to " + newPos);
                    }
                    player.setPosition(newPos);
                }

                System.out.println(player.getName() + " is now at position " + player.getPosition());

                // Win check
                if (player.getPosition() == 100) {
                    System.out.println("\n " + player.getName() + " WINS!");
                    won = true;
                    break;
                }
            }
        }
    }

    // Override rules
    @Override
    public void showRules() {
        System.out.println("====== Snake and Ladder Rules ======");
        System.out.println("1. Roll the dice to move forward.");
        System.out.println("2. Ladders move you up.");
        System.out.println("3. Snakes send you down.");
        System.out.println("4. You must land exactly on 100 to win.");
        System.out.println("===================================");
    }
}

// ===== Main class =====
public class SnakeAndLadderMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input players
        System.out.print("Enter number of players: ");
        int n = scanner.nextInt();
        scanner.nextLine(); 

        Player[] players = new Player[n];            // Array of players
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String name = scanner.nextLine();
            players[i] = new Player(name);
        }

        // Create game
        SnakeAndLadder game = new SnakeAndLadder(players);
        game.showRules();
        game.startGame();
    }
}
