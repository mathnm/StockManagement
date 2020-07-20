package dao;

import factory.ConnectionFactory;
import modelo.Compra;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Compra> getList() {
		List<Compra> compras = new ArrayList<>();
		String sql = "SELECT * FROM acao";
		try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
            	Compra c = new Compra();
            	c.setNome(rs.getString("nome"));
            	c.setVlrUnit(rs.getDouble("vlrunit"));
            	c.setQtd(rs.getInt("qtd"));
            	c.setVlrTot(rs.getDouble("vlrtot"));
            	c.setData(rs.getString("data"));
            	
            	compras.add(c);            	
            }
            stmt.close();
            rs.close();
            connection.close();
        } 
        catch (SQLException u) { 
        	System.out.println("lista não retornada");
            return null;
        } 
		
		return compras;
		
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
