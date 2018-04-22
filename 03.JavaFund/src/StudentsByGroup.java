import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class StudentsByGroup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        String line;
        while(true)
        {
            line = scanner.nextLine();
            if(line.equals("END"))
                break;
            students.add(new Student(line));
        }
        Stream<Student> stream = students.stream();
        /*stream.filter(x -> x.getGroup() == 2)
                .sorted((x, y) -> x.getName().compareTo(y.getName()))
                .forEach(x -> System.out.println(x.getName()));*/
        /*stream.filter(x -> x.compareNames() < 0)
                .forEach(x -> System.out.println(x.getName()));*/
        /*stream.filter(x -> x.getGroup() >= 18 && x.getGroup() <= 24)
                .forEach(x -> System.out.println(x.getName() + " " + x.getGroup()));*/
        /*stream.sorted(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                int c = o1.getLastName().compareTo(o2.getLastName());
                if(c == 0) {
                    return  o2.getFirstName().compareTo(o1.getFirstName());
                }
                else return c;
            }
        })
                .forEach(x -> System.out.println(x.getFirstName() + " " + x.getLastName()));*/
        /*stream.filter(x -> x.getEmail().substring(x.getEmail().indexOf('@')).equals("@gmail.com"))
                .forEach(x -> x.printName());*/
        /*stream.filter(x -> x.getEmail().substring(0, 2).equals("02") || x.getEmail().substring(0, 5).equals("+3592"))
                .forEach(x -> x.printName());*/
       /* stream.filter(x -> x.isExcellent())
                .forEach(x -> x.printName());*/
       stream.filter(x -> x.isWeak())
               .forEach(x -> x.printName());
    }

    public static class Student{
        private String firstName;
        private String lastName;
        private int group;
        private String email;
        private ArrayList<Integer> marks;

        public Student(String firstName, String lastName, int group, String email) {
            marks = new ArrayList<>();
            this.firstName = firstName;
            this.lastName = lastName;
            this.group = group;
            this.email = email;
        }

        public Student(String parse) {
            marks = new ArrayList<>();
            String[] args = parse.split("\\s+");
            firstName = args[0];
            lastName = args[1];
            //email = args[2];
            //group = Integer.parseInt(args[2]);
            for(int i = 2; i<args.length; i++)
                marks.add(Integer.parseInt(args[i]));
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getGroup() {
            return group;
        }

        public String getEmail() {
            return email;
        }

        public ArrayList<Integer> getMarks() {
            return marks;
        }

        /*public int compareNames()
        {
            String[] names = name.split("\\s+");
            return names[0].compareTo(names[1]);
        }*/

        public void printName()
        {
            System.out.println(firstName + " " + lastName);
        }

        public boolean isExcellent()
        {
            for(Integer mark : marks)
                if(mark == 6)
                    return true;
            return false;
        }

        public boolean isWeak()
        {
            int num = 0;

            for(Integer mark : marks)
                if(mark <= 3)
                    num++;

            return num >= 2;
        }
    }
}
