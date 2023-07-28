package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("!  Mayın Tarlası'na Hoşgeldiniz ! Lütfen Satır Sayısınız Giriniz : ");
        int satir = scanner.nextInt();
        System.out.print("Lütfen Sütun Sayısını Giriniz : ");
        int sutun = scanner.nextInt();

        MayinTarlasi oyun = new MayinTarlasi(satir, sutun);
    }
}