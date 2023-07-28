package org.example;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
public class MayinTarlasi {
    private int satir;
    private int sutun;
    private int[][] mayinAlani;
    private boolean[][] revealed;
    private int remainingCells;

    public MayinTarlasi(int rows, int cols) {
        this.satir = rows;
        this.sutun = cols;
        this.mayinAlani = new int[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.remainingCells = rows * cols;

        // Mayınları yerleştir
        placeMines(rows * cols / 4);

        // Oyunu başlat
        startGame();
    }

    private void placeMines(int mineCount) {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < mineCount) {
            int row = random.nextInt(satir);
            int col = random.nextInt(sutun);

            if (mayinAlani[row][col] != -1) {
                mayinAlani[row][col] = -1; // Mayını işaretle
                minesPlaced++;
            }
        }
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (remainingCells > 0) {
            printField();
            int row, col;

            do {
                System.out.print("Satır seçin (0-" + (satir - 1) + "): ");
                row = scanner.nextInt();
                System.out.print("Sütun seçin (0-" + (sutun - 1) + "): ");
                col = scanner.nextInt();
            } while (!isValidMove(row, col));

            if (mayinAlani[row][col] == -1) {
                revealField();
                System.out.println("Kaybettiniz!");
                printField();
                return;
            } else {
                revealCell(row, col);
            }
        }

        System.out.println("Kazandınız !");
        printField();
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < satir && col >= 0 && col < sutun && !revealed[row][col];
    }

    private void revealCell(int row, int col) {
        if (!isValidMove(row, col)) {
            return;
        }

        if (mayinAlani[row][col] != -1) {
            int minesAround = countMinesAround(row, col);
            mayinAlani[row][col] = minesAround;
            revealed[row][col] = true;
            remainingCells--;

            if (minesAround == 0) {
                // Etrafındaki tüm boş hücreleri aç
                revealCell(row - 1, col);
                revealCell(row + 1, col);
                revealCell(row, col - 1);
                revealCell(row, col + 1);
                revealCell(row - 1, col - 1);
                revealCell(row - 1, col + 1);
                revealCell(row + 1, col - 1);
                revealCell(row + 1, col + 1);
            }
        }
    }

    private int countMinesAround(int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < satir && j >= 0 && j < sutun && mayinAlani[i][j] == -1) {
                    count++;
                }
            }
        }

        return count;
    }

    private void revealField() {
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                revealed[i][j] = true;
            }
        }
    }

    private void printField() {
        System.out.println("Harita:");
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                if (revealed[i][j]) {
                    if (mayinAlani[i][j] == -1) {
                        System.out.print("* ");
                    } else {
                        System.out.print(mayinAlani[i][j] + " ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
