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

import com.promesa.cobranzas.bean.DiaDemoraTrasVencimiento;

public class ModeloTablaVencimientoNeto implements TableModel {

    private List<DiaDemoraTrasVencimiento> listaDias = new ArrayList<DiaDemoraTrasVencimiento>();
    @SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

    @Override
    public int getRowCount() {
        return listaDias.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] nombresColumnas = {"CLIENTE", "SOCIEDAD", "MONEDA", "CUADRO", "PART.VENCIDAS", "NO VENCIDAS"};
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
        DiaDemoraTrasVencimiento dv = listaDias.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dv.getCodigoCliente();
            case 1:
                return dv.getSociedad();
            case 2:
                return dv.getMoneda();
            case 3:
                return dv.getCuadro();
            case 4:
                return dv.getPartidasVencidas();
            case 5:
                return dv.getNoVencidas();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        DiaDemoraTrasVencimiento dv = listaDias.get(rowIndex);
        if (aValue == null) {
            aValue = "";
        }
        switch (columnIndex) {
            case 0:
                dv.setCodigoCliente(aValue.toString());
                break;
            case 1:
                dv.setSociedad(aValue.toString());
                break;
            case 2:
                dv.setMoneda(aValue.toString());
                break;
            case 3:
                dv.setCuadro(aValue.toString());
                break;
            case 4:
                dv.setPartidasVencidas(aValue.toString());
                break;
            case 5:
                dv.setNoVencidas(aValue.toString());
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
        listaDias.clear();
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        avisaSuscriptores(evento);
    }

    public void agregarDiaDemoraVencimiento(DiaDemoraTrasVencimiento dia) {
        listaDias.add(dia);
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