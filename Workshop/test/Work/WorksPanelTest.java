package Work;

import Data.Services.WorkService;
import Panels.WorksPanel;
import javax.swing.JFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.mockito.Mockito.*;

public class WorksPanelTest {
    private FrameFixture window;
    private WorkService mockService;

    @Before
    public void setUp() {
        mockService = mock(WorkService.class);
        WorksPanel panel = new WorksPanel(mockService);
        
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    public void testDeleteButtonDisabledInitially() {
        // Verifica que el botón de eliminar esté desactivado al inicio
        window.button("deleteButton").requireDisabled();
    }

    @Test
    public void testUpdateButtonDisabledInitially() {
        // Verifica que el botón de actualizar esté desactivado al inicio
        window.button("updateButton").requireDisabled();
    }

    @Test
    public void testAddButtonEnabledInitially() {
        // Verifica que el botón de agregar esté siempre activo
        window.button("addButton").requireEnabled();
    }

    @Test
    public void testSearchComponentsEnabledInitially() {
        // Verifica que los componentes de búsqueda estén listos
        window.textBox("searchTextField").requireEnabled();
        window.button("searchButton").requireEnabled();
    }
    
    @Test
    public void testTableIsVisible() {
        // Verifica que la tabla exista en la interfaz
        window.table("tableWork").requireVisible();
    }
    
    @After
    public void tearDown() {
        window.cleanUp();
    }
}