import java.util.Scanner;

public class VarInHex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = Integer.parseInt(scanner.nextLine(), 16);
        System.out.println(a);
    }
}
