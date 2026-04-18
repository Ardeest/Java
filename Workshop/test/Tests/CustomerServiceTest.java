package Tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import Data.Services.CustomerService;
import Data.DataAccessObject.CustomerDAO;
import Data.Models.Customer;
import Resources.Result;

public class CustomerServiceTest {

    // CREATE TESTS
    @Test
    public void tryAddValidCustomer() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setIdCard("123456789");
        c.setFirstName("Esteban");
        c.setLastName1("Perez");
        c.setPhone("88888888");

        when(dao.getByIdCard("123456789")).thenReturn(null);

        Result r = service.addCustomer(c);

        assertTrue(r.isSuccess());
        verify(dao).insert(c); // ✔ solo esto
    }

    @Test
    public void tryAddEmptyFields() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();

        Result r = service.addCustomer(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void tryAddInvalidIdCard() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setIdCard("123");
        c.setFirstName("Esteban");
        c.setLastName1("Perez");
        c.setPhone("88888888");

        Result r = service.addCustomer(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void tryAddDuplicateIdCard() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setIdCard("123456789");
        c.setFirstName("Esteban");
        c.setLastName1("Perez");
        c.setPhone("88888888");

        when(dao.getByIdCard("123456789"))
            .thenReturn(new Customer());

        Result r = service.addCustomer(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void tryAddInvalidPhone() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setIdCard("123456789");
        c.setFirstName("Esteban");
        c.setLastName1("Perez");
        c.setPhone("123");

        Result r = service.addCustomer(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void tryAddLongName() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setIdCard("123456789");
        c.setFirstName("A".repeat(51));
        c.setLastName1("Perez");
        c.setPhone("88888888");

        when(dao.getByIdCard("123456789")).thenReturn(null);

        Result r = service.addCustomer(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).insert(any());
    }
    
    
    // UPDATE TEST
    @Test
    public void tryUpdateCustomer() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setId(1);
        c.setIdCard("123456789");
        c.setFirstName("Esteban");
        c.setPhone("88888888");

        Result r = service.update(c);

        assertTrue(r.isSuccess());
        verify(dao).update(c);
    }
    
    @Test
    public void tryUpdateInvalidId() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setId(0);

        Result r = service.update(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).update(any());
    }
    
    @Test
    public void tryUpdateInvalidCard() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setId(1);
        c.setIdCard("123"); // inválida
        c.setFirstName("Esteban");
        c.setPhone("88888888");

        Result r = service.update(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).update(any());
    }
    
    @Test
    public void tryUpdateInvalidPhone() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setId(1);
        c.setIdCard("123456789");
        c.setFirstName("Esteban");
        c.setPhone("123"); // inválido

        Result r = service.update(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).update(any());
    }
    
    @Test
    public void tryUpdateEmptySpace() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Customer c = new Customer();
        c.setId(1);
        c.setIdCard("123456789");
        c.setFirstName(""); 
        c.setPhone("88888888");

        Result r = service.update(c);

        assertFalse(r.isSuccess());
        verify(dao, never()).update(any());
    }
    
    
    // DELETE TESTS
    @Test
    public void tryDeleteCustomer() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Result r = service.delete(1);

        assertTrue(r.isSuccess());
        verify(dao).delete(1);
    }
    
    @Test
    public void tryDeleteInvalidId() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        Result r = service.delete(0);

        assertFalse(r.isSuccess());
        verify(dao, never()).delete(anyInt());
    }
    
    // SELECT TESTS
    @Test
    public void trySelectAll() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        service.search("");

        verify(dao).getAll();
    }
    
    @Test
    public void searchDebeUsarDaoSearch() {

        CustomerDAO dao = mock(CustomerDAO.class);
        CustomerService service = new CustomerService(dao);

        service.search("Esteban");

        verify(dao).search("Esteban");
    }
}
