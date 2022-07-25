package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatTiposAsentamientoDTO implements Serializable{

	private static final long serialVersionUID = -6638439002471999553L;
	private int idTipoAsentamiento;
	private String descripcion;
	
	public CatTiposAsentamientoDTO() {}
	
	public CatTiposAsentamientoDTO(int idTipoAsentamiento, String descripcion) {
		this.idTipoAsentamiento = idTipoAsentamiento;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoAsentamiento
	 */
	public int getIdTipoAsentamiento() {
		return idTipoAsentamiento;
	}

	/**
	 * @param idTipoAsentamiento the idTipoAsentamiento to set
	 */
	public void setIdTipoAsentamiento(int idTipoAsentamiento) {
		this.idTipoAsentamiento = idTipoAsentamiento;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
