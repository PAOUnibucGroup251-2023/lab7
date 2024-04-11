package ex1.model;

public sealed interface Manager permits TeamManager, GeneralManager, ProgrammingManager {
}
