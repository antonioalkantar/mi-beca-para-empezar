package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BitacoraCambiosTutorDAO;

@Named
@SessionScoped
public class InformacionSolicitudBean implements Serializable {
	
	private static final long serialVersionUID = -4149047742302747828L;
	private static final Logger LOGGER = LoggerFactory.getLogger(InformacionSolicitudBean.class);
	
	@Inject
	private BitacoraCambiosTutorDAO bitacoraCambiosTutorDAO;
	
	private List<BitacoraCambiosTutorDTO> lstBitacora;
	private String titulo;
	private String vista;
	private Boolean isTutor;
	private Boolean isBeneficiario;
	private Boolean isEncuesta;
	private Boolean isTarjeta;
	private Boolean isHistorial;
	
	public void mostrarInformacionTutor() {	
		titulo = Constantes.RETURN_SOLICITUD_TITULO_TUTOR;
		setIsTutor(true);
		setIsBeneficiario(false);
		setIsEncuesta(false);
		setIsTarjeta(false);
		setIsHistorial(false);
		vista = Constantes.RETURN_SOLICITUD_TUTOR;
	}
	
	public void mostrarInformacionBeneficiario() {	
		titulo = Constantes.RETURN_SOLICITUD_TITULO_BENEFICIARIO;
		setIsTutor(false);
		setIsBeneficiario(true);
		setIsEncuesta(false);
		setIsTarjeta(false);
		setIsHistorial(false);
		vista = Constantes.RETURN_SOLICITUD_BENEFICIARIO;
	}
	
	public void mostrarInformacionEncuesta() {	
		titulo = Constantes.RETURN_SOLICITUD_TITULO_ENCUESTA;
		setIsTutor(false);
		setIsBeneficiario(false);
		setIsEncuesta(true);
		setIsTarjeta(false);
		setIsHistorial(false);
		vista = Constantes.RETURN_SOLICITUD_ENCUESTA;
	}
	
	public void mostrarInformacionTarjeta() {	
		titulo = Constantes.RETURN_SOLICITUD_TITULO_TARJETA;
		setIsTutor(false);
		setIsBeneficiario(false);
		setIsEncuesta(false);
		setIsTarjeta(true);
		setIsHistorial(false);
		vista = Constantes.RETURN_SOLICITUD_TARJETA;
	}
	
	//este metodo debe recibir el id de la solicitud
	public void mostrarInformacionHistorial() {	
		titulo = Constantes.RETURN_SOLICITUD_TITULO_HISTORIAL;
		setIsTutor(false);
		setIsBeneficiario(false);
		setIsEncuesta(false);
		setIsTarjeta(false);
		setIsHistorial(true);
		//remover el id ejemplo
		Long id = (long) 16;
		lstBitacora = bitacoraCambiosTutorDAO.buscarPorIdSolicitud(id);
		vista = Constantes.RETURN_SOLICITUD_HISTORIAL;
	}
	
	public void siguiente() {
        switch (titulo) {
        case Constantes.RETURN_SOLICITUD_TITULO_TUTOR:
        	mostrarInformacionBeneficiario();
            break;
        case Constantes.RETURN_SOLICITUD_TITULO_BENEFICIARIO:
        	mostrarInformacionEncuesta();
            break;
        case Constantes.RETURN_SOLICITUD_TITULO_ENCUESTA:
        	mostrarInformacionTarjeta();
            break;
        case Constantes.RETURN_SOLICITUD_TITULO_TARJETA:
        	mostrarInformacionHistorial();;
            break;
        default:
            break;
        }
    }

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Boolean getIsTutor() {
		return isTutor;
	}

	public void setIsTutor(Boolean isTutor) {
		this.isTutor = isTutor;
	}

	public Boolean getIsBeneficiario() {
		return isBeneficiario;
	}

	public void setIsBeneficiario(Boolean isBeneficiario) {
		this.isBeneficiario = isBeneficiario;
	}

	public Boolean getIsEncuesta() {
		return isEncuesta;
	}

	public void setIsEncuesta(Boolean isEncuesta) {
		this.isEncuesta = isEncuesta;
	}

	public Boolean getIsTarjeta() {
		return isTarjeta;
	}

	public void setIsTarjeta(Boolean isTarjeta) {
		this.isTarjeta = isTarjeta;
	}

	public Boolean getIsHistorial() {
		return isHistorial;
	}

	public void setIsHistorial(Boolean isHistorial) {
		this.isHistorial = isHistorial;
	}

	public List<BitacoraCambiosTutorDTO> getLstBitacora() {
		return lstBitacora;
	}

	public void setLstBitacora(List<BitacoraCambiosTutorDTO> lstBitacora) {
		this.lstBitacora = lstBitacora;
	}

}
