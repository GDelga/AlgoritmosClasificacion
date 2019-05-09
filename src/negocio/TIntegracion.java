package negocio;

import java.util.ArrayList;
import java.util.HashMap;
import archivo.Elemento;

public class TIntegracion {

	private HashMap<String,ArrayList<Elemento>> listaElementos;
	private ArrayList<ArrayList<Double>> matrizDatos;
	
	public TIntegracion(HashMap<String,ArrayList<Elemento>> listaElementos, ArrayList<ArrayList<Double>> matrizDatos) {
		this.listaElementos = listaElementos;
		this.matrizDatos = matrizDatos;
	}

	public HashMap<String, ArrayList<Elemento>> getListaElementos() {
		return listaElementos;
	}


	public void setListaElementos(HashMap<String, ArrayList<Elemento>> listaElementos) {
		this.listaElementos = listaElementos;
	}


	public ArrayList<ArrayList<Double>> getMatrizDatos() {
		return matrizDatos;
	}
	
	public void setMatrizDatos(ArrayList<ArrayList<Double>> matrizDatos) {
		this.matrizDatos = matrizDatos;
	}
	
}
