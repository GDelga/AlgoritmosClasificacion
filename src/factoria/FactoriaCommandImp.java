/**
 * 
 */
package factoria;

import command.Command;
import command.CommandBayes;
import command.CommandKMedias;
import command.CommandLloyd;
import presentacion.Events;

/**
 * @author Guillermo Delgado Yepes
 */
public class FactoriaCommandImp extends FactoriaCommand {

	@Override
	public Command generarComando(int evento) {
		switch(evento){
		case(Events.BAYES):
			return new CommandBayes();
		case(Events.KMEDIAS):
			return new CommandKMedias();
		case(Events.LLOYD):
			return new CommandLloyd();
		}
		return null;
	
	}
}