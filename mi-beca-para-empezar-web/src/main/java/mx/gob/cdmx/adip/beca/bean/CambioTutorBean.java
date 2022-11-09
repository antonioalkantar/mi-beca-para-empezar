package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.client.ObtenMasReasignarTutor;
import mx.gob.cdmx.adip.beca.client.ObtenMasRegistrarTutor;
import mx.gob.cdmx.adip.beca.client.ObtenMasToken;
import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;
import mx.gob.cdmx.adip.beca.facade.SolicitudFacade;
import mx.gob.cdmx.adip.beca.util.WebResources;

@Named
@SessionScoped
public class CambioTutorBean implements Serializable{

	private static final long serialVersionUID = -6631539288680860723L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CambioTutorBean.class);
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject
	private SolicitudDAO solicitudDAO;
	
	@Inject
	private SolicitudFacade solicitudFacade;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private ObtenMasReasignarTutor obtenMasReasignarTutor;	
	
	@Inject
	private ObtenMasToken obtenMasToken;
	
	private SolicitudDTO solicitud;
	private TutorDTO nuevoTutor;
	private String nombreNuevoTutor;
	private String aPaternoNuevoTutor;
	private String aMaternoNuevoTutor;
	private String curp;
	private String msjObs;
//	private String observaciones;
	private boolean busquedaCorrecta;
	private Boolean habilitaCampos;
	private Boolean habilitaNombre;
	private Boolean habilitaCurp;
	private Boolean sinResultados;
	private List<TutorDTO> lstTutores;
	private UploadedFile file;
	private StreamedContent scFile;
	private BitacoraCambiosTutorDTO bitacoraCambiosTutorDTO;
	
	private List<SolicitudDTO> lstSolicitudesNuevoT;

	private String mensaje;
	private Boolean envioExitoso = false;
	
	
	public String init(Long idSolicitud) {
		solicitud = new SolicitudDTO();
		bitacoraCambiosTutorDTO = new BitacoraCambiosTutorDTO();
		busquedaCorrecta = false;
		habilitaCampos = null;
		habilitaNombre = false;
		habilitaCurp = false;
		lstTutores = new ArrayList<TutorDTO>();
		curp = Constantes.EMPTY_STRING;
		nombreNuevoTutor = Constantes.EMPTY_STRING;
		aPaternoNuevoTutor = Constantes.EMPTY_STRING;
		aMaternoNuevoTutor = Constantes.EMPTY_STRING;
//		observaciones = Constantes.EMPTY_STRING;
		solicitud = solicitudDAO.buscarPorId(idSolicitud);	
		sinResultados = null;
		return Constantes.RETURN_CAMBIAR_TUTOR + Constantes.JSF_REDIRECT;
	}
	
	public void buscarNuevoTutor() {
		if(habilitaNombre && nombreNuevoTutor.length() > 0 && aPaternoNuevoTutor.length() > 0) {
			lstTutores = tutorDAO.buscarPorNombreCompleto(nombreNuevoTutor, aPaternoNuevoTutor, aMaternoNuevoTutor);
			if (lstTutores != null && lstTutores.size() >= 0) { 
				busquedaCorrecta = true;
				sinResultados = false;
			}
			else {
				busquedaCorrecta = false;
				sinResultados = true;
				lstTutores = new ArrayList<TutorDTO>();
			}
		} else if (habilitaCurp && curp.length() > 0) {
			nuevoTutor = tutorDAO.buscarPorCurp(curp);
			if (nuevoTutor != null) {
				if(nuevoTutor.getIdUsuarioLlaveCdmx().equals(solicitud.getTutorDTO().getIdUsuarioLlaveCdmx())) {
					init(solicitud.getIdSolicitud());
				} else {
					busquedaCorrecta = true;
					sinResultados = false;
					lstTutores.add(nuevoTutor);
				}
			} else  {
				lstTutores = new ArrayList<TutorDTO>();
				busquedaCorrecta = false;
				sinResultados = true;
			}
		}
	}

	public void habilitaCamposBusqueda(){
		
		if (habilitaCampos) {
			habilitaNombre = true;
			habilitaCurp = false;
		} else {
			habilitaNombre = false;
			habilitaCurp = true;			
		}
		nombreNuevoTutor = Constantes.EMPTY_STRING;
		aPaternoNuevoTutor = Constantes.EMPTY_STRING;
		aMaternoNuevoTutor = Constantes.EMPTY_STRING;
		curp = Constantes.EMPTY_STRING;
	}
	
	public String guardar() {
		
		LOGGER.info("Observaciones: " + bitacoraCambiosTutorDTO.getObservaciones());
		msjObs = null;
		if (bitacoraCambiosTutorDTO.getObservaciones() == null || bitacoraCambiosTutorDTO.getObservaciones().isEmpty()) {
			msjObs = "Agregue un motivo para asignar el cambio de tutor";
			LOGGER.info("Observaciones nulas: " + bitacoraCambiosTutorDTO.getObservaciones());
			return null;
		}

		// Revisar tutor esta dado de alta  		
		lstSolicitudesNuevoT = new ArrayList<SolicitudDTO>();		
		lstSolicitudesNuevoT = solicitudDAO.buscarPorIdTutor(nuevoTutor.getIdUsuarioLlaveCdmx());
		
		
		//Valida el limite de Beneficiarios para el tutor nuevo
		if (lstSolicitudesNuevoT.size() >= 3 && !nuevoTutor.isEsMayorTresBeneficiarios()) {
			LOGGER.info("El tutor nuevo tiene " + lstSolicitudesNuevoT.size() + " beneficiarios");
			WebResources.validationMessage("msj_validation_tutor_mas_tres_beneficiarios", false);	
			PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
			PrimeFaces.current().scrollTo("messages");
			return null;
		}		
				
		// Consulta en mi beca  si el tutor nuevo  esta dado de alta en pagatodo //
		if (!lstSolicitudesNuevoT.isEmpty()){
			for (SolicitudDTO solicitud:lstSolicitudesNuevoT ) {			
				if (solicitud.getCatCodigosRespuestaPagatodoDTO().getIdCodigoPagatodo() == 0) {
					envioExitoso = true;
				}			
			}
		// Consulta si solo esta dado de alta el Tutor nuevo, sin beneficiarios asignados
		}else if (nuevoTutor.isEnviadoAPagatodo() == true) {
			envioExitoso = true;
		}
			
		if (envioExitoso) {			
			//Cambio de tutor		
	        RespuestaDTO respuesta = obtenMasReasignarTutor.reasignarTutor(obtenMasToken.obtenerToken(), nuevoTutor.getCurp(), solicitud.getBeneficiarioDTO().getCurpBeneficiario());			
	        if (respuesta.getCode() == 0) {	        
				try {
					solicitudFacade.registrarCambioTutor(bitacoraCambiosTutorDTO, nuevoTutor, solicitud, authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
					PrimeFaces.current().executeScript("PF('mdlAsignacionTutorGreen').show()");
					PrimeFaces.current().executeScript("rcBandejaTutor()");
					PrimeFaces.current().scrollTo("messages");
					 // actualizar todos los registros a envio true
					 //solicitudDAO.actualizaObtenMasRegistrarTutor(nuevoTutor.getIdUsuarioLlaveCdmx());
					//init(solicitud.getIdSolicitud());
				} catch (Exception ex) {
					LOGGER.error("ERROR AL GUARDAR CAMBIO DE TUTOR: ", ex);
					WebResources.validationMessage("msj_validation_mi_beca", false);
					PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
					PrimeFaces.current().scrollTo("messages");
					init(solicitud.getIdSolicitud());
					return null;
				}				
	        }else if(respuesta.getCode() == 1) {
	        	WebResources.validationMessage("msj_validation_cambio_tutor_curp", false);
				PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
				PrimeFaces.current().scrollTo("messages");
				init(solicitud.getIdSolicitud());
				return null;	        	
	        }else if (respuesta.getCode() == 2) {
	        	WebResources.validationMessage("msj_validation_cambio_tutor_curp_beneficiario", false);
				PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
				PrimeFaces.current().scrollTo("messages");
				init(solicitud.getIdSolicitud());
				return null;	        	
	        }
		}else {
			WebResources.validationMessage("msj_validation_no_pagatodo", false);
			PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
			PrimeFaces.current().scrollTo("messages");
			init(solicitud.getIdSolicitud());
			return null;
		}		
		
//		if (lstSolicitudesNuevoT.isEmpty() || !envioExitoso) {						
//			// Registrar Tutor Nuevo			
//			RespuestaDTO rest = obtenMasRegistrarTutor.registrarSoloTutor(obtenMasToken.obtenerToken(), nuevoTutor);
//			if (rest.getCode() == 0) {
//				envioExitoso = true;				 
//			}else {
//				envioExitoso = false;
//				WebResources.validationMessage("msj_validation_tutor_no_registro_paga_todo", false);	
//				PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
//				PrimeFaces.current().scrollTo("messages");
//				init(solicitud.getIdSolicitud());
//				return null;				
//			}							
//		}		
		
		return null;
		//return bandejaFuncionarioBean.init() + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Método auxiliar que realiza la carga del archivo seleccionado.
	 * 
	 * @param event
	 */
	public void agregaDocumento(FileUploadEvent event) {
		file = event.getFile();
	}

	/**
	 * Método auxiliar que asigna el documento adjunto para la identificación
	 * oficial
	 */
	public void asignarSoporteDocumental() {
		
			if (file != null) {
				if (file.getContent().length <= Constantes.VALOR_04_MB) {
					StringBuilder path = new StringBuilder();
					path.append(Environment.getPathDocumentos());
					path.append(nuevoTutor.getIdUsuarioLlaveCdmx());
					path.append(Constantes.SEPARADOR_RUTA);
					path.append(Constantes.RUTA_CAMBIO_TUTOR);
					path.append(Constantes.SEPARADOR_RUTA);
					path.append(file.getFileName());
					bitacoraCambiosTutorDTO.setRutaDocto(path.toString());
					bitacoraCambiosTutorDTO.setContentFileSoporteDoc(file.getContent());
				} else {
					WebResources.addErrorMessage("msj_documento_oversize", "form:uploadIdenOf", false);
				}
			} else {
				WebResources.addErrorMessage("msg_error_carga_archivo", "form:uploadIdenOf", false);
			}
		file = null;
	}

	public boolean isBusquedaCorrecta() {
		return busquedaCorrecta;
	}

	public void setBusquedaCorrecta(boolean busquedaCorrecta) {
		this.busquedaCorrecta = busquedaCorrecta;
	}

	public TutorDTO getNuevoTutor() {
		return nuevoTutor;
	}

	public void setNuevoTutor(TutorDTO nuevoTutor) {
		this.nuevoTutor = nuevoTutor;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombreNuevoTutor() {
		return nombreNuevoTutor;
	}

	public void setNombreNuevoTutor(String nombreNuevoTutor) {
		this.nombreNuevoTutor = nombreNuevoTutor;
	}

	public SolicitudDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}

	public Boolean getSinResultados() {
		return sinResultados;
	}

	public void setSinResultados(Boolean sinResultados) {
		this.sinResultados = sinResultados;
	}

	public String getaPaternoNuevoTutor() {
		return aPaternoNuevoTutor;
	}

	public void setaPaternoNuevoTutor(String aPaternoNuevoTutor) {
		this.aPaternoNuevoTutor = aPaternoNuevoTutor;
	}

	public String getaMaternoNuevoTutor() {
		return aMaternoNuevoTutor;
	}

	public void setaMaternoNuevoTutor(String aMaternoNuevoTutor) {
		this.aMaternoNuevoTutor = aMaternoNuevoTutor;
	}

	public Boolean getHabilitaNombre() {
		return habilitaNombre;
	}

	public void setHabilitaNombre(Boolean habilitaNombre) {
		this.habilitaNombre = habilitaNombre;
	}

	public Boolean getHabilitaCampos() {
		return habilitaCampos;
	}

	public void setHabilitaCampos(Boolean habilitaCampos) {
		this.habilitaCampos = habilitaCampos;
	}

	public Boolean getHabilitaCurp() {
		return habilitaCurp;
	}

	public void setHabilitaCurp(Boolean habilitaCurp) {
		this.habilitaCurp = habilitaCurp;
	}

	public List<TutorDTO> getLstTutores() {
		return lstTutores;
	}

	public void setLstTutores(List<TutorDTO> lstTutores) {
		this.lstTutores = lstTutores;
	}

	public StreamedContent getScFile() {
		return scFile;
	}

	public void setScFile(StreamedContent scFile) {
		this.scFile = scFile;
	}

	public BitacoraCambiosTutorDTO getBitacoraCambiosTutorDTO() {
		return bitacoraCambiosTutorDTO;
	}

	public void setBitacoraCambiosTutorDTO(BitacoraCambiosTutorDTO bitacoraCambiosTutorDTO) {
		this.bitacoraCambiosTutorDTO = bitacoraCambiosTutorDTO;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMsjObs() {
		return msjObs;
	}

	public void setMsjObs(String msjObs) {
		this.msjObs = msjObs;
	}

}
