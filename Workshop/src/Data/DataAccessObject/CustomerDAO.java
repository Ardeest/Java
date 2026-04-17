package Data.DataAccessObject;

import Data.DatabaseConnection;
import Data.Models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void insert(Customer c) {
        String sql = "INSERT INTO Customer(id_card, first_name, last_name1, last_name2, phone, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getIdCard());
            stmt.setString(2, c.getFirstName());
            stmt.setString(3, c.getLastName1());
            stmt.setString(4, c.getLastName2());
            stmt.setString(5, c.getPhone());
            stmt.setString(6, c.getAddress());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setIdCard(rs.getString("id_card"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName1(rs.getString("last_name1"));
                c.setLastName2(rs.getString("last_name2"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Customer getByIdCard(String idCard) {
        String sql = "SELECT * FROM Customer WHERE id_card = ?";
        Customer c = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idCard);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Customer();
                c.setId(rs.getInt("id"));
                c.setIdCard(rs.getString("id_card"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName1(rs.getString("last_name1"));
                c.setLastName2(rs.getString("last_name2"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    public List<Customer> getByName(String name) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE first_name LIKE ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setIdCard(rs.getString("id_card"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName1(rs.getString("last_name1"));
                c.setLastName2(rs.getString("last_name2"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Customer> search(String text) {
        List<Customer> list = new ArrayList<>();

        String sql = """
            SELECT * FROM Customer
            WHERE id_card LIKE ?
               OR first_name LIKE ?
               OR last_name1 LIKE ?
               OR last_name2 LIKE ?
               OR phone LIKE ?
        """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String value = "%" + text + "%";

            stmt.setString(1, value);
            stmt.setString(2, value);
            stmt.setString(3, value);
            stmt.setString(4, value);
            stmt.setString(5, value);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setIdCard(rs.getString("id_card"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName1(rs.getString("last_name1"));
                c.setLastName2(rs.getString("last_name2"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public void update(Customer c) {
        String sql = "UPDATE Customer SET id_card=?, first_name=?, last_name1=?, last_name2=?, phone=?, address=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getIdCard());
            stmt.setString(2, c.getFirstName());
            stmt.setString(3, c.getLastName1());
            stmt.setString(4, c.getLastName2());
            stmt.setString(5, c.getPhone());
            stmt.setString(6, c.getAddress());
            stmt.setInt(7, c.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
