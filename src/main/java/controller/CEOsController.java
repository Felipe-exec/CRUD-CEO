package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.User;
import model.CEO;
import model.Company;
import model.dao.DAOFactory;
import model.dao.CEODAO;

import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(urlPatterns = {"/ceos", "/ceo/form", "/ceo/delete", "/ceo/insert", "/ceo/update"})
public class CEOsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
            case "/CRUD-CEO/ceo/form": {
            	CommonsController.listAll(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-ceo.jsp");
                break;
            }
            case "/CRUD-CEO/ceo/update": {
            	CommonsController.listAll(req);
                CEO ceo = loadCEO(req);
                req.setAttribute("ceo", ceo);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-ceo.jsp");
                break;
            }
            default:
                listCEOs(req);

                ControllerUtil.transferSessionMessagesToRequest(req);

                ControllerUtil.forward(req, resp, "/ceos.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        if (action == null || action.equals("")) {
            ControllerUtil.forward(req, resp, "/index.jsp");
            return;
        }

        switch (action) {
            case "/CRUD-CEO/ceo/delete":
                deleteCEO(req, resp);
                break;
            case "/CRUD-CEO/ceo/insert": {
                insertCEO(req, resp);
                break;
            }
            case "/CRUD-CEO/ceo/update": {
                updateCEO(req, resp);
                break;
            }
            default:
                System.out.println("URL inválida " + action);
                break;
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/ceos");
    }

    private CEO loadCEO(HttpServletRequest req) {
        String ceoIdParameter = req.getParameter("ceoId");

        int ceoId = Integer.parseInt(ceoIdParameter);

        CEODAO dao = DAOFactory.createDAO(CEODAO.class);

        try {
            CEO ceo = dao.findById(ceoId);

            if (ceo == null)
                throw new ModelException("CEO não encontrado para alteração");

            return ceo;
        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }

        return null;
    }

    private void listCEOs(HttpServletRequest req) {
        CEODAO dao = DAOFactory.createDAO(CEODAO.class);

        List<CEO> ceos = null;
        try {
            ceos = dao.listAll();
        } catch (ModelException e) {
            // Log no servidor
            e.printStackTrace();
        }

        if (ceos != null)
            req.setAttribute("ceos", ceos);
    }

    private void insertCEO(HttpServletRequest req, HttpServletResponse resp) {
        String ceoCpf = req.getParameter("cpf");
        String ceoRg = req.getParameter("rg");
        Integer userId = Integer.parseInt(req.getParameter("user"));
        Integer companyId = Integer.parseInt(req.getParameter("company"));

        

        try {
        	
        	CEO ceo = new CEO();
            ceo.setCpf(ceoCpf);
            ceo.setRg(ceoRg);
        	ceo.setUser(new User(userId));
            ceo.setCompany(new Company(companyId));
            
            CEODAO dao = DAOFactory.createDAO(CEODAO.class);
            
            if (dao.save(ceo)) {
                ControllerUtil.sucessMessage(req, "CEO salvo com sucesso.");
            }
            
            else {
                ControllerUtil.errorMessage(req, "O CEO não pôde ser salvo.");
            }
            
        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void updateCEO(HttpServletRequest req, HttpServletResponse resp) {
        String ceoCpf = req.getParameter("cpf");
        String ceoRg = req.getParameter("rg");
        Integer userId = Integer.parseInt(req.getParameter("user"));
        Integer companyId = Integer.parseInt(req.getParameter("company"));
        
        
        try {
        	
        	CEO ceo = loadCEO(req);
            ceo.setCpf(ceoCpf);
            ceo.setRg(ceoRg);
            ceo.setUser(new User(userId));
            ceo.setCompany(new Company(companyId));

            CEODAO dao = DAOFactory.createDAO(CEODAO.class);
        	
            if (dao.update(ceo)) {
                ControllerUtil.sucessMessage(req, "CEO atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "O CEO não pôde ser atualizado.");
            }

        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteCEO(HttpServletRequest req, HttpServletResponse resp) {
        String ceoIdParameter = req.getParameter("id");

        int ceoId = Integer.parseInt(ceoIdParameter);

        CEODAO dao = DAOFactory.createDAO(CEODAO.class);

        try {
            CEO ceo = dao.findById(ceoId);

            if (ceo == null)
                throw new ModelException("CEO não encontrado para deleção.");

            if (dao.delete(ceo)) {
                ControllerUtil.sucessMessage(req, "CEO deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req, "CEO não pôde ser deletado. Há dados relacionados ao CEO.");
            }
        } catch (ModelException e) {
            // log no servidor
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                ControllerUtil.errorMessage(req, e.getMessage());
            }
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }
}
