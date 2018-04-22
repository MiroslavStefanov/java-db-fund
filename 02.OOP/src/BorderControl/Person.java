package BorderControl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Person implements Identifiable, Birthable {
    private String name;
    private int age;
    private String id;
    private Date birthdate;

    public Person(String name, int age, String id, Date birthdate) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.birthdate = birthdate;
    }

    @Override
    public boolean CheckIfFake(String fake) {
        if(id.substring(id.length() - fake.length()).equals(fake))
            return true;
        return false;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean CheckBirthday(int year) {
        LocalDate localDate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear() == year;
    }

    @Override
    public String getBirthdate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(birthdate);
    }
}
