package ex2.model;

public sealed interface Manager permits TeamManager, GeneralManager, ProgrammingManager {
}
