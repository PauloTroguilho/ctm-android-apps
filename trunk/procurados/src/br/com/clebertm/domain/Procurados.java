/**
 * 
 */
package br.com.clebertm.domain;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class Procurados {
	
	private Set<Procurado> procurados;

	/**
	 * 
	 */
	public Procurados() {
		// TODO Auto-generated constructor stub
	}

	public Set<Procurado> getProcurados() {
		if (this.procurados == null) {
			this.procurados = new TreeSet<Procurado>();
		}
		return procurados;
	}

	public void setProcurados(Set<Procurado> procurados) {
		this.procurados = procurados;
	}

}
