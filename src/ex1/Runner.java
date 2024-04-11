package ex1;

import ex1.model.*;

import java.util.Random;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Enter number of employees: ");
        Scanner sc = new Scanner(System.in);
        int numberOfEmployees = Integer.parseInt(sc.nextLine());
        Employee[] employees = new Employee[numberOfEmployees];
        for (int i = 0; i < numberOfEmployees; i++) {
            employees[i] = readEmployee(i, sc, numberOfEmployees);
        }

        displayEmployees(employees);
    }

    private static Employee readEmployee(int i, Scanner sc, int numberOfEmployees) {
        System.out.println("Enter data for employee #" + (i + 1));
        System.out.println("Enter employee first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter employee last name: ");
        String lastName = sc.nextLine();
        Random random = Random.from(RandomGenerator.getDefault());
        long employeeID = random.nextInt(numberOfEmployees *1000 + i);
        System.out.println("Enter role: ");
        Role role = Role.valueOf(sc.nextLine().replace(' ','_').toUpperCase());

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
        for(int i = 0; i < employees.length; i++){
            System.out.printf("%d. \t %s%n", i+1, employees[i].tabular());
        }
    }
}
