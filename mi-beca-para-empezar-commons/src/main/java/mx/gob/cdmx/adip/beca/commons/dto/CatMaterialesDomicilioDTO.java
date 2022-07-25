package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatMaterialesDomicilioDTO implements Serializable {

	private static final long serialVersionUID = 793222667745124231L;

	private int idMaterialesDomicilio;
	private String descripcion;
	private Boolean estatus;

	public CatMaterialesDomicilioDTO() {
	}

	public CatMaterialesDomicilioDTO(int idMaterialesDomicilio, String descripcion, Boolean estatus) {
		this.idMaterialesDomicilio = idMaterialesDomicilio;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public int getIdMaterialesDomicilio() {
		return idMaterialesDomicilio;
	}

	public void setIdMaterialesDomicilio(int idMaterialesDomicilio) {
		this.idMaterialesDomicilio = idMaterialesDomicilio;
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
