package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.util.WebResources;

@Named
@SessionScoped
public class RegistroCompletadoBean implements Serializable {

	private static final long serialVersionUID = 2467170835328759372L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroCompletadoBean.class);

	@Inject
	private AuthenticatorBean authenticatorBean;	

	@Inject
	private BandejaTutorBean bandejaTutorBean;

	@Inject
	private BandejaFuncionarioBean bandejaFuncionarioBean;

	private CrcBeneficiarioSolicitudDTO solicitud;
	private boolean muestraInformacion;

	/**
	 * Método que inicializa la pantalla de resultados de la solicitud del trámite.
	 * 
	 * @param tramite
	 */
	public String inicializar() {
		muestraInformacion =	authenticatorBean.getRolTutor() ? false :true;					
		return Constantes.RETURN_SOLICITUD_REGISTRO_COMPLETADO + Constantes.JSF_REDIRECT;
	}

	/**
	 * Metodo para redireccionar dependiendo del origen de la vista
	 */

	public void redirect() {
		try {

			if (authenticatorBean.getRolTutor()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect(bandejaTutorBean.init());
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect(bandejaFuncionarioBean.init());
			}
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			LOGGER.error(WebResources.getBundleMsg("msj_error_rerireccionar"), e);
		}
	}

	public CrcBeneficiarioSolicitudDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(CrcBeneficiarioSolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}

	public boolean ismuestraInformacion() {
		return muestraInformacion;
	}

	public void setMuestraInformacion(boolean muestraInformacion) {
		this.muestraInformacion = muestraInformacion;
	}	
	
}
