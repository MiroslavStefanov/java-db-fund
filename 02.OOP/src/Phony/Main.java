package Phony;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] numbers = scanner.nextLine().split("\\s+");
        String[] urls = scanner.nextLine().split("\\s+");
        Smartphone smartphone = new Smartphone();
        for(String n : numbers)
            smartphone.Call(n);
        for(String url : urls)
            smartphone.Browse(url);
    }
}
