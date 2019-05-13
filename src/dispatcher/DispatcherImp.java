package dispatcher;

import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUIMain;

/**
 * @author Guillermo Delgado Yepes
 */
public class DispatcherImp extends Dispatcher {

	@Override
	public void generarVista(Contexto contexto) {
		int evento = contexto.getEvento();
		switch(evento) {
		case(Events.GUI_MAIN):
			GUIMain.getInstance().actualizar(contexto);
		break;
		case(Events.BAYES):
			GUIMain.getInstance().actualizar(contexto);
		break;
		case(Events.KMEDIAS):
			GUIMain.getInstance().actualizar(contexto);
		break;
		case(Events.LLOYD):
			GUIMain.getInstance().actualizar(contexto);
		break;
		}
	}
}