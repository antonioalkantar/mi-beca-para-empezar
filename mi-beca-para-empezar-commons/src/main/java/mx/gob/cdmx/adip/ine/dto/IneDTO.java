package mx.gob.cdmx.adip.ine.dto;

import java.io.Serializable;

public class IneDTO implements Serializable {
	private static final long serialVersionUID = 316613428307559405L;

	private String tipoSituacionRegistral;

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

}
