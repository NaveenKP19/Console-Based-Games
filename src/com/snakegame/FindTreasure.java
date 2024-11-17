package com.snakegame;

import java.util.Random;
import java.util.Scanner;

public class FindTreasure {
    static final int SIZE = 15; // Dungeon grid size
    static char[][] dungeon = new char[SIZE][SIZE];
    static int playerRow, playerCol;
    static int playerHealth = 100;
    static int treasures = 0;
    static boolean gameRunning = true;
    static Random random = new Random();

    public static void main(String[] args) {
        initializeDungeon();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Dungeon Explorer!");
        System.out.println("Find treasures (T), avoid traps (X), and escape through the Exit (E).");
        System.out.println("Your health: " + playerHealth + "\n");

        while (gameRunning) {
            printDungeon();
            System.out.print("Move (W/A/S/D): ");
            char move = scanner.nextLine().toUpperCase().charAt(0);
            makeMove(move);
        }

        scanner.close();
    }

    // Initialize dungeon grid and place player, treasures, traps, and exit
    static void initializeDungeon() {
        // Fill grid with empty spaces
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                dungeon[i][j] = '.';
            }
        }

        // Place player
        playerRow = random.nextInt(SIZE);
        playerCol = random.nextInt(SIZE);
        dungeon[playerRow][playerCol] = 'P';

        // Place treasures (T), traps (X), and exit (E)
        placeRandom('T', 3); // 3 treasures
        placeRandom('X', 3); // 3 traps
        placeRandom('E', 1); // 1 exit
    }

    // Place a specific number of an element on the grid at random empty positions
    static void placeRandom(char element, int count) {
        while (count > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (dungeon[row][col] == '.') {
                dungeon[row][col] = element;
                count--;
            }
        }
    }

    // Print the dungeon grid
    static void printDungeon() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print("P "); // Player position
                } else {
                    System.out.print(dungeon[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Handle player movement
    static void makeMove(char direction) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (direction) {
            case 'W': newRow--; break; // Up
            case 'A': newCol--; break; // Left
            case 'S': newRow++; break; // Down
            case 'D': newCol++; break; // Right
            default:
                System.out.println("Invalid move! Use W, A, S, or D.");
                return;
        }

        // Check if the move is within bounds
        if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) {
            System.out.println("You hit a wall! Try a different direction.");
            return;
        }

        // Process the cell the player is moving to
        char cell = dungeon[newRow][newCol];
        if (cell == 'T') {
            System.out.println("You found a treasure!");
            treasures++;
        } else if (cell == 'X') {
            System.out.println("You triggered a trap! Lost 20 health.");
            playerHealth -= 20;
            if (playerHealth <= 0) {
                System.out.println("You have perished in the dungeon. Game Over!");
                gameRunning = false;
                return;
            }
        } else if (cell == 'E') {
            System.out.println("You found the exit!");
            System.out.println("Congratulations! Treasures collected: " + treasures);
            gameRunning = false;
            return;
        }

        // Update player position
        dungeon[playerRow][playerCol] = '.'; // Clear the old position
        playerRow = newRow;
        playerCol = newCol;
    }
}
