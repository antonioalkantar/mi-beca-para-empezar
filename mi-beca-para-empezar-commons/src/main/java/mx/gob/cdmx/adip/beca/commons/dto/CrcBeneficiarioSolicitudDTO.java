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
			String primerApellidoBeneficiario, String segundoApellidoBeneficiario, Long idSolicitud, String folioSolicitud, int idEncuesta,
			Integer idEstatusBeneficiario, String descripcionEstatusBeneficiario)
 {
		this.idBenefSolic = idBenefSolic;
		this.beneficiarioDTO = new BeneficiarioDTO(idBeneficiario, nombresBeneficiario, primerApellidoBeneficiario,
				segundoApellidoBeneficiario);
		this.solicitudDTO = new SolicitudDTO(idSolicitud, folioSolicitud, idEstatusBeneficiario, descripcionEstatusBeneficiario);
		this.encuestaDTO = new EncuestaDTO(idEncuesta);
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
			, String gradoEscolar, String cct, String colonia, String calle, String alcaldia, String codigoPostal
			, Integer idEstatusBeneficiario, String descripcionEstatusBeneficiario
			, int idParentesco
			, String descripcionParentesco
			) {
		this.idBenefSolic = idBenefSolic;
		this.beneficiarioDTO = new BeneficiarioDTO(idBeneficiario, curpBeneficiario, nombresBeneficiario, primerApellidoBeneficiario,
				segundoApellidoBeneficiario, nacionalidad, fechaNacimientoBeneficiario, esTutor);
		this.solicitudDTO = new SolicitudDTO(idSolicitud, folioSolicitud, nombre, idNive, descripcion, idEstatusBeneficiario, descripcionEstatusBeneficiario, turno, gradoEscolar,  cct,  colonia,  calle,  alcaldia, codigoPostal, idParentesco, descripcionParentesco);
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
