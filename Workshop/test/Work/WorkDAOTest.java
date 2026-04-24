package Work;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Data.DatabaseConnection;
import Data.InitDatabase;
import Data.DataAccessObject.WorkDAO;
import Data.DataAccessObject.CustomerDAO;
import Data.DataAccessObject.TechnicianDAO;
import Data.Models.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import java.io.File;

public class WorkDAOTest {

    private WorkDAO dao;
    private CustomerDAO customerDAO;
    private TechnicianDAO technicianDAO;

    private int customerId;
    private int technicianId;

    @Before
    public void setup() {

        DatabaseConnection.setUrl("jdbc:sqlite:test.db");

        // limpiar DB
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }

        // crear esquema real
        InitDatabase.init();

        dao = new WorkDAO();
        customerDAO = new CustomerDAO();
        technicianDAO = new TechnicianDAO();

        // crear customer
        Customer c = new Customer();
        c.setIdCard("123456789");
        c.setFirstName("Cliente");
        c.setLastName1("Test");
        c.setPhone("88888888");

        customerDAO.insert(c);
        customerId = customerDAO.getByIdCard("123456789").getId();

        // crear technician
        Technician t = new Technician("987654321", "Tech", "Test", null);

        technicianDAO.insert(t);
        technicianId = technicianDAO.getByIdCard("987654321").getId();
    }

    @Test
    public void shouldInsertAndRetrieveWork() {

        Work w = new Work(customerId, technicianId, "Fix PC", WorkStatus.PENDING, LocalDate.now());

        dao.insert(w);

        List<Work> list = dao.getAll();

        assertFalse(list.isEmpty());
        assertEquals("Fix PC", list.get(0).getDescription());
    }

    @Test
    public void shouldUpdateWork() {

        Work w = new Work(customerId, technicianId, "Original", WorkStatus.PENDING, LocalDate.now());

        dao.insert(w);

        Work saved = dao.getAll().get(0);

        saved.setDescription("Updated");
        dao.update(saved);

        Work updated = dao.getById(saved.getId());

        assertEquals("Updated", updated.getDescription());
    }

    @Test
    public void shouldDeleteWork() {

        Work w = new Work(customerId, technicianId, "Delete", WorkStatus.PENDING, LocalDate.now());

        dao.insert(w);

        Work saved = dao.getAll().get(0);

        dao.delete(saved.getId());

        assertNull(dao.getById(saved.getId()));
    }

    @Test
    public void shouldFilterByTechnician() {

        Work w = new Work(customerId, technicianId, "Task", WorkStatus.PENDING, LocalDate.now());

        dao.insert(w);

        List<Work> list = dao.getByTechnician(technicianId);

        assertFalse(list.isEmpty());
    }

    @Test
    public void shouldStoreEnumCorrectly() {

        Work w = new Work(customerId, technicianId, "Task", WorkStatus.DONE, LocalDate.now());

        dao.insert(w);

        Work saved = dao.getAll().get(0);

        assertEquals(WorkStatus.DONE, saved.getStatus());
    }

    @Test
    public void shouldReturnDTOWithNames() {

        // insertar work conocido
        Work w = new Work(customerId, technicianId, "Fix PC", WorkStatus.PENDING, LocalDate.now());
        dao.insert(w);

        List<WorkDTO> result = dao.searchWithDetails("Fix");

        assertFalse(result.isEmpty());

        WorkDTO dto = result.get(0);

        assertEquals("Cliente", dto.getCustomerName());
        assertEquals("Tech", dto.getTechnicianName());
        assertEquals("Fix PC", dto.getDescription());
    }

    @Test
    public void shouldReturnEmptyIfNotFound() {

        List<WorkDTO> result = dao.searchWithDetails("doesnotexist");

        assertTrue(result.isEmpty());
    }
    
    @After
    public void cleanup() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
    }
}