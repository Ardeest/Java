package Data.Models;

import java.time.LocalDateTime;

public class WorkDTO {

    private int id;
    private String customerName;
    private String technicianName;
    private String description;
    private WorkStatus status;
    private LocalDateTime dateTime;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public WorkStatus getStatus() { return status; }
    public void setStatus(WorkStatus status) { this.status = status; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}