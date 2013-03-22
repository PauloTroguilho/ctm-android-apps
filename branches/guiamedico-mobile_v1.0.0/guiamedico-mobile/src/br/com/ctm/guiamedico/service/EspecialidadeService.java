/**
 * 
 */
package br.com.ctm.guiamedico.service;

import br.com.ctm.guiamedico.model.Especialidade;

/**
 * @author Cleber
 *
 */
public class EspecialidadeService extends EntityService<Especialidade> {

	/**
	 * @param entityResourceName
	 */
	public EspecialidadeService() {
		super("especialidades", Especialidade.class);
	}

}
