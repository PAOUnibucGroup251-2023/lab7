package ex5.model;

public sealed interface Employee permits GeneralManager, ProgrammingManagerImpl, Secretary {

    String firstName();

    String lastName();

    Long idNo();

    Role role();

    default String tabular(){
        return String.format("%s \t %s \t %d \t %s", firstName(), lastName(), idNo(), role());
    }
}
