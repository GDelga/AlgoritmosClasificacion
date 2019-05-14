package command;

import factoria.FactoriaNegocio;
import negocio.TDatos;
import presentacion.Contexto;
import presentacion.Events;

public class CommandLloyd implements Command {

	@Override
	public Contexto execute(Object dato) {
		TDatos tDatos = (TDatos) dato;
		Object o = FactoriaNegocio.getInstance().crearLloyd().algoritmo(tDatos);
		if(o instanceof String) return new Contexto(Events.LLOYD_OK, o);
		else return new Contexto(Events.LLOYD_KO, o);
	}

}
