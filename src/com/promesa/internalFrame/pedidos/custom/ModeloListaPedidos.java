package com.promesa.internalFrame.pedidos.custom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

//import com.promesa.cobranzas.bean.PagoParcial;
import com.promesa.pedidos.bean.BeanPedidoHeader;
import com.promesa.util.Util;
/*
 * @autor	MARCELO MOYANO
 * 
 * 			CLASES QUE IMPLEMENTA TABLEMODEL PARA
 * 			CREAR UN MODELO DE TABLE PARA LAS CONSULTAS
 * 			DE PEDIDOS.
 */
public class ModeloListaPedidos implements TableModel {

	private List<BeanPedidoHeader> listaPedidos = new ArrayList<BeanPedidoHeader>();
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
	/*
	 * 			METODO QUE RETORNA UN PEDIDO CABECERA EN LA POSICIÓN INDICE.
	 * 	
	 * 	@param	indice	INDICA LA POSICIÓN DE LA CABECERA.
	 * 
	 *  @return	RETORNA LA CABECERA DE UNA PEDIDO EN LA POSICION INDICE.
	 */
	public BeanPedidoHeader obtenerPedidoHeader(int indice) {
		if (indice > -1) {
			return listaPedidos.get(indice);
		}
		return null;
	}
	
	/*
	 * 		INSERTA UN PEDIDO CABECERA A LA LISTA DE 
	 * 		PEDIDOS DE CABECERA EN LA POSICIÓN I.
	 * 
	 *  @param 	pedidoCabecera ES LA CABECERA QUE SE 
	 *  		DECEA INSERTAR A LA LISTA DE PEDIDOS
	 *  		DE CABECERA.
	 *  @param	i LA POSICIÓN EN DONDE SE DESEA INSERTAR
	 *  		LA CABECERA EN LA LISTA DE PEDIDOS DE 
	 *  		CABECERA.
	 */
	public void actualizarModeloPedido(BeanPedidoHeader pedidoCabecera, int i){
		if(i >= 0 && i < listaPedidos.size()){
			listaPedidos.set(i, pedidoCabecera);
		}
	}

	@Override
	public int getColumnCount() {
		return 14;
	}
	
	/*
	 * 		METODO QUE OBTIENE LA LISTA DE PEDIDO.
	 * 
	 * 	@return DEVUELVE LA LISTA DE PEDIDOS A CONSULTAR. 
	 */
	public List<BeanPedidoHeader> getAsistencia(){
		return listaPedidos;
	}

	@Override
	public String getColumnName(int columnIndex) {//
		switch (columnIndex) {
		case 0:
		case 13:
			return "";
		case 1://	Marcelo Moyano 12/05/2013
			return "<html><b>DOC. DE VENTA ▼</b></html>";//	Marcelo Moyano 12/05/2013
		case 2:
			return "FECHA DOC.";
		case 3:
			return "STATUS";
		case 4:
			return "DOC. REFER.";
		case 5:
			return "FECH. REFER.";
		case 6:
			return "VALOR NETO";
		case 7:
			return "<html><b>CL. DOC. VENTAS ▼</b></html>";
		case 8:
			return "COD. DE CLIENTE";
		case 9:
			return "CLIENTE";
		case 10:
			return "CREADO POR";
		case 11:
			return "COD. VENDEDOR";
		case 12:
			return "CREADO EL";
		default:
			return "";
				
		}
	}

	@Override
	public int getRowCount() {
		return listaPedidos.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BeanPedidoHeader f = listaPedidos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return f.getSource();
		case 1:
			return f.getStrDocumentoVenta();
		case 2:
			return f.getStrFechaDocumento();
		case 3:
			return f.getStatus();
		case 4:
			return f.getStrDocumentoReferencia();
		case 5:
			return f.getStrFechaReferencia();
		case 6:
			return f.getStrValorNeto();
		case 7:
			return f.getStrClDocVentas();
		case 8:
			return f.getStrCodCliente();
		case 9:
			return f.getStrCliente();
		case 10:
			return f.getStrCreador();
		case 11:
			return f.getStrCodVendedor();
		case 12:
			return f.getStrFechaCreacion();
		case 13:
			return f.getSource();
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
		BeanPedidoHeader f = listaPedidos.get(rowIndex);
		String valor = ("" + aValue).toString();
		switch (columnIndex) {
		case 0:
		case 13:
			f.setSource(Integer.parseInt(valor));
		case 1:
			f.setStrDocumentoVenta(valor);
		case 2:
			f.setStrFechaDocumento(valor);
		case 3:
			f.setStatus(valor);
		case 4:
			f.setStrDocumentoReferencia(valor);
		case 5:
			f.setStrFechaReferencia(valor);
		case 6:
//			f.setStrValorNeto(valor);
			f.setStrValorNeto(Util.formatearNumero(valor));
		case 7:
			f.setStrClDocVentas(valor);
		case 8:
			f.setStrCodCliente(valor);
		case 9:
			f.setStrCliente(valor);
		case 10:
			f.setStrCreador(valor);
		case 11:
			f.setStrCodVendedor(valor);
		case 12:
			f.setStrFechaCreacion(valor);
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
		listaPedidos.clear();
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
		avisaSuscriptores(evento);
	}
	
	public int obtenerCantidadPedidos() {
		return listaPedidos.size();
	}
	
	public BeanPedidoHeader obtenerFila(int fila){
		BeanPedidoHeader filaPedido = listaPedidos.get(fila);
		return filaPedido;
	}

	public void agregarPedido(BeanPedidoHeader factura) {
		listaPedidos.add(factura);
		TableModelEvent evento;
		evento = new TableModelEvent(this, this.getRowCount() - 1, this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		avisaSuscriptores(evento);
	}
	
	public void elimnarFila(int fila){
		listaPedidos.remove(fila);
	}
}
