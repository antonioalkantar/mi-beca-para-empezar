package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.EncuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.EncuestaDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Named
@SessionScoped
public class BandejaTutorBean implements Serializable {

	private static final long serialVersionUID = 9069648913442118340L;
	
	@Inject
	private CrcBeneficiarioSolicitudDAO crcSolicitudDAO;
	
	@Inject
	private	EncuestaDAO encuestaDAO;
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject
	private RegistroBeneficiarioBean registroBeneficiarioBean;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	private List<CrcBeneficiarioSolicitudDTO> lstCrcBeneficiarioSolicitud; 
	private List<EncuestaDTO> lstEncuestaDTO;
	
	private TutorDTO tutorDTO;

	private int ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA = Constantes.ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA;
	private int ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO = Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO;
	private int ID_ESTATUS_SUSPENDIDAS = Constantes.ID_ESTATUS_SUSPENDIDAS;
	private Boolean habilitaEncuesta;
	private Boolean mostrarDialog;

	public BandejaTutorBean() {
	}

	/**
	 * Método que inicializa la bandeja del tutor
	 * 
	 * @return url a la que se va a direccionar
	 */
	public String init() {
		habilitaEncuesta = false;
		mostrarDialog = false;
		lstEncuestaDTO = new ArrayList<EncuestaDTO>();
		consultaTutor();
		return Constantes.RETURN_BANDEJA_SOLICITUD + Constantes.JSF_REDIRECT;
	}

	private void consultaTutor() {
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
			tutorDTO.setSexo(authenticatorBean.getUsuarioLogueado().getSexo());
			tutorDTO.setFechaNacimiento(authenticatorBean.getUsuarioLogueado().getFechaNacimiento());
			return;
		}
		
		lstCrcBeneficiarioSolicitud = new ArrayList<CrcBeneficiarioSolicitudDTO>();
			
		List<CrcBeneficiarioSolicitudDTO> lstDatos = crcSolicitudDAO.consultaSolicitudesByIdUsuarioLlave(tutorDTO.getIdUsuarioLlaveCdmx());
		for (CrcBeneficiarioSolicitudDTO datos:lstDatos) {
			lstEncuestaDTO.clear();
			lstEncuestaDTO = encuestaDAO.buscarTodosPorIdSOlicitud(datos.getSolicitudDTO().getIdSolicitud());
			if (lstEncuestaDTO == null || lstEncuestaDTO.size() == 0) {
				datos.setEncuestaDTO(new EncuestaDTO(0));
			} else {
				datos.setEncuestaDTO(new EncuestaDTO(lstEncuestaDTO.get(0).getIdEncuesta()));
				lstEncuestaDTO.clear();
				lstEncuestaDTO = encuestaDAO.buscarCicloPorIdSolicitud(datos.getSolicitudDTO().getIdSolicitud());
				if (lstEncuestaDTO == null || lstEncuestaDTO.size() == 0) {						
					registroBeneficiarioBean.consultaEncuestaNuevoCiclo(datos.getSolicitudDTO().getIdSolicitud());
					habilitaEncuesta = true;	
					mostrarDialog = true;
					return;
				}
			}
			lstCrcBeneficiarioSolicitud.add(datos);
		}
	}
	
	//Cambio de bandera
	public void deshabilitaDialog() {
		mostrarDialog = false;
	}

	public String consultaBeneficiario(Long idSolicitud, Integer idEncuesta) {
		return registroBeneficiarioBean.consulta(idSolicitud);
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

	public Boolean getHabilitaEncuesta() {
		return habilitaEncuesta;
	}

	public void setHabilitaEncuesta(Boolean habilitaEncuesta) {
		this.habilitaEncuesta = habilitaEncuesta;
	}

	public List<EncuestaDTO> getLstEncuestaDTO() {
		return lstEncuestaDTO;
	}

	public void setLstEncuestaDTO(List<EncuestaDTO> lstEncuestaDTO) {
		this.lstEncuestaDTO = lstEncuestaDTO;
	}

	public Boolean getMostrarDialog() {
		return mostrarDialog;
	}

	public void setMostrarDialog(Boolean mostrarDialog) {
		this.mostrarDialog = mostrarDialog;
	}	
}
