package negocio;

import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import factoria.FactoriaIntegracion;

public class LloydImp implements Lloyd{

	@Override
	public Object algoritmo(TDatos tDatos) {
		TIntegracion tIntegracion = FactoriaIntegracion.getInstance().crearArchivo().leerElementos(tDatos);
		if(tIntegracion != null) {
			HashMap<String,ArrayList<Elemento>> elementos = tIntegracion.getListaElementos();
			Object o = FactoriaIntegracion.getInstance().crearArchivo().leerClases(tDatos, elementos);
			if (o != null && o instanceof ArrayList) {
				ArrayList<Clase> clases = (ArrayList<Clase>) o;
				ArrayList<ArrayList<Double>> matrizU = new ArrayList<>(); //centros
				for(Clase elem: clases) {
					matrizU.add(elem.getCentro());
				}
				ArrayList<ArrayList<Double>> datos = tIntegracion.getMatrizDatos();
				matrizU = this.execute(matrizU, datos, clases);
				//Modificamos el vector de la clase
				for(int i = 0; i < clases.size(); ++i) {
					clases.get(i).setCentro(matrizU.get(i));
				}
				//VERIFICAR PUNTO
				//1º leer archivo de ejemplo
				Object o1 = FactoriaIntegracion.getInstance().crearArchivo().leerEjemplo(tDatos);
				if (o1 != null && o1 instanceof ArrayList) {
					ArrayList<Double> ejemplo = (ArrayList<Double>) o1;
					//crearVectores
					String p = pertenencia(clases, ejemplo);
					String sol = "El vector (";
					for(int i = 0; i < ejemplo.size(); ++i) {
						sol += " " + ejemplo.get(i);
						if(i + 1 < ejemplo.size()) sol += ",";
					}
					sol += ") pertenece a la clase " + p;
					return sol;
				}
				else return o1; //ERROR
			}
			else return o; //ERROR
		}
		else return null; //ERROR
	}

	private String pertenencia(ArrayList<Clase> clases, ArrayList<Double> ejemplo) {
		double minimo, aux;
		int centroMin = 0;
		for(int i = 0; i < clases.size(); ++i) {
			minimo = 0.0;
			aux = 0.0;
			if(i == 0) {
				minimo = distancia(ejemplo, clases.get(i).getCentro());
				centroMin = i;
			}
			else {
				aux = distancia(ejemplo, clases.get(i).getCentro());
				if(aux < minimo) {
					minimo = aux;
					centroMin = i;
				}
			}
		}
		return clases.get(centroMin).getNombre();
	}

	private ArrayList<ArrayList<Double>> execute(ArrayList<ArrayList<Double>> matrizU, ArrayList<ArrayList<Double>> datos, ArrayList<Clase> clases) {
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
		return matrizU;
	}

	private boolean continuar(ArrayList<ArrayList<Double>> centrosOriginales, ArrayList<ArrayList<Double>> matrizU) {
		int i = 0;
		boolean encontrado = false;
		while(i < matrizU.size() && !encontrado) {
			if (distancia(matrizU.get(i), centrosOriginales.get(i)) > Constantes.toleranciaL) encontrado = true;
			++i;
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
