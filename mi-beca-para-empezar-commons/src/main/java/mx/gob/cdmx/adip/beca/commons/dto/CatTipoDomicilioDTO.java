package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatTipoDomicilioDTO implements Serializable {

	private static final long serialVersionUID = 2716129183776856120L;

	private int idTipoDomicilio;
	private String descripcion;
	private Boolean estatus;

	public CatTipoDomicilioDTO() {
	}

	public CatTipoDomicilioDTO(int idTipoDomicilio, String descripcion, Boolean estatus) {
		this.idTipoDomicilio = idTipoDomicilio;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	public int getIdTipoDomicilio() {
		return idTipoDomicilio;
	}

	public void setIdTipoDomicilio(int idTipoDomicilio) {
		this.idTipoDomicilio = idTipoDomicilio;
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
