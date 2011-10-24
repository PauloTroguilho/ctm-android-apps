/**
 * 
 */
package br.com.clebertm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class Procurados {
	
	private List<Procurado> procurados;

	/**
	 * 
	 */
	public Procurados() {
		// TODO Auto-generated constructor stub
	}

	public List<Procurado> getProcurados() {
		if (this.procurados == null) {
			this.procurados = new ArrayList<Procurado>();
		}
		return procurados;
	}

	public void setProcurados(List<Procurado> procurados) {
		this.procurados = procurados;
	}

}
