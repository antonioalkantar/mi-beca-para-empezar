package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;
import java.util.Date;

import mx.gob.cdmx.adip.beca.oauth.dto.UsuarioDTO;

public class SolicitudDTO implements Serializable {

	private static final long serialVersionUID = -5417434225645838838L;
	private Long idSolicitud;
	private CatEstatusBeneficiarioDTO catEstatusBeneficiarioDTO;
	private CatEstatusDTO catEstatusDTO;
	private CatEstatusDTO catEstatusTutorDTO;
	private CatMunicipiosDTO catMunicipiosDTO;
	private CatParentescoDTO catParentescoDTO;
	private CatCodigosRespuestaPagatodoDTO catCodigosRespuestaPagatodoDTO;
	private TutorDTO tutorDTO;
	private UsuarioDTO usuarioDTO;
	private BeneficiarioDTO  beneficiarioDTO;
	private String folioSolicitud;
	private Date fechaSolicitud;
	private Date fechaInicio;
	private Date fechaFin;
	private String respuestaAutoridadEducativa;
	private Integer idUsuarioFidegar;
	private String cct;
	private String nombre;
	private String calle;
	private String colonia;
	private String codigopostal;
	private String entidad;
	private String turno;
	private CatNivelEducativoDTO catNivelEducativoDTO;
	private String gradoEscolar;
	private String estatusBeneficiario;
	private boolean pagatodoEnvioExitoso;
	private String nombreCompletoTutor;
	private String nombreCompletoBeneficiario;
	private Boolean esNuevoRegistro;
	private Boolean externo;
	private Date pagatodoFechaEnvio;

	public SolicitudDTO() {
		catParentescoDTO = new CatParentescoDTO();
		tutorDTO = new TutorDTO();
		catEstatusDTO = new CatEstatusDTO();
		catEstatusTutorDTO = new CatEstatusDTO();
		beneficiarioDTO = new BeneficiarioDTO();
		catMunicipiosDTO = new CatMunicipiosDTO();
		catEstatusBeneficiarioDTO = new CatEstatusBeneficiarioDTO();
		catNivelEducativoDTO = new CatNivelEducativoDTO();
		catCodigosRespuestaPagatodoDTO = new CatCodigosRespuestaPagatodoDTO();
	}
	
