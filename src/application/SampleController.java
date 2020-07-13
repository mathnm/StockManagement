package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Date;

public class SampleController {

    @FXML TextField acao;
    @FXML TextField vlrUnit;
    @FXML TextField qtd;
    @FXML TextField data;
    
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
    	totC = Double.parseDouble(vlrUnit.getText()) * Integer.parseInt(qtd.getText());
    	tabelaC.add(new TableC(acao.getText(),Double.parseDouble(vlrUnit.getText()), totC, Integer.parseInt(qtd.getText()),data.getText()));
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
    }
    
    
    public void atualizaTbl() {
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
	}
    
    public void venda() {
    	
    	totC = tblCompra.getSelectionModel().getSelectedItem().getVlrTotal();
    	totV = Double.parseDouble(vlrUnitVen.getText())*tblCompra.getSelectionModel().getSelectedItem().getQtd();
    	
    	tabelaV.add(new TableV(tblCompra.getSelectionModel().getSelectedItem().getAcao(),
    			tblCompra.getSelectionModel().getSelectedItem().getVlrUnit(),
    			totC,
    			tblCompra.getSelectionModel().getSelectedItem().getQtd(),
    			Double.parseDouble(vlrUnitVen.getText()),
    			totV,
    			tblCompra.getSelectionModel().getSelectedItem().getData(),
    			new Date().toString()
    			));
    	tblVenda.setItems(FXCollections.observableArrayList(tabelaV));					//inclui a ação vendida na visualização da tabela de venda
    	tabelaC.remove(tblCompra.getSelectionModel().getSelectedIndex()); 				//remove a ação vendida do array
    	tblCompra.getItems().remove(tblCompra.getSelectionModel().getSelectedIndex()); 	//remove a ação vendida da visualizaçao da tabela de compra
    	atualizaLucroNaVenda();
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
    	tabelaC.remove(tblCompra.getSelectionModel().getSelectedIndex());
    	tblCompra.getItems().remove(tblCompra.getSelectionModel().getSelectedIndex());
    }
    
    public void excluiRegistroV() {
    	tabelaV.remove(tblVenda.getSelectionModel().getSelectedIndex());
    	tblVenda.getItems().remove(tblVenda.getSelectionModel().getSelectedIndex());
    	atualizaLucroDesfeito();
    }
    
    public void desfazVenda() {
    	
    	tabelaC.add(new TableC(tblVenda.getSelectionModel().getSelectedItem().getAcao(),
    			tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC(),
    			tblVenda.getSelectionModel().getSelectedItem().getVlrTotC(),
    			tblVenda.getSelectionModel().getSelectedItem().getQtd(),
    			tblVenda.getSelectionModel().getSelectedItem().getDataC()
    			));
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
    	tabelaV.remove(tblVenda.getSelectionModel().getSelectedIndex());
    	tblVenda.getItems().remove(tblVenda.getSelectionModel().getSelectedIndex());
    	atualizaLucroDesfeito();
    	
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
