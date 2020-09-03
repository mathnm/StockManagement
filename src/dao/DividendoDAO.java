package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Dividendo;

public class DividendoDAO {

	private Connection connection;
	
	String nome;
	double vlrUnit;
	int qtd;
	double vlrTot;
	String data;
	
	public DividendoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Dividendo dividendo){ 
        String sql = "INSERT INTO dividendo(nome,qtd,vlrunit,vlrtot,data) VALUES(?,?,?,?,?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, dividendo.getNome());
            stmt.setInt(2, dividendo.getQtd());
            stmt.setDouble(3, dividendo.getVlrUnit());
            stmt.setDouble(4, dividendo.getVlrTot());
            stmt.setString(5, dividendo.getData());
            stmt.execute();
            stmt.close();
        } 
        catch (SQLException u) { 
            throw new RuntimeException(u);
        } 
        
    }
	
	public List<Dividendo> getList() {
		List<Dividendo> dividendos = new ArrayList<>();
		String sql = "SELECT * FROM dividendo";
		try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
            	Dividendo d = new Dividendo();
            	d.setNome(rs.getString("nome"));
            	d.setQtd(rs.getInt("qtd"));
            	d.setVlrUnit(rs.getDouble("vlrunit"));
            	d.setVlrTot(rs.getDouble("vlrtot"));
            	d.setData(rs.getString("data"));
            	
            	dividendos.add(d);            	
            }
            stmt.close();
            rs.close();
            connection.close();
        } 
        catch (SQLException u) { 
        	System.out.println("lista não retornada");
            return null;
        } 
		
		return dividendos;
		
	}
	
	public void exclui(Dividendo dividendo) {
		String sql = "DELETE FROM dividendo WHERE nome = '"+ dividendo.getNome()+ "' and vlrunit = "+dividendo.getVlrUnit()+" and qtd = "+dividendo.getQtd()+" and vlrtot = "+dividendo.getVlrTot()+" and data = '"+dividendo.getData()+"'";
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
