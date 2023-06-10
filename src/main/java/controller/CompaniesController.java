package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.User;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/companies", "/company/form", "/company/insert", "/company/update", "/company/delete"})
public class CompaniesController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/CRUD-CEO/company/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-company.jsp");
			break;
		}
		case "/CRUD-CEO/company/update": {
			CommonsController.listUsers(req);
			Company c = loadCompany(req);
			req.setAttribute("company", c);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-company.jsp");
			break;
		}
		default:
			listCompanies(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
			
			ControllerUtil.forward(req, resp, "/companies.jsp");
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
		case "/CRUD-CEO/company/insert": {
			insertCompany(req, resp);
			break;
		}
		case "/CRUD-CEO/company/update": {
			updateCompany(req, resp);
			break;
		}
		case "/CRUD-CEO/company/delete": {
			deleteCompany(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
			break;
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/companies");
	}

	private Company loadCompany(HttpServletRequest req) {
		String companyIdParameter = req.getParameter("companyId");
		
		int companyId = Integer.parseInt(companyIdParameter);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			Company company = dao.findById(companyId);
			
			if (company == null)
				throw new ModelException("Empresa não encontrada para alteração");
			
			return company;
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}
	
	private void listCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		List<Company> companies = null;
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (companies != null)
			req.setAttribute("companies", companies);
	}
	
	private void insertCompany(HttpServletRequest req, HttpServletResponse resp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String companyName = req.getParameter("name");
		String companyRole = req.getParameter("role");
		String companyStart = req.getParameter("start");
		String companyEnd = req.getParameter("end");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		try {
			Company company = new Company();
			company.setName(companyName);
			company.setRole(companyRole);
			company.setStart(sdf.parse(companyStart));
			
			if (!companyEnd.isEmpty()) {
				company.setEnd(sdf.parse(companyEnd));
			}
			
			company.setUser(new User(userId));
			
			CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
			
			if (dao.save(company)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + company.getName() + "' salva com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + company.getName() + "' não pode ser salva.");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, "Data inválida. Certifique-se de que o formato é dd/mm/aaaa.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateCompany(HttpServletRequest req, HttpServletResponse resp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String companyName = req.getParameter("name");
		String companyRole = req.getParameter("role");
		String companyStart = req.getParameter("start");
		String companyEnd = req.getParameter("end");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		try {
			Company company = loadCompany(req);
			company.setName(companyName);
			company.setRole(companyRole);
			company.setStart(sdf.parse(companyStart));
			
			if (!companyEnd.isEmpty()) {
				company.setEnd(sdf.parse(companyEnd));
			}
			
			company.setUser(new User(userId));
			
			CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
			
			if (dao.update(company)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + company.getName() + "' atualizada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + company.getName() + "' não pode ser atualizada.");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}		
	}
	
	private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) {
		String companyIdParameter = req.getParameter("id");
		
		int companyId = Integer.parseInt(companyIdParameter);
		
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		
		try {
			Company company = dao.findById(companyId);
			
			if (company == null)
				throw new ModelException("Empresa não encontrada para remoção.");
			
			if (dao.delete(company)) {
				ControllerUtil.sucessMessage(req, "Empresa '" + company.getName() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Empresa '" + company.getName() + "' não pode ser deletada. Há dados relacionados ao usuário.");
			}
		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}