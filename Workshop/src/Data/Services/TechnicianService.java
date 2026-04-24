package Data.Services;

import Data.DataAccessObject.TechnicianDAO;
import Data.Models.Technician;
import Resources.Result;

import java.util.List;

public class TechnicianService {

    private TechnicianDAO dao;

    public TechnicianService() {
        this.dao = new TechnicianDAO();
    }

    public TechnicianService(TechnicianDAO dao) {
        this.dao = dao;
    }

    // SELECT
    public List<Technician> getAll() {
        return dao.getAll();
    }

    public Technician getById(int id) {
        return dao.getById(id);
    }

    // INSERT
    public Result addTechnician(Technician t) {

        if (t == null) {
            return new Result(false, "Technician null");
        }

        if (isEmpty(t.getIdCard()) ||
            isEmpty(t.getFirstName()) ||
            isEmpty(t.getPhone()) ||
            isEmpty(t.getLastName1())) {

            return new Result(false, "Campos obligatorios vacíos");
        }

        if (!t.getIdCard().matches("\\d{9}")) {
            return new Result(false, "Cédula inválida");
        }
        
        // Validar teléfono (8 dígitos)
        if (!t.getPhone().matches("\\d{8}")) {
            return new Result(false, "El teléfono debe tener 8 dígitos numéricos");
        }

        if (t.getFirstName().length() > 50 ||
            t.getLastName1().length() > 50 ||
            (t.getLastName2() != null && t.getLastName2().length() > 50)) {

            return new Result(false, "Nombre o apellidos demasiado largos");
        }

        dao.insert(t);
        return new Result(true, "Técnico agregado correctamente");
    }

    // UPDATE
    public Result update(Technician t) {

        if (t == null) {
            return new Result(false, "Technician null");
        }

        if (t.getId() <= 0) {
            return new Result(false, "ID inválido");
        }

        if (isEmpty(t.getIdCard()) ||
            isEmpty(t.getFirstName()) ||
            isEmpty(t.getPhone()) ||
            isEmpty(t.getLastName1())) {

            return new Result(false, "Campos obligatorios vacíos");
        }
        
        // Validar teléfono (8 dígitos)
        if (!t.getPhone().matches("\\d{8}")) {
            return new Result(false, "El teléfono debe tener 8 dígitos numéricos");
        }

        if (!t.getIdCard().matches("\\d{9}")) {
            return new Result(false, "Cédula inválida");
        }

        if (t.getFirstName().length() > 50 ||
            t.getLastName1().length() > 50 ||
            (t.getLastName2() != null && t.getLastName2().length() > 50)) {

            return new Result(false, "Nombre o apellidos demasiado largos");
        }

        dao.update(t);
        return new Result(true, "Técnico actualizado correctamente");
    }

    // DELETE
    public Result delete(int id) {

        if (id <= 0) {
            return new Result(false, "ID inválido");
        }

        dao.delete(id);
        return new Result(true, "Técnico eliminado correctamente");
    }
    
        // Buscar cliente por cédula, nombres o teléfono
    public List<Technician> search(String text) {
        if (text == null || text.trim().isEmpty()) {
            return dao.getAll();
        }
        return dao.search(text);
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}