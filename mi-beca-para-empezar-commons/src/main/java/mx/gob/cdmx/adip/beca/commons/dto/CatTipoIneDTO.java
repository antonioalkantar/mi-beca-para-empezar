package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatTipoIneDTO implements Serializable {

	private static final long serialVersionUID = -7600307331709730786L;

	private int idTipoIne;
	private String descripcion;
	private Boolean estatus;

	public CatTipoIneDTO() {
	}

	public CatTipoIneDTO(int idTipoIne) {
		this.idTipoIne = idTipoIne;
	}
	
	public CatTipoIneDTO(int idTipoIne, String descripcion, Boolean estatus) {
		this.idTipoIne = idTipoIne;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public int getIdTipoIne() {
		return idTipoIne;
	}

	public void setIdTipoIne(int idTipoIne) {
		this.idTipoIne = idTipoIne;
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
