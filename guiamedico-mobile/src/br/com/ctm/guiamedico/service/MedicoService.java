/**
 * 
 */
package br.com.ctm.guiamedico.service;

import br.com.ctm.guiamedico.model.Medico;

/**
 * @author Cleber
 *
 */
public class MedicoService extends EntityService<Medico> {

	/**
	 * @param entityResourceName
	 */
	public MedicoService() {
		super("medicos", Medico.class);
	}

}
