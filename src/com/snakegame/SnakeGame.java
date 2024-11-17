package com.snakegame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    static int width = 20, height = 10; // Grid size
    static char[][] board = new char[height][width];
    static ArrayList<int[]> snake = new ArrayList<>();
    static int[] food = new int[2];
    static String direction = "RIGHT";
    static boolean gameOver = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize game
        initGame();
        printBoard();

        // Game loop
        while (!gameOver) {
            System.out.println("Enter direction (U =Up /D= Down /L=Left /R=Right): ");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("U") && !direction.equals("DOWN")) direction = "UP";
            if (input.equals("D") && !direction.equals("UP")) direction = "DOWN";
            if (input.equals("L") && !direction.equals("RIGHT")) direction = "LEFT";
            if (input.equals("R") && !direction.equals("LEFT")) direction = "RIGHT";

            updateGame();
            printBoard();
        }

        System.out.println("Game Over! Your Score: " + (snake.size() - 1));
    }

    static void initGame() {
        // Initialize snake
        snake.clear();
        snake.add(new int[]{height / 2, width / 2});

        // Initialize food
        placeFood();

        // Initialize board
        updateBoard();
    }

    static void placeFood() {
        Random rand = new Random();
        while (true) {
            food[0] = rand.nextInt(height);
            food[1] = rand.nextInt(width);

            // Ensure food is not placed on the snake
            boolean onSnake = false;
            for (int[] part : snake) {
                if (part[0] == food[0] && part[1] == food[1]) {
                    onSnake = true;
                    break;
                }
            }
            if (!onSnake) break;
        }
    }

    static void updateGame() {
        // Get head position
        int[] head = snake.get(0);
        int newHeadRow = head[0];
        int newHeadCol = head[1];

        // Move the snake based on the direction
        switch (direction) {
            case "UP" -> newHeadRow--;
            case "DOWN" -> newHeadRow++;
            case "LEFT" -> newHeadCol--;
            case "RIGHT" -> newHeadCol++;
        }

        // Check for collisions
        if (newHeadRow < 0 || newHeadRow >= height || newHeadCol < 0 || newHeadCol >= width) {
            gameOver = true; // Hit the wall
            return;
        }
        for (int[] part : snake) {
            if (part[0] == newHeadRow && part[1] == newHeadCol) {
                gameOver = true; // Hit itself
                return;
            }
        }

        // Add new head
        snake.add(0, new int[]{newHeadRow, newHeadCol});

        // Check if the snake ate the food
        if (newHeadRow == food[0] && newHeadCol == food[1]) {
            placeFood(); // Generate new food
        } else {
            // Remove the tail (snake moves forward)
            snake.remove(snake.size() - 1);
        }

        // Update the board
        updateBoard();
    }

    static void updateBoard() {
        // Clear board
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = ' ';
            }
        }

        // Draw the snake
        for (int[] part : snake) {
            board[part[0]][part[1]] = 'O';
        }

        // Draw the food
        board[food[0]][food[1]] = '*';
    }

    static void printBoard() {
        for (int i = 0; i < width + 2; i++) System.out.print("#");
        System.out.println();

        for (int i = 0; i < height; i++) {
            System.out.print("#");
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println("#");
        }

        for (int i = 0; i < width + 2; i++) System.out.print("#");
        System.out.println();
    }
}
