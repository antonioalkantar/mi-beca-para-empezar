package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;
import java.util.Date;

public class BitacoraCambioContactoDTO implements Serializable {

	private static final long serialVersionUID = -9200176709587744894L;

	private Long idBitacora;
	private Long idUsuarioLlaveCdmx;
	private Long idUsuarioFidegar;
	private String correoAnterior;
	private String correoNuevo;
	private String telefonoAnterior;
	private String telefonoNuevo;
	private Date fechaModificacion;
	private boolean enviadoAPagatodo;
	
	public BitacoraCambioContactoDTO() {
	}

	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public Long getIdUsuarioLlaveCdmx() {
		return idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(Long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	public Long getIdUsuarioFidegar() {
		return idUsuarioFidegar;
	}

	public void setIdUsuarioFidegar(Long idUsuarioFidegar) {
		this.idUsuarioFidegar = idUsuarioFidegar;
	}

	public String getCorreoAnterior() {
		return correoAnterior;
	}

	public void setCorreoAnterior(String correoAnterior) {
		this.correoAnterior = correoAnterior;
	}

	public String getCorreoNuevo() {
		return correoNuevo;
	}

	public void setCorreoNuevo(String correoNuevo) {
		this.correoNuevo = correoNuevo;
	}

	public String getTelefonoAnterior() {
		return telefonoAnterior;
	}

	public void setTelefonoAnterior(String telefonoAnterior) {
		this.telefonoAnterior = telefonoAnterior;
	}

	public String getTelefonoNuevo() {
		return telefonoNuevo;
	}

	public void setTelefonoNuevo(String telefonoNuevo) {
		this.telefonoNuevo = telefonoNuevo;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public boolean isEnviadoAPagatodo() {
		return enviadoAPagatodo;
	}

	public void setEnviadoAPagatodo(boolean enviadoAPagatodo) {
		this.enviadoAPagatodo = enviadoAPagatodo;
	}

}
