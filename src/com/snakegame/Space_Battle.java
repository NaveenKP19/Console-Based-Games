package com.snakegame;

import java.util.Scanner;

public class Space_Battle {
    static class Player {
        String name;
        int health;
        int ammo;
        int x, y;

        public Player(String name, int health, int ammo, int x, int y) {
            this.name = name;
            this.health = health;
            this.ammo = ammo;
            this.x = x;
            this.y = y;
        }

        public boolean isAlive() {
            return health > 0;
        }

        public void move(char direction) {
            switch (direction) {
                case 'W': y = Math.max(0, y - 1); break; // Move up
                case 'A': x = Math.max(0, x - 1); break; // Move left
                case 'S': y = Math.min(4, y + 1); break; // Move down
                case 'D': x = Math.min(4, x + 1); break; // Move right
                default: System.out.println("Invalid move! Use W, A, S, D.");
            }
        }

        public void shoot(Player opponent) {
            if (ammo > 0) {
                ammo--;
                System.out.println(name + " shoots a missile!");
                if (Math.abs(x - opponent.x) <= 1 && Math.abs(y - opponent.y) <= 1) {
                    opponent.health -= 10;
                    System.out.println("Hit! " + opponent.name + " takes 10 damage. Health: " + opponent.health);
                } else {
                    System.out.println("Miss! " + opponent.name + " is out of range.");
                }
            } else {
                System.out.println(name + " is out of ammo!");
            }
        }

        public void displayStats() {
            System.out.println(name + " [Health: " + health + ", Ammo: " + ammo + "]");
        }
    }

    public static void displayGrid(Player player1, Player player2, boolean missileFired, Player shooter) {
        System.out.println("\nBattlefield:");
        System.out.println("   0   1   2   3   4");
        System.out.println("  +---+---+---+---+---+");

        for (int i = 0; i < 5; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 5; j++) {
                if (player1.x == j && player1.y == i) {
                    System.out.print(" >o<|"); // Player 1
                } else if (player2.x == j && player2.y == i) {
                    System.out.print(" <o>|"); // Player 2
                } else if (missileFired && shooter != null && Math.abs(j - shooter.x) <= 1 && Math.abs(i - shooter.y) <= 1) {
                    if (shooter.name.equals(player1.name)) {
                        System.out.print(" -->|"); // Player 1 missile
                    } else {
                        System.out.print("<-- |"); // Player 2 missile
                    }
                } else {
                    System.out.print("    |");
                }
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize players
        System.out.print("Enter Player 1's name: ");
        String player1Name = scanner.nextLine();
        Player player1 = new Player(player1Name, 100, 5, 0, 0);

        System.out.print("Enter Player 2's name: ");
        String player2Name = scanner.nextLine();
        Player player2 = new Player(player2Name, 100, 5, 4, 4); // Initial positions

        Player currentPlayer = player1;
        Player opponent = player2;

        while (player1.isAlive() && player2.isAlive()) {
            System.out.println("\n" + currentPlayer.name + "'s Turn");
            currentPlayer.displayStats();
            displayGrid(player1, player2, false, null);

            System.out.println("Choose an action:");
            System.out.println("1. Move (W/A/S/D)");
            System.out.println("2. Shoot");
            System.out.print("Enter choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            boolean missileFired = false;

            if (choice == 1) {
                System.out.print("Enter direction (W/A/S/D): ");
                char direction = scanner.nextLine().toUpperCase().charAt(0);
                currentPlayer.move(direction);
            } else if (choice == 2) {
                currentPlayer.shoot(opponent);
                missileFired = true;
            } else {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            // Display the battlefield with missile graphics
            displayGrid(player1, player2, missileFired, currentPlayer);

            // Check if the opponent is still alive
            if (!opponent.isAlive()) {
                System.out.println(opponent.name + " has been defeated!");
                break;
            }

            // Swap players
            Player temp = currentPlayer;
            currentPlayer = opponent;
            opponent = temp;
        }

        // End of game
        if (player1.isAlive()) {
            System.out.println(player1.name + " wins!");
        } else {
            System.out.println(player2.name + " wins!");
        }

        scanner.close();
    }
}
