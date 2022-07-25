package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatIngresosFamiliaDTO implements Serializable {

	private static final long serialVersionUID = -2993465658116206883L;

	private int idIngresosFamilia;
	private String descripcion;
	private Boolean estatus;

	public CatIngresosFamiliaDTO() {
	}

	public CatIngresosFamiliaDTO(int idIngresosFamilia, String descripcion, Boolean estatus) {
		this.idIngresosFamilia = idIngresosFamilia;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public int getIdIngresosFamilia() {
		return idIngresosFamilia;
	}

	public void setIdIngresosFamilia(int idIngresosFamilia) {
		this.idIngresosFamilia = idIngresosFamilia;
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
