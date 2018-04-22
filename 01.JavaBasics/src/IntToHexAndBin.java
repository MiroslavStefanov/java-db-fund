import java.util.Scanner;

public class IntToHexAndBin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        System.out.println(Integer.toHexString(n).toUpperCase());
        System.out.println(Integer.toBinaryString(n));
    }
}
