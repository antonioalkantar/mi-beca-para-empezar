package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.net.ProtocolException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BitacoraCambiosTutorDAO;
import mx.gob.cdmx.adip.beca.dao.BitacoraTutorDAO;
import mx.gob.cdmx.adip.beca.dao.CatEstatusDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Named
@SessionScoped
public class ConsultaBean implements Serializable {

	private static final long serialVersionUID = -4324012361015434687L;
	
	@Inject
	private RegistroTutorBean registroTutorBean;
	
	@Inject
	private RegistroBeneficiarioBean registroBeneficiarioBean;
	
	@Inject
	private EncuestaBeneficiarioBean encuestaBeneficiarioBean;
	
	@Inject
	private BitacoraCambiosTutorDAO bitacoraCambiosTutorDAO;
	
	@Inject
	private CatEstatusDAO catEstatusDAO;
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject 
	private AuthenticatorBean authenticatorBean;
	
	@Inject 
	private BitacoraTutorDAO bitacoraTutorDAO;
	
	private List<BitacoraCambiosTutorDTO> lstBitacora;
	private List<CatEstatusDTO> lstEstatus;
	private String titulo;
	private String vista;
	private String estiloSelectOneMenu;
	private String descripcionEstatus;
	private boolean infoTutor;
	private boolean infoBeneficiario;
	private boolean infoEncuesta;
	private boolean infoTarjeta;
	private boolean infoBitacora;
	private boolean consulta = false;
	
	@PostConstruct
	public void iniciarBandejaFuncionario() {
		actualizaSeccion(1);
	}	
	
	public String init(Long idUsuarioLaveCdmx, Long idSolicitud) throws ProtocolException {
		descripcionEstatus = "";
		consulta = true;
		estiloSelectOneMenu = "background-color: #fff !important;";
		registroTutorBean.consultaTutorDeshabilitado(idUsuarioLaveCdmx);
		registroBeneficiarioBean.consulta(idSolicitud);
		encuestaBeneficiarioBean.consulta(idSolicitud);
		cambiarEstatusSolicitud();
		lstBitacora = bitacoraCambiosTutorDAO.buscarPorIdSolicitud(idSolicitud);
		lstEstatus = catEstatusDAO.buscarEstatusTutor();
		return Constantes.RETURN_HOME_BACKOFFICE_PAGE_ADMIN_CONSULTA + Constantes.JSF_REDIRECT;
	}
	
	public void validacionVisible() {
		consulta = false;
	}
	
	public void seleccionaEstatus(int estatusModal) {
		if (estatusModal == 5) {
			descripcionEstatus = "Suspendida";
		}else {
			descripcionEstatus = "Aprobada";
		}
		PrimeFaces.current().executeScript("PF('dialogCambioEstatus').show();");
	}
	
