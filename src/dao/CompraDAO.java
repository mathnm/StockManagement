package dao;

import factory.ConnectionFactory;
import modelo.Compra;

import java.sql.*;
import java.sql.PreparedStatement;

public class CompraDAO {

	private Connection connection;
	
	String nome;
	double vlrUnit;
	int qtd;
	double vlrTot;
	String data;
	
	public CompraDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Compra compra){ 
        String sql = "INSERT INTO acao(nome,vlrunit,qtd,vlrtot,data) VALUES(?,?,?,?,?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, compra.getNome());
            stmt.setDouble(2, compra.getVlrUnit());
            stmt.setInt(3, compra.getQtd());
            stmt.setDouble(4, compra.getVlrTot());
            stmt.setString(5, compra.getData());
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        } 
        
    } 
	
	public void seleciona() {
		//...
	}
	
	public void exclui(Compra compra) {
		String sql = "DELETE FROM acao WHERE nome = '"+ compra.getNome()+ "' and vlrunit = "+compra.getVlrUnit()+" and qtd = "+compra.getQtd()+" and vlrtot = "+compra.getVlrTot()+" and data = '"+compra.getData()+"'";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        } 
	}
	
}
