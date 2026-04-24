package Customer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import Data.DatabaseConnection;
import Data.DataAccessObject.CustomerDAO;
import Data.Models.Customer;
import Data.InitDatabase;

import java.util.List;

import org.junit.After;
import java.io.File;

public class CustomerDAOTest {

    private CustomerDAO dao;

    @Before
    public void setup() {

        DatabaseConnection.setUrl("jdbc:sqlite:test.db");

        // borrar DB anterior
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }

        // crear estructura real
        InitDatabase.init();

        dao = new CustomerDAO();
    }
    
    @Test
    public void deberiaInsertarYBuscarCliente() {

        Customer c = new Customer();
        c.setIdCard("111111111");
        c.setFirstName("Test");
        c.setLastName1("User");
        c.setPhone("88888888");

        dao.insert(c);

        Customer result = dao.getByIdCard("111111111");

        assertNotNull(result);
        assertEquals("Test", result.getFirstName());
    }
    
    @Test
    public void getAllDebeRetornarClientes() {

        Customer c = new Customer();
        c.setIdCard("222222222");
        c.setFirstName("Otro");
        c.setLastName1("User");
        c.setPhone("88888888");

        dao.insert(c);

        List<Customer> list = dao.getAll();

        assertFalse(list.isEmpty());
    }
    
    @Test
    public void deberiaActualizarCliente() {

        Customer c = new Customer();
        c.setIdCard("333333333");
        c.setFirstName("Original");
        c.setLastName1("User");
        c.setPhone("88888888");

        dao.insert(c);

        Customer saved = dao.getByIdCard("333333333");

        saved.setFirstName("Modificado");
        dao.update(saved);

        Customer updated = dao.getByIdCard("333333333");

        assertEquals("Modificado", updated.getFirstName());
    }
    
    @Test
    public void deberiaEliminarCliente() throws SQLException {

        Customer c = new Customer();
        c.setIdCard("444444444");
        c.setFirstName("Delete");
        c.setLastName1("User");
        c.setPhone("88888888");

        dao.insert(c);

        Customer saved = dao.getByIdCard("444444444");

        dao.delete(saved.getId());

        Customer deleted = dao.getByIdCard("444444444");

        assertNull(deleted);
    }
    
    @Test
    public void searchDebeEncontrarCliente() {

        Customer c = new Customer();
        c.setIdCard("555555555");
        c.setFirstName("Esteban");
        c.setLastName1("Perez");
        c.setPhone("88888888");

        dao.insert(c);

        List<Customer> result = dao.search("Esteban");

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