package ex4.model;

public sealed interface Manager permits TeamManager, GeneralManager, ProgrammingManager {
}
