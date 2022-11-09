package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatEstatusDispersionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7556406127108271018L;
	private Integer idEstatusDispersion;
	private String descripcion;
	private Boolean estatus;

	public CatEstatusDispersionDTO() {

	}
	
	public CatEstatusDispersionDTO(Integer idEstatusDispersion) {
		this.idEstatusDispersion = idEstatusDispersion;
	}
	
	public CatEstatusDispersionDTO(Integer idEstatusDispersion, String descripcion) {
		this.idEstatusDispersion = idEstatusDispersion;
		this.descripcion = descripcion;
	}

	public CatEstatusDispersionDTO(Integer idEstatusDispersion, String descripcion, Boolean estatus) {
		this.idEstatusDispersion = idEstatusDispersion;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public Integer getIdEstatusDispersion() {
		return idEstatusDispersion;
	}

	public void setIdEstatusDispersion(Integer idEstatusDispersion) {
		this.idEstatusDispersion = idEstatusDispersion;
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
