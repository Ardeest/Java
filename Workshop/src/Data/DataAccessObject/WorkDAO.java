package Data.DataAccessObject;

import Data.DatabaseConnection;
import Data.Models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkDAO {

    public void insert(Work w) {
        String sql = "INSERT INTO Work(customer_id, technician_id, description, status, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, w.getCustomerId());
            stmt.setInt(2, w.getTechnicianId());
            stmt.setString(3, w.getDescription());

            // null safety enum
            stmt.setString(4, w.getStatus() != null ? w.getStatus().name() : null);

            // null safety date
            stmt.setString(5, w.getDate() != null ? w.getDate().toString() : null);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Work> getAll() {
        List<Work> list = new ArrayList<>();
        String sql = "SELECT * FROM Work";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Work getById(int id) {
        String sql = "SELECT * FROM Work WHERE id = ?";
        Work w = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                w = map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return w;
    }

    public List<Work> getByTechnician(int technicianId) {
        List<Work> list = new ArrayList<>();
        String sql = "SELECT * FROM Work WHERE technician_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, technicianId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Work> getByCustomer(int customerId) {
        List<Work> list = new ArrayList<>();
        String sql = "SELECT * FROM Work WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void update(Work w) {
        String sql = "UPDATE Work SET customer_id=?, technician_id=?, description=?, status=?, date=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, w.getCustomerId());
            stmt.setInt(2, w.getTechnicianId());
            stmt.setString(3, w.getDescription());

            // null safety enum
            stmt.setString(4, w.getStatus() != null ? w.getStatus().name() : null);

            // null safety date
            stmt.setString(5, w.getDate() != null ? w.getDate().toString() : null);

            stmt.setInt(6, w.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Work WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Work map(ResultSet rs) throws SQLException {
        Work w = new Work();

        w.setId(rs.getInt("id"));
        w.setCustomerId(rs.getInt("customer_id"));
        w.setTechnicianId(rs.getInt("technician_id"));
        w.setDescription(rs.getString("description"));

        // enum seguro
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            w.setStatus(WorkStatus.valueOf(statusStr));
        }

        // date seguro
        String dateStr = rs.getString("date");
        if (dateStr != null) {
            w.setDate(LocalDate.parse(dateStr));
        }

        return w;
    }
    
    public List<WorkDTO> searchWithDetails(String text) {

        List<WorkDTO> list = new ArrayList<>();

        String sql = """
            SELECT 
                w.id,
                c.first_name AS customer_name,
                t.first_name AS technician_name,
                w.description,
                w.status,
                w.date
            FROM Work w
            JOIN Customer c ON w.customer_id = c.id
            JOIN Technician t ON w.technician_id = t.id
            WHERE w.description LIKE ?
               OR c.first_name LIKE ?
               OR t.first_name LIKE ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String value = "%" + text + "%";

            stmt.setString(1, value);
            stmt.setString(2, value);
            stmt.setString(3, value);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                WorkDTO dto = new WorkDTO();
                dto.setId(rs.getInt("id"));
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setTechnicianName(rs.getString("technician_name"));
                dto.setDescription(rs.getString("description"));

                String status = rs.getString("status");
                if (status != null) {
                    dto.setStatus(WorkStatus.valueOf(status));
                }

                String date = rs.getString("date");
                if (date != null) {
                    dto.setDate(LocalDate.parse(date));
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<WorkDTO> getByTechnicianWithDetails(int technicianId) {

        List<WorkDTO> list = new ArrayList<>();

        String sql = """
            SELECT 
                w.id,
                c.first_name AS customer_name,
                t.first_name AS technician_name,
                w.description,
                w.status,
                w.date
            FROM Work w
            JOIN Customer c ON w.customer_id = c.id
            JOIN Technician t ON w.technician_id = t.id
            WHERE w.technician_id = ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, technicianId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                WorkDTO dto = new WorkDTO();
                dto.setId(rs.getInt("id"));
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setTechnicianName(rs.getString("technician_name"));
                dto.setDescription(rs.getString("description"));

                String status = rs.getString("status");
                if (status != null) {
                    try {
                        dto.setStatus(WorkStatus.valueOf(status.toUpperCase()));
                    } catch (Exception e) {
                        dto.setStatus(null);
                    }
                }

                String date = rs.getString("date");
                if (date != null) {
                    dto.setDate(LocalDate.parse(date));
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}