package mx.gob.cdmx.adip.ine.dto;

import java.io.Serializable;

public class IneDTO implements Serializable {
	
	private static final long serialVersionUID = 316613428307559405L;

	private String tipoSituacionRegistral;
	private Boolean curpCorrecto;

	public IneDTO(String tipoSituacionRegistral) {
		super();
		this.tipoSituacionRegistral = tipoSituacionRegistral;
	}	

	public IneDTO(String tipoSituacionRegistral, Boolean curpCorrecto) {
		super();
		this.tipoSituacionRegistral = tipoSituacionRegistral;
		this.curpCorrecto = curpCorrecto;
	}

	/**
	 * @return the tipoSituacionRegistral
	 */
	public String getTipoSituacionRegistral() {
		return tipoSituacionRegistral;
	}

	/**
	 * @param tipoSituacionRegistral the tipoSituacionRegistral to set
	 */
	public void setTipoSituacionRegistral(String tipoSituacionRegistral) {
		this.tipoSituacionRegistral = tipoSituacionRegistral;
	}

	/**
	 * @return the curpCorrecto
	 */
	public Boolean curpCorrecto() {
		return curpCorrecto;
	}

	/**
	 * @param curpCorrecto the curpCorrecto to set
	 */
	public void setCurpCorrecto(Boolean curpCorrecto) {
		this.curpCorrecto = curpCorrecto;
	}

	
}