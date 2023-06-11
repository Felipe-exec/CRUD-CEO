package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.User;
import model.CEO;
import model.Company;

public class MySQLCEODAO implements CEODAO {

    @Override
    public boolean save(CEO ceo) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sqlInsert = "INSERT INTO ceos (cpf, rg, user_id, company_id) VALUES (?, ?, ?, ?);";
        
        db.prepareStatement(sqlInsert);
        db.setString(1, ceo.getCpf());
        db.setString(2, ceo.getRg());
        db.setInt(3, ceo.getUser().getId());
        db.setInt(4, ceo.getCompany().getId());
        
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(CEO ceo) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sqlUpdate = "UPDATE ceos "
				+ " SET cpf = ?, "
				+ " rg = ?, "
				+ " user_id = ?, "
				+ " company_id = ? "
				+ " WHERE id = ?; "; 
        
        
        db.prepareStatement(sqlUpdate);
        db.setString(1, ceo.getCpf());
        db.setString(2, ceo.getRg());
        db.setInt(3, ceo.getUser().getId());
        db.setInt(4, ceo.getCompany().getId());
        db.setInt(5, ceo.getId());
        
        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(CEO ceo) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sqlDelete = "DELETE FROM ceos WHERE id = ?";
        
        db.prepareStatement(sqlDelete);
        db.setInt(1, ceo.getId());
        
        try {
            return db.executeUpdate() > 0;
        } catch (ModelException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return false;
            }
            throw e;
        }
    }

    @Override
    public List<CEO> listAll() throws ModelException {
        DBHandler db = new DBHandler();
        
        List<CEO> ceos = new ArrayList<>();
        
        String sqlQuery = "SELECT * FROM ceos";
        
        db.createStatement();
        
        db.executeQuery(sqlQuery);
        
        while (db.next()) {
            CEO c = createCEO(db);
            ceos.add(c);
        }
        
        return ceos;
    }

    @Override
    public CEO findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sql = "SELECT * FROM ceos WHERE id = ?";
        
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();
        
        CEO c = null;
        while (db.next()) {
            c = createCEO(db);
            break;
        }
        
        return c;
    }
    
    private CEO createCEO(DBHandler db) throws ModelException {
    	CEO c = new CEO(db.getInt("id"));
    	c.setCpf(db.getString("cpf"));
        c.setRg(db.getString("rg"));
		
		
		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class); 
		
		User user = userDAO.findById(db.getInt("user_id"));
		c.setUser(user);
		
		CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class);
		
		Company company = companyDAO.findById(db.getInt("company_id"));
		c.setCompany(company);
		
		return c;
	}
}
