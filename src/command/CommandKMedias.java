package command;

import factoria.FactoriaNegocio;
import negocio.TDatos;
import presentacion.Contexto;
import presentacion.Events;

public class CommandKMedias implements Command{

	@Override
	public Contexto execute(Object dato) {
		TDatos tDatos = (TDatos) dato;
		Object o = FactoriaNegocio.getInstance().crearKMedias().algoritmo(tDatos);
		if(o instanceof String) return new Contexto(Events.KMEDIAS_OK, o);
		else return new Contexto(Events.KMEDIAS_KO, o);
	}

}
