package factoria;

import negocio.KMediasImp;

/**
 * @author Guillermo Delgado Yepes
 */
public class FactoriaNegocioImp extends FactoriaNegocio{

	@Override
	public KMediasImp crearKMedias() {
		return new KMediasImp();
	}

}
