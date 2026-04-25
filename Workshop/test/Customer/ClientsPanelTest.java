package Customer;

import Data.Services.CustomerService;
import Panels.ClientsPanel;
import javax.swing.JFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.mockito.Mockito.*;

public class ClientsPanelTest {
    private FrameFixture window;
    private CustomerService mockService;

    @Before
    public void setUp() {
        mockService = mock(CustomerService.class);
        // Pasamos el mock al panel
        ClientsPanel panel = new ClientsPanel(mockService);
        
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    public void testInitialStateButtons() {
        // Al iniciar, actualizar y eliminar deben estar deshabilitados
        window.button("updateButton").requireDisabled();
        window.button("deleteButton").requireDisabled();
        // Agregar debe estar habilitado
        window.button("addButton").requireEnabled();
    }


    @Test
    public void testPhoneFieldValidation() {
        // Intenta escribir letras en el teléfono (el filtro debería bloquearlas)
        window.textBox("phoneTextField").enterText("ABC12345");
        // Debería quedar solo el número
        window.textBox("phoneTextField").requireText("1234-5");
    }
    
    @Test
    public void testIdFieldValidation() {
        // Verifica que el filtro de ID funcione igual
        window.textBox("idTextField").enterText("ID-999-ABC");
        window.textBox("idTextField").requireText("9-99");
    }

    @Test
    public void testTableSelectionEnablesActions() {
        // Simulamos que el mock devuelve datos para que la tabla no esté vacía
        // (Esto dependería de cómo implementes el llenado en tu panel)
        
        // Seleccionamos la primera fila de la tabla
        window.table("tableCustomer").selectRows(0);
        
        // Verificamos que ahora los botones se habiliten
        window.button("updateButton").requireEnabled();
        window.button("deleteButton").requireEnabled();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }
}