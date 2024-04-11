package ex3.model;

public sealed interface Manager permits TeamManager, GeneralManager, ProgrammingManager {
}
