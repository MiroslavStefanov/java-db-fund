import java.util.Scanner;

public class VowelOrDigit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char a;
        a = scanner.nextLine().charAt(0);
        switch (a)
        {
            case 'a':
            case 'o':
            case 'i':
            case 'e':
            case 'u':
                System.out.println("vowel");
                break;
            default:
                if(a>= '0' && a<='9')
                    System.out.println("digit");
                else
                    System.out.println("other");
                break;
        }
    }
}
