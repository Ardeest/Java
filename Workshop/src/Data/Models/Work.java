package Data.Models;

import java.time.LocalDate;

public class Work {

    private int id;
    private int customerId;
    private int technicianId;
    private String description;
    private WorkStatus status;
    private LocalDate date;

    public Work() {}

    public Work(int customerId, int technicianId, String description, WorkStatus status, LocalDate date) {
        this.customerId = customerId;
        this.technicianId = technicianId;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    // getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getTechnicianId() { return technicianId; }
    public void setTechnicianId(int technicianId) { this.technicianId = technicianId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public WorkStatus getStatus() { return status; }
    public void setStatus(WorkStatus status) { this.status = status; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}