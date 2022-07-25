package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.client.ObtenMasReasignarTutor;
import mx.gob.cdmx.adip.beca.client.ObtenMasToken;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;
import mx.gob.cdmx.adip.beca.facade.SolicitudFacade;

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
	private BandejaFuncionarioBean bandejaFuncionarioBean;
	
	private SolicitudDTO solicitud;
	private TutorDTO nuevoTutor;
	private String nombreNuevoTutor;
	private String aPaternoNuevoTutor;
	private String aMaternoNuevoTutor;
	private String curp;
	private String observaciones;
	private boolean busquedaCorrecta;
	private Boolean habilitaCampos;
	private Boolean habilitaNombre;
	private Boolean habilitaCurp;
	private Boolean sinResultados;
	private List<TutorDTO> lstTutores;
	
	public String init(Long idSolicitud) {
		busquedaCorrecta = false;
		habilitaCampos = null;
		habilitaNombre = false;
		habilitaCurp = false;
		lstTutores = new ArrayList<TutorDTO>();
		curp = Constantes.EMPTY_STRING;
		nombreNuevoTutor = Constantes.EMPTY_STRING;
		aPaternoNuevoTutor = Constantes.EMPTY_STRING;
		aMaternoNuevoTutor = Constantes.EMPTY_STRING;
		observaciones = Constantes.EMPTY_STRING;
		solicitud = solicitudDAO.buscarPorId(idSolicitud);	
		sinResultados = null;
		return Constantes.RETURN_CAMBIAR_TUTOR + Constantes.JSF_REDIRECT;
	}
	
	public void buscarNuevoTutor() {
		if(habilitaNombre && nombreNuevoTutor.length() > 0 && aPaternoNuevoTutor.length() > 0) {
			lstTutores = tutorDAO.buscarPorNombreCompleto(nombreNuevoTutor, aPaternoNuevoTutor, aMaternoNuevoTutor);
			LOGGER.info("lstTutores " + lstTutores.size());
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

		if (nuevoTutor.getIdUsuarioLlaveCdmx().equals(solicitud.getTutorDTO().getIdUsuarioLlaveCdmx())) {
			init(solicitud.getIdSolicitud());
		}
		
		solicitudFacade.registrarCambioTutor(nuevoTutor, solicitud, authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx(), observaciones);
		PrimeFaces.current().executeScript("PF('mdlAsignacionTutor').hide()");
		init(solicitud.getIdSolicitud());

		return bandejaFuncionarioBean.init() + Constantes.JSF_REDIRECT;
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

}
