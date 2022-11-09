package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;
import java.util.Date;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

public class BitacoraCambiosTutorDTO implements Serializable {

	private static final long serialVersionUID = -6658921046983528215L;

	private int idCambioTutor;
	private SolicitudDTO solicitudDTO;
	private TutorDTO tutorByIdTutorNuevoDTO;
	private TutorDTO tutorByIdTutorAnteriorDTO;
	private Date fecha;
	private Long idUsuario;
	private String observaciones;
	private String soporteDocDescripcion;
	private String rutaDocto;
	
	// Auxiliares documentos
	private byte[] contentFileSoporteDoc;

	public BitacoraCambiosTutorDTO() {
	}
	
	public BitacoraCambiosTutorDTO(int idCambioTutor, Date fecha,
			Long idSolicitud, 
			String nombreTutorAnterior, String primerApellidoTutorAnterior, String segundoApellidoTutorAnterior,
			String nombreTutorNuevo, String primerApellidoTutorNuevo, String segundoApellidoTutorNuevo,
			Long idUsuario, String observaciones, String soporteDocDescripcion, String rutaDocto) {
		this.idCambioTutor = idCambioTutor;
		this.solicitudDTO = new SolicitudDTO(idSolicitud);
		this.tutorByIdTutorNuevoDTO = new TutorDTO(nombreTutorNuevo, primerApellidoTutorNuevo, segundoApellidoTutorNuevo);
		this.tutorByIdTutorAnteriorDTO = new TutorDTO(nombreTutorAnterior, primerApellidoTutorAnterior, segundoApellidoTutorAnterior);
		this.fecha = fecha;
		this.idUsuario = idUsuario;
		this.observaciones = observaciones;
		this.soporteDocDescripcion = soporteDocDescripcion;
		this.rutaDocto = rutaDocto;
	}

	public int getIdCambioTutor() {
		return idCambioTutor;
	}

	public void setIdCambioTutor(int idCambioTutor) {
		this.idCambioTutor = idCambioTutor;
	}

	public SolicitudDTO getSolicitudDTO() {
		return solicitudDTO;
	}

	public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
		this.solicitudDTO = solicitudDTO;
	}

	public TutorDTO getTutorByIdTutorNuevoDTO() {
		return tutorByIdTutorNuevoDTO;
	}

	public void setTutorByIdTutorNuevoDTO(TutorDTO tutorByIdTutorNuevoDTO) {
		this.tutorByIdTutorNuevoDTO = tutorByIdTutorNuevoDTO;
	}

	public TutorDTO getTutorByIdTutorAnteriorDTO() {
		return tutorByIdTutorAnteriorDTO;
	}

	public void setTutorByIdTutorAnteriorDTO(TutorDTO tutorByIdTutorAnteriorDTO) {
		this.tutorByIdTutorAnteriorDTO = tutorByIdTutorAnteriorDTO;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}	

	public byte[] getContentFileSoporteDoc() {
		return contentFileSoporteDoc;
	}

	public void setContentFileSoporteDoc(byte[] contentFileSoporteDoc) {
		this.contentFileSoporteDoc = contentFileSoporteDoc;
	}

	public String getSoporteDocDescripcion() {
		return soporteDocDescripcion;
	}

	public void setSoporteDocDescripcion(String soporteDocDescripcion) {
		this.soporteDocDescripcion = soporteDocDescripcion;
	}

	public String getRutaDocto() {
		return rutaDocto;
	}

	public void setRutaDocto(String rutaDocto) {
		this.rutaDocto = rutaDocto;
	}

	public String getNombreArchivoCorto() {
		return rutaDocto == null ? ""
				: rutaDocto.contains(Constantes.SEPARADOR_RUTA)
						? rutaDocto
								.substring(rutaDocto.lastIndexOf(Constantes.SEPARADOR_RUTA) + 1)
						: rutaDocto;
	}
}

