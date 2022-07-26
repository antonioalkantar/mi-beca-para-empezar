package mx.gob.cdmx.adip.beca.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BitacoraCambiosTutor generated by hbm2java
 */
@Entity
@Table(name = "bitacora_cambios_tutor", schema = "mibecaparaempezar")
@NamedQueries({
	  @NamedQuery(name = "BitacoraCambiosTutor.BuscarPorIdSolicitud"
	, query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO("
		+ " bct.idCambioTutor, "
		+ " bct.fecha, "
		+ " s.idSolicitud, "
		+ " ta.nombre, "
		+ " ta.primerApellido, "
		+ " ta.segundoApellido,  "
		+ " tn.nombre, "
		+ " tn.primerApellido, "
		+ " tn.segundoApellido,  "
		+ " bct.idUsuario, "
		+ " bct.observaciones, "
		+ " bct.soporteDocDescripcion, "
		+ " bct.rutaDocto "
		+ ")"
		+ " FROM BitacoraCambiosTutor bct "
		+ "	LEFT JOIN bct.solicitud s "
		+ "	LEFT JOIN bct.tutorByIdTutorAnterior ta "
		+ "	LEFT JOIN bct.tutorByIdTutorNuevo tn "
		+ " WHERE s.idSolicitud = :idSolicitud ")
})
public class BitacoraCambiosTutor implements java.io.Serializable {
	
	private static final long serialVersionUID = -5967226979368139529L;
	private int idCambioTutor;
	private Solicitud solicitud;
	private Tutor tutorByIdTutorNuevo;
	private Tutor tutorByIdTutorAnterior;
	private Date fecha;
	private Long idUsuario;
	private String observaciones;
	private String soporteDocDescripcion;
	private String rutaDocto;

	public BitacoraCambiosTutor() {
	}

	public BitacoraCambiosTutor(int idCambioTutor, Solicitud solicitud, Tutor tutorByIdTutorNuevo,
			Tutor tutorByIdTutorAnterior, Date fecha, Long idUsuario) {
		this.idCambioTutor = idCambioTutor;
		this.solicitud = solicitud;
		this.tutorByIdTutorNuevo = tutorByIdTutorNuevo;
		this.tutorByIdTutorAnterior = tutorByIdTutorAnterior;
		this.fecha = fecha;
		this.idUsuario = idUsuario;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_cambio_tutor", unique = true, nullable = false)
	public int getIdCambioTutor() {
		return this.idCambioTutor;
	}

	public void setIdCambioTutor(int idCambioTutor) {
		this.idCambioTutor = idCambioTutor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_solicitud", nullable = false)
	public Solicitud getSolicitud() {
		return this.solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tutor_nuevo", nullable = false)
	public Tutor getTutorByIdTutorNuevo() {
		return this.tutorByIdTutorNuevo;
	}

	public void setTutorByIdTutorNuevo(Tutor tutorByIdTutorNuevo) {
		this.tutorByIdTutorNuevo = tutorByIdTutorNuevo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tutor_anterior", nullable = false)
	public Tutor getTutorByIdTutorAnterior() {
		return this.tutorByIdTutorAnterior;
	}

	public void setTutorByIdTutorAnterior(Tutor tutorByIdTutorAnterior) {
		this.tutorByIdTutorAnterior = tutorByIdTutorAnterior;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", nullable = false)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "id_usuario", nullable = false)
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "observaciones", nullable = false)
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Column(name = "soporte_documental")
	public String getSoporteDocDescripcion() {
		return soporteDocDescripcion;
	}

	public void setSoporteDocDescripcion(String soporteDocDescripcion) {
		this.soporteDocDescripcion = soporteDocDescripcion;
	}

	@Column(name = "ruta_docto")
	public String getRutaDocto() {
		return rutaDocto;
	}

	public void setRutaDocto(String rutaDocto) {
		this.rutaDocto = rutaDocto;
	}

}
