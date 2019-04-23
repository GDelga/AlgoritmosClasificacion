package negocio;

import java.util.ArrayList;

/**
 * @author Guillermo Delgado Yepes
 */
public class TDatos {

	private String archivoDatos;
	private String archivoClases;
	private ArrayList<String> clases;

	public TDatos(String a1, String a2) {
		this.archivoDatos = a1;
		this.archivoClases = a2;
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

	public ArrayList<String> getClases() {
		return clases;
	}

	public void setClases(ArrayList<String> clases) {
		this.clases = clases;
	}
}
