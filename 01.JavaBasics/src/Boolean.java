import java.util.Scanner;

public class Boolean {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean b = java.lang.Boolean.parseBoolean(scanner.nextLine());
        if(b)
            System.out.println("Yes");
        else
            System.out.println("No");
    }
}
