package Animals;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line, data;
        ArrayList<Animal> animals = new ArrayList<>();
        while(true)
        {
            line = scanner.nextLine();
            if(line.equals("Beast!"))
                break;
            data = scanner.nextLine();
            String[] d = data.split("\\s+");
            int age = -1;
            try {
                age = Integer.parseInt(d[1]);
                if(age < 0)
                    System.out.println("Invalid input!");
                else {
                    switch (line)
                    {
                        case "Dog":
                            animals.add(new Dog(d[0], age, d[2]));
                            break;
                        case "Frog":
                            animals.add(new Frog(d[0], age, d[2]));
                            break;
                        case "Cat":
                            animals.add(new Cat(d[0], age, d[2]));
                            break;
                        case "Kittens":
                            animals.add(new Kittens(d[0], age, d[2]));
                            break;
                        case "Tomcat":
                            animals.add(new Tomcat(d[0], age, d[2]));
                            break;
                        default:
                            System.out.println("Invalid input!");
                    }
                    animals.get(animals.size() - 1)
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }
}
