package ex3.model;

public record ProgrammingManagerImpl(String firstName, String lastName, Long idNo, Role role) implements ProgrammingManager, Employee {
}
