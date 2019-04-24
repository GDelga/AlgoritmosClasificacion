package integracion;

import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import negocio.TDatos;

/**
 * @author Guillermo Delgado Yepes
 */
public interface Archivo {
	public HashMap<String,ArrayList<Elemento>> leerElementos(TDatos tDatos);
	public Object leerClases(TDatos tDatos, HashMap<String,ArrayList<Elemento>> elemento);
}
