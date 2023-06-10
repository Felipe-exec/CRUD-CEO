package model.dao;

import java.util.List;

import model.ModelException;
import model.CEO;

public interface CEODAO {
    boolean save(CEO ceo) throws ModelException;
    boolean update(CEO ceo) throws ModelException;
    boolean delete(CEO ceo) throws ModelException;
    List<CEO> listAll() throws ModelException;
    CEO findById(int id) throws ModelException;
}