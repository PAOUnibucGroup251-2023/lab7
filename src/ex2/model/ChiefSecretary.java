package ex2.model;

public class ChiefSecretary extends Secretary {
    public ChiefSecretary(String firstName, String lastName, Long idNo, Role role) {
        super(firstName, lastName, idNo, role != null? role : Role.CHIEF_SECRETARY);
    }

    public ChiefSecretary(ChiefSecretary employee) {
        super(employee);
    }

    public void bossSecretaryAround(){
        System.out.println("Bossing secretary around");
    }
}
