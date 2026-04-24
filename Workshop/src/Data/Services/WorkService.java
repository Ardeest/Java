package Data.Services;

import Data.DataAccessObject.WorkDAO;
import Data.Models.Work;
import Data.Models.WorkDTO;
import Data.Models.WorkStatus;
import Resources.Result;

import java.time.LocalDate;
import java.util.List;

public class WorkService {

    private WorkDAO dao;

    public WorkService() {
        this.dao = new WorkDAO();
    }

    public WorkService(WorkDAO dao) {
        this.dao = dao;
    }

    // SELECT

    public List<Work> getAll() {
        return dao.getAll();
    }

    public Work getById(int id) {
        return dao.getById(id);
    }

    public List<Work> getByTechnician(int technicianId) {
        return dao.getByTechnician(technicianId);
    }

    public List<Work> getByCustomer(int customerId) {
        return dao.getByCustomer(customerId);
    }

    // INSERT

    public Result create(Work w) {

        if (w == null) {
            return new Result(false, "Work es null");
        }

        if (w.getCustomerId() <= 0) {
            return new Result(false, "Customer inválido");
        }

        if (w.getTechnicianId() <= 0) {
            return new Result(false, "Technician inválido");
        }

        if (isEmpty(w.getDescription())) {
            return new Result(false, "Descripción requerida");
        }

        // reglas de negocio
        if (w.getDateTime() == null) {
            w.setDateTime(java.time.LocalDateTime.now());
        }

        if (w.getStatus() == null) {
            w.setStatus(WorkStatus.PENDING);
        }

        dao.insert(w);
        return new Result(true, "Trabajo creado correctamente");
    }

    // UPDATE

    public Result update(Work w) {

        if (w.getId() <= 0) {
            return new Result(false, "ID inválido");
        }

        if (w.getCustomerId() <= 0) {
            return new Result(false, "Customer inválido");
        }

        if (w.getTechnicianId() <= 0) {
            return new Result(false, "Technician inválido");
        }

        if (isEmpty(w.getDescription())) {
            return new Result(false, "Descripción requerida");
        }

        dao.update(w);
        return new Result(true, "Trabajo actualizado correctamente");
    }

    // DELETE

    public Result delete(int id) {

        if (id <= 0) {
            return new Result(false, "ID inválido");
        }

        dao.delete(id);
        return new Result(true, "Trabajo eliminado correctamente");
    }
    
    public List<WorkDTO> search(String text) {
        return dao.searchWithDetails(text);
    }
    
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    public List<WorkDTO> getByTechnicianWithDetails(int technicianId) {
        return dao.getByTechnicianWithDetails(technicianId);
    }
}