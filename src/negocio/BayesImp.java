package negocio;

import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import factoria.FactoriaIntegracion;

public class BayesImp implements Bayes {

	@Override
	public Object algoritmo(TDatos tDatos) {
		TIntegracion tIntegracion = FactoriaIntegracion.getInstance().crearArchivo().leerElementos(tDatos);
		if(tIntegracion != null) {
			HashMap<String,ArrayList<Elemento>> elementos = tIntegracion.getListaElementos();
			Object o = FactoriaIntegracion.getInstance().crearArchivo().leerClases(tDatos, elementos);
			if (o instanceof ArrayList) {
				ArrayList<Clase> clases = (ArrayList<Clase>) o;
				this.execute(clases);				
				//VERIFICAR PUNTO
				//1º leer archivo de ejemplo
				ArrayList<Double> ejemplo = FactoriaIntegracion.getInstance().crearArchivo().leerEjemplo(tDatos);
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
			else if (o == null) return null;//ERROR 1
			else return o; //ERROR NUMERO
			
		}
		else return null;
	}

	private String pertenencia(ArrayList<Clase> clases, ArrayList<Double> ejemplo) {
		int claseMinima = 0;
		double suma, min = 0.0;
		boolean primero = true;
		double[][] vectoresClases = new double[ejemplo.size()][ejemplo.size()];
		//Crear vectores
		for(int i = 0; i < clases.size(); ++i) { //Clase
			for(int j = 0; j < clases.get(i).getMatrizBayes().length; ++j) { //Fila
				suma = 0.0;
				for(int z = 0; z < clases.get(i).getMatrizBayes()[j].length; ++z) { //Columna
					suma += clases.get(i).getMatrizBayes()[j][z];
				}
				vectoresClases[i][j] = suma / clases.get(i).getMatrizBayes().length;
			}
		}
		
		//Resta del ejemplo con el vector y multiplicacion
		for(int i = 0; i < clases.size(); ++i) {
			ArrayList<Double> resta = new ArrayList<>();
			for(int j = 0; j < vectoresClases[i].length; ++j) { //Columnas
				resta.add(ejemplo.get(j) - vectoresClases[i][j]);
			}
			double res = multiplicar(resta);
			if(primero) {
				min = res;
				primero = false;
			}
			else if(res < min) {
				min = res;
				claseMinima = i;
			}
		}
		return clases.get(claseMinima).getNombre();
	}

	private double multiplicar(ArrayList<Double> resta) {
		double res = 0.0;
		for(int i = 0; i < resta.size(); ++i) {
			res += Math.pow(resta.get(i),2);
		}
		return res;
	}

	private void execute(ArrayList<Clase> clases) {
		
		double[][] anterior = null;
		for(int i = 0; i < clases.size(); ++i) {
			for(int j = 0; j < clases.get(i).getMatriz().length; ++j) {
				anterior = calcularMatriz(clases.get(i).getMatriz()[j], clases.get(i).getCentro(), anterior);
			}
			multiplicarMatriz(anterior, clases.get(i).getMatriz().length);
			clases.get(i).setMatrizBayes(anterior);
		}
	}

	private void multiplicarMatriz(double[][] anterior, int length) {
		for(int i = 0; i <anterior.length; ++i) {
			for(int j = 0; j < anterior.length; ++j) {
				anterior[i][j] *= (1/length);
			}
		}
	}

	private double[][] calcularMatriz(double[] ds, ArrayList<Double> centro, double[][] anterior) {
		if(anterior == null) {
			anterior = new double[centro.size()][centro.size()];
			for(int i = 0; i < centro.size(); ++i) {
				for(int j = 0; j < centro.size(); ++j) {
					anterior[i][j] = 0.0;
				}
			}
		}
		ArrayList<Double> resta = new ArrayList<>();
		for(int i = 0; i < centro.size(); ++i) {
			resta.add(ds[i] - centro.get(i));
		}
		double[][] m = new double[centro.size()][centro.size()];
		for(int i = 0; i < centro.size(); ++i) {
			for(int j = 0; j < centro.size(); ++j) {
				m[i][j] = resta.get(i) * resta.get(j);
			}
		}
		for(int i = 0; i < centro.size(); ++i) {
			for(int j = 0; j < centro.size(); ++j) {
				anterior[i][j] += m[i][j];
			}
		}
		return anterior;
	}
	
}
