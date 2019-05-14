package integracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import negocio.TDatos;
import negocio.TIntegracion;

/**
 * @author Guillermo Delgado Yepes
 */
public class ArchivoImp implements Archivo {

	@Override
	public TIntegracion leerElementos(TDatos tDatos){
		try{
			String cadena;
			FileReader f = new FileReader(tDatos.getArchivoDatos());
			BufferedReader b = new BufferedReader(f);
			HashMap<String,ArrayList<Elemento>> listaElementos = new HashMap<>();
			HashMap<String,String> clases = new HashMap<>();
			ArrayList<ArrayList<Double>> datos = new ArrayList<>();
			ArrayList<String> nombreClases = new ArrayList<>();
			// LEER EL ARCHIVO HASTA EL FINAL DE ARCHIVO
			while ((cadena = b.readLine()) != null) {
				// PARSEAR CADA LINEA
				ArrayList<Double> numeros = new ArrayList<Double>();
				String op[] = cadena.split(",");
				for (String elementData : op) {
					if (elementData.matches("[0-9]+.+[0-9]+")) { //Si es un numero
							numeros.add(Double.parseDouble(elementData));
						} else {
							datos.add(numeros);
							if(!clases.containsKey(elementData)){
								clases.put(elementData, elementData);
								nombreClases.add(elementData);
							}
							Elemento elem= new Elemento(numeros, elementData);
							if(listaElementos.containsKey(elementData)){
								listaElementos.get(elementData).add(elem);
							}else{
								listaElementos.put(elementData, new ArrayList<Elemento>());
								listaElementos.get(elementData).add(elem);
							}
						}
					}
				}
			tDatos.setClases(nombreClases);
			b.close();
			return new TIntegracion(listaElementos, datos);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Object leerClases(TDatos tDatos, HashMap<String,ArrayList<Elemento>> elemento) {
		try {
			String cadena;
			FileReader f = new FileReader(tDatos.getArchivoClases());
			BufferedReader b = new BufferedReader(f);
			ArrayList<Clase> clase = new ArrayList<>();
			int contador = 0;
			// LEER EL ARCHIVO HASTA EL FINAL DE ARCHIVO
			while ((cadena = b.readLine()) != null) {
				// PARSEAR CADA LINEA
				ArrayList<Double> numeros = new ArrayList<Double>();
				String op[] = cadena.split(",");
				for (String elementData : op) {
					if (elementData.matches("[0-9]+.+[0-9]+")) { //Si es un numero
						numeros.add(Double.parseDouble(elementData));
					} 
					else return -3; //ERROR
				}
				if(contador < tDatos.getClases().size()) {
					clase.add(new Clase(elemento.get(tDatos.getClases().get(contador)), tDatos.getClases().get(contador), numeros));
					contador++;
				}
				else {
					//ERROR, NO COINCIDEN ELEMENTOS DE INICIALIZACION CON NUMERO DE CLASES + INICIALIZACION QUE CLASES
					return -1;
				}
			}
			if(contador != tDatos.getClases().size()) return -2; //ERROR, NO COINCIDEN ELEMENTOS DE INICIALIZACION CON NUMERO DE CLASES - INICIALIZACION QUE CLASES
			b.close();
			return clase;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Object leerEjemplo(TDatos tDatos){
		try{
			String cadena;
			FileReader f = new FileReader(tDatos.getArchivoEjemplos());
			BufferedReader b = new BufferedReader(f);
			ArrayList<Double> datos = new ArrayList<>();
			cadena = b.readLine(); //Solo tiene una linea
			String op[] = cadena.split(",");
			for (String elementData : op) {
				if (elementData.matches("[0-9]+.+[0-9]+")) { //Si es un numero
					datos.add(Double.parseDouble(elementData));
				} 
				//else return -4; //ERROR
			}
			b.close();
			return datos;
		} catch (Exception e) {
			return null;
		}
	}

}
