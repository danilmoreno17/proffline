package com.promesa.administracion.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ModeloTablaOpcion implements TableModel {

	private List<BeanIdentificacion> identificacionese = new ArrayList<BeanIdentificacion>();
	@SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

	@SuppressWarnings("unchecked")
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	public List<BeanIdentificacion> getAsistencia(){
		return identificacionese;
	}

	@Override
	public String getColumnName(int columnIndex) {//
		switch (columnIndex) {
		case 0:
			return "ID";
		case 1://	Marcelo Moyano 12/05/2013
			return "NOMBRE";//	Marcelo Moyano 12/05/2013
		case 2:
			return "ZONA";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return identificacionese.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BeanIdentificacion f = identificacionese.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return f.getStrId();
		case 1:
			return f.getStrNombre();
		case 2:
			return f.getStrC1();
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		BeanIdentificacion f = identificacionese.get(rowIndex);
		String valor = ("" + aValue).toString();
		switch (columnIndex) {
		case 0:
			f.setStrId(valor);
		case 1:
			f.setStrNombre(valor);
		case 2:
			f.setStrC1(valor);
		}
		TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
		avisaSuscriptores(evento);
	}

	private void avisaSuscriptores(TableModelEvent evento) {
		int i;
		for (i = 0; i < listeners.size(); i++) {
			((TableModelListener) listeners.get(i)).tableChanged(evento);
		}
	}

	public void limpiar() {
		identificacionese.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}
	
	public int obtenerCantidadDatos() {
		return identificacionese.size();
	}
	
	public BeanIdentificacion obtenerFila(int ifila){
		BeanIdentificacion fila = identificacionese.get(ifila);
		return fila;
	}

	public void agregarOpciones(BeanIdentificacion bi) {
		identificacionese.add(bi);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
	
	public void elimnarFila(int fila){
		identificacionese.remove(fila);
	}
}