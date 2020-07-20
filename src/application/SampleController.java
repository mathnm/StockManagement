package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.Compra;
import modelo.Venda;

import java.util.ArrayList;

import dao.CompraDAO;
import dao.VendaDAO;

public class SampleController {

    @FXML TextField acao;
    @FXML TextField vlrUnit;
    @FXML TextField qtd;
    @FXML DatePicker data;
    @FXML DatePicker dataVen;
    
    @FXML TextField vlrUnitVen;
    @FXML Label lucro;
    
    @FXML TableView<TableC> tblCompra;
    @FXML TableColumn<TableC, String> colAcao;
    @FXML TableColumn<TableC, Number> colVlrUnit;
    @FXML TableColumn<TableC, Number> colVlrTotal;
    @FXML TableColumn<TableC, Number> colQtd;
    @FXML TableColumn<TableC, String> colData;
    
    public double totC;
    public double totV;
    
    @FXML TableView<TableV> tblVenda;
    @FXML TableColumn<TableV, String> colAcaoV;
    @FXML TableColumn<TableV, Number> colVlrUnitC;
    @FXML TableColumn<TableV, Number> colVlrTotC;
    @FXML TableColumn<TableV, Number> colQtdV;
    @FXML TableColumn<TableV, Number> colVlrUnitV;
    @FXML TableColumn<TableV, Number> colVlrTotV;
    @FXML TableColumn<TableV, String> colDataV;
    @FXML TableColumn<TableV, String> colDataC;
    
    ArrayList<TableC> tabelaC = new ArrayList<TableC>();
    ArrayList<TableV> tabelaV = new ArrayList<TableV>();
    
    public void insereNaTabela() {
    	if(acao.getText().isEmpty() || vlrUnit.getText().isEmpty() || qtd.getText().isEmpty() || data.getValue().toString().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erro!");
    		alert.setHeaderText("Campo em branco.");
    		alert.setContentText("Preencha todos os campos da compra.");

    		alert.showAndWait();    		
    		
    	} else {
    		totC = Double.parseDouble(vlrUnit.getText()) * Integer.parseInt(qtd.getText());
        	tabelaC.add(new TableC(acao.getText(),Double.parseDouble(vlrUnit.getText()), totC, Integer.parseInt(qtd.getText()),data.getValue().toString()));
        	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
        	
        	Compra compra = new Compra();
        	compra.setNome(acao.getText());
        	compra.setVlrUnit(Double.parseDouble(vlrUnit.getText()));
        	compra.setQtd(Integer.parseInt(qtd.getText()));
        	compra.setVlrTot(totC);
        	compra.setData(data.getValue().toString());
        	
        	CompraDAO dao = new CompraDAO();
        	dao.adiciona(compra);
        	
        	acao.setText("");
        	vlrUnit.setText("");
        	qtd.setText("");
        	data.setValue(null);
        	
    	}
    	    	
    }
    
    
    public void atualizaTbl() {
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
	}
    
