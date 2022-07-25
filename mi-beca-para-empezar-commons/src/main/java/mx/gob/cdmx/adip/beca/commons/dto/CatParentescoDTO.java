package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatParentescoDTO implements Serializable {

	private static final long serialVersionUID = 7987558686262551210L;

	private int idParentesco;
	private String descripcion;
	private Boolean estatus;

	public CatParentescoDTO() {
	}

	public CatParentescoDTO(int idParentesco, String descripcion) {
		this.idParentesco = idParentesco;
		this.descripcion = descripcion;
	}

	public CatParentescoDTO(int idParentesco, String descripcion, Boolean estatus) {
		this.idParentesco = idParentesco;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public int getIdParentesco() {
		return idParentesco;
	}

	public void setIdParentesco(int idParentesco) {
		this.idParentesco = idParentesco;
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
