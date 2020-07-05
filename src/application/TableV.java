package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableV {

	private StringProperty acao;
	private DoubleProperty vlrUnitC;
	private IntegerProperty qtd;
	private DoubleProperty vlrUnitV;
	private StringProperty dataC;
	private StringProperty dataV;
	
	public TableV() {
		acao = new SimpleStringProperty();
		vlrUnitC = new SimpleDoubleProperty();
		qtd = new SimpleIntegerProperty();
		vlrUnitV = new SimpleDoubleProperty();
		dataC = new SimpleStringProperty();
		dataV = new SimpleStringProperty();
	}
	
	public TableV(String acao, double vlrUnitC, int qtd, double vlrUnitV, String dataC, String dataV) {
		this();
		setAcao(acao);
		setVlrUnitC(vlrUnitC);
		setQtd(qtd);
		setVlrUnitV(vlrUnitV);
		setDataC(dataC);
		setDataV(dataV);
	}
	
	public final StringProperty acaoProperty() {
		return this.acao;
	}
	
	public final String getAcao() {
		return this.acaoProperty().get();
	}
	
	public final void setAcao(final String acao) {
		this.acaoProperty().set(acao);
	}
	
	public final DoubleProperty vlrUnitCProperty() {
		return this.vlrUnitC;
	}
	
	public final double getVlrUnitC() {
		return this.vlrUnitCProperty().get();
	}
	
	public final void setVlrUnitC(final double vlrUnitC) {
		this.vlrUnitCProperty().set(vlrUnitC);
	}
	
	public final IntegerProperty qtdProperty() {
		return this.qtd;
	}
	
	public final int getQtd() {
		return this.qtdProperty().get();
	}
	
	public final void setQtd(final int qtd) {
		this.qtdProperty().set(qtd);
	}
	
	public final DoubleProperty vlrUnitVProperty() {
		return this.vlrUnitV;
	}
	
	public final double getVlrUnitV() {
		return this.vlrUnitVProperty().get();
	}
	
	public final void setVlrUnitV(final double vlrUnitV) {
		this.vlrUnitVProperty().set(vlrUnitV);
	}
	
	public final StringProperty dataCProperty() {
		return this.dataC;
	}
	
	public final String getDataC() {
		return this.dataCProperty().get();
	}
	
	public final void setDataC(final String dataC) {
		this.dataCProperty().set(dataC);
	}
	
	public final StringProperty dataVProperty() {
		return this.dataV;
	}
	
	public final String getDataV() {
		return this.dataVProperty().get();
	}
	
	public final void setDataV(final String dataV) {
		this.dataVProperty().set(dataV);
	}
	
	
	
	
	
	
}
