package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatPeriodoEscolarDTO implements Serializable {

	private static final long serialVersionUID = 1670507576652389950L;
	private Integer idPeriodoEscolar;
	private String descripcion;
	private Boolean estatus;

	public CatPeriodoEscolarDTO() {	}
	
	public CatPeriodoEscolarDTO(Integer idPeriodoEscolar) {
		this.idPeriodoEscolar = idPeriodoEscolar;
	}
	
	public CatPeriodoEscolarDTO(Integer idPeriodoEscolar, String descripcion) {
		this.idPeriodoEscolar = idPeriodoEscolar;
		this.descripcion = descripcion;
	}
	
	public CatPeriodoEscolarDTO(Integer idPeriodoEscolar, String descripcion, Boolean estatus) {
		this.idPeriodoEscolar = idPeriodoEscolar;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public Integer getIdPeriodoEscolar() {
		return idPeriodoEscolar;
	}

	public void setIdPeriodoEscolar(Integer idPeriodoEscolar) {
		this.idPeriodoEscolar = idPeriodoEscolar;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
}

