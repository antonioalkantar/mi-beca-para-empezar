package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;
import java.util.Date;

public class CrcBeneficiarioSolicitudDTO implements Serializable {

	private static final long serialVersionUID = 6058004010765301608L;
	private Long idBenefSolic;
	private BeneficiarioDTO beneficiarioDTO;
	private SolicitudDTO solicitudDTO;
	private EncuestaDTO encuestaDTO;

	public CrcBeneficiarioSolicitudDTO() {
		beneficiarioDTO = new BeneficiarioDTO();
		solicitudDTO = new SolicitudDTO();
		encuestaDTO = new EncuestaDTO();
	}
	
	public CrcBeneficiarioSolicitudDTO(Long idBenefSolic) {
		this.idBenefSolic = idBenefSolic;
	}
// DTO para CrcBeneficiarioSolicitud.findByIdLlave	
	public CrcBeneficiarioSolicitudDTO(Long idBenefSolic, Long idBeneficiario, String nombresBeneficiario,
			String primerApellidoBeneficiario, String segundoApellidoBeneficiario, Long idSolicitud, String folioSolicitud,
			Integer idEstatusBeneficiario, String descripcionEstatusBeneficiario) {
		this.idBenefSolic = idBenefSolic;
		this.beneficiarioDTO = new BeneficiarioDTO(idBeneficiario, nombresBeneficiario, primerApellidoBeneficiario,
				segundoApellidoBeneficiario);
		this.solicitudDTO = new SolicitudDTO(idSolicitud, folioSolicitud, idEstatusBeneficiario, descripcionEstatusBeneficiario);
	}
//DTO para CrcBeneficiarioSolicitud.findByFolioSolicitud
	public CrcBeneficiarioSolicitudDTO(
			  Long idBenefSolic
			, Long idBeneficiario
			, String curpBeneficiario
			, String nombresBeneficiario
			, String primerApellidoBeneficiario
			, String segundoApellidoBeneficiario
			, String nacionalidad
			, Date fechaNacimientoBeneficiario
			, Boolean esTutor
			, Long idSolicitud
			, String folioSolicitud
			, String nombre
			, Integer idNive
			, String descripcion
			, String turno
			, String gradoEscolar
			, String cct
			, String colonia
			, String calle
			, Integer idMunicipio
			, String municipio
			, String codigoPostal
			, Integer idEstatusBeneficiario, String descripcionEstatusBeneficiario
			, int idParentesco
			, String descripcionParentesco
			) {
		this.idBenefSolic = idBenefSolic;
		this.beneficiarioDTO = new BeneficiarioDTO(idBeneficiario, curpBeneficiario, nombresBeneficiario, primerApellidoBeneficiario,
				segundoApellidoBeneficiario, nacionalidad, fechaNacimientoBeneficiario, esTutor);
		this.solicitudDTO = new SolicitudDTO(idSolicitud, folioSolicitud, nombre, idNive, descripcion, idEstatusBeneficiario, descripcionEstatusBeneficiario, turno, gradoEscolar,  cct,  colonia,  calle,  idMunicipio, municipio, codigoPostal, idParentesco, descripcionParentesco);
	}
	
	// DTO para CrcBeneficiarioSolicitud.findByRechazadoPagatodo
	public CrcBeneficiarioSolicitudDTO(Long idBenefSolic, Long idBeneficiario, String curpBeneficiario,
			String nombresBeneficiario, String primerApellidoBeneficiario, String segundoApellidoBeneficiario,
			String nacionalidad, Date fechaNacimientoBeneficiario, Boolean esTutor, Long idSolicitud,
			Long idUsuarioLlaveCdmx, String nombre, String primerApellido, String segundoApellido, String curp,
			String fechaNacimiento, String correo, String telefono, boolean esExtranjero, String sexo, Long idCuentaBeneficiario, String numeroCuenta) {
		this.idBenefSolic = idBenefSolic;
		this.beneficiarioDTO = new BeneficiarioDTO(idBeneficiario, curpBeneficiario, nombresBeneficiario,
				primerApellidoBeneficiario, segundoApellidoBeneficiario, nacionalidad, fechaNacimientoBeneficiario,
				esTutor);
		this.solicitudDTO = new SolicitudDTO(idSolicitud, new TutorDTO(idUsuarioLlaveCdmx, nombre, primerApellido,
				segundoApellido, curp, telefono, correo, fechaNacimiento, sexo, esExtranjero));
		this.beneficiarioDTO.setDetCuentaBeneficiarioDTO( new DetCuentaBeneficiarioDTO(idCuentaBeneficiario, numeroCuenta));
	}

	public Long getIdBenefSolic() {
		return idBenefSolic;
	}

	public void setIdBenefSolic(Long idBenefSolic) {
		this.idBenefSolic = idBenefSolic;
	}

	public BeneficiarioDTO getBeneficiarioDTO() {
		return beneficiarioDTO;
	}

	public void setBeneficiarioDTO(BeneficiarioDTO beneficiarioDTO) {
		this.beneficiarioDTO = beneficiarioDTO;
	}

	public SolicitudDTO getSolicitudDTO() {
		return solicitudDTO;
	}

	public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
		this.solicitudDTO = solicitudDTO;
	}

	public EncuestaDTO getEncuestaDTO() {
		return encuestaDTO;
	}

	public void setEncuestaDTO(EncuestaDTO encuestaDTO) {
		this.encuestaDTO = encuestaDTO;
	}

}
