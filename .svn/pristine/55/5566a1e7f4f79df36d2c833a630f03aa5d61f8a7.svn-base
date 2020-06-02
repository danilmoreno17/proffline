/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promesa.internalFrame.cobranzas.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.cobranzas.bean.HistorialPago;

public class ModeloHistorialPago implements TableModel {

    private List<HistorialPago> listaHistorialPagos = new ArrayList<HistorialPago>();
    @SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

    @Override
    public int getRowCount() {
        return listaHistorialPagos.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] nombresColumnas = {"MONEDA", "EJERCICIO", "PERIODO", "CON DPP", "DIAS DE DEMORA", "SIN DESCUENTO", "DIAS DE DEMORA", "CDT"};
        return nombresColumnas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HistorialPago hp = listaHistorialPagos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return hp.getMoneda();
            case 1:
                return hp.getEjercicio();
            case 2:
                return hp.getPeriodo();
            case 3:
                return hp.getConDPP();
            case 4:
                return hp.getDiasDemora1();
            case 5:
                return hp.getSinDescuento();
            case 6:
                return hp.getDiasDemora2();
            case 7:
                return hp.getCtd();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        HistorialPago dv = listaHistorialPagos.get(rowIndex);
        if (aValue == null) {
            aValue = "";
        }
        switch (columnIndex) {
            case 0:
                dv.setMoneda(aValue.toString());
                break;
            case 1:
                dv.setEjercicio(aValue.toString());
                break;
            case 2:
                dv.setPeriodo(aValue.toString());
                break;
            case 3:
                dv.setConDPP(aValue.toString());
                break;
            case 4:
                dv.setDiasDemora1(aValue.toString());
                break;
            case 5:
                dv.setSinDescuento(aValue.toString());
                break;
            case 6:
                dv.setDiasDemora2(aValue.toString());
                break;
            case 7:
                dv.setCtd(aValue.toString());
                break;
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
        listaHistorialPagos.clear();
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        avisaSuscriptores(evento);
    }

    public void agregarHistorialPago(HistorialPago historial) {
        listaHistorialPagos.add(historial);
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        avisaSuscriptores(evento);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
