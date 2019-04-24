package integracion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import archivo.Clase;
import archivo.Elemento;
import negocio.TDatos;

/**
 * @author Guillermo Delgado Yepes
 */
public class ArchivoImp implements Archivo {

	@Override
	public HashMap<String,ArrayList<Elemento>> leerElementos(TDatos tDatos){
		try{
			String cadena;
			FileReader f = new FileReader(tDatos.getArchivoDatos());
			BufferedReader b = new BufferedReader(f);
			HashMap<String,ArrayList<Elemento>> listaElementos = new HashMap<>();
			HashMap<String,String> clases = new HashMap<>();
			// LEER EL ARCHIVO HASTA EL FINAL DE ARCHIVO
			while ((cadena = b.readLine()) != null) {
				// PARSEAR CADA LINEA
				ArrayList<Double> numeros = new ArrayList<Double>();
				String op[] = cadena.split(",");
				for (String elementData : op) {
					if (elementData.matches("[0-9]+.+[0-9]+")) { //Si es un numero
							numeros.add(Double.parseDouble(elementData));
						} else {
							if(!clases.containsKey(elementData)){
								clases.put(elementData, elementData);
								tDatos.getClases().add(elementData);
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
				b.close();
				return listaElementos;
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
					numeros.add(Double.parseDouble(elementData));
				}
				if(contador < tDatos.getClases().size()) {
					clase.add(new Clase(elemento.get(tDatos.getClases().get(contador)), tDatos.getClases().get(contador), numeros));
					contador++;
				}
				else {
					//ERROR, NO COINCIDEN ELEMENTOS DE INICIALIZACION CON NUMERO DE CLASES
					return -1;
				}
			}
			if(contador != tDatos.getClases().size()) return -2;
			b.close();
			return clase;
		} catch (Exception e) {
			return null;
		}
	}

}
