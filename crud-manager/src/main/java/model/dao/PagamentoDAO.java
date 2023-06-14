package model.dao;

import java.util.List;

import model.ModelException;
import model.Pagamento;

public interface PagamentoDAO {
	boolean save(Pagamento pagamento) throws ModelException;
	boolean update(Pagamento pagamento) throws ModelException;
	boolean delete(Pagamento pagamento) throws ModelException;
	List<Pagamento> listAll() throws ModelException;
	Pagamento findById(int id) throws ModelException;
}
