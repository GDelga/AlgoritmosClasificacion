package negocio;

import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import factoria.FactoriaIntegracion;

public class KMediasImp implements KMedias{

	public void algoritmo(TDatos tDatos) {
		TIntegracion tIntegracion = FactoriaIntegracion.getInstance().crearArchivo().leerElementos(tDatos);
		if(tIntegracion != null) {
			HashMap<String,ArrayList<Elemento>> elementos = tIntegracion.getListaElementos();
			Object o = FactoriaIntegracion.getInstance().crearArchivo().leerClases(tDatos, elementos);
			if (o instanceof ArrayList) {
				ArrayList<Clase> clases = (ArrayList<Clase>) o;
				ArrayList<ArrayList<Double>> matrizU = new ArrayList<>(); //centros
				for(Clase elem: clases) {
					matrizU.add(elem.getCentro());
				}
				ArrayList<ArrayList<Double>> datos = tIntegracion.getMatrizDatos();
				this.execute(tDatos, matrizU, datos);
			}
			else if (o == null); //ERROR 1
			else; //ERROR NUMERO
			
		}
	}
	
	private ArrayList<ArrayList<Double>> execute(TDatos tDatos, ArrayList<ArrayList<Double>> matrizU, ArrayList<ArrayList<Double>> datos){
		ArrayList<ArrayList<Double>> pertenencia = null;
		ArrayList<ArrayList<Double>> matrizAnterior = null;
		do{
			calcularPertenencia(pertenencia, matrizU, datos);
			recalcularCentros(matrizAnterior, matrizU, datos, pertenencia);
		}while(!criterioDeFinalizacion(matrizU, matrizAnterior));
		return matrizU;
	}
	
	private boolean criterioDeFinalizacion(ArrayList<ArrayList<Double>> matrizU, ArrayList<ArrayList<Double>> matrizAnterior) {
		boolean finalizacion = true;
		int i = 0;
		while(finalizacion && i<matrizU.size()){
			if (distanciaEntre(matrizU.get(i), matrizAnterior.get(i)) > Constantes.toleranciaK){
				finalizacion = false;
			}
			i++;
		}
		return finalizacion;
	}
	
	private void calcularPertenencia(ArrayList<ArrayList<Double>> pertenencia, ArrayList<ArrayList<Double>> matrizU, ArrayList<ArrayList<Double>> datos) {
		pertenencia = new ArrayList<>();
		ArrayList<Double> probabilidadMuestra = null;
		for (int i=0; i< datos.size();i++){ //matriz de muestra junta
			probabilidadMuestra = new ArrayList<>();
			for (int clase=0; clase < matrizU.size();clase++){ // Clases
				ArrayList<Double> elemento = datos.get(i); //muestra X
				probabilidadMuestra.add(calcularPertenencia(elemento, clase, matrizU)); // V1
			}
			pertenencia.add(probabilidadMuestra);
		}
	}
	
	private Double calcularPertenencia(ArrayList<Double> elemento, int clase, ArrayList<ArrayList<Double>> matrizU) {
		return Math.pow(1 / distanciaEntre(elemento, matrizU.get(clase)), 1 / (Constantes.pesoExponencial - 1)) / Math.pow(sumatorioDeClases(elemento, matrizU), 1 / (Constantes.pesoExponencial - 1));
	}
	
	private double sumatorioDeClases(ArrayList<Double> elemento, ArrayList<ArrayList<Double>> matrizU) {
		double resultado = 0;
		for(int i = 0; i < matrizU.size(); i++) resultado = resultado + 1 / (distanciaEntre(elemento,matrizU.get(i)));
		return resultado;
	}
	
	private double distanciaEntre(ArrayList<Double> elemento, ArrayList<Double> clase) {
		double resultado = 0;
		if(elemento.size() != clase.size()) resultado = -1;
		else{
			for (int i=0 ; i<elemento.size(); i++){
				resultado = resultado + Math.pow(elemento.get(i) - clase.get(i), 2);
			}
		}
		return Math.sqrt(resultado);
	}
	
	private void recalcularCentros(ArrayList<ArrayList<Double>> matrizAnterior, ArrayList<ArrayList<Double>> matrizU, ArrayList<ArrayList<Double>> datos, ArrayList<ArrayList<Double>> pertenencia) {
		matrizAnterior = matrizU;
		ArrayList<ArrayList<Double>> nuevosCentros = new ArrayList<>();	
		ArrayList<Double> aux = new ArrayList<>();
		
		for(int j = 0; j < matrizU.size(); j++){
			ArrayList<Double> centro = new ArrayList<>();
			aux = new ArrayList<Double>();
			double aux2 = 0.0;
			for (int k = 0; k <matrizU.get(0).size();k++) centro.add(0.0);
			
			for(int i = 0; i < datos.size(); i++){	
				aux = (ArrayList<Double>) datos.get(i).clone();
				multiplicarVector(aux,Math.pow(pertenencia.get(i).get(j), Constantes.pesoExponencial));
				aux2 = aux2 + (Math.pow(pertenencia.get(i).get(j), Constantes.pesoExponencial));
				
				centro = sumaVectores(centro,aux);
			}
			multiplicarVector(centro, 1 / aux2);
			nuevosCentros.add(centro);
		}
		matrizU = nuevosCentros;
	}
	
	private void multiplicarVector(ArrayList<Double> vector, double valor) {
		for (int i = 0; i < vector.size(); i++)	vector.set(i, vector.get(i) * valor);
	}
	
	private ArrayList<Double> sumaVectores(ArrayList<Double> v1, ArrayList<Double> v2) {
		ArrayList<Double> resultado = new ArrayList<>();
		for(int i = 0; i<v1.size();i++) resultado.add(v1.get(i) + v2.get(i));
		return resultado;
	}
}
