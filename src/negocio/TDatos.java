package negocio;

import java.util.ArrayList;

/**
 * @author Guillermo Delgado Yepes
 */
public class TDatos {

	private String archivoDatos;
	private String archivoClases;
	private String archivoEjemplos;
	private ArrayList<String> clases;

	public TDatos(String a1, String a2, String a3) {
		this.archivoDatos = a1;
		this.archivoClases = a2;
		this.archivoEjemplos = a3;
	}

	public String getArchivoDatos() {
		return archivoDatos;
	}

	public void setArchivoDatos(String archivoDatos) {
		this.archivoDatos = archivoDatos;
	}

	public String getArchivoClases() {
		return archivoClases;
	}

	public void setArchivoClases(String archivoClases) {
		this.archivoClases = archivoClases;
	}

	public String getArchivoEjemplos() {
		return archivoEjemplos;
	}

	public void setArchivoEjemplos(String archivoEjemplos) {
		this.archivoEjemplos = archivoEjemplos;
	}

	public ArrayList<String> getClases() {
		return clases;
	}

	public void setClases(ArrayList<String> clases) {
		this.clases = clases;
	}

}
