package ex4;

import ex4.exceptions.EmployeeNotFoundException;
import ex4.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Enter number of employees: ");
        Scanner sc = new Scanner(System.in);
        int numberOfEmployees = Integer.parseInt(sc.nextLine());
        Employee[] employees = null;
        try {
            employees = readEmployees(numberOfEmployees, sc);
        } catch (Exception e) {
            System.out.println("Error reading employees file: " + e.getMessage());
            System.exit(1);
        }

        displayEmployees(employees);

        try {
            System.out.println(searchEmployee(sc, employees));
        } catch (EmployeeNotFoundException e) { // if we remove the try-catch block, the code will still compile and run
            // in that case, if the exception occurs, the stacktrace will be displayed and the exception will be
            // propagated either to the point of handling or, if it reaches main and it's not handled even there, it
            // will be displayed with full stack trace to the user and the program will exit
            System.out.println("Error searching employee: " + e.getMessage());
        }
    }

    private static Employee searchEmployee(Scanner sc, Employee[] employees){
        System.out.println("Enter the name of the employee to search: ");
        System.out.println("First Name: ");
        String firstName = sc.nextLine();
        System.out.println("Last Name: ");
        String lastName = sc.nextLine();

        for (Employee e : employees) {
            if (e.firstName().equals(firstName) && e.lastName().equals(lastName)) {
                return e;
            }
        }

        throw new EmployeeNotFoundException();
    }

    private static Employee[] readEmployees(int numberOfEmployees, Scanner sc) throws IOException {
        Employee[] employees = readEmployees("employees");
        int fileEmployees = employees.length;
        System.out.printf("Read %d employees%n", fileEmployees);
        employees = Arrays.copyOf(employees, employees.length + numberOfEmployees - 1);

        for (int i = 0; i < numberOfEmployees; i++) {
            try {
                employees[fileEmployees -1 + i] = readEmployee(i, sc, numberOfEmployees);
            } catch (Exception e) {
                System.out.println("Error reading employee: " + e.getMessage());
                System.exit(1);
            }
        }
        return employees;
    }

    private static Employee[] readEmployees(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Employee[] employees = null;
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
                if (employees == null) {
                    employees = new Employee[1];
                } else {
                    employees = Arrays.copyOf(employees, employees.length + 1);
                }
                employees[employees.length - 1] = employee;
            }
        } while (!line.equals("end"));
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

        Employee employee = createEmployee(role, firstName, lastName, employeeID);
        return employee;
    }


    private static Employee createEmployee(Role role, String firstName, String lastName, long employeeID) {
        return switch (role) {
            case SECRETARY -> new Secretary(firstName, lastName, employeeID, role);
            case CHIEF_SECRETARY -> new ChiefSecretary(firstName, lastName, employeeID, role);
            case GENERAL_MANAGER -> new GeneralManager(firstName, lastName, employeeID, role);
            case HEAD_OF_DEVELOPMENT -> new ProgrammingManagerImpl(firstName, lastName, employeeID, role);
        };
    }

    private static void displayEmployees(Employee[] employees) {
        for (int i = 0; i < employees.length; i++) {
            System.out.printf("%d. \t %s%n", i + 1, employees[i].tabular());
        }
    }
}
