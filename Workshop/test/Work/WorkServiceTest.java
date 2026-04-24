package Work;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import Data.DataAccessObject.WorkDAO;
import Data.Models.Work;
import Data.Models.WorkDTO;
import Data.Models.WorkStatus;
import Data.Services.WorkService;
import Resources.Result;

import java.util.Arrays;
import java.util.List;

public class WorkServiceTest {

    @Test
    public void shouldCreateWorkSuccessfully() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Work w = new Work();
        w.setCustomerId(1);
        w.setTechnicianId(1);
        w.setDescription("Fix PC");

        Result result = service.create(w);

        assertTrue(result.isSuccess());
        verify(dao).insert(w);

        assertNotNull(w.getDateTime());
        assertEquals(WorkStatus.PENDING, w.getStatus());
    }

    @Test
    public void shouldFailIfWorkIsNull() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Result result = service.create(null);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldFailIfCustomerInvalid() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Work w = new Work();
        w.setCustomerId(0);
        w.setTechnicianId(1);
        w.setDescription("Fix PC");

        Result result = service.create(w);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldFailIfTechnicianInvalid() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Work w = new Work();
        w.setCustomerId(1);
        w.setTechnicianId(0);
        w.setDescription("Fix PC");

        Result result = service.create(w);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldFailIfDescriptionEmpty() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Work w = new Work();
        w.setCustomerId(1);
        w.setTechnicianId(1);
        w.setDescription("   ");

        Result result = service.create(w);

        assertFalse(result.isSuccess());
        verify(dao, never()).insert(any());
    }

    @Test
    public void shouldUpdateWorkSuccessfully() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Work w = new Work();
        w.setId(1);
        w.setCustomerId(1);
        w.setTechnicianId(1);
        w.setDescription("Updated");

        Result result = service.update(w);

        assertTrue(result.isSuccess());
        verify(dao).update(w);
    }

    @Test
    public void shouldFailUpdateInvalidId() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Work w = new Work();
        w.setId(0);

        Result result = service.update(w);

        assertFalse(result.isSuccess());
        verify(dao, never()).update(any());
    }

    @Test
    public void shouldDeleteWorkSuccessfully() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Result result = service.delete(1);

        assertTrue(result.isSuccess());
        verify(dao).delete(1);
    }

    @Test
    public void shouldFailDeleteInvalidId() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        Result result = service.delete(0);

        assertFalse(result.isSuccess());
        verify(dao, never()).delete(anyInt());
    }
    
    
    @Test
    public void shouldReturnSearchResults() {

        WorkDAO dao = mock(WorkDAO.class);
        WorkService service = new WorkService(dao);

        WorkDTO dto = new WorkDTO();
        dto.setId(1);
        dto.setCustomerName("Juan");
        dto.setTechnicianName("Carlos");
        dto.setDescription("Fix PC");

        when(dao.searchWithDetails("Juan"))
                .thenReturn(Arrays.asList(dto));

        List<WorkDTO> result = service.search("Juan");

        assertFalse(result.isEmpty());
        assertEquals("Juan", result.get(0).getCustomerName());

        verify(dao).searchWithDetails("Juan");
    }
}