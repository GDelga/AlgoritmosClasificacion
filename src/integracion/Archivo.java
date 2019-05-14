package integracion;

import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import negocio.TDatos;
import negocio.TIntegracion;

/**
 * @author Guillermo Delgado Yepes
 */
public interface Archivo {
	public TIntegracion leerElementos(TDatos tDatos);
	public Object leerClases(TDatos tDatos, HashMap<String,ArrayList<Elemento>> elemento);
	public Object leerEjemplo(TDatos tDatos);
}
