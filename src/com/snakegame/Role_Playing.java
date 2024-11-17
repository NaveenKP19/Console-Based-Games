package com.snakegame;

import java.util.Scanner;
import java.util.Random;

class Character {
    String name;
    int health;
    int attackPower;

    public Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public boolean isAlive() {
        return health > 0;
    }
}

class Player extends Character {
    int potions;

    public Player(String name) {
        super(name, 100, 20); // Starting health and attack power
        this.potions = 3;     // Starting potions
    }

    public void heal() {
        if (potions > 0) {
            health += 30;
            potions--;
            System.out.println("You used a potion and healed 30 HP. Remaining potions: " + potions);
        } else {
            System.out.println("No potions left!");
        }
    }
}

class Enemy extends Character {
    public Enemy(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }
}

public class Role_Playing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Player creation
        System.out.println("Welcome to the RPG Adventure!");
        System.out.print("Enter your character's name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);

        System.out.println("Hello, " + player.name + "! Your adventure begins now...\n");

        // Game loop
        while (player.isAlive()) {
            System.out.println("You encounter a wild enemy!");

            // Generate a random enemy
            String[] enemyNames = {"Goblin", "Orc", "Skeleton", "Zombie"};
            String enemyName = enemyNames[random.nextInt(enemyNames.length)];
            int enemyHealth = random.nextInt(30) + 50; // Random health between 50-80
            int enemyAttack = random.nextInt(10) + 10; // Random attack power between 10-20
            Enemy enemy = new Enemy(enemyName, enemyHealth, enemyAttack);

            System.out.println("A " + enemy.name + " appears with " + enemy.health + " HP!");

            // Battle loop
            while (enemy.isAlive() && player.isAlive()) {
                System.out.println("\nYour HP: " + player.health + " | Enemy HP: " + enemy.health);
                System.out.println("1. Attack");
                System.out.println("2. Heal");
                System.out.println("3. Run");

                System.out.print("Choose an action: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: // Attack
                        int playerDamage = random.nextInt(player.attackPower) + 5;
                        enemy.health -= playerDamage;
                        System.out.println("You attack the " + enemy.name + " for " + playerDamage + " damage!");
                        break;

                    case 2: // Heal
                        player.heal();
                        break;

                    case 3: // Run
                        System.out.println("You ran away from the " + enemy.name + "!");
                        return; // Exit the game

                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }

                // Enemy's turn to attack
                if (enemy.isAlive()) {
                    int enemyDamage = random.nextInt(enemy.attackPower) + 5;
                    player.health -= enemyDamage;
                    System.out.println("The " + enemy.name + " attacks you for " + enemyDamage + " damage!");
                }
            }

            if (!enemy.isAlive()) {
                System.out.println("\nYou defeated the " + enemy.name + "!");
                System.out.println("You found a potion on the enemy!");
                player.potions++;
            }

            if (!player.isAlive()) {
                System.out.println("\nYou have been defeated...");
                System.out.println("Game over!");
                break;
            }
        }

        scanner.close();
    }
}
