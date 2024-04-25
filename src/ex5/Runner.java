package ex5;

import ex5.exceptions.EmployeeNotFoundException;
import ex5.exceptions.NoEmployeesPresentException;
import ex5.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.random.RandomGenerator;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Enter number of employees: ");
        Scanner sc = new Scanner(System.in);
        int numberOfEmployees = Integer.parseInt(sc.nextLine());
        Set<Employee> employees = new HashSet<>();
        try {
            employees = readEmployees(numberOfEmployees, sc);
        } catch (Exception e) {
            System.out.println("Error reading employees file: " + e.getMessage());
            System.exit(1);
        }

        displayEmployees(employees);

        try {
            Employee emp = searchEmployee(sc, employees).orElseThrow();
            System.out.println(emp.tabular());
        } catch (EmployeeNotFoundException | NoEmployeesPresentException e) { //the checked exception will oblige us
            // to either declare it in the method signature or handle it in a try-catch or try-with-resources block
            //otherwise it will not compile
            System.out.println("Error searching employee: " + e.getMessage());
        }
    }

    private static Optional<Employee> searchEmployee(Scanner sc, Set<Employee> employees) throws NoEmployeesPresentException {
        System.out.println("Enter the name of the employee to search: ");
        System.out.println("First Name: ");
        String firstName = sc.nextLine();
        System.out.println("Last Name: ");
        String lastName = sc.nextLine();

        if (employees == null || employees.isEmpty()){
            throw new NoEmployeesPresentException();
        }

        return Optional.ofNullable(employees.stream().filter(employee -> employee.firstName().equals(firstName) && employee.lastName().equals(lastName))
                .findFirst().orElseThrow(EmployeeNotFoundException::new));
    }



    private static Set<Employee> readEmployees(int numberOfEmployees, Scanner sc) throws IOException {
        Set<Employee> employees = readEmployees("employees");
        int fileEmployees = employees.size();
        System.out.printf("Read %d employees%n", fileEmployees);

        for (int i = 0; i < numberOfEmployees; i++) {
            try {
                employees.add(readEmployee(i, sc, numberOfEmployees));
            } catch (Exception e) {
                System.out.println("Error reading employee: " + e.getMessage());
                System.exit(1);
            }
        }
        return employees;
    }

    private static Set<Employee> readEmployees(String filename) throws IOException {
        Set<Employee> employees = new HashSet<>();
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        do {
            line = bufferedReader.readLine();
            String[] fields = "end".equals(line) ? null : line.split(",");
            Employee employee =
                    fields != null ?
                            createEmployee(Role.valueOf(fields[fields.length - 1].replace(' ', '_').toUpperCase()),
                    fields[0],
                    fields[1], Long.parseLong(fields[2])):null;
            if(employee != null) {
                employees.add(employee);
            }
        } while (!line.equals("end"));
        bufferedReader.close();
        fileReader.close();
        return employees;
    }

    private static Employee readEmployee(int i, Scanner sc, int numberOfEmployees) {
        System.out.println("Enter data for employee #" + (i + 1));
        System.out.println("Enter employee first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter employee last name: ");
        String lastName = sc.nextLine();
        Random random = Random.from(RandomGenerator.getDefault());
        long employeeID = random.nextInt(numberOfEmployees * 1000 + i);
        System.out.println("Enter role: ");
        Role role = null;

        try {
            role = Role.valueOf(sc.nextLine().replace(' ', '_').toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role");
            System.exit(1);
        }

        return createEmployee(role, firstName, lastName, employeeID);
    }


    private static Employee createEmployee(Role role, String firstName, String lastName, long employeeID) {
        return switch (role) {
            case SECRETARY -> new Secretary(firstName, lastName, employeeID, role);
            case CHIEF_SECRETARY -> new ChiefSecretary(firstName, lastName, employeeID, role);
            case GENERAL_MANAGER -> new GeneralManager(firstName, lastName, employeeID, role);
            case HEAD_OF_DEVELOPMENT -> new ProgrammingManagerImpl(firstName, lastName, employeeID, role);
        };
    }

    private static void displayEmployees(Set<Employee> employees) {
        for (Employee employee: employees) {
            System.out.printf("%s%n", employee.tabular());
        }
    }
}