	public SolicitudDTO(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public SolicitudDTO(Long idSolicitud, String estatus) {
		this.idSolicitud = idSolicitud;
		this.estatusBeneficiario = estatus;
	}
	
	public SolicitudDTO(Long idSolicitud,  String folioSolicitud, Integer idEstatusBeneficiario, String descripcionEstatusBeneficiario) {
		this.idSolicitud = idSolicitud;
		this.folioSolicitud = folioSolicitud;
		this.catEstatusBeneficiarioDTO = new CatEstatusBeneficiarioDTO(idEstatusBeneficiario, descripcionEstatusBeneficiario);
	}
// DTO para cunsultar las solicitudes de la bandeja del Funcionario
	public SolicitudDTO(Long idSolicitud, String folioSolicitud, Long idUsuarioLlaveCdmx, String nombreTutor, String apellidoPTutor, String apellidoMTutor, 
						String nombresBeneficiario, String primerApellidoBeneficiario, String segundoApellidoBeneficiario,
						String cct, int idEstatusTutor, String descripcionEstatusTutor, int idEstatusBeneficiario, String descripcionBeneficiario
						
			) {
		this.idSolicitud = idSolicitud;
		this.folioSolicitud = folioSolicitud;
		this.tutorDTO = new TutorDTO(idUsuarioLlaveCdmx);
		this.nombreCompletoTutor = nombreTutor + " " + apellidoPTutor + " " + (apellidoMTutor == null ? "" : apellidoMTutor);		
		this.nombreCompletoBeneficiario = (nombresBeneficiario.equals(null) ? " " : nombresBeneficiario) +" "+
										  (primerApellidoBeneficiario.equals(null)? "" : primerApellidoBeneficiario) +" "+(segundoApellidoBeneficiario == null ? "" : segundoApellidoBeneficiario);	
		this.cct = cct;	
		this.catEstatusTutorDTO = new CatEstatusDTO(idEstatusTutor, descripcionEstatusTutor);
		this.catEstatusBeneficiarioDTO = new CatEstatusBeneficiarioDTO(idEstatusBeneficiario, descripcionBeneficiario);
	}
	//DTO para consulta Solicitud.findById
	public SolicitudDTO(Long idSolicitud, String folioSolicitud, Long idUsuarioLlaveCdmx, String nombreTutor, String apellidoPTutor, String apellidoMTutor, String curpTutor,
			Long idBeneficiario, String nombresBeneficiario, String primerApellidoBeneficiario, String segundoApellidoBeneficiario, String nacionalidadBeneficiario, String curpBeneficiario,
			Integer idEstatusTutor ,String estatusTutor, int idEstatusBeneficiario, String descripcionBeneficiario, Long idCodigoRespuestaPagaTodo) {
		this.idSolicitud = idSolicitud;
		this.folioSolicitud = folioSolicitud;
		this.tutorDTO = new TutorDTO(idUsuarioLlaveCdmx, nombresBeneficiario, primerApellidoBeneficiario, segundoApellidoBeneficiario, curpTutor, idEstatusTutor, estatusTutor);
		this.beneficiarioDTO = new BeneficiarioDTO(idBeneficiario ,nombresBeneficiario, primerApellidoBeneficiario, segundoApellidoBeneficiario, nacionalidadBeneficiario, curpBeneficiario);
		this.nombreCompletoTutor = (nombreTutor + " " + apellidoPTutor + " " + (apellidoMTutor == null ? "" : apellidoMTutor)).toUpperCase();
		this.nombreCompletoBeneficiario = (nombresBeneficiario+" "+primerApellidoBeneficiario+" "+(segundoApellidoBeneficiario == null ? "" : segundoApellidoBeneficiario)).toUpperCase();
		this.catEstatusBeneficiarioDTO = new CatEstatusBeneficiarioDTO(idEstatusBeneficiario, descripcionBeneficiario);
		this.catCodigosRespuestaPagatodoDTO = new CatCodigosRespuestaPagatodoDTO(idCodigoRespuestaPagaTodo);
	}

	public SolicitudDTO(Long idSolicitud, int idEstatusSolicitud, String descripcionEstatusSolicitud) {
		this.idSolicitud = idSolicitud;
		this.catEstatusDTO = new CatEstatusDTO(idEstatusSolicitud, descripcionEstatusSolicitud );
	}
//Constructor utilizado para la consulta CrcBeneficiarioSolicitud.findByFolioSolicitud
	public SolicitudDTO(Long idSolicitud
			, String folioSolicitud
			, String nombre
			, Integer idNive
			, String descripcion
			, Integer idEstatusBeneficiario
			, String descripcionEstatusBeneficiario
			, String turno
			, String gradoEscolar
			, String cct
			, String colonia
			, String calle
			, Integer idMunicipio
			, String municipio
			, String codigoPostal
			, int idPatentesco
			, String descripcionParentesco) {
		this.idSolicitud = idSolicitud;
		this.folioSolicitud = folioSolicitud;
		this.nombre = nombre;
		this.catNivelEducativoDTO = idNive != null ? new CatNivelEducativoDTO(idNive, descripcion, null) : new CatNivelEducativoDTO();
		this.turno = turno;
		this.cct = cct;
		this.colonia = colonia; 
		this.calle = calle;
		this.catMunicipiosDTO = new CatMunicipiosDTO(idMunicipio, municipio);
		this.codigopostal = codigoPostal;
		this.gradoEscolar = gradoEscolar;
		this.catParentescoDTO =  new CatParentescoDTO(idPatentesco, descripcionParentesco);
		this.catEstatusBeneficiarioDTO = new CatEstatusBeneficiarioDTO(idEstatusBeneficiario, descripcionEstatusBeneficiario, null);
	}
	
	//findByIdTutor
	public SolicitudDTO(Long idCodigoRespuestaPagaTodo, Long idUsuarioLlaveCdmx ) {
		this.catCodigosRespuestaPagatodoDTO = new CatCodigosRespuestaPagatodoDTO(idCodigoRespuestaPagaTodo);
		this.tutorDTO = new TutorDTO(idUsuarioLlaveCdmx);
	}
	
	public SolicitudDTO(Long idSolicitud, TutorDTO tutorDTO ) {
		this.idSolicitud = idSolicitud;
		this.tutorDTO = tutorDTO;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getFolioSolicitud() {
		return folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getRespuestaAutoridadEducativa() {
		return respuestaAutoridadEducativa;
	}

	public void setRespuestaAutoridadEducativa(String respuestaAutoridadEducativa) {
		this.respuestaAutoridadEducativa = respuestaAutoridadEducativa;
	}

	public Integer getIdUsuarioFidegar() {
		return idUsuarioFidegar;
	}

	public void setIdUsuarioFidegar(Integer idUsuarioFidegar) {
		this.idUsuarioFidegar = idUsuarioFidegar;
	}

	public String getCct() {
		return cct;
	}

	public void setCct(String cct) {
		this.cct = cct;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigopostal() {
		return codigopostal;
	}

	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public CatNivelEducativoDTO getCatNivelEducativoDTO() {
		return catNivelEducativoDTO;
	}

	public void setCatNivelEducativoDTO(CatNivelEducativoDTO catNivelEducativoDTO) {
		this.catNivelEducativoDTO = catNivelEducativoDTO;
	}

	public String getGradoEscolar() {
		return gradoEscolar;
	}

	public void setGradoEscolar(String gradoEscolar) {
		this.gradoEscolar = gradoEscolar;
	}

	public String getEstatusBeneficiario() {
		return estatusBeneficiario;
	}

	public void setEstatusBeneficiario(String estatusBeneficiario) {
		this.estatusBeneficiario = estatusBeneficiario;
	}

	public CatCodigosRespuestaPagatodoDTO getCatCodigosRespuestaPagatodoDTO() {
		return catCodigosRespuestaPagatodoDTO;
	}

	public void setCatCodigosRespuestaPagatodoDTO(CatCodigosRespuestaPagatodoDTO catCodigosRespuestaPagatodoDTO) {
		this.catCodigosRespuestaPagatodoDTO = catCodigosRespuestaPagatodoDTO;
	}

	public boolean isPagatodoEnvioExitoso() {
		return pagatodoEnvioExitoso;
	}

	public void setPagatodoEnvioExitoso(boolean pagatodoEnvioExitoso) {
		this.pagatodoEnvioExitoso = pagatodoEnvioExitoso;
	}

	public CatEstatusDTO getCatEstatusDTO() {
		return catEstatusDTO;
	}

	public void setCatEstatusDTO(CatEstatusDTO catEstatusDTO) {
		this.catEstatusDTO = catEstatusDTO;
	}

	public CatParentescoDTO getCatParentescoDTO() {
		return catParentescoDTO;
	}

	public void setCatParentescoDTO(CatParentescoDTO catParentescoDTO) {
		this.catParentescoDTO = catParentescoDTO;
	}

	public TutorDTO getTutorDTO() {
		return tutorDTO;
	}

	public void setTutorDTO(TutorDTO tutorDTO) {
		this.tutorDTO = tutorDTO;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public String getNombreCompletoTutor() {
		return nombreCompletoTutor;
	}

	public void setNombreCompletoTutor(String nombreCompletoTutor) {
		this.nombreCompletoTutor = nombreCompletoTutor;
	}

	public String getNombreCompletoBeneficiario() {
		return nombreCompletoBeneficiario;
	}

	public void setNombreCompletoBeneficiario(String nombreCompletoBeneficiario) {
		this.nombreCompletoBeneficiario = nombreCompletoBeneficiario;
	}

	public BeneficiarioDTO getBeneficiarioDTO() {
		return beneficiarioDTO;
	}

	public void setBeneficiarioDTO(BeneficiarioDTO beneficiarioDTO) {
		this.beneficiarioDTO = beneficiarioDTO;
	}

	public CatEstatusDTO getCatEstatusTutorDTO() {
		return catEstatusTutorDTO;
	}

	public void setCatEstatusTutorDTO(CatEstatusDTO catEstatusTutorDTO) {
		this.catEstatusTutorDTO = catEstatusTutorDTO;
	}

	public CatMunicipiosDTO getCatMunicipiosDTO() {
		return catMunicipiosDTO;
	}

	public void setCatMunicipiosDTO(CatMunicipiosDTO catMunicipiosDTO) {
		this.catMunicipiosDTO = catMunicipiosDTO;
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

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public CatEstatusBeneficiarioDTO getCatEstatusBeneficiarioDTO() {
		return catEstatusBeneficiarioDTO;
	}

	public void setCatEstatusBeneficiarioDTO(CatEstatusBeneficiarioDTO catEstatusBeneficiarioDTO) {
		this.catEstatusBeneficiarioDTO = catEstatusBeneficiarioDTO;
	}

	public Boolean getEsNuevoRegistro() {
		return esNuevoRegistro;
	}

	public void setEsNuevoRegistro(Boolean esNuevoRegistro) {
		this.esNuevoRegistro = esNuevoRegistro;
	}

	public Boolean getExterno() {
		return externo;
	}

	public void setExterno(Boolean externo) {
		this.externo = externo;
	}

	public Date getPagatodoFechaEnvio() {
		return pagatodoFechaEnvio;
	}

	public void setPagatodoFechaEnvio(Date pagatodoFechaEnvio) {
		this.pagatodoFechaEnvio = pagatodoFechaEnvio;
	}
	
	
	
	

}
