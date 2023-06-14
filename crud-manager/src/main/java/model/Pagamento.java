package model;

import java.util.Date;

public class Pagamento {
	private int id;
	private String salario;
	private String bonus;
	private Date dataAdmin;
	private int qtdDias ;
	private User user;
	
	public Pagamento() {}
	
	public  Pagamento(int id) {
		this.id = id;
	}
	
	
	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public Date getDataAdmin() {
		return dataAdmin;
	}
	public void setDataAdmin(Date dataAdmin) {
		this.dataAdmin = dataAdmin;
	}
	public int getQtdDias() {
		return qtdDias;
	}
	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
}
