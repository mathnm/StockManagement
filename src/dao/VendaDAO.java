package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import factory.ConnectionFactory;
import modelo.Venda;

public class VendaDAO {

private Connection connection;
	
	String nome;
	double vlrUnitC;
	double vlrTotC;
	int qtd;
	double vlrUnitV;
	double vlrTotV;
	String dataC;
	String dataV;
	
	public VendaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Venda venda){ 
        String sql = "INSERT INTO venda(nome,vlrunitc,vlrtotc,qtd,vlrunitv,vlrtotv,datav,datac) VALUES(?,?,?,?,?,?,?,?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, venda.getNome());
            stmt.setDouble(2, venda.getVlrUnitC());
            stmt.setDouble(3, venda.getVlrTotC());
            stmt.setInt(4, venda.getQtd());
            stmt.setDouble(5, venda.getVlrUnitV());
            stmt.setDouble(6, venda.getVlrTotV());
            stmt.setString(7, venda.getDataV());
            stmt.setString(8, venda.getDataC());
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
	
	public void exclui(Venda venda) {
		String sql = "DELETE FROM venda WHERE nome = '"+ venda.getNome()+ "' and vlrunitc = "+venda.getVlrUnitC()+" and vlrtotc = "+venda.getVlrTotC()+" and qtd = "+venda.getQtd()+" and vlrunitv = "+venda.getVlrUnitV()+" and vlrtotv = "+venda.getVlrTotV()+" and datav = '"+venda.getDataV()+"'"+" and datac = '"+venda.getDataC()+"'";
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
