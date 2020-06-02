package com.promesa.pedidos.busqueda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.promesa.pedidos.bean.BeanJerarquia;
import com.promesa.pedidos.bean.BeanMaterial;
import com.promesa.pedidos.sql.impl.SqlDivisionImpl;

/*
 * @author	MARCELO MOYANO
 * 
 * 			CLASES QUE GENERA LA INDEXACION
 * 			DE LOS MATERIALES Y LAS JERARQUIA
 * 			DE LOS MATERIALES.
 */
public class Indexador {

	private IndexWriter indexW;
	private StandardAnalyzer analizador;
	private Directory direccion;
	
	/*
	 * 	CONSTRUCTOR
	 */
	public Indexador() {
		analizador = new StandardAnalyzer(Version.LUCENE_30);
		direccion = new RAMDirectory();
	}
	
	public Indexador(String path) throws IOException{
		analizador = new StandardAnalyzer(Version.LUCENE_30);
		direccion = FSDirectory.open(new File(path));
	}

	/*
	 * 	SETEA EL ANALIZADOR LEXICO
	 */
	public void setAnalyzer(StandardAnalyzer analyzer) {
		this.analizador = analyzer;
	}

	/*
	 * 	OBTIEN EL ANALIZADOR LEXICO
	 */
	public StandardAnalyzer getAnalyzer() {
		return analizador;
	}

	/*
	 * 	SETEA LA DIRECCION DE MEMORIA
	 * 	EN DONDE SE REGISTRAR EL INDEXADOR
	 */
	public void setDirec(Directory direc) {
		this.direccion = direc;
	}

	/*
	 * 	OBTIENE LA DIRECCION DE
	 * 	MEMORIA
	 */
	public Directory getDirec() {
		return direccion;
	}

	/*
	 * SETEA EL INDEXWRITER
	 */
	public void setIndex(IndexWriter index) {
		this.indexW = index;
	}

	/*
	 * 	OBTIENE EL INDEXWRITER
	 */
	@SuppressWarnings("deprecation")
	public IndexWriter getIndex(boolean bCreater) throws IOException {
		if (indexW == null) {
			indexW = new IndexWriter(direccion, analizador, bCreater, IndexWriter.MaxFieldLength.UNLIMITED);
		}
		return indexW;
	}

	/*
	 * CIERRA EL INDEXWRITER
	 */
	public void closeIndexador() throws CorruptIndexException, IOException {
		if (indexW != null) {
			indexW.close();
		}
	}
	
	/*
	 * 	LIMPIA EL INDEXWRITER
	 */
	@SuppressWarnings("unused")
	private void limpiarIndexador() throws IOException {
		if(indexW != null){
			indexW.deleteAll();
		}
	}

	/*
	 * 			INDEXADOR DE MATERIALES
	 * 
	 * @param	RECIBE COMO PARAMETRO EL
	 * 			MATERIAL QUE SE VA A INDEXAR
	 */
	public void indexarMaterial(BeanMaterial bm) throws IOException {
		IndexWriter index = getIndex(false);
		Document doc = new Document();
		doc.add(new Field("Codigo", bm.getIdMaterial(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("Descrip Larga", bm.getText_line().replaceAll("'", ""), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("Descrip Corta", bm.getDescripcion().replaceAll("'", ""), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("Precio", bm.getPrice_1(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("Acumulada", "" + bm.getDblAcumulado(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("Promedio", "" + bm.getDblPromedio(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("tipoMaterial", bm.getTypeMat(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("Un", bm.getUn(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("PRDHA", bm.getPrdha(), Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("NORMT", bm.getNormt(), Field.Store.YES,Field.Index.ANALYZED));
		
		String strDescripcionLarga = bm.getText_line();
		String texto;
		if(strDescripcionLarga.length() > 0 && strDescripcionLarga.equals(bm.getDescripcion())){
			texto = bm.getIdMaterial() + " " + bm.getDescripcion();
		} else {
			texto = bm.getIdMaterial() + " " + bm.getText_line() + " " + bm.getDescripcion();
		}
		
		doc.add(new Field("Busqueda", texto, Field.Store.YES, Field.Index.ANALYZED));
		index.addDocument(doc);
	}

	public void reconstruirIndexMaterial() throws IOException {
		getIndex(true);
		SqlDivisionImpl sql = new SqlDivisionImpl();
		List<BeanMaterial> lista = new ArrayList<BeanMaterial>();
		lista = sql.obtenerMateriales();
		for (BeanMaterial bm : lista) {
			indexarMaterial(bm);
		}
		closeIndexador();
	}

	/*
	 * 			INDEXADOR DE JERARQUIA
	 * 
	 * @param	RECIBE COMO PARAMETRO LA
	 * 			JERARQUIA PARA REALIZAR EL
	 * 			INDEXADO
	 */
	public void indexarBeanJerarquia(BeanJerarquia bj) throws IOException {
		IndexWriter indexJerarquia = getIndex(false);
		Document docJerarquia = new Document();
		docJerarquia.add(new Field("PRDHA", bj.getStrPRODH(), Field.Store.YES, Field.Index.ANALYZED));
		docJerarquia.add(new Field("VTEXT", bj.getStrVTEXT(), Field.Store.YES, Field.Index.ANALYZED));
		String texto = " " + bj.getStrPRODH() + " - " + bj.getStrVTEXT() + " ";
		docJerarquia.add(new Field("Busqueda", texto, Field.Store.YES, Field.Index.ANALYZED));
		indexJerarquia.addDocument(docJerarquia);
	}

	/*
	 * 			RECONTRUYE EL INDEXADO CON TODAS LAS
	 * 			JERARQUIA DE LOS MATERIALES
	 * 
	 * @param	LA INDEXACION DE JERARQUIA DE MATERIALES
	 * 			LA REALIZA POR MEDIO DE UN NIVEL DE JERARQUIA
	 */
	public void reconstruirIndexJerarquia(String nivel) throws IOException {
		getIndex(true);
		SqlDivisionImpl sqlJerarquia = new SqlDivisionImpl();
		List<BeanJerarquia> listaJerarquia = sqlJerarquia.getListaJerarquiaPorNiveles(nivel);
		if (listaJerarquia.size() > 0) {
			for (BeanJerarquia beanJerarquia : listaJerarquia) {
				indexarBeanJerarquia(beanJerarquia);
			}
		}
		closeIndexador();
	}

	/*
	 * 			RECONSTRUYE LA INDEXACION DE LOS
	 * 			MATERIALES POR TOP DE CLIENTE
	 * 
	 * @param	INDEXA LOS MATERIALES POR EL
	 * 			CODIGO DEL CLIENTE DESDE LA TABLA
	 * 			PROFFLINE_TB_MATERIALES_TOP_CLIENTE
	 */
	public void reconstruirTopCliente(String strCodigoCliente) throws IOException {
		getIndex(true);
		SqlDivisionImpl sqlTopcliente = new SqlDivisionImpl();
		List<BeanMaterial> listaTopCliente = new ArrayList<BeanMaterial>();
		listaTopCliente = sqlTopcliente.obtenerTopCliente(strCodigoCliente);
		if (listaTopCliente.size() > 0) {
			for (BeanMaterial beanMaterial : listaTopCliente) {
				indexarMaterial(beanMaterial);
			}
		}
		closeIndexador();
	}
	
	/*
	 * 			RECONSTRUYE LA INDEXACION DE LOS
	 * 			MATERIALES POR TOP DE TIPOLOGIA
	 * 
	 * @param	INDEXA LOS MATERIALES POR EL
	 * 			CODIGO DEL TIPOLOGIA DESDE LA TABLA
	 * 			PROFFLINE_TB_MATERIALES_TOP_TIPOLOGIA
	 */
	public void reconstruirTopTipologia(String strCodigoTipologia) throws IOException {
		getIndex(true);
		SqlDivisionImpl sqlToptipologia = new SqlDivisionImpl();
		List<BeanMaterial> listaTopTipologia = new ArrayList<BeanMaterial>();
		listaTopTipologia = sqlToptipologia.obtenerTopTipologia(strCodigoTipologia);
		if (listaTopTipologia.size() > 0) {
			for (BeanMaterial beanMaterial : listaTopTipologia) {
				indexarMaterial(beanMaterial);
			}
		}
		closeIndexador();
	}
	
	/*
	 * 	RECONSTRUYE LA INDEXACION DE LOS
	 * 	MATERIALES PROMOOFERTA
	 */
	public void reconstruirPromoOferta() throws IOException {
		getIndex(true);
		SqlDivisionImpl sqlPromoOferta = new SqlDivisionImpl();
		List<BeanMaterial> listaPromoOferta = new ArrayList<BeanMaterial>();
		listaPromoOferta = sqlPromoOferta.obtenerPromoOferta();
		if (listaPromoOferta.size() > 0) {
			for (BeanMaterial beanMaterial : listaPromoOferta) {
				indexarMaterial(beanMaterial);
			}
		}
		closeIndexador();
	}
	
	/*
	 * 	RECONSTRUYE LA INDEXACION DE LOS
	 * 	MATERIALES DE DESCUENTO POLITICA
	 */
	public void reconstruirDescuentoPolitica() throws IOException {
		getIndex(true);
		SqlDivisionImpl sqlDescPolitica = new SqlDivisionImpl();
		List<BeanMaterial> listaDescPolitica = new ArrayList<BeanMaterial>();
		listaDescPolitica = sqlDescPolitica.obtenerDescuentosPolitica();
		if (listaDescPolitica.size() > 0) {
			for (BeanMaterial beanMaterial : listaDescPolitica) {
				indexarMaterial(beanMaterial);
			}
		}
		closeIndexador();
	}
	
	/*
	 * 	RECONSTRUYE LA INDEXACION DE LOS
	 * 	MATERIALES DESCUENTO MANUALES
	 */
	public void reconstruirDescuentoManuales() throws IOException {
		getIndex(true);
		SqlDivisionImpl sqlDescManuales = new SqlDivisionImpl();
		List<BeanMaterial> listaDescManuales = new ArrayList<BeanMaterial>();
		listaDescManuales = sqlDescManuales.obtenerDescuentosManuales();
		if (listaDescManuales.size() > 0) {
			for (BeanMaterial beanMaterial : listaDescManuales) {
				indexarMaterial(beanMaterial);
			}
		}
		closeIndexador();
	}
}
