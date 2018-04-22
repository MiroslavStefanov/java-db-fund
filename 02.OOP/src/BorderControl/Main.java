package BorderControl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException{
        Scanner scanner = new Scanner(System.in);
        String line;
        ArrayList<Birthable> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        while(true)
        {
            line = scanner.nextLine();
            if(line.equals("End"))
                break;
            String[] data = line.split("\\s+");
            switch (data[0])
            {
                case "Citizen":
                    list.add(new Person(data[1], Integer.parseInt(data[2]), data[3], df.parse(data[4])));
                    break;
                case "Pet":
                    list.add(new Pet(data[1], df.parse(data[2])));
            }
        }
        int year = Integer.parseInt(scanner.nextLine());
        for(Birthable i : list)
            if(i.CheckBirthday(year))
                System.out.println(i.getBirthdate());
    }

}
