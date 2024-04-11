package ex5.exceptions;

public class NoEmployeesPresentException extends Exception {
    public NoEmployeesPresentException() {
        super("No employees present");
    }
}
