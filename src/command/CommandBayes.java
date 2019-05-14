package command;

import factoria.FactoriaNegocio;
import negocio.TDatos;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBayes implements Command{

	@Override
	public Contexto execute(Object dato) {
		TDatos tDatos = (TDatos) dato;
		Object o = FactoriaNegocio.getInstance().crearBayes().algoritmo(tDatos);
		if(o instanceof String) return new Contexto(Events.BAYES_OK, o);
		else return new Contexto(Events.BAYES_KO, o);
	}

}
