package ex5.model;

public record GeneralManager(String firstName, String lastName, Long idNo, Role role) implements Manager, Employee {
}
