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

import com.promesa.cobranzas.bean.PartidaIndividual;

public class ModeloPartidasIndividuales implements TableModel {

    private List<PartidaIndividual> listaPartidasIndividuales = new ArrayList<PartidaIndividual>();
    @SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

    @Override
    public int getRowCount() {
        return listaPartidasIndividuales.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] nombresColumnas = {"REFERENCIA", "FECHA DOCUMENTO", "FECHA VENCIMIENTO", "REGISTRADO EL", "N° DOCUMENTO", "CLASE DOCUMENTO", "POSICION", "MONEDA", "IMPTE DE POS."};
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
        PartidaIndividual hp = listaPartidasIndividuales.get(rowIndex);
        switch (columnIndex) {
	        case 0:
	            return hp.getReferencia();
	        case 1:
                return hp.getFechaDocumento();
            case 2:
                return hp.getFechaVencimiento();
            case 3:
                return hp.getRegistradoEl();
	        case 4:
                return hp.getNumeroDocumento();
            case 5:
                return hp.getClaseDocumento();
            case 6:
                return hp.getPosicion();
            case 7:
                return hp.getMoneda();
            case 8:
                return hp.getImpteDePos();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PartidaIndividual dv = listaPartidasIndividuales.get(rowIndex);
        if (aValue == null) {
            aValue = "";
        }
        switch (columnIndex) {
	        case 0:
	            dv.setReferencia(aValue.toString());
	            break;
	        case 1:
                dv.setFechaDocumento(aValue.toString());
                break;
            case 2:
                dv.setFechaVencimiento(aValue.toString());
                break;
            case 3:
                dv.setRegistradoEl(aValue.toString());
                break;
        	case 4:
                dv.setNumeroDocumento(aValue.toString());
                break;
            case 5:
                dv.setClaseDocumento(aValue.toString());
                break;
            case 6:
                dv.setPosicion(aValue.toString());
                break;
            case 7:
                dv.setMoneda(aValue.toString());
                break;
            case 8:
                dv.setImpteDePos(aValue.toString());
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
        listaPartidasIndividuales.clear();
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        avisaSuscriptores(evento);
    }

    public void agregarPartidaIndividual(PartidaIndividual partida) {
        listaPartidasIndividuales.add(partida);
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