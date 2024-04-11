package ex5.model;

public sealed interface Manager permits TeamManager, GeneralManager, ProgrammingManager {
}
