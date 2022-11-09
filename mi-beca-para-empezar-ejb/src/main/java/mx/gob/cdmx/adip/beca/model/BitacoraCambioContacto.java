package mx.gob.cdmx.adip.beca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bitacora_cambio_contacto", schema = "mibecaparaempezar")
@NamedQueries({

})
public class BitacoraCambioContacto implements Serializable {

	private static final long serialVersionUID = 235500099988368409L;

	private Long idBitacora;
	private Long idUsuarioLlaveCdmx;
	private Long idUsuarioFidegar;
	private String correoAnterior;
	private String correoNuevo;
	private String telefonoAnterior;
	private String telefonoNuevo;
	private Date fechaModificacion;
	private boolean enviadoAPagatodo;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_bitacora", unique = true, nullable = false)
	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	@Column(name = "id_usuario_llave_cdmx", nullable = false)
	public Long getIdUsuarioLlaveCdmx() {
		return idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(Long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}
	
	
	@Column(name = "id_usuario_fidegar")
	public Long getIdUsuarioFidegar() {
		return idUsuarioFidegar;
	}

	public void setIdUsuarioFidegar(Long idUsuarioFidegar) {
		this.idUsuarioFidegar = idUsuarioFidegar;
	}

	@Column(name = "correo_anterior", nullable = false)
	public String getCorreoAnterior() {
		return correoAnterior;
	}

	public void setCorreoAnterior(String correoAnterior) {
		this.correoAnterior = correoAnterior;
	}

	@Column(name = "correo_nuevo", nullable = false)
	public String getCorreoNuevo() {
		return correoNuevo;
	}

	public void setCorreoNuevo(String correoNuevo) {
		this.correoNuevo = correoNuevo;
	}

	@Column(name = "telefono_anterior", nullable = false)
	public String getTelefonoAnterior() {
		return telefonoAnterior;
	}

	public void setTelefonoAnterior(String telefonoAnterior) {
		this.telefonoAnterior = telefonoAnterior;
	}

	@Column(name = "telefono_nuevo", nullable = false)
	public String getTelefonoNuevo() {
		return telefonoNuevo;
	}

	public void setTelefonoNuevo(String telefonoNuevo) {
		this.telefonoNuevo = telefonoNuevo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", nullable = false)
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Column(name = "enviado_a_pagatodo")
	public boolean isEnviadoAPagatodo() {
		return enviadoAPagatodo;
	}

	public void setEnviadoAPagatodo(boolean enviadoAPagatodo) {
		this.enviadoAPagatodo = enviadoAPagatodo;
	}

}
