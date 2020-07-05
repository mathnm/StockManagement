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
    @FXML TableColumn<TableC, Number> colQtd;
    @FXML TableColumn<TableC, String> colData;
    
    @FXML TableView<TableV> tblVenda;
    @FXML TableColumn<TableV, String> colAcaoV;
    @FXML TableColumn<TableV, Number> colVlrUnitV;
    @FXML TableColumn<TableV, Number> colVlrUnitC;
    @FXML TableColumn<TableV, Number> colQtdV;
    @FXML TableColumn<TableV, String> colDataC;
    @FXML TableColumn<TableV, String> colDataV;
    
    ArrayList<TableC> tabelaC = new ArrayList<TableC>();
    ArrayList<TableV> tabelaV = new ArrayList<TableV>();
    
    public void insereNaTabela() {
    	tabelaC.add(new TableC(acao.getText(),Double.parseDouble(vlrUnit.getText()),Integer.parseInt(qtd.getText()),data.getText()));
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
    }
    
    
    public void atualizaTbl() {
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
	}
    
    public void venda() {
    	tabelaV.add(new TableV(tblCompra.getSelectionModel().getSelectedItem().getAcao(),
    			tblCompra.getSelectionModel().getSelectedItem().getVlrUnit(),
    			tblCompra.getSelectionModel().getSelectedItem().getQtd(),
    			Double.parseDouble(vlrUnitVen.getText()),
    			tblCompra.getSelectionModel().getSelectedItem().getData(),
    			new Date().toString()
    			));
    	tblVenda.setItems(FXCollections.observableArrayList(tabelaV));
    	atualizaLucro();
    }
    
    public void atualizaLucro() {
    	
    	if(!tblVenda.getColumns().isEmpty()) {
    		Double vlrVenda = 0.0;
    		Double vlrCompra = 0.0;
    		Double vlrFinal = 0.0;
    		
    		for (int i = 0; i < tabelaV.size(); i++) {
    			vlrVenda 	+= colVlrUnitV.getCellData(i).doubleValue()*colQtdV.getCellData(i).doubleValue();
    			vlrCompra	+= colVlrUnitC.getCellData(i).doubleValue()*colQtdV.getCellData(i).doubleValue();
			}
    		
    		vlrFinal = vlrVenda - vlrCompra;
    		
    		lucro.setText("Lucro: R$"+vlrFinal);
    	}
    	
    }
    
    public void initialize() {
    	colAcao.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());
    	colVlrUnit.setCellValueFactory(cellData -> cellData.getValue().vlrUnitProperty());
    	colQtd.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
    	
    	colAcaoV.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());
    	colVlrUnitV.setCellValueFactory(cellData -> cellData.getValue().vlrUnitVProperty());
    	colVlrUnitC.setCellValueFactory(cellData -> cellData.getValue().vlrUnitCProperty());
    	colQtdV.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colDataC.setCellValueFactory(cellData -> cellData.getValue().dataCProperty());
    	colDataV.setCellValueFactory(cellData -> cellData.getValue().dataVProperty());
    	
    	//TALVEZ SEJA AQUI QUE EU TENHA QUE LER DO BANCO DE DADOS OS DADOS JA GRAVADOS
		atualizaTbl();
	}
    
}
