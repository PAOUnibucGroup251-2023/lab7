package ex3.model;

public non-sealed class Secretary implements Employee {
    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public Long idNo() {
        return idNo;
    }

    @Override
    public Role role() {
        return role;
    }

    String  firstName;
    String  lastName;
    Long idNo;
    Role role;

    public Secretary(String firstName, String lastName, Long idNo, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNo = idNo;
        this.role = role != null ? role : Role.SECRETARY;
    }

    public Secretary(Secretary employee) {
        this(employee.firstName(), employee.lastName(), employee.idNo(), employee.role());
    }

    public void showMeetings(){
        System.out.println("You have 3 meetings today");
    }
}
