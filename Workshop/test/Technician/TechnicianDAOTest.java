package Technician;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import Data.DatabaseConnection;
import Data.InitDatabase;
import Data.DataAccessObject.TechnicianDAO;
import Data.Models.Technician;

import java.util.List;

import org.junit.After;
import java.io.File;

public class TechnicianDAOTest {

    private TechnicianDAO dao;

    @Before
    public void setup() {

        DatabaseConnection.setUrl("jdbc:sqlite:test.db");

        // limpiar DB
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }

        // crear estructura real
        InitDatabase.init();

        dao = new TechnicianDAO();
    }

    @Test
    public void shouldInsertAndGetTechnician() {

        Technician t = new Technician("123456789", "Juan", "Perez", null, "12345678");

        dao.insert(t);

        Technician result = dao.getByIdCard("123456789");

        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
    }

    @Test
    public void getAllShouldReturnTechnicians() {

        Technician t = new Technician("111111111", "Test", "User", null, "12345678");

        dao.insert(t);

        List<Technician> list = dao.getAll();

        assertFalse(list.isEmpty());
    }

    @Test
    public void shouldUpdateTechnician() {

        Technician t = new Technician("222222222", "Original", "User", null,"12345678");

        dao.insert(t);

        Technician saved = dao.getByIdCard("222222222");

        saved.setFirstName("Updated");
        dao.update(saved);

        Technician updated = dao.getById(saved.getId());

        assertEquals("Updated", updated.getFirstName());
    }

    @Test
    public void shouldDeleteTechnician() throws SQLException {

        Technician t = new Technician("333333333", "Delete", "User", null, "12345678");

        dao.insert(t);

        Technician saved = dao.getByIdCard("333333333");

        dao.delete(saved.getId());

        Technician deleted = dao.getByIdCard("333333333");

        assertNull(deleted);
    }

    @Test
    public void searchShouldFindTechnician() {

        Technician t = new Technician("444444444", "Esteban", "Perez", null, "12345678");

        dao.insert(t);

        List<Technician> result = dao.search("Esteban");

        assertFalse(result.isEmpty());
    }

    @After
    public void cleanup() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
    }
}