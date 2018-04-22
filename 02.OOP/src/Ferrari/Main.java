package Ferrari;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ferrari ferrari = new Ferrari(scanner.nextLine());
        System.out.print(Ferrari.getModel() + "/");
        ferrari.PushBrakes();
        System.out.print("/");
        ferrari.PushGas();
        System.out.print("/");
        System.out.println(ferrari.getDriver());
    }
}
