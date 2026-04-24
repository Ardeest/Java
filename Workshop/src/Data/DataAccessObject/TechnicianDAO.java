package Data.DataAccessObject;

import Data.DatabaseConnection;
import Data.Models.Technician;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechnicianDAO {

    public void insert(Technician t) {
        String sql = "INSERT INTO Technician(id_card, first_name, last_name1, last_name2, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getIdCard());
            stmt.setString(2, t.getFirstName());
            stmt.setString(3, t.getLastName1());
            stmt.setString(4, t.getLastName2());
            stmt.setString(5, t.getPhone());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Technician getById(int id) {
        String sql = "SELECT * FROM Technician WHERE id = ?";
        Technician t = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                t = map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public Technician getByIdCard(String idCard) {
        String sql = "SELECT * FROM Technician WHERE id_card = ?";
        Technician t = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idCard);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                t = map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public List<Technician> getAll() {
        List<Technician> list = new ArrayList<>();
        String sql = "SELECT * FROM Technician";

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

    public List<Technician> search(String text) {
        List<Technician> list = new ArrayList<>();

        String sql = """
            SELECT * FROM Technician
            WHERE id_card LIKE ?
               OR first_name LIKE ?
               OR last_name1 LIKE ?
               OR last_name2 LIKE ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String value = "%" + text + "%";

            stmt.setString(1, value);
            stmt.setString(2, value);
            stmt.setString(3, value);
            stmt.setString(4, value);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void update(Technician t) {
        String sql = "UPDATE Technician SET id_card=?, first_name=?, last_name1=?, last_name2=?, phone=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getIdCard());
            stmt.setString(2, t.getFirstName());
            stmt.setString(3, t.getLastName1());
            stmt.setString(4, t.getLastName2());
            stmt.setString(5, t.getPhone());
            stmt.setInt(6, t.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) throws SQLException { // <--- Agrega 'throws SQLException'
        String sql = "DELETE FROM Technician WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } // NO pongas catch aquí dentro
    }

    private Technician map(ResultSet rs) throws SQLException {
        Technician t = new Technician();
        t.setId(rs.getInt("id"));
        t.setIdCard(rs.getString("id_card"));
        t.setFirstName(rs.getString("first_name"));
        t.setLastName1(rs.getString("last_name1"));
        t.setLastName2(rs.getString("last_name2"));
        t.setPhone(rs.getString("phone"));
        return t;
    }
}