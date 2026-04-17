package Data.Services;

import Data.DataAccessObject.CustomerDAO;
import Data.Models.Customer;
import Resources.Result;
import java.util.List;

public class CustomerService {

    private CustomerDAO dao = new CustomerDAO();
    
    // SELECT
    // Obtener todos los clientes
    public List<Customer> getAll() {
        return dao.getAll();
    }
 
    // Buscar cliente por cédula, nombres o teléfono
    public List<Customer> search(String text) {
        if (text == null || text.trim().isEmpty()) {
            return dao.getAll();
        }
        return dao.search(text);
    }

    // INSERT
    public Result addCustomer(Customer c) {

        // Validar campos obligatorios
        if (isEmpty(c.getIdCard()) ||
            isEmpty(c.getFirstName()) ||
            isEmpty(c.getLastName1()) ||
            isEmpty(c.getPhone())) {

            return new Result(false, "Campos obligatorios vacíos");
        }

        // Validar cédula (9 dígitos)
        if (!c.getIdCard().matches("\\d{9}")) {
            return new Result(false, "La cédula debe tener 9 dígitos numéricos");
        }

        // Validar teléfono (8 dígitos)
        if (!c.getPhone().matches("\\d{8}")) {
            return new Result(false, "El teléfono debe tener 8 dígitos numéricos");
        }

        // Validar duplicado
        if (dao.getByIdCard(c.getIdCard()) != null) {
            return new Result(false, "La cédula ya existe");
        }

        // Validar longitud nombre
        if (c.getFirstName().length() > 50) {
            return new Result(false, "Nombre demasiado largo");
        }

        dao.insert(c);
        return new Result(true, "Cliente agregado correctamente");
    }

    // UPDATE
    public Result update(Customer c) {

        if (c.getId() <= 0) {
            return new Result(false, "ID inválido");
        }

        if (isEmpty(c.getFirstName())) {
            return new Result(false, "Nombre vacío");
        }

        if (!c.getIdCard().matches("\\d{9}")) {
            return new Result(false, "La cédula debe tener 9 dígitos");
        }

        if (!c.getPhone().matches("\\d{8}")) {
            return new Result(false, "Teléfono inválido");
        }

        dao.update(c);
        return new Result(true, "Cliente actualizado correctamente");
    }

    // DELETE
    public Result delete(int id) {

        if (id <= 0) {
            return new Result(false, "ID inválido");
        }

        dao.delete(id);
        return new Result(true, "Cliente eliminado correctamente");
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}