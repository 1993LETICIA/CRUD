package controller;

import java.io.IOException;
import java.util.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.Pagamento;
import model.Post;
import model.User;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.dao.PagamentoDAO;
import model.dao.*;
@WebServlet(urlPatterns = {"/pagamentos", "/pagamento/form", "/pagamento/insert", "/pagamento/delete", "/pagamento/update"})
public class PagamentosController extends HttpServlet{
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();
        
        switch (action) {
            case "/crud-manager/pagamento/form": {
                CommonsController.listUsers(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-pagamento.jsp");
                break;
            }
            
            case "/crud-manager/pagamento/update": {
                
                String idStr = req.getParameter("pagamentoId");
                int idPagamento = Integer.parseInt(idStr);
                
                PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);
                
                Pagamento pagamento = null;
                try {
                    pagamento = dao.findById(idPagamento);
                } catch (ModelException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                CommonsController.listUsers(req);
                req.setAttribute("action", "update");
                req.setAttribute("pagamento", pagamento);
                ControllerUtil.forward(req, resp, "/form-pagamento.jsp");
                break;
            }
            
            default:
                
                listPagamentos(req);
                
                ControllerUtil.transferSessionMessagesToRequest(req);
                
                ControllerUtil.forward(req, resp, "/pagamentos.jsp");

        }
    }
    private void listPagamentos(HttpServletRequest req) {
        PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);
        
        List<Pagamento> pagamentos = null;
        try {
            pagamentos = dao.listAll();
        } catch (ModelException e) {
            // Log no servidor
            e.printStackTrace();
        }
        
        if (pagamentos != null)
            req.setAttribute("pagamentos", pagamentos);
        
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();
    
        switch (action) {
            case "/crud-manager/pagamento/insert":
                insertPagamento(req,resp);
                break;
            case "/crud-manager/pagamento/delete":
                deletePagamento(req, resp);
                break;
            case "/crud-manager/pagamento/update":
                updatePagamento(req, resp);
                break;
            default:
                System.out.println("URL inválida" + action);
        }
        
        ControllerUtil.redirect(resp, req.getContextPath() + "/pagamentos");

    }
    
    private void updatePagamento(HttpServletRequest req, HttpServletResponse resp) {
        String pagamentoIdStr = req.getParameter("pagamentoId");
        String salario = req.getParameter("salario");
        String bonus = req.getParameter("bonus");
        String dataAdmin = req.getParameter("dataAdmin");
        Integer qtdDias = Integer.parseInt(req.getParameter("qtdDias"));
        Integer userId = Integer.parseInt(req.getParameter("user"));
        
        Pagamento pagamento = new Pagamento(Integer.parseInt(pagamentoIdStr));
        pagamento.setSalario(salario);
        pagamento.setBonus(bonus);
        pagamento.setDataAdmin(ControllerUtil.formatDate(dataAdmin));
        pagamento.setQtdDias(qtdDias);
        pagamento.setUser(new User(userId));
        
        PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);
        
        try {
            if (dao.update(pagamento)) {
                ControllerUtil.sucessMessage(req, "Pagamento  atualizado com sucesso.");
            }
            else {
                ControllerUtil.errorMessage(req, "Pagamento não pode ser atualizado.");
            }                
        } catch (ModelException e) {
            // log no servidor
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
        
    }
    private void deletePagamento(HttpServletRequest req, HttpServletResponse resp) {
        String pagamentoIdParameter = req.getParameter("id");
        
        int pagamentoId = Integer.parseInt(pagamentoIdParameter);
        
       PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);
        
        try {
        	Pagamento pagamento = dao.findById(pagamentoId);
            
            if (pagamento == null)
                throw new ModelException("Pagamento não encontrado para deleção.");
            
            if (dao.delete(pagamento)) {
                ControllerUtil.sucessMessage(req, "Pagamento deletado com sucesso.");
            }
            else {
                ControllerUtil.errorMessage(req, "Pagamento  não pode ser deletado. Há dados relacionados á empresa.");
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
    private void insertPagamento(HttpServletRequest req, HttpServletResponse resp) {
        String salario = req.getParameter("salario");
        String bonus = req.getParameter("bonus");
        String dataAdmin = req.getParameter("dataAdmin");
        String qtdDias = req.getParameter("qtdDias");
        Integer userId = Integer.parseInt(req.getParameter("user"));
        
      Pagamento pag = new Pagamento();
        pag.setSalario(salario);
        pag.setBonus(bonus);
        pag.setDataAdmin(ControllerUtil.formatDate(dataAdmin));
        Integer QtdDias = Integer.parseInt(req.getParameter("qtdDias"));
        pag.setUser(new User(userId));
        
        PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);
        
        try {
            if (dao.save(pag)) {
                ControllerUtil.sucessMessage(req, "Pagamento salvo com sucesso.");
            }
            else {
                ControllerUtil.errorMessage(req, "Pagamento não pode ser salvo.");
            }
        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
        
    }
    
}