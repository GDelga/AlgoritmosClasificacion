package archivo;

import java.util.ArrayList;

/**
 * @author Guillermo Delgado Yepes
 */
public class Clase {

	private String nombre;
	private double[][] matriz;
	private ArrayList<Double> centro;
	private double[][] matrizBayes;

	
	public Clase(ArrayList<Elemento> elementos, String nombre, ArrayList<Double> ds){		
		this.nombre = nombre;
		this.centro=ds;
		matriz = new double[elementos.size()][elementos.get(0).getLista().size()];
		int i=0;
			for(Elemento elem : elementos) {
				int k=0;
				for(Double num : elem.getLista()) {
					matriz[i][k]=num;
					++k;
				}
				++i;
			}
	}
	
	
	public ArrayList<Double> getCentro(){
		return centro;
	}
	
	public void setCentro(ArrayList<Double> centro) {
		
		this.centro= centro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public double[][] getMatriz(){
		return matriz;
	}
	
	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}

	public double[][] getMatrizBayes() {
		return matrizBayes;
	}

	public void setMatrizBayes(double[][] matrizBayes) {
		this.matrizBayes = matrizBayes;
	}
	
	

}