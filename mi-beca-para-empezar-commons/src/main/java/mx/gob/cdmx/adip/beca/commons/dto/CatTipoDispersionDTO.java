package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatTipoDispersionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3827780635510968367L;
	private Integer idTipoDispersion;
	private String descripcion;
	private Boolean estatus;

	public CatTipoDispersionDTO() {
	}
	
	public CatTipoDispersionDTO(Integer idTipoDispersion) {
		this.idTipoDispersion = idTipoDispersion;
	}
	
	public CatTipoDispersionDTO(Integer idTipoDispersion, String descripcion) {
		this.idTipoDispersion = idTipoDispersion;
		this.descripcion = descripcion;
	}

	public CatTipoDispersionDTO(Integer idTipoDispersion, String descripcion, Boolean estatus) {
		this.idTipoDispersion = idTipoDispersion;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public Integer getIdTipoDispersion() {
		return idTipoDispersion;
	}

	public void setIdTipoDispersion(Integer idTipoDispersion) {
		this.idTipoDispersion = idTipoDispersion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
}
