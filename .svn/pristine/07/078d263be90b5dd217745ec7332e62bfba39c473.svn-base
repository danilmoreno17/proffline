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

import com.promesa.cobranzas.bean.DatoCredito;

public class ModeloDatosCredito implements TableModel {
    
    private List<DatoCredito> listaDatoCredito = new ArrayList<DatoCredito>();
    @SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

    @Override
    public int getRowCount() {
        return listaDatoCredito.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] nombresColumnas = {"N° DOCUMENTO", "FECHA CONTAB.", "FECHA DOC.", "REGISTRADO EL", "MONEDA", "IMPTE. DE POS.", "UN.ORG.REFER."};
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
        DatoCredito dc = listaDatoCredito.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dc.getNumeroDocumento();
            case 1:
                return dc.getFechaContable();
            case 2:
                return dc.getFechaDocumento();
            case 3:
                return dc.getRegistradoEl();
            case 4:
                return dc.getMoneda();
            case 5:
                return dc.getImporte();
            case 6:
                return dc.getUnOrgRefer();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        DatoCredito dc = listaDatoCredito.get(rowIndex);
        if (aValue == null) {
            aValue = "";
        }
        switch (columnIndex) {
            case 0:
                dc.setNumeroDocumento(aValue.toString());
                break;
            case 1:
                dc.setFechaContable(aValue.toString());
                break;
            case 2:
                dc.setFechaDocumento(aValue.toString());
                break;
            case 3:
                dc.setRegistradoEl(aValue.toString());
                break;
            case 4:
                dc.setMoneda(aValue.toString());
                break;
            case 5:
                dc.setImporte(aValue.toString());
                break;
            case 6:
                dc.setUnOrgRefer(aValue.toString());
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
        listaDatoCredito.clear();
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        avisaSuscriptores(evento);
    }

    public void agregarDiaDemoraVencimiento(DatoCredito dc) {
        listaDatoCredito.add(dc);
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