    public void venda() {
    	//try {
    		
    		totC = tblCompra.getSelectionModel().getSelectedItem().getVlrTotal();
        	totV = Double.parseDouble(vlrUnitVen.getText())*tblCompra.getSelectionModel().getSelectedItem().getQtd();
        	
        	Venda venda = new Venda();
        	
        	venda.setNome(tblCompra.getSelectionModel().getSelectedItem().getAcao());
        	venda.setVlrUnitC(tblCompra.getSelectionModel().getSelectedItem().getVlrUnit());
        	venda.setVlrTotC(totC);
        	venda.setQtd(tblCompra.getSelectionModel().getSelectedItem().getQtd());
        	venda.setVlrUnitV(Double.parseDouble(vlrUnitVen.getText()));
        	venda.setVlrTotV(totV);
        	venda.setDataV(dataVen.getValue().toString());
        	venda.setDataC(tblCompra.getSelectionModel().getSelectedItem().getData());
        	
        	VendaDAO dao = new VendaDAO();
        	dao.adiciona(venda);
        	
        	tabelaV.add(new TableV(tblCompra.getSelectionModel().getSelectedItem().getAcao(),
        			tblCompra.getSelectionModel().getSelectedItem().getVlrUnit(),
        			totC,
        			tblCompra.getSelectionModel().getSelectedItem().getQtd(),
        			Double.parseDouble(vlrUnitVen.getText()),
        			totV,
        			tblCompra.getSelectionModel().getSelectedItem().getData(),
        			dataVen.getValue().toString()
        			));        	
        	tblVenda.setItems(FXCollections.observableArrayList(tabelaV));					//inclui a ação vendida na visualização da tabela de venda
        	
        	Compra compra = new Compra();
        	
        	compra.setNome(tblCompra.getSelectionModel().getSelectedItem().getAcao());
        	compra.setVlrUnit(tblCompra.getSelectionModel().getSelectedItem().getVlrUnit());
        	compra.setQtd(tblCompra.getSelectionModel().getSelectedItem().getQtd());
        	compra.setVlrTot(tblCompra.getSelectionModel().getSelectedItem().getVlrTotal());
        	compra.setData(tblCompra.getSelectionModel().getSelectedItem().getData());
        	
        	CompraDAO daoC = new CompraDAO();
        	daoC.exclui(compra);
        	
        	
        	tabelaC.remove(tblCompra.getSelectionModel().getSelectedIndex()); 				//remove a ação vendida do array
        	tblCompra.getItems().remove(tblCompra.getSelectionModel().getSelectedIndex()); 	//remove a ação vendida da visualizaçao da tabela de compra
        	atualizaLucroNaVenda();
//    	} catch (Exception e) {
//    		Alert alert = new Alert(AlertType.ERROR);
//    		alert.setTitle("Erro!");
//    		alert.setHeaderText("Possíveis erros:");
//    		alert.setContentText("- Valor de venda inválido \n"
//    				+ "- Data da venda em branco \n"
//    				+ "- Nenhuma ação selecionada para venda");
//
//    		alert.showAndWait();
//    	}
    	
    }
    
    public void atualizaLucroNaVenda() {
    	
    	if(!tblVenda.getColumns().isEmpty()) {
    		Double vlrVenda = 0.0;
    		Double vlrCompra = 0.0;
    		Double vlrFinal = 0.0;
    		
    		for (int i = 0; i < tabelaV.size(); i++) {
    			vlrVenda 	+= colVlrTotV.getCellData(i).doubleValue();
    			vlrCompra	+= colVlrTotC.getCellData(i).doubleValue();
			}
    		
    		vlrFinal = vlrVenda - vlrCompra;
    		
    		lucro.setText("Lucro: R$"+vlrFinal);
    	}
    	
    }
    
    public void excluiRegistroC() {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar ação?", ButtonType.YES, ButtonType.NO);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    		
    		Compra compra = new Compra();
    		compra.setNome(tblCompra.getSelectionModel().getSelectedItem().getAcao());
        	compra.setVlrUnit(tblCompra.getSelectionModel().getSelectedItem().getVlrUnit());
        	compra.setQtd(tblCompra.getSelectionModel().getSelectedItem().getQtd());
        	compra.setVlrTot(tblCompra.getSelectionModel().getSelectedItem().getVlrTotal());
        	compra.setData(tblCompra.getSelectionModel().getSelectedItem().getData());
        	
        	CompraDAO dao = new CompraDAO();
        	dao.exclui(compra);
    		
    		tabelaC.remove(tblCompra.getSelectionModel().getSelectedIndex());
        	tblCompra.getItems().remove(tblCompra.getSelectionModel().getSelectedIndex());
    	}
    	
    }
    
    public void excluiRegistroV() {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar venda?", ButtonType.YES, ButtonType.NO);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
    		tabelaV.remove(tblVenda.getSelectionModel().getSelectedIndex());
    		tblVenda.getItems().remove(tblVenda.getSelectionModel().getSelectedIndex());
    		atualizaLucroDesfeito();
    	}
    }
    
    public void desfazVenda() {
    	
    	tabelaC.add(new TableC(tblVenda.getSelectionModel().getSelectedItem().getAcao(),
    			tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC(),
    			tblVenda.getSelectionModel().getSelectedItem().getVlrTotC(),
    			tblVenda.getSelectionModel().getSelectedItem().getQtd(),
    			tblVenda.getSelectionModel().getSelectedItem().getDataC()
    			));
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
    	
    	Compra compra = new Compra();
		compra.setNome(tblVenda.getSelectionModel().getSelectedItem().getAcao());
    	compra.setVlrUnit(tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC());
    	compra.setQtd(tblVenda.getSelectionModel().getSelectedItem().getQtd());
    	compra.setVlrTot(tblVenda.getSelectionModel().getSelectedItem().getVlrTotC());
    	compra.setData(tblVenda.getSelectionModel().getSelectedItem().getDataC());
    	
    	CompraDAO dao = new CompraDAO();
    	dao.adiciona(compra);
    	
    	tabelaV.remove(tblVenda.getSelectionModel().getSelectedIndex());
    	tblVenda.getItems().remove(tblVenda.getSelectionModel().getSelectedIndex());
    	atualizaLucroDesfeito();
    	
    	Venda venda = new Venda();
    	
    	venda.setNome(tblCompra.getSelectionModel().getSelectedItem().getAcao());
    	venda.setVlrUnitC(tblCompra.getSelectionModel().getSelectedItem().getVlrUnit());
    	venda.setVlrTotC(totC);
    	venda.setQtd(tblCompra.getSelectionModel().getSelectedItem().getQtd());
    	venda.setVlrUnitV(Double.parseDouble(vlrUnitVen.getText()));
    	venda.setVlrTotV(totV);
    	venda.setDataV(dataVen.getValue().toString());
    	venda.setDataC(tblCompra.getSelectionModel().getSelectedItem().getData());
    	
    	VendaDAO daoV = new VendaDAO();
    	daoV.exclui(venda);
    	
    }
	    
	public void atualizaLucroDesfeito() {
	    	
	    	if(!tblVenda.getColumns().isEmpty()) {
	    		Double vlrVenda = 0.0;
	    		Double vlrCompra = 0.0;
	    		Double vlrFinal = 0.0;
	    		
	    		for (int i = 0; i < tabelaV.size(); i++) {
	    			vlrVenda 	+= colVlrTotV.getCellData(i).doubleValue();
	    			vlrCompra	+= colVlrTotC.getCellData(i).doubleValue();
				}
	    		
	    		vlrFinal = vlrVenda - vlrCompra;
	    		
	    		lucro.setText("Lucro: R$"+vlrFinal);
	    	} else {
	    		lucro.setText("R$ 0.00");
	    	}
	    	
	    }
    
    public void initialize() {
    	colAcao.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());
    	colVlrUnit.setCellValueFactory(cellData -> cellData.getValue().vlrUnitProperty());
    	colVlrTotal.setCellValueFactory(cellData -> cellData.getValue().vlrTotalProperty());
    	colQtd.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
    	
    	colAcaoV.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());    	
    	colVlrUnitC.setCellValueFactory(cellData -> cellData.getValue().vlrUnitCProperty());
    	colVlrTotC.setCellValueFactory(cellData -> cellData.getValue().vlrTotCProperty());
    	colQtdV.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colVlrUnitV.setCellValueFactory(cellData -> cellData.getValue().vlrUnitVProperty());
    	colVlrTotV.setCellValueFactory(cellData -> cellData.getValue().vlrTotVProperty());
    	colDataC.setCellValueFactory(cellData -> cellData.getValue().dataCProperty());
    	colDataV.setCellValueFactory(cellData -> cellData.getValue().dataVProperty());
    	
    	//TALVEZ SEJA AQUI QUE EU TENHA QUE LER DO BANCO DE DADOS OS DADOS JA GRAVADOS
		atualizaTbl();
	}
    
    
    
}
