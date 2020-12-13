package com.company;

public class Main {

    public static void main(String[] args) {
        for (int komnata = 1; komnata < 15; komnata++) {
            for (int workPlace = 1; workPlace < 9; workPlace++) {
                for (int rozetka = 1; rozetka < 3; rozetka++) {
                    System.out.println(komnata + "-" + workPlace + "-" + rozetka);
                }
            }
        }
    }

}
