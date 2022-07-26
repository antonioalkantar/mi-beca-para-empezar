package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;
import java.util.Date;

public class CatCicloEscolarDTO implements Serializable{

	private static final long serialVersionUID = -1778445119011049078L;
	private Integer idCicloEscolar;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean estatus;
	private Date fechaRegistro;

	public CatCicloEscolarDTO() {

	}

	public CatCicloEscolarDTO(Integer idCicloEscolar, String descripcion, Date fechaInicio, Date fechaFin,
			Boolean estatus, Date fechaRegistro) {
		this.idCicloEscolar = idCicloEscolar;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estatus = estatus;
		this.fechaRegistro = fechaRegistro;
	}
	
	public CatCicloEscolarDTO(Integer idCicloEscolar, String descripcion) {
		this.idCicloEscolar = idCicloEscolar;
		this.descripcion = descripcion;
	}
	

	public Integer getIdCicloEscolar() {
		return idCicloEscolar;
	}

	public void setIdCicloEscolar(Integer idCicloEscolar) {
		this.idCicloEscolar = idCicloEscolar;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