	public void cambiarEstatusSolicitud() {

		switch(registroTutorBean.getTutorDTO().getCatEstatusDTO().getIdEstatus()) {
			case Constantes.ID_ESTATUS_PENDIENTE_VALIDACION :
				estiloSelectOneMenu = "background-color: #54B0BC !important; color: #fff !important; border-radius: 5px; text-align: center;";
			      break;
			case Constantes.ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO :
				estiloSelectOneMenu = "background-color: #6EC844 !important; color: #fff !important; border-radius: 5px; text-align: center;";
			      break;
			case Constantes.ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA :
				estiloSelectOneMenu = "background-color: #4479C8 !important; color: #fff !important; border-radius: 5px; text-align: center;";
			      break;
			case Constantes.ID_ESTATUS_SUSPENDIDAS :
				estiloSelectOneMenu = "background-color: #ED9511 !important; color: #fff !important; border-radius: 5px; text-align: center;";
			      break;
			case Constantes.ID_ESTATUS_APROBADA :
				estiloSelectOneMenu = "background-color: #6EC844 !important; color: #fff !important; border-radius: 5px; text-align: center;";
			      break;
			case Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO :
				estiloSelectOneMenu = "background-color: #ED9511 !important; color: #fff !important; border-radius: 5px; text-align: center;";
			      break;
		}
//		tutorDAO.actualizaEstatusTutor(registroTutorBean.getTutorDTO());
//		registroBeneficiarioBean.consulta(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO().getSolicitudDTO().getIdSolicitud());
	}
	
	
	public void actualizaEstatus() {
		tutorDAO.actualizaEstatusTutor(registroTutorBean.getTutorDTO());
		BitacoraTutorDTO bitacoraTutorDTO = new BitacoraTutorDTO();
		bitacoraTutorDTO.setTutorDTO(registroTutorBean.getTutorDTO());
		bitacoraTutorDTO.setIdUsuarioFidegar(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		bitacoraTutorDTO.setFechaCaptura(new Date());
		bitacoraTutorDTO.setCatEstatusDTO(registroTutorBean.getTutorDTO().getCatEstatusDTO());		
		bitacoraTutorDAO.guardar(bitacoraTutorDTO);	
		cambiarEstatusSolicitud();
		registroBeneficiarioBean.consulta(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO().getSolicitudDTO().getIdSolicitud());
	}
	
	public static String getDescripcionEstatusById(Integer id) {
		return CatEstatusDTO.getDescripcionEstatusById(id);
	}
	
	public void actualizaSeccion(int seccion) {
		switch(seccion) {
		case 1:
			infoTutor = true;
			infoBeneficiario = false;
			infoEncuesta = false;
			infoTarjeta = false;
			infoBitacora = false;
			titulo = Constantes.RETURN_SOLICITUD_TITULO_TUTOR;
			vista = Constantes.RETURN_SOLICITUD_TUTOR;
			//registroTutorBean.consultaTutorDeshabilitado(16562L);
			break;
		case 2:
			infoTutor = false;
			infoBeneficiario = true;
			infoEncuesta = false;
			infoTarjeta = false;
			infoBitacora = false;
			titulo = Constantes.RETURN_SOLICITUD_TITULO_BENEFICIARIO;
			vista = Constantes.RETURN_SOLICITUD_BENEFICIARIO;
			//registroBeneficiarioBean.consulta(34L);
			break;
		case 3:
			infoTutor = false;
			infoBeneficiario = false;
			infoEncuesta = true;
			infoTarjeta = false;
			infoBitacora = false;
			titulo = Constantes.RETURN_SOLICITUD_TITULO_ENCUESTA;
			vista = Constantes.RETURN_SOLICITUD_ENCUESTA;
			break;
		case 4:
			infoTutor = false;
			infoBeneficiario = false;
			infoEncuesta = false;
			infoTarjeta = true;
			infoBitacora = false;
			titulo = Constantes.RETURN_SOLICITUD_TITULO_TARJETA;
			vista = Constantes.RETURN_SOLICITUD_TARJETA;
			//tarjetaBeneficiarioBean.init(34L);
			break;
		case 5:
			infoTutor = false;
			infoBeneficiario = false;
			infoEncuesta = false;
			infoTarjeta = false;
			infoBitacora = true;
			titulo = Constantes.RETURN_SOLICITUD_TITULO_HISTORIAL;
			/*
			lstBitacora = bitacoraCambiosTutorDAO.buscarPorIdSolicitud(id);*/
			vista = Constantes.RETURN_SOLICITUD_HISTORIAL;
			//tarjetaBeneficiarioBean.init(34L);
			break;	
			
			
		}
	}
	
	public void siguiente() {
        switch (titulo) {
        case Constantes.RETURN_SOLICITUD_TITULO_TUTOR:
        	actualizaSeccion(2);
            break;
        case Constantes.RETURN_SOLICITUD_TITULO_BENEFICIARIO:
        	actualizaSeccion(3);
            break;
        case Constantes.RETURN_SOLICITUD_TITULO_ENCUESTA:
        	actualizaSeccion(5);//Se desabilito el 4 que redirige a tarjeta
            break;
        case Constantes.RETURN_SOLICITUD_TITULO_TARJETA:
        	actualizaSeccion(5);
            break;
        default:
            break;
        }
    }

	public boolean isInfoBeneficiario() {
		return infoBeneficiario;
	}

	public void setInfoBeneficiario(boolean infoBeneficiario) {
		this.infoBeneficiario = infoBeneficiario;
	}

	public boolean isInfoTutor() {
		return infoTutor;
	}

	public void setInfoTutor(boolean infoTutor) {
		this.infoTutor = infoTutor;
	}

	public boolean isInfoEncuesta() {
		return infoEncuesta;
	}

	public void setInfoEncuesta(boolean infoEncuesta) {
		this.infoEncuesta = infoEncuesta;
	}

	public boolean isInfoTarjeta() {
		return infoTarjeta;
	}

	public void setInfoTarjeta(boolean infoTarjeta) {
		this.infoTarjeta = infoTarjeta;
	}

	public boolean isInfoBitacora() {
		return infoBitacora;
	}

	public void setInfoBitacora(boolean infoBitacora) {
		this.infoBitacora = infoBitacora;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public List<BitacoraCambiosTutorDTO> getLstBitacora() {
		return lstBitacora;
	}

	public void setLstBitacora(List<BitacoraCambiosTutorDTO> lstBitacora) {
		this.lstBitacora = lstBitacora;
	}

	public boolean isConsulta() {
		return consulta;
	}

	public void setConsulta(boolean consulta) {
		this.consulta = consulta;
	}

	public List<CatEstatusDTO> getLstEstatus() {
		return lstEstatus;
	}

	public void setLstEstatus(List<CatEstatusDTO> lstEstatus) {
		this.lstEstatus = lstEstatus;
	}

	public String getEstiloSelectOneMenu() {
		return estiloSelectOneMenu;
	}

	public void setEstiloSelectOneMenu(String estiloSelectOneMenu) {
		this.estiloSelectOneMenu = estiloSelectOneMenu;
	}

	public String getDescripcionEstatus() {
		return descripcionEstatus;
	}

	public void setDescripcionEstatus(String descripcionEstatus) {
		this.descripcionEstatus = descripcionEstatus;
	}
}