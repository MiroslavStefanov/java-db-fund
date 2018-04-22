package MainApp;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AppEntry {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        EntityManager em = emf.createEntityManager();

        //To test a problem just uncomment the function associated to it.

        //Problem 2
        //RemoveObjects(em);

        //Problem3
        //ContainsEmployee(em);

        //Problem4
        //EmployeesWithSalaryOverFiftyBucks(em);

        //Problem5
        //EmployeesFromDepartment(em);

        //Problem6
        /*try {
            NewAddressOldEmployee(em);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }*/

        //Problem7
        //AddressesWithEmployees(em);

        //Problem8
        //GetEmployeeWithProject(em);

        //Problem9
        //GetLatestProjects(em);

        //Problem10
        //IncreaseSalaries(em);

        //Problem11
        //RemoveTowns(em);

        //Problem12
        //FindEmployeeByFirstName(em);

        //Problem13
        //EmployeeMaximumSalaries(em);
    }

    public static void RemoveObjects(EntityManager em) {
        em.getTransaction().begin();
        List<Town> towns = em.createQuery("SELECT t FROM Town AS t", Town.class).getResultList();
        for(Town t : towns) {
            if(t.getName().length() > 5)
                em.detach(t);
            else {
                t.setName(t.getName().toLowerCase());
                em.persist(t);
            }
        }
        em.getTransaction().commit();
    }

    public static void ContainsEmployee(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        String[] employeeNames = sc.nextLine().split("\\s+");
        Query q;
        if(employeeNames.length == 2) {
            q = em.createQuery("SELECT e FROM Employee AS e WHERE e.firstName = :fName AND e.lastName = :lName", Employee.class);
            q.setParameter("fName", employeeNames[0]);
            q.setParameter("lName", employeeNames[1]);
        } else {
            q = em.createQuery("SELECT e FROM Employee AS e WHERE e.firstName = :fName AND e.middleName = :mName AND e.lastName = :lName",
                    Employee.class);
            q.setParameter("fName", employeeNames[0]);
            q.setParameter("mName", employeeNames[1]);
            q.setParameter("lName", employeeNames[2]);
        }
        List<Employee> emp = q.getResultList();
        if(emp.size() == 0)
            System.out.println("No");
        else
            System.out.println("Yes");
    }

    public static void EmployeesWithSalaryOverFiftyBucks(EntityManager em) {
        List<String> employeeNames = em.createQuery("SELECT e.firstName FROM Employee AS e WHERE e.salary > 50000",
                String.class)
                .getResultList();
        for(String name : employeeNames)
            System.out.println(name);
    }

    public static void EmployeesFromDepartment(EntityManager em) {
        List<Employee> employees = em.createQuery(
                "SELECT e " +
                        "FROM Employee AS e " +
                        "INNER JOIN e.department d " +
                        "WHERE d.name = 'Research and Development' " +
                        "ORDER BY e.salary ASC, e.id ASC",
                Employee.class
        ).getResultList();
        for(Employee e : employees) {
            System.out.printf("%s %s from %s - $%.2f\n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary());
        }
    }

    public static void NewAddressOldEmployee(EntityManager em) throws Exception {
        Scanner sc = new Scanner(System.in);
        String lastName = sc.nextLine();
        Query q = em.createQuery("SELECT e FROM Employee AS e WHERE e.lastName = :name");
        q.setParameter("name", lastName);
        Employee e = (Employee) q.getSingleResult();
        if(e == null)
            throw new Exception("No employee with such last name");

        Address adr = new Address();
        adr.setText("Vitoshka 15");
        Set<Employee> set = new HashSet<>();
        set.add(e);
        adr.setEmployees(set);
        e.setAddress(adr);

        em.getTransaction().begin();
        em.persist(adr);
        em.persist(e);
        em.getTransaction().commit();
        System.out.println("Successfully added Vitoshka 15 to the database.");
        System.out.printf("Successfully updated %s %s to live on Vitoshka 15.", e.getFirstName(), e.getLastName());
    }

    public static void AddressesWithEmployees(EntityManager em) {
        List<Object[]> addresses = em.createQuery(
                "SELECT a, COUNT(e) AS num " +
                        "FROM Address AS a " +
                        "JOIN a.employees e " +
                        "GROUP BY a.id " +
                        "ORDER BY num DESC, a.id ASC ",
                Object[].class
        ).setMaxResults(10).getResultList();

        for(Object[] a : addresses) {
            Address adr = (Address)a[0];
            System.out.printf("%s, %s - %d employees\n", adr.getText(), adr.getTown().getName(), (Long)a[1]);
        }
    }

    public static void GetEmployeeWithProject(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        int empId = Integer.parseInt(sc.nextLine());

        Employee e = em.createQuery("FROM Employee WHERE id = :id", Employee.class).setParameter("id", empId).getSingleResult();
        System.out.printf("%s %s - %s\n", e.getFirstName(), e.getLastName(), e.getJobTitle());
        for(String p : e.getProjects().stream().map(Project::getName).sorted().collect(Collectors.toList())) {
            System.out.println("\t"+p);
        }
    }

    public static void GetLatestProjects(EntityManager em) {
        List<Project> projects = em.createQuery("FROM Project AS p ORDER BY p.startDate DESC, p.name", Project.class)
                .setMaxResults(10).getResultList();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        for(Project p : projects) {
            System.out.printf("Project name: %s\n", p.getName());
            System.out.printf("\tProject Description: %s\n", p.getDescription());
            System.out.printf("\tProject Start Date: %s\n", df.format(p.getStartDate()));
            System.out.printf("\tProject End Date: %s\n", p.getEndDate()!=null? df.format(p.getEndDate()) : "null");
        }
    }

    public static void IncreaseSalaries(EntityManager em) {
        List<Employee> employees = em.createQuery("FROM Employee AS e " +
                        "WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')",
                Employee.class).getResultList();
        em.getTransaction().begin();
        for(Employee e : employees) {
            e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
            System.out.printf("%s %s ($%.2f)\n", e.getFirstName(), e.getLastName(), e.getSalary());
            em.persist(e);
        }
        em.getTransaction().commit();
    }

    public static void RemoveTowns(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        String townName = sc.nextLine();

        Town town = em.createQuery("FROM Town WHERE name = :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        List<Address> addresses = em.createQuery("FROM Address WHERE town.id = :id", Address.class)
                .setParameter("id", town.getId())
                .getResultList();

        em.getTransaction().begin();

        em.remove(town);
        //int rows = em.createQuery("DELETE FROM Address WHERE town.id = :id").setParameter("id", town.getId()).executeUpdate();
        em.createQuery("UPDATE Employee SET address = null WHERE address IN :addrList")
                .setParameter("addrList", addresses).executeUpdate();
        for(Address a : addresses)
            em.remove(a);
        em.getTransaction().commit();
        String adrString = "";
        if(addresses.size() == 1)
            adrString = "address";
        else
            adrString = "addresses";
        System.out.printf("%d %s in %s deleted", addresses.size(), adrString, townName);
    }

    public static void FindEmployeeByFirstName(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        String pat = sc.nextLine();
        List<Employee> employees = em.createQuery("FROM Employee WHERE firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pat+"%").getResultList();
        for(Employee e : employees) {
            System.out.printf("%s %s - %s - ($%.2f)\n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary());
        }
    }

    public static void EmployeeMaximumSalaries(EntityManager em) {
        List<Object[]> result = em.createQuery("SELECT e.department, MAX(e.salary) AS m FROM Employee AS e\n" +
                        "GROUP BY e.department HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000",
                Object[].class)
                .getResultList();
        for(Object[] rs : result) {
            System.out.printf("%s - %.2f\n", ((Department)rs[0]).getName(), (BigDecimal)rs[1]);
        }
    }
}
