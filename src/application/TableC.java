package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableC {

	private StringProperty acao;
	private DoubleProperty vlrUnit;
	private IntegerProperty qtd;
	private StringProperty data;
	
	public TableC(String acao, double vlrUnit, int qtd, String data) {
		this();
		setAcao(acao);
		setVlrUnit(vlrUnit);
		setQtd(qtd);
		setData(data);
	}
	
	public TableC() {
		acao = new SimpleStringProperty();
		vlrUnit = new SimpleDoubleProperty();
		qtd = new SimpleIntegerProperty();
		data = new SimpleStringProperty();
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
	
	public final DoubleProperty vlrUnitProperty() {
		return this.vlrUnit;
	}
	
	public final double getVlrUnit() {
		return this.vlrUnitProperty().get();
	}
	
	public final void setVlrUnit(final double vlrUnit) {
		this.vlrUnitProperty().set(vlrUnit);
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
	
	public final StringProperty dataProperty() {
		return this.data;
	}
	
	public final String getData() {
		return this.dataProperty().get();
	}
	
	public final void setData(final String data) {
		this.dataProperty().set(data);
	}
	
	
	
	
	
}
