import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentsEnrolled {
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

        stream.filter(x -> x.getFacultyNumber().substring(4).equals("14") || x.getFacultyNumber().substring(4).equals("15"))
                .forEach(x -> System.out.println(x.marksToString()));
    }

    public static class Student
    {
        private String facultyNumber;
        private ArrayList<Integer> marks;

        public Student(String parse) {
            marks = new ArrayList<>();
            String[] args = parse.split("\\s+");
            facultyNumber = args[0];
            for(int i = 1; i<args.length; i++)
                marks.add(Integer.parseInt(args[i]));
        }

        public String getFacultyNumber() {
            return facultyNumber;
        }

        public ArrayList<Integer> getMarks() {
            return marks;
        }

        public String marksToString()
        {
            return String.join(" ", marks.stream().map(x -> x.toString()).collect(Collectors.toList()));
        }
    }
}
