package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatGrupoPerteneceDTO implements Serializable {

	private static final long serialVersionUID = 1326193428952711311L;

	private int idGrupoPertenece;
	private String descripcion;
	private Boolean estatus;

	public CatGrupoPerteneceDTO() {
	}

	public CatGrupoPerteneceDTO(int idGrupoPertenece, String descripcion, Boolean estatus) {
		this.idGrupoPertenece = idGrupoPertenece;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public int getIdGrupoPertenece() {
		return idGrupoPertenece;
	}

	public void setIdGrupoPertenece(int idGrupoPertenece) {
		this.idGrupoPertenece = idGrupoPertenece;
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
