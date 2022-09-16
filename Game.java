package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Game {
    private Board board;
    private boolean gameIsRunning;

    public Game() throws InterruptedException {
        menu();
    }

    public void menu() throws InterruptedException {
        System.out.println("POLISH DRAUGHTS");
        TimeUnit.SECONDS.sleep(2);
    }

    public void promptEnterKey() {
        System.out.println("Hit \"ENTER\" to Start");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearScreen();
    }

    public static void clearScreen() {
        //System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void start() {
        int player = 1;
        promptEnterKey();
        this.board = new Board();
        clearScreen();
        this.gameIsRunning = true;
        while (gameIsRunning) {
            playRound();
            board.movePawn();
            player = player == 1 ? 2 : 1;
        }
    }


    private void playRound() {
        clearScreen();
        System.out.println("White moves first...");
        Board.printBoard(board.getBoard());

    }

    public boolean tryToMakeMove(Pawn[][] board, int fromX, int fromY, int toX, int toY) {
        return (startCoordinateIsAPawn(board, fromX, fromY) && coordinateIsEmpty(board, toX, toY) && coordinateIsBlack(board));
    }

    public boolean coordinateIsEmpty(Pawn[][] board, int toX, int toY) {
        return board[toX][toY] == null;
    }

    public boolean startCoordinateIsAPawn(Pawn[][] board, int fromX, int fromY) {
        return board[fromX][fromY] != null;
    }

    public boolean coordinateIsBlack(Pawn[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if ((row + col) % 2 != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean adjacentPawnCheck(Pawn[][] board, int fromX, int fromY) {
        return board[fromX + 1][fromY + 1] != null || board[fromX + 1][fromY - 1] != null ||
                board[fromX - 1][fromY + 1] != null || board[fromX - 1][fromY - 1] != null;
    }


    public static int[] getEnemyCoordinates(int fromX, int fromY, int toX, int toY) {
        if (((toX == (fromX + 2) && toY == (fromY + 2))) ||
                ((fromX == (toX - 2) && fromY == (toY - 2)))) {
            return new int[]{fromX + 1, fromY + 1};
        } else if (((toX == (fromX + 2) && toY == (fromY - 2))) ||
                (fromX == (toX - 2) && fromY == (toY + 2))) {
            return new int[]{fromX + 1, fromY - 1};
        } else if (((toX == (fromX - 2) && toY == (fromY - 2))) ||
                ((fromX == (toX + 2) && fromY == (toY + 2)))) {
            return new int[]{fromX - 1, fromY - 1};
        } else if (((toX == (fromX - 2) && toY == (fromY + 2))) ||
                ((fromX == (toX + 2) && fromY == (toY - 2)))) {
            return new int[]{fromX - 1, fromY + 1};
        }
        return null;
    }


    public static boolean canHit(Pawn[][] board, int fromX, int fromY, int toX, int toY) {
        int[] enemyCoordinates = getEnemyCoordinates(fromX, fromY, toX, toY);
        if (enemyCoordinates == null) {
            return false;
        } else {
            return !board[enemyCoordinates[0]][enemyCoordinates[1]].toString().equals(board[fromX][fromY].toString());
        }
    }

    public static void hitEnemy(Pawn[][] board, int fromX, int fromY, int toX, int toY) {
        int[] enemyCoordinates = getEnemyCoordinates(fromX, fromY, toX, toY);
        if (adjacentPawnCheck(board, fromX, fromY)) {
            Board.removePawn(board, enemyCoordinates[0], enemyCoordinates[1]);
        }
    }

    public String checkForWinner(Pawn[][] board) {
        String defeatedOpponent = checkForPawns(board);
        if (defeatedOpponent.equals("white")) {
            return "Black has won";
        } else if (defeatedOpponent.equals("black")) {
            return "White has won";
        }
        return "No winner yet";
    }

    public String checkForPawns(Pawn[][] board) {
        int blackPucks = 0;
        int whitePucks = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < row; col++) {
                if (board[row][col].toString().equals("white")) {
                    whitePucks += 1;
                } else if (board[row][col].toString().equals("black")) {
                    blackPucks += 1;
                }
            }
        }
        if (blackPucks == 0) {
            return "white";
        } else if (whitePucks == 0) {
            return "black";
        } else {
            return "neither";
        }
    }


    public static int[] getMove() {
        Scanner scanner;
        System.out.println("Please enter a coordinate!");
        scanner = new Scanner(System.in);
        int XCoordinate = -1;
        int YCoordinate = -1;
        String coordinates = scanner.nextLine();
        XCoordinate = (int) coordinates.toLowerCase().charAt(0) - 97;
        YCoordinate = coordinates.charAt(1) - 49;
        System.out.println(Arrays.toString(new int[]{XCoordinate, YCoordinate}));
        return new int[]{XCoordinate, YCoordinate};
    }


    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.start();
    }
}