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
import modelo.Dividendo;
import modelo.Venda;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dao.CompraDAO;
import dao.DividendoDAO;
import dao.VendaDAO;

public class SampleController {

    @FXML TextField acao;
    @FXML TextField vlrUnit;
    @FXML TextField qtd;
    @FXML DatePicker data;
    @FXML DatePicker dataVen;
    
    @FXML TextField acaoD;
    @FXML TextField vlrUnitD;
    @FXML TextField qtdD;
    @FXML DatePicker dataD;
    
    @FXML TextField vlrUnitVen;
    @FXML Label lucro;
    @FXML Label investido;
    @FXML Label recebido;
    
    @FXML TableView<TableC> tblCompra;
    @FXML TableColumn<TableC, String> colAcao;
    @FXML TableColumn<TableC, Number> colVlrUnit;
    @FXML TableColumn<TableC, Number> colVlrTotal;
    @FXML TableColumn<TableC, Number> colQtd;
    @FXML TableColumn<TableC, String> colData;
    
    public double totC;
    public double totV;
    public double totD;
    
    @FXML TableView<TableV> tblVenda;
    @FXML TableColumn<TableV, String> colAcaoV;
    @FXML TableColumn<TableV, Number> colVlrUnitC;
    @FXML TableColumn<TableV, Number> colVlrTotC;
    @FXML TableColumn<TableV, Number> colQtdV;
    @FXML TableColumn<TableV, Number> colVlrUnitV;
    @FXML TableColumn<TableV, Number> colVlrTotV;
    @FXML TableColumn<TableV, String> colDataV;
    @FXML TableColumn<TableV, String> colDataC;
    
    @FXML TableView<TableD> tblDividendo;
    @FXML TableColumn<TableD, String> colAcaoD;
    @FXML TableColumn<TableD, Number> colVlrUnitD;
    @FXML TableColumn<TableD, Number> colVlrTotalD;
    @FXML TableColumn<TableD, Number> colQtdD;
    @FXML TableColumn<TableD, String> colDataD;
    
    ArrayList<TableC> tabelaC = new ArrayList<TableC>();
    ArrayList<TableV> tabelaV = new ArrayList<TableV>();
    ArrayList<TableD> tabelaD = new ArrayList<TableD>();
    
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    
    public void insereNaTabela() {
    	if(acao.getText().isEmpty() || vlrUnit.getText().isEmpty() || qtd.getText().isEmpty() || data.getValue().toString().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erro!");
    		alert.setHeaderText("Campo em branco.");
    		alert.setContentText("Preencha todos os campos da compra.");

    		alert.showAndWait();    		
    		
    	} else {
    		totC = Double.parseDouble(vlrUnit.getText()) * Double.parseDouble(qtd.getText());
        	tabelaC.add(new TableC(acao.getText(),Double.parseDouble(vlrUnit.getText()), totC, Integer.parseInt(qtd.getText()),data.getValue().toString()));
        	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
        	
        	
        	
        	BigDecimal bd = new BigDecimal(totC).setScale(2, RoundingMode.HALF_EVEN);
        	
        	Compra compra = new Compra();
        	compra.setNome(acao.getText());
        	compra.setVlrUnit(Double.parseDouble(vlrUnit.getText()));
        	compra.setQtd(Integer.parseInt(qtd.getText()));
        	compra.setVlrTot(bd.doubleValue());
        	compra.setData(data.getValue().toString());
        	
        	CompraDAO dao = new CompraDAO();
        	dao.adiciona(compra);
        	
        	acao.setText("");
        	vlrUnit.setText("");
        	qtd.setText("");
        	data.setValue(null);
        	
        	atualizaInvestimentoNaCompra();
        	
    	}
    	    	
    }
    
    public void recebeDiv() {
    	if(acaoD.getText().isEmpty() || vlrUnitD.getText().isEmpty() || qtdD.getText().isEmpty() || dataD.getValue().toString().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erro!");
    		alert.setHeaderText("Campo em branco.");
    		alert.setContentText("Preencha todos os campos da compra.");

    		alert.showAndWait();    		
    		
    	} else {
    		totD = Double.parseDouble(vlrUnitD.getText()) * Double.parseDouble(qtdD.getText());
        	tabelaD.add(new TableD(acaoD.getText(),Integer.parseInt(qtdD.getText()),Double.parseDouble(vlrUnitD.getText()), totD,dataD.getValue().toString()));
        	tblDividendo.setItems(FXCollections.observableArrayList(tabelaD));
        	
        	//DecimalFormat df = new DecimalFormat("#.00");
        	
        	BigDecimal bd = new BigDecimal(totD).setScale(2, RoundingMode.HALF_EVEN);
        	
        	Dividendo dividendo = new Dividendo();
        	dividendo.setNome(acaoD.getText());
        	dividendo.setVlrUnit(Double.parseDouble(vlrUnitD.getText()));
        	dividendo.setQtd(Integer.parseInt(qtdD.getText()));
        	dividendo.setVlrTot(bd.doubleValue());
        	dividendo.setData(dataD.getValue().toString());
        	
        	DividendoDAO dao = new DividendoDAO();
        	dao.adiciona(dividendo);
        	
        	acaoD.setText("");
        	vlrUnitD.setText("");
        	qtdD.setText("");
        	dataD.setValue(null);
        	
        	atualizaTotalRecebido();
    	}
    	    	
    }
    
    
    public void atualizaTbl() {
    	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
	}
    
    public void venda() {
    	try {
    		
    		totC = tblCompra.getSelectionModel().getSelectedItem().getVlrTotal();
        	totV = Double.parseDouble(vlrUnitVen.getText())*tblCompra.getSelectionModel().getSelectedItem().getQtd();
        	
        	Venda venda = new Venda();
        	
        	BigDecimal bdC = new BigDecimal(totC).setScale(2, RoundingMode.HALF_EVEN);
        	BigDecimal bdV = new BigDecimal(totV).setScale(2, RoundingMode.HALF_EVEN);
        	
        	venda.setNome(tblCompra.getSelectionModel().getSelectedItem().getAcao());
        	venda.setVlrUnitC(tblCompra.getSelectionModel().getSelectedItem().getVlrUnit());
        	venda.setVlrTotC(bdC.doubleValue());
        	venda.setQtd(tblCompra.getSelectionModel().getSelectedItem().getQtd());
        	venda.setVlrUnitV(Double.parseDouble(vlrUnitVen.getText()));
        	venda.setVlrTotV(bdV.doubleValue());
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
        	
        	for (int i = 0; i < tabelaC.size(); i++) {
				if(tabelaC.get(i).getAcao().equals(tblCompra.getSelectionModel().getSelectedItem().getAcao()) && tabelaC.get(i).getData().equals(tblCompra.getSelectionModel().getSelectedItem().getData()) && tabelaC.get(i).getVlrUnit() == tblCompra.getSelectionModel().getSelectedItem().getVlrUnit()) {
					tabelaC.remove(i);
				}
			} 
        	//tabelaC.remove(tblCompra.getSelectionModel().getSelectedIndex()); 				//remove a ação vendida do array
        	tblCompra.getItems().remove(tblCompra.getSelectionModel().getSelectedIndex()); 	//remove a ação vendida da visualizaçao da tabela de compra
        	atualizaLucroNaVenda();
        	atualizaInvestimentoNaCompra();
        	
        	vlrUnitVen.setText("");
        	dataVen.setValue(null);
        	
    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erro!");
    		alert.setHeaderText("Possíveis erros:");
    		alert.setContentText("- Valor de venda inválido \n"
    				+ "- Data da venda em branco \n"
    				+ "- Nenhuma ação selecionada para venda");

    		alert.showAndWait();
    	}
    	
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
    		
    		lucro.setText("Lucro: R$"+decimalFormat.format(vlrFinal));
    	}
    	
    }
    
    public void atualizaInvestimentoNaCompra() {
    	
    	if(!tblCompra.getColumns().isEmpty()) {
    		Double vlrCompra = 0.0;
    		
    		for (int i = 0; i < tabelaC.size(); i++) {
    			vlrCompra	+= colVlrTotal.getCellData(i).doubleValue();
			}
    		
    		investido.setText("Investido: R$"+decimalFormat.format(vlrCompra));
    	}
    	
    }
    
    public void atualizaTotalRecebido() {
    	
    	if(!tblDividendo.getColumns().isEmpty()) {
    		Double vlrRecebido = 0.0;
    		
    		for (int i = 0; i < tabelaD.size(); i++) {
    			vlrRecebido	+= colVlrTotalD.getCellData(i).doubleValue();
			}
    		
    		recebido.setText("Total Recebido: R$"+decimalFormat.format(vlrRecebido));
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
        		
            	//problema
            	for (int i = 0; i < tabelaC.size(); i++) {
    				if(tabelaC.get(i).getAcao().equals(tblCompra.getSelectionModel().getSelectedItem().getAcao()) && tabelaC.get(i).getData().equals(tblCompra.getSelectionModel().getSelectedItem().getData()) && tabelaC.get(i).getVlrUnit() == tblCompra.getSelectionModel().getSelectedItem().getVlrUnit()) {
    					tabelaC.remove(i);
    				}
    			} 
            	tblCompra.getItems().remove(tblCompra.getSelectionModel().getSelectedIndex());
            	atualizaInvestimentoNaCompra();
        	} 	
    }
    
    public void excluiRegistroD() {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar dividendo?", ButtonType.YES, ButtonType.NO);
    	alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES) {
    		
    		Dividendo dividendo = new Dividendo();
    		dividendo.setNome(tblDividendo.getSelectionModel().getSelectedItem().getAcao());
    		dividendo.setQtd(tblDividendo.getSelectionModel().getSelectedItem().getQtd());
    		dividendo.setVlrUnit(tblDividendo.getSelectionModel().getSelectedItem().getVlrUnit());
    		dividendo.setVlrTot(tblDividendo.getSelectionModel().getSelectedItem().getVlrTotal());
    		dividendo.setData(tblDividendo.getSelectionModel().getSelectedItem().getData());
        	
        	DividendoDAO dao = new DividendoDAO();
        	dao.exclui(dividendo);
    		
        	//problema
        	for (int i = 0; i < tabelaD.size(); i++) {
				if(tabelaD.get(i).getAcao().equals(tblDividendo.getSelectionModel().getSelectedItem().getAcao()) && tabelaD.get(i).getData().equals(tblDividendo.getSelectionModel().getSelectedItem().getData()) && tabelaD.get(i).getVlrUnit() == tblDividendo.getSelectionModel().getSelectedItem().getVlrUnit()) {
					tabelaD.remove(i);
				}
			} 
        	tblDividendo.getItems().remove(tblDividendo.getSelectionModel().getSelectedIndex());
        	atualizaTotalRecebido();
    	}
    }
    
    public void excluiRegistroV() {
    		Alert alert = new Alert(AlertType.CONFIRMATION, "Deletar venda?", ButtonType.YES, ButtonType.NO);
        	alert.showAndWait();
        	
        	if (alert.getResult() == ButtonType.YES) {
        		
        		Venda venda = new Venda();
        		
        		venda.setNome(tblVenda.getSelectionModel().getSelectedItem().getAcao());
        		venda.setVlrUnitC(tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC());
        		venda.setVlrTotC(tblVenda.getSelectionModel().getSelectedItem().getVlrTotC());
        		venda.setQtd(tblVenda.getSelectionModel().getSelectedItem().getQtd());
        		venda.setVlrUnitV(tblVenda.getSelectionModel().getSelectedItem().getVlrUnitV());
        		venda.setVlrTotV(tblVenda.getSelectionModel().getSelectedItem().getVlrTotV());
        		venda.setDataV(tblVenda.getSelectionModel().getSelectedItem().getDataV());
        		venda.setDataC(tblVenda.getSelectionModel().getSelectedItem().getDataC());
        		
        		VendaDAO dao = new VendaDAO();
        		dao.exclui(venda);
        		
        		//problema
        		for (int i = 0; i < tabelaV.size(); i++) {
    				if(tabelaV.get(i).getAcao().equals(tblVenda.getSelectionModel().getSelectedItem().getAcao()) && tabelaV.get(i).getDataC().equals(tblVenda.getSelectionModel().getSelectedItem().getDataC()) && tabelaV.get(i).getVlrUnitC() == tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC() && tabelaV.get(i).getDataV().equals(tblVenda.getSelectionModel().getSelectedItem().getDataV())) {
    					tabelaV.remove(i);
    				}
    			}        		
        		tblVenda.getItems().remove(tblVenda.getSelectionModel().getSelectedIndex());
        		atualizaLucroDesfeito();
        	}
    }
    
    public void desfazVenda() {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Desfazer a venda?", ButtonType.YES, ButtonType.NO);
    	alert.showAndWait();
    	
    	if (alert.getResult() == ButtonType.YES) {
    	
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
	    	
	    	Venda venda = new Venda();
	    	
	    	venda.setNome(tblVenda.getSelectionModel().getSelectedItem().getAcao());
	    	venda.setVlrUnitC(tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC());
	    	venda.setVlrTotC(tblVenda.getSelectionModel().getSelectedItem().getVlrTotC());
	    	venda.setQtd(tblVenda.getSelectionModel().getSelectedItem().getQtd());
	    	venda.setVlrUnitV(tblVenda.getSelectionModel().getSelectedItem().getVlrUnitV());
	    	venda.setVlrTotV(tblVenda.getSelectionModel().getSelectedItem().getVlrTotV());
	    	venda.setDataV(tblVenda.getSelectionModel().getSelectedItem().getDataV());
	    	venda.setDataC(tblVenda.getSelectionModel().getSelectedItem().getDataC());
	    	
	    	VendaDAO daoV = new VendaDAO();
	    	daoV.exclui(venda);
	    	
	    	//problema
	    	for (int i = 0; i < tabelaV.size(); i++) {
				if(tabelaV.get(i).getAcao().equals(tblVenda.getSelectionModel().getSelectedItem().getAcao()) && tabelaV.get(i).getDataC().equals(tblVenda.getSelectionModel().getSelectedItem().getDataC()) && tabelaV.get(i).getVlrUnitC() == tblVenda.getSelectionModel().getSelectedItem().getVlrUnitC() && tabelaV.get(i).getDataV().equals(tblVenda.getSelectionModel().getSelectedItem().getDataV())) {
					tabelaV.remove(i);
				}
			}    	
	    	tblVenda.getItems().remove(tblVenda.getSelectionModel().getSelectedIndex());
	    	atualizaLucroDesfeito();
	    	atualizaInvestimentoNaCompra();
    	}
    	
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
	    		
	    		lucro.setText("Lucro: R$"+decimalFormat.format(vlrFinal));
	    	} else {
	    		lucro.setText("R$ 0,00");
	    	}
	    	
	    }
    
	
	
    public void initialize() {
    	
    	CompraDAO dao = new CompraDAO();
    	List<Compra> compras = dao.getList();
    	
    	if(compras != null) {
    		for (int i = 0; i < compras.size(); i++) {
    			tabelaC.add(new TableC(compras.get(i).getNome(),compras.get(i).getVlrUnit(), compras.get(i).getVlrTot(), compras.get(i).getQtd(), compras.get(i).getData()));
            	tblCompra.setItems(FXCollections.observableArrayList(tabelaC));
			}
    	}
    	
    	
    	colAcao.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());
    	colVlrUnit.setCellValueFactory(cellData -> cellData.getValue().vlrUnitProperty());
    	colVlrTotal.setCellValueFactory(cellData -> cellData.getValue().vlrTotalProperty());
    	colQtd.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colData.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
    	
    	VendaDAO daoV = new VendaDAO();
    	List<Venda> vendas = daoV.getList();
    	
    	if(vendas != null) {
    		for (int i = 0; i < vendas.size(); i++) {
    			tabelaV.add(new TableV(vendas.get(i).getNome(),vendas.get(i).getVlrUnitC(), vendas.get(i).getVlrTotC(), vendas.get(i).getQtd(),vendas.get(i).getVlrUnitV(), vendas.get(i).getVlrTotV(), vendas.get(i).getDataC(), vendas.get(i).getDataV()));
            	tblVenda.setItems(FXCollections.observableArrayList(tabelaV));
			}
    	}
    	
    	colAcaoV.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());    	
    	colVlrUnitC.setCellValueFactory(cellData -> cellData.getValue().vlrUnitCProperty());
    	colVlrTotC.setCellValueFactory(cellData -> cellData.getValue().vlrTotCProperty());
    	colQtdV.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colVlrUnitV.setCellValueFactory(cellData -> cellData.getValue().vlrUnitVProperty());
    	colVlrTotV.setCellValueFactory(cellData -> cellData.getValue().vlrTotVProperty());
    	colDataC.setCellValueFactory(cellData -> cellData.getValue().dataCProperty());
    	colDataV.setCellValueFactory(cellData -> cellData.getValue().dataVProperty());
    	
    	DividendoDAO daoD = new DividendoDAO();
    	List<Dividendo> dividendos = daoD.getList();
    	
    	if(dividendos != null) {
    		for (int i = 0; i < dividendos.size(); i++) {
				tabelaD.add(new TableD(dividendos.get(i).getNome(),dividendos.get(i).getQtd(),dividendos.get(i).getVlrUnit(),dividendos.get(i).getVlrTot(),dividendos.get(i).getData()));
				tblDividendo.setItems(FXCollections.observableArrayList(tabelaD));
			}
    	}
    	
    	colAcaoD.setCellValueFactory(cellData -> cellData.getValue().acaoProperty());
    	colQtdD.setCellValueFactory(cellData -> cellData.getValue().qtdProperty());
    	colVlrUnitD.setCellValueFactory(cellData -> cellData.getValue().vlrUnitProperty());
    	colVlrTotalD.setCellValueFactory(cellData -> cellData.getValue().vlrTotalProperty());
    	colDataD.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
    	
    	atualizaInvestimentoNaCompra();
    	atualizaLucroNaVenda();
    	atualizaTotalRecebido();
		atualizaTbl();
	}
    
    
    
}
