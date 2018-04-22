import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupByGroup {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Person> people = new ArrayList<>();
        String line;
        while(true)
        {
            line = scanner.nextLine();
            if(line.equals("END"))
                break;
            people.add(new Person(line));
        }
        Stream<Person> stream = people.stream();

        Map<Integer, List<Person>> peopleGroupped = stream.collect(Collectors.groupingBy(Person::getGroup));
        for(Integer group : peopleGroupped.keySet())
        {
            System.out.print(group + " - ");
            System.out.println(String.join(", ", peopleGroupped.get(group).stream().map(x -> x.getName()).collect(Collectors.toList())));
        }
    }

    public static class Person
    {
        private String name;
        private Integer group;

        public Person(String parse) {
            String[] args = parse.split("\\s+");
            name = args[0] + " " + args[1];
            group = Integer.parseInt(args[2]);
        }

        public String getName() {
            return name;
        }

        public Integer getGroup() {
            return group;
        }
    }
}
