package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Named
@SessionScoped
public class BandejaTutorBean implements Serializable {

	private static final long serialVersionUID = 9069648913442118340L;

	private TutorDTO tutorDTO;
	private List<CrcBeneficiarioSolicitudDTO> lstCrcBeneficiarioSolicitud; 
	
	private int ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA = Constantes.ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA;
	private int ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO = Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO;
	private int ID_ESTATUS_SUSPENDIDAS = Constantes.ID_ESTATUS_SUSPENDIDAS;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	@Inject
	private TutorDAO tutorDAO;
	@Inject
	private CrcBeneficiarioSolicitudDAO crcSolicitudDAO;
	@Inject
	private RegistroBeneficiarioBean registroBeneficiarioBean;

	public BandejaTutorBean() {
	}

	/**
	 * Método que inicializa la bandeja del tutor
	 * 
	 * @return url a la que se va a direccionar
	 */
	public String init() {
		consultaTutor();
		return Constantes.RETURN_BANDEJA_SOLICITUD + Constantes.JSF_REDIRECT;
	}

	public void consultaTutor() {
		tutorDTO = tutorDAO.buscarPorId(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		if (tutorDTO == null) {
			tutorDTO = new TutorDTO();
			tutorDTO.setNombre(authenticatorBean.getUsuarioLogueado().getNombre());
			tutorDTO.setPrimerApellido(authenticatorBean.getUsuarioLogueado().getPrimerApellido());
			tutorDTO.setSegundoApellido(authenticatorBean.getUsuarioLogueado().getSegundoApellido());
			tutorDTO.setCurp(authenticatorBean.getUsuarioLogueado().getCurp());
			tutorDTO.setCorreo(authenticatorBean.getUsuarioLogueado().getCorreo());
			tutorDTO.setTelefono(authenticatorBean.getUsuarioLogueado().getTelefono());
			tutorDTO.setNacionalidad(authenticatorBean.getEsExtranjero() != null && authenticatorBean.getEsExtranjero() == true ? Constantes.NACIONALIDAD_EXTRANJERA : Constantes.NACIONALIDAD_MEXICANA);
			tutorDTO.setEsExtranjero(authenticatorBean.getEsExtranjero() != null ? authenticatorBean.getEsExtranjero() : false);
			tutorDTO.setLada(authenticatorBean.getUsuarioLogueado().getLada());
			return;
		}
		
		lstCrcBeneficiarioSolicitud =  crcSolicitudDAO.consultaSolicitudesByIdUsuarioLlave(tutorDTO.getIdUsuarioLlaveCdmx());

	}

	public String consultaBeneficiario(Long idSolicitud, Integer idEncuesta) {
//		if (idEncuesta == 0) 
			return registroBeneficiarioBean.consulta(idSolicitud);
//		else
//			return tarjetaBeneficiarioBean.init(idSolicitud);
	}
	
	public String consultaRevalidacion(Long idSolicitud, Integer idEncuesta) {
		 
		return registroBeneficiarioBean.consultaRevalidacion(idSolicitud);
	
}
	
	public TutorDTO getTutorDTO() {
		return tutorDTO;
	}

	public void setTutorDTO(TutorDTO tutorDTO) {
		this.tutorDTO = tutorDTO;
	}

	public List<CrcBeneficiarioSolicitudDTO> getLstCrcBeneficiarioSolicitud() {
		return lstCrcBeneficiarioSolicitud;
	}

	public void setLstCrcBeneficiarioSolicitud(List<CrcBeneficiarioSolicitudDTO> lstCrcBeneficiarioSolicitud) {
		this.lstCrcBeneficiarioSolicitud = lstCrcBeneficiarioSolicitud;
	}

	public int getID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA() {
		return ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA;
	}
	
	public int getID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO() {
		return ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO;
	}
	
	public int getID_ESTATUS_SUSPENDIDAS() {
			return ID_ESTATUS_SUSPENDIDAS;
		}
}
