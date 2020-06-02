package com.promesa.internalFrame.cobranzas.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.promesa.cobranzas.bean.PedidoPendienteDevolucion;

public class ModeloCarteraPendienteDevolucion implements TableModel {

    private List<PedidoPendienteDevolucion> listaPedidosPendientesDevolucion = new ArrayList<PedidoPendienteDevolucion>();
    @SuppressWarnings("rawtypes")
	private LinkedList listeners = new LinkedList();

    @Override
    public int getRowCount() {
        return listaPedidosPendientesDevolucion.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] nombresColumnas = {"N° DOCUMENTO", "FECHA DOCUMENTO", "CREADO EL", "VALOR NETO", "CÓDIGO VENDEDOR", "NOMBRE VENDEDOR"};
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
        PedidoPendienteDevolucion dc = listaPedidosPendientesDevolucion.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dc.getNumeroDocumento();
            case 1:
                return dc.getFechaDocumento();
            case 2:
                return dc.getCreadoEl();
            case 3:
                return dc.getValorNeto();
            case 4:
                return dc.getCodigoVendedor();
            case 5:
                return dc.getNombreVendedor();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PedidoPendienteDevolucion dc = listaPedidosPendientesDevolucion.get(rowIndex);
        if (aValue == null) {
            aValue = "";
        }
        switch (columnIndex) {
            case 0:
                dc.setNumeroDocumento(aValue.toString());
                break;
            case 1:
                dc.setFechaDocumento(aValue.toString());
                break;
            case 2:
                dc.setCreadoEl(aValue.toString());
                break;
            case 3:
                dc.setValorNeto(aValue.toString());
                break;
            case 4:
                dc.setCodigoVendedor(aValue.toString());
                break;
            case 5:
                dc.setNombreVendedor(aValue.toString());
                break;
        }
        TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex,
                columnIndex);
        avisaSuscriptores(evento);
    }

    private void avisaSuscriptores(TableModelEvent evento) {
        int i;
        for (i = 0; i < listeners.size(); i++) {
            ((TableModelListener) listeners.get(i)).tableChanged(evento);
        }
    }

    public void limpiar() {
        listaPedidosPendientesDevolucion.clear();
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        avisaSuscriptores(evento);
    }

    public void agregarDetalleCarteraPendienteDevolucion(PedidoPendienteDevolucion ppd) {
        listaPedidosPendientesDevolucion.add(ppd);
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
