package UserInterface;

import entities.Diagnose;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HospitalConsole {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;
    static private DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public HospitalConsole() {
        this.emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        this.em = emf.createEntityManager();
        sc = null;
    }

    public void Run() {
        sc = new Scanner(System.in);
        String line;

        while(true) {
            ClearConsole();
            PrintMenu();
            line = sc.nextLine();
            if(line.equals("0"))
                break;
            switch (line) {
                case "1":
                    AddPatient(); break;
                case "2":
                    AddVisitation(); break;
                case "3":
                    AddDiagnose(); break;
                case "4":
                    AddMedicament(); break;
                case "5":
                    SetMedicament(); break;
                case "6":
                    ShowPatients(); break;
                case "7":
                    ShowVisitations(); break;
                case "8":
                    ShowDiagnoses(); break;
                case "9":
                    ShowMedicaments(); break;
            }
        }

        CleanUp();
    }

    private void AddPatient() {
        ClearConsole();
        System.out.println("Adding new patient.");
        Patient p = FindPatient(true);
        String line;

        //Birth date
        boolean bBreak = false;
        java.util.Date bDate = null;
        ClearConsole();
        System.out.println("Please enter birth date of the patient in format 'dd.mm.yyyy'");
        while(!bBreak) {
            line = sc.nextLine();
            try {
                bDate = df.parse(line);
                bBreak = true;
            }
            catch (Exception e) {
                ClearConsole();
                System.out.println("Enter a valid date in format 'dd.mm.yyyy'");
            }
        }
        p.setBirthDate(new Date(bDate.getTime()));

        //Address
        ClearConsole();
        System.out.println("Please enter address of the patient");
        line = sc.nextLine();
        p.setAddress(line);

        //Email
        ClearConsole();
        System.out.println("Please enter email of the patient");
        line = sc.nextLine();
        p.setEmail(line);

        //Has...
        ClearConsole();
        System.out.println("Does the patient has a medical insurance? y/n");
        line = sc.nextLine();
        switch (line) {
            case "y":
                p.setHasMedicalInsurance(true); break;
            case "n":
                p.setHasMedicalInsurance(false); break;
        }

        //Save in the database
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        System.out.println("Successfully added patient.");
    }

    private void AddDiagnose() {
        System.out.println("Setting a diganose to a patient.");

        //Handle the patient
        Patient p = FindPatient(false);
        if(p == null) {
            System.out.println("Canceled.");
            return;
        }

        Diagnose d = new Diagnose();
        d.setPatient(p);

        if(p.getDiagnoses() == null) {
            Set<Diagnose> set = new HashSet<>();
            set.add(d);
            p.setDiagnoses(set);
        }
        else p.getDiagnoses().add(d);

        //Initialize diagnose`s properties
        ClearConsole();
        System.out.println("Please enter diagnose name.");
        String line = sc.nextLine();
        while(line.length() == 0) {
            ClearConsole();
            System.out.println("Please enter valid diagnose name.");
            line = sc.nextLine();
        }
        d.setName(line);

        ClearConsole();
        System.out.println("Please enter comments to the diagnose.");
        line = sc.nextLine();
        d.setComments(line);

        //Update the database
        em.getTransaction().begin();
        em.persist(d);
        em.persist(p);
        em.getTransaction().commit();

        System.out.println("Successfully added diagnose");
    }

    private void AddVisitation() {
        System.out.println("Adding a new visitation.");

        //Handle the patient
        Patient p = FindPatient(false);
        if(p == null) {
            System.out.println("Canceled.");
            return;
        }

        Visitation v = new Visitation();
        v.setPatient(p);

        if(p.getVisitations() == null) {
            Set<Visitation> set = new HashSet<>();
            set.add(v);
            p.setVisitations(set);
        }
        else p.getVisitations().add(v);

        //Initialize visiation`s properties
        ClearConsole();
        System.out.println("Please enter date in format 'dd.mm.yyyy'.");
        java.util.Date date = null;
        String line;
        boolean bBreak = false;
        while(!bBreak) {
            line = sc.nextLine();
            try {
                date = df.parse(line);
                bBreak = true;
            }
            catch (Exception e) {
                ClearConsole();
                System.out.println("Please enter valid date in format 'dd.mm.yyyy'.");
            }
        }
        v.setDate(new Date(date.getTime()));

        ClearConsole();
        System.out.println("Please enter comments to the visitation.");
        line = sc.nextLine();
        v.setCommetns(line);

        //Save to the database
        em.getTransaction().begin();
        em.persist(v);
        em.persist(p);
        em.getTransaction().commit();

        System.out.println("Successfully added visitation.");
    }

    private void SetMedicament() {
        System.out.println("Prescribing a medicament to a patient");

        //Handle the patient
        Patient p = FindPatient(false);
        if(p == null) {
            System.out.println("Canceled.");
            return;
        }


        //Find the medicament
        Medicament m = FindMedicament();
        if(m == null) {
            System.out.println("Canceled.");
            return;
        }

        if(p.getMedicaments() == null) {
            Set<Medicament> set = new HashSet<>();
            set.add(m);
            p.setMedicaments(set);
        }
        else p.getMedicaments().add(m);

        //Save to the database
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        System.out.println("Successfully prescribed medicament to the patient.");
    }

    private void AddMedicament() {
        System.out.println("Adding new meidcament");

        System.out.println("Please enter name of the medicament");
        String line = sc.nextLine();
        while(line.length() == 0) {
            ClearConsole();
            System.out.println("Please enter valid name of the medicament");
            line = sc.nextLine();
        }
        Medicament m = new Medicament(line);

        //Save to the database
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

        System.out.println("Successfully added medicament.");
    }

    private void ShowPatients() {
        List<Patient> patients = em.createQuery("FROM Patient ORDER BY firstName, lastName", Patient.class)
                .getResultList();

        System.out.println("Name\tBirthDate\tEmail\tAddress\t");
        for(Patient p : patients) {
            System.out.printf("%s %s\t%s\t%s\t%s\n",
                    p.getFirstName(), p.getLastName(),
                    df.format(p.getBirthDate()),
                    p.getEmail(), p.getAddress());
        }
    }

    private void ShowVisitations() {
        Patient p = FindPatient(false);
        if(p == null) {
            System.out.printf("Canceled.");
            return;
        }

        if(p.getVisitations() == null || p.getVisitations().size() == 0) {
            System.out.printf("No visitations for this patient are existent.");
            return;
        }

        for(Visitation v : p.getVisitations()) {
            System.out.printf("%s - %s\n", df.format(v.getDate()), v.getCommetns());
        }
    }

    private void ShowDiagnoses() {
        Patient p = FindPatient(false);
        if(p == null) {
            System.out.printf("Canceled.");
            return;
        }

        if(p.getDiagnoses() == null || p.getDiagnoses().size() == 0) {
            System.out.printf("No diagnoses for this patient are existent.");
            return;
        }

        for(Diagnose d : p.getDiagnoses()) {
            System.out.printf("%s - %s\n", d.getName(), d.getComments());
        }
    }

    private void ShowMedicaments() {
        Patient p = FindPatient(false);
        if(p == null) {
            System.out.printf("Canceled.");
            return;
        }

        if(p.getMedicaments() == null || p.getMedicaments().size() == 0) {
            System.out.printf("No medicaments for this patient are existent.");
            return;
        }

        for(Medicament m : p.getMedicaments()) {
            System.out.println(m.getName());
        }
    }

    private Medicament FindMedicament() {
        Medicament m = null;
        System.out.println("Please enter the name of the medicament.");
        String line = sc.nextLine();
        while(line.length() == 0) {
            ClearConsole();
            System.out.println("Please enter a valid name of the medicament");
            line = sc.nextLine();
        }
        List<Medicament> list = em.createQuery("FROM Medicament WHERE name = :name", Medicament.class)
                .setParameter("name", line).getResultList();

        if(list.size() == 0) {
            ClearConsole();
            System.out.println("There is no such medicament. Would you like to try again? y/n");
            line = sc.nextLine();
            if(line.equals("y")) return FindMedicament();
        }
        else m = list.get(0);

        return m;
    }

    private Patient FindPatient(boolean newPatient) {
        String firstName, lastName, line;
        Pattern namePattern = Pattern.compile("^[A-Z][a-z]+$");
        Matcher m;
        Patient p = null;

        //First name
        System.out.println("Please enter first name of the patient. Use only letters and a capital letter");
        while(true) {
            line = sc.nextLine();
            m = namePattern.matcher(line);
            if(m.find())
                break;
            else {
                ClearConsole();
                System.out.println("Enter a valid name containing only letters with capital letter.");
            }
        }
        firstName = line;

        //Last name
        ClearConsole();
        System.out.println("Please enter last name of the patient. Use only letters and a capital letter");
        while(true) {
            line = sc.nextLine();
            m = namePattern.matcher(line);
            if(m.find())
                break;
            else {
                ClearConsole();
                System.out.println("Enter a valid name containing only letters with capital letter.");
            }
        }
        lastName = line;

        if(newPatient)
            p = new Patient(firstName, lastName);
        else {
            List<Patient> patients = em.createQuery("FROM Patient WHERE firstName = :fn AND lastName = :ln", Patient.class)
                    .setParameter("fn", firstName).setParameter("ln", lastName).getResultList();
            if(patients.size() == 0) {
                ClearConsole();
                System.out.println("There is no such patient. Would you like to try again? y/n");
                line = sc.nextLine();
                if(line.equals("y")) return FindPatient(false);
            }
            else p = patients.get(0);
        }

        return p;
    }

    private void PrintMenu() {
        System.out.println("1. Add new patient.");
        System.out.println("2. Add new visitation.");
        System.out.println("3. Add new diagnose.");
        System.out.println("4. Add new medicament.");
        System.out.println("5. Prescribe a medicament to a patient.");
        System.out.println("6. Show all patients.");
        System.out.println("7. Show all visitations of a patient.");
        System.out.println("8. Show all diagnoses of a patient.");
        System.out.println("9. Show all medicaments of a patient.");
        System.out.println("0. Exit");
    }

    private void ClearConsole() {
        //for(int i = 0; i<50; i++)
            System.out.println();
    }

    private void CleanUp() {
        em.close();
        emf.close();
        sc.close();
    }
}
