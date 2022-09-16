package com.company;

import java.util.Scanner;


public class Board {
    private Pawn[][] board;
    private Scanner scanner;
    private int n;
    private Game game;


    Board() {
        // specify board size
        scanner = new Scanner(System.in);
        System.out.println("Type in an integer between 10 & 20 to specify board size: ");
        String input = scanner.nextLine();
        n = Integer.parseInt(input); //parse input

        // validate input between values
        while (n < 10 || n > 20) {
            System.out.println("Wrong value specified, try between 10 & 20: ");
            input = scanner.nextLine();
            n = Integer.parseInt(input);
        }

        // set pawns on board
        board = setPawns(new Pawn[n][n], n);
    }



    private Pawn[][] setPawns(Pawn[][] board, int n) {
        return setWhitePawns(setBlackPawns(board, n), n);

    }

    private Pawn[][] setWhitePawns(Pawn[][] board, int n) {
        int whitePieces = n * 2;
        for (int row = board.length - 1; row >= 0; row--) {
            if ((row - 1) % 2 == 0) {
                for (int i = 0; i < board[row].length; i += 2) {
                    if (whitePieces > 0) {
                        board[row][i] = new Pawn("white", row, i);
                        whitePieces--;
                    }
                }
            } else {
                for (int i = 1; i < board[row].length; i += 2) {
                    if (whitePieces > 0) {
                        board[row][i] = new Pawn("white", row, i);
                        whitePieces--;
                    }
                }
            }
        }
        return board;
    }

    private Pawn[][] setBlackPawns(Pawn[][] board, int n) {
        int blackPieces = n * 2;
        for (int row = 0; row < board.length; row++) {
            if ((row + 1) % 2 == 0) {
                for (int i = 0; i < board[row].length; i += 2) {
                    if (blackPieces > 0) {
                        board[row][i] = new Pawn("black", row, i);
                        blackPieces--;
                    }
                }
            } else {
                for (int i = 1; i < board[row].length; i += 2) {
                    if (blackPieces > 0) {
                        board[row][i] = new Pawn("black", row, i);
                        blackPieces--;
                    }
                }
            }
        }
        return board;
    }

    private void setWhite(Pawn[][] board, int n) {
        int pieces = n * 2;
    }

    public void setBoard(Pawn[][] board) {
        this.board = board;
    }

    public Pawn[][] getBoard() {
        return board;
    }


    public static void printBoard(Pawn[][] board) {
        int whitePawnCode = (int) 0x26AB;
        String puck = Character.toString((char) whitePawnCode);
        char[] abc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuilder displayBoard = new StringBuilder("    ");
        for (int row = 0; row < board.length; row++) {
            if (row < 9) {
                displayBoard.append(row + 1).append("  ");
            } else {
                displayBoard.append(row + 1).append(" ");
            }
        }
        displayBoard.append("\n");
        for (int row = 0; row < board.length; row++) {
            displayBoard.append(abc[row]).append("  ");
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null) {
                    if ((row + col) % 2 == 0) {
                        displayBoard.append("\u001b[47;1m" + "   " + "\u001b[0m");
                    } else {
                        displayBoard.append("   ");
                    }
                } else {
                    String s = board[row][col].toString();
                    if ("black".equals(s)) {
                        displayBoard.append("\u001b[33m" + " ").append(puck).append(" ").append("\u001b[0m");
                    } else if ("white".equals(s)) {
                        displayBoard.append(" ").append(puck).append(" ");
                    }
                }
            }
            displayBoard.append("\n");

        }
        System.out.println(displayBoard);
    }

    public static void removePawn(Pawn[][] board, int x, int y) {
        board[x][y] = null;
    }

    public int[] selectPawn() {
        return Game.getMove();
    }


    public void movePawn() {
        int[] selectedPawn = Game.getMove();
        int fromX = selectedPawn[0];
        int fromY = selectedPawn[1];
        int[] selectedField = Game.getMove();
        int toX = selectedField[0];
        int toY = selectedField[1];
        String pawnColor = board[fromX][fromY].toString();
        if ((pawnColor.equals("black") && (toX == (fromX + 1) && toY == (fromY + 1))) ||
                (pawnColor.equals("black") && (toX == (fromX + 1) && toY == (fromY - 1)))) {
            if (Game.canHit(board, fromX, fromY, toX, toY)) {
                Game.hitEnemy(board, fromX, fromY, toX, toY);
            }
            board[toX][toY] = board[fromX][fromY];
            removePawn(board, fromX, fromY);
        } else if ((pawnColor.equals("white") && (toX == (fromX - 1) && toY == (fromY + 1))) ||
                (pawnColor.equals("white") && (toX == (fromX -1) && toY == (fromY - 1)))) {
            if (Game.canHit(board, fromX, fromY, toX, toY)) {
                Game.hitEnemy(board, fromX, fromY, toX, toY);
            }
            board[toX][toY] = board[fromX][fromY];
            removePawn(board, fromX, fromY);
        }
    }

}