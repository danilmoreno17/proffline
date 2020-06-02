package com.promesa.pedidos.busqueda;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.BooleanClause;
//import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/*
 * @author		MARCELO MOYANO
 * 
 * 				CLASE QUE SE ENCARGA DE
 * 				REALIZAR LA BUSQUEDA DE
 * 				UNA PALABRA EN EL INDEXADOR
 * 				
 */
public class Buscador {

	private IndexSearcher ibuscador;
	private QueryParser qParser;
	
	/*
	 * 			CONSTRUCTOR
	 * 
	 * @param	EL CONSTRUCTOR RECIBE COMO PARAMETRO
	 * 			EL INDEXADOR QUE TIENE EN MEMORIA
	 *  		EL REGISTRO	DE LOS DATOS QUE SE REALIZARA
	 *  		LA BUSQUEDA. 
	 */
	@SuppressWarnings("deprecation")
	public Buscador(Indexador index) {
		try {
			IndexReader r = IndexReader.open(index.getDirec());
			ibuscador = new IndexSearcher(r);
			qParser = new QueryParser(Version.LUCENE_20, "Busqueda", new StandardAnalyzer(Version.LUCENE_20));
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * new
	 */
	@SuppressWarnings("deprecation")
	public Buscador(String path){
		try {
			Directory direccion = FSDirectory.open(new File(path));
			IndexReader r = IndexReader.open(direccion);
			ibuscador = new IndexSearcher(r);
			qParser = new QueryParser(Version.LUCENE_20, "Busqueda", new StandardAnalyzer(Version.LUCENE_20));
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 	SETEA EL BUSCADOR
	 */
	public void setIbuscador(IndexSearcher ibuscador) {
		this.ibuscador = ibuscador;
	}
	
	/*
	 * 	OBTIENE EL BUSCADOR
	 */
	public IndexSearcher getIbuscador() {
		return ibuscador;
	}
	
	/*
	 * 	SETEA EL PARSER
	 */
	public void setqParser(QueryParser qParser) {
		this.qParser = qParser;
	}
	
	/*
	 * 	OBTIENE EL PARSER
	 */
	public QueryParser getqParser() {
		return qParser;
	}
	
	/*
	 * 			REALIZA LA BUSQUEDA DE LA PALABRA
	 * 			QUE SE LE ENVIA.
	 * 
	 * @param	RECIBE COMO PARAMETRO LA PALABRA QUE
	 * 			SE REQUIERE BUSCAR
	 * 
	 * @return	RETORNA EL RESULTADO DE LA BUSCADA 
	 * 			REALIZADA
	 */
	public ScoreDoc[] funcionBuscador(String strAbuscar) throws ParseException, IOException {
		Query q = qParser.parse(strAbuscar);
		TopScoreDocCollector result = TopScoreDocCollector.create(1000, false);
		ibuscador.search(q, result);
		ScoreDoc[]  h = result.topDocs().scoreDocs;
		ibuscador.close();
		return h;
	}
}