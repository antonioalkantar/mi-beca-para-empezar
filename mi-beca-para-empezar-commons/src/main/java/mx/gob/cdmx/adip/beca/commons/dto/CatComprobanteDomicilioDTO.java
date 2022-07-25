package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatComprobanteDomicilioDTO implements Serializable {

	private static final long serialVersionUID = -5474998979164021909L;

	private Integer idComprobanteDomicilio;
	private String descripcion;
	private Boolean estatus;

	public CatComprobanteDomicilioDTO() {
	}

	public CatComprobanteDomicilioDTO(Integer idComprobanteDomicilio) {
		this.idComprobanteDomicilio = idComprobanteDomicilio;
	}

	public CatComprobanteDomicilioDTO(Integer idComprobanteDomicilio, String descripcion) {
		this.idComprobanteDomicilio = idComprobanteDomicilio;
		this.descripcion = descripcion;
	}
	public CatComprobanteDomicilioDTO(Integer idComprobanteDomicilio, String descripcion, Boolean estatus) {
		this.idComprobanteDomicilio = idComprobanteDomicilio;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public Integer getIdComprobanteDomicilio() {
		return idComprobanteDomicilio;
	}

	public void setIdComprobanteDomicilio(Integer idComprobanteDomicilio) {
		this.idComprobanteDomicilio = idComprobanteDomicilio;
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
