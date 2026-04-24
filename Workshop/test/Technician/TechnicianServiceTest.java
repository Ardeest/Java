package Technician;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import Data.DataAccessObject.TechnicianDAO;
import Data.Models.Technician;
import Data.Services.TechnicianService;
import Resources.Result;

public class TechnicianServiceTest {

    @Test
    public void shouldCreateTechnicianSuccessfully() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Technician t = new Technician();
        t.setIdCard("123456789");
        t.setFirstName("Juan");
        t.setLastName1("Perez");

        Result result = service.addTechnician(t);

        assertTrue(result.isSuccess());
        verify(dao).insert(t);
    }

    @Test
    public void shouldFailIfTechnicianIsNull() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Result result = service.addTechnician(null);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldFailIfIdCardInvalid() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Technician t = new Technician();
        t.setIdCard("123"); // inválida
        t.setFirstName("Juan");
        t.setLastName1("Perez");

        Result result = service.addTechnician(t);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldFailIfNameTooLong() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Technician t = new Technician();
        t.setIdCard("123456789");
        t.setFirstName("X".repeat(60)); // > 50
        t.setLastName1("Perez");

        Result result = service.addTechnician(t);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldUpdateTechnicianSuccessfully() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Technician t = new Technician();
        t.setId(1);
        t.setIdCard("123456789");
        t.setFirstName("Juan");
        t.setLastName1("Perez");

        Result result = service.update(t);

        assertTrue(result.isSuccess());
        verify(dao).update(t);
    }

    @Test
    public void shouldFailUpdateInvalidId() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Technician t = new Technician();
        t.setId(0);

        Result result = service.update(t);

        assertFalse(result.isSuccess());
        verify(dao, never()).update(any());
    }

    @Test
    public void shouldFailUpdateMissingFields() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Technician t = new Technician();
        t.setId(1);
        t.setIdCard(""); // inválido
        t.setFirstName("");
        t.setLastName1("");

        Result result = service.update(t);

        assertFalse(result.isSuccess());
        verify(dao, never()).update(any());
    }

    @Test
    public void shouldDeleteTechnician() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Result result = service.delete(1);

        assertTrue(result.isSuccess());
        verify(dao).delete(1);
    }

    @Test
    public void shouldFailDeleteInvalidId() {

        TechnicianDAO dao = mock(TechnicianDAO.class);
        TechnicianService service = new TechnicianService(dao);

        Result result = service.delete(0);

        assertFalse(result.isSuccess());
        verify(dao, never()).delete(anyInt());
    }
}