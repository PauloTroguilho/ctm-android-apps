/**
 * 
 */
package br.com.clebertm.domain;

import java.io.Serializable;

import br.com.clebertm.procurados.util.Consts;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class Procurado implements Comparable<Procurado>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7955275787397468411L;
	
	private Integer id;
	private String fotoId;
	private String nome;
	private String apelido;
	private String historico;
	private String detalheUrl;
	private String vulgo;
	private String numeroProcesso;
	private String juizCompetente;
	private String comarca;
	private String dataExpedicaoMandado;
	private String dataNascimento;
	private String rg;

	/**
	 * 
	 */
	public Procurado() {
		// TODO Auto-generated constructor stub
	}

	public String getDetalheUrl() {
		return detalheUrl;
	}

	public void setDetalheUrl(String detalheUrl) {
		this.detalheUrl = detalheUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFotoId() {
		return fotoId;
	}

	public void setFotoId(String fotoId) {
		this.fotoId = fotoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getJuizCompetente() {
		return juizCompetente;
	}

	public void setJuizCompetente(String juizCompetente) {
		this.juizCompetente = juizCompetente;
	}

	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	public String getDataExpedicaoMandado() {
		return dataExpedicaoMandado;
	}

	public void setDataExpedicaoMandado(String dataExpedicaoMandado) {
		this.dataExpedicaoMandado = dataExpedicaoMandado;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getVulgo() {
		return vulgo;
	}

	public void setVulgo(String vulgo) {
		this.vulgo = vulgo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Procurado arg0) {
		Integer id1 = this.id != null ? this.id : -1;
		Integer id2 = arg0.getId() != null ? arg0.getId() : -1;
		return id1.compareTo(id2);
	}
	
	/**
	 * @return
	 */
	public String getApelidoTratado() {
		String apelidoTratado = "";
		if (getApelido() != null && getApelido().trim().length() > 0) {
			apelidoTratado = getApelido();
		} else {
			apelidoTratado = getVulgo();
		}
		if (apelidoTratado == null || apelidoTratado.trim().length() == 0) {
			apelidoTratado = getNome().split(" ")[0];
		}
		return apelidoTratado;
	}
	
	public String getFotoUrl() {
		return Consts.SERVER_URL_TO_FOTOSDIR + "proc_" + getFotoId() + ".jpeg";
	}

}
