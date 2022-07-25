package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatIdentificacionOficialDTO implements Serializable {

	private static final long serialVersionUID = 6403005395270067294L;

	private Integer idIdentificacion;
	private String descripcion;
	private boolean estatus;
	private Integer idCatLlave;
	private boolean extranjero;
	private boolean mexicano;

	public CatIdentificacionOficialDTO() {
	}

	public CatIdentificacionOficialDTO(int idIdentificacion) {
		this.idIdentificacion = idIdentificacion;
	}

	public CatIdentificacionOficialDTO(int idIdentificacion, String descripcion, boolean estatus) {
		this.idIdentificacion = idIdentificacion;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}
	public CatIdentificacionOficialDTO(int idIdentificacion, String descripcion, boolean estatus, Integer idCatLlave) {
		this.idIdentificacion = idIdentificacion;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.idCatLlave = idCatLlave;
	}

	public CatIdentificacionOficialDTO(Integer idIdentificacion, String descripcion, boolean estatus, Integer idCatLlave,
			boolean extranjero) {
		this.idIdentificacion = idIdentificacion;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.idCatLlave = idCatLlave;
		this.extranjero = extranjero;
	}

	public CatIdentificacionOficialDTO(Integer idIdentificacion, String descripcion, boolean estatus, Integer idCatLlave,
			boolean extranjero, boolean mexicano) {
		this.idIdentificacion = idIdentificacion;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.idCatLlave = idCatLlave;
		this.extranjero = extranjero;
		this.mexicano = mexicano;
	}

	public Integer getIdIdentificacion() {
		return idIdentificacion;
	}

	public void setIdIdentificacion(Integer idIdentificacion) {
		this.idIdentificacion = idIdentificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	public Integer getIdCatLlave() {
		return idCatLlave;
	}

	public void setIdCatLlave(Integer idCatLlave) {
		this.idCatLlave = idCatLlave;
	}

	public boolean isExtranjero() {
		return extranjero;
	}

	public void setExtranjero(boolean extranjero) {
		this.extranjero = extranjero;
	}

	public boolean isMexicano() {
		return mexicano;
	}

	public void setMexicano(boolean mexicano) {
		this.mexicano = mexicano;
	}
}
