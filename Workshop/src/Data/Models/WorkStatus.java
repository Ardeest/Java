package Data.Models;

public enum WorkStatus {
    PENDING("Pendiente"),
    IN_PROGRESS("En Progreso"),
    DONE("Completado");

    private final String displayName;

    WorkStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
