package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.ModelException;
import model.Pagamento;
import model.User;

public class MySQLPagamentoDAO implements PagamentoDAO {

    @Override
    public boolean save(Pagamento pagamento) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sqlInsert = "INSERT INTO pagamentos VALUES (DEFAULT, ?, ?, ?, ?, ?);";
        
        db.prepareStatement(sqlInsert);
        
        db.setString(1, pagamento.getSalario());
        db.setString(2, pagamento.getBonus());
        db.setDate(3, pagamento.getDataAdmin() == null ? new Date() : pagamento.getDataAdmin());
        db.setInt(4, pagamento.getQtdDias());
        db.setInt(5, pagamento.getUser().getId());
        
    
        return db.executeUpdate() > 0;    
    }

    @Override
    public boolean update(Pagamento pagamento) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sqlUpdate = "UPDATE pagamentos "
                + " SET salario = ?, "
                + " bonus = ?, "
                + " dataAdmin = ?, "
                + " qtdDias = ?, "
                + " user_id = ? "
                + " WHERE id = ?; "; 
        
        db.prepareStatement(sqlUpdate);
        
        db.setString(1, pagamento.getSalario());
        db.setString(2, pagamento.getBonus());
        db.setDate(3, pagamento.getDataAdmin() == null ? new Date() : pagamento.getDataAdmin());
        db.setInt(4, pagamento.getQtdDias());
        db.setInt(5, pagamento.getUser().getId());
        db.setInt(6, pagamento.getId());
        
        return db.executeUpdate() > 0;
    }
    @Override
    public boolean delete(Pagamento pagamento) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sqlDelete = " DELETE FROM pagamentos "
                 + " WHERE id = ?;";

        db.prepareStatement(sqlDelete);        
        db.setInt(1, pagamento.getId());
        
        return db.executeUpdate() > 0;
    }

    @Override
    public List<Pagamento> listAll() throws ModelException {
        DBHandler db = new DBHandler();
        
        List<Pagamento> pagamentos = new ArrayList<Pagamento>();
            
        // Declara uma instrução SQL
        String sqlQuery = " SELECT c.id as 'pagamento_id', c.*, u.* \n"
                + " FROM pagamentos c \n"
                + " INNER JOIN users u \n"
                + " ON c.user_id = u.id;";
        
        db.createStatement();
    
        db.executeQuery(sqlQuery);

        while (db.next()) {
            User user = new User(db.getInt("user_id"));
            user.setName(db.getString("salario"));
            user.setGender(db.getString("bonus"));
            user.setEmail(db.getString("dataAdmin"));
            
            Pagamento pagamento = new Pagamento(db.getInt(" pagamento_id"));
            pagamento.setSalario(db.getString("salario"));
            pagamento.setBonus(db.getString("bonus"));
            pagamento.setDataAdmin(db.getDate("dataAdmin"));
            pagamento.setQtdDias(db.getInt("qtdDias"));
            pagamento.setUser(user);
            
            pagamentos.add( pagamento);
        }
        
        return  pagamentos;
    }
    @Override
    public Pagamento findById(int id) throws ModelException {
        DBHandler db = new DBHandler();
        
        String sql = "SELECT * FROM  pagamentos WHERE id = ?;";
        
        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();
        
        Pagamento c = null;
        while (db.next()) {
            c = new Pagamento(id);
            c.setSalario(db.getString("salario"));
            c.setBonus(db.getString("bonus"));
            c.setDataAdmin(db.getDate("dataAdmin"));
            c.setQtdDias(db.getInt("qtdDias"));
            
            UserDAO userDAO = DAOFactory.createDAO(UserDAO.class); 
            User user = userDAO.findById(db.getInt("user_id"));
            c.setUser(user);
            
            break;
        }
        
        return c;
    }
}

