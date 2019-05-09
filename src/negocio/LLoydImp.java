package negocio;

import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import factoria.FactoriaIntegracion;

public class LLoydImp implements Lloyd{

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
				this.execute(tDatos, matrizU, datos, clases);
			}
			else if (o == null); //ERROR 1
			else; //ERROR NUMERO
			
		}
	}

	private void execute(TDatos tDatos, ArrayList<ArrayList<Double>> matrizU, ArrayList<ArrayList<Double>> datos, ArrayList<Clase> clases) {
		double minimo, aux;
		int centroMin = 0 , iteraciones = 0;
		ArrayList<ArrayList<Double>> centrosOriginales = (ArrayList<ArrayList<Double>>) matrizU.clone();
		do {
			for(int i = 0; i < datos.size(); ++i) {
				minimo = 0.0;
				aux = 0.0;
				for(int j = 0; j < matrizU.size(); ++j) {
					if(j == 0) {
						minimo = distancia(datos.get(i), matrizU.get(j));
						centroMin = j;
					}
					else {
						aux = distancia(datos.get(i), matrizU.get(j));
						if(aux < minimo) {
							minimo = aux;
							centroMin = j;
						}
					}
				}
				calcularCentro(matrizU.get(centroMin), datos.get(i));
			}
			iteraciones++;
		} while(continuar(centrosOriginales, matrizU) && iteraciones < Constantes.kMax);
	}

	private boolean continuar(ArrayList<ArrayList<Double>> centrosOriginales, ArrayList<ArrayList<Double>> matrizU) {
		int i = 0;
		boolean encontrado = false;
		while(i < matrizU.size() && !encontrado) {
			if (distancia(matrizU.get(i), centrosOriginales.get(i)) > Constantes.toleranciaL) encontrado = true;
		}
		return encontrado;
	}

	private void calcularCentro(ArrayList<Double> centro, ArrayList<Double> muestra) {
		ArrayList<Double> resta = new ArrayList<>();
		for(int i = 0; i < centro.size(); ++i) {
			resta.add(Constantes.aprendizaje*(muestra.get(i) - centro.get(i)));
		}
		for(int i = 0; i < centro.size(); ++i) {
			centro.set(i, centro.get(i) + resta.get(i));
		}
	}

	private double distancia(ArrayList<Double> x, ArrayList<Double> c) {
		double resultado = 0.0;
		for(int i = 0; i < x.size(); ++i) {
			resultado += Math.pow(x.get(i) - c.get(i), 2);
		}
		return resultado;
	}
}
