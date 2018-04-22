package BorderControl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Pet implements Birthable {
    private String name;
    private Date birthdate;

    public Pet(String name, Date birthdate) {
        this.name = name;
        this.birthdate = birthdate;
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
