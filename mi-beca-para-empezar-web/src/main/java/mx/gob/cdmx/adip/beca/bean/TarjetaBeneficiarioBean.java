package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;

@Named
@SessionScoped
public class TarjetaBeneficiarioBean implements Serializable {

	private static final long serialVersionUID = -4716145836201053229L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TarjetaBeneficiarioBean.class);

	@Inject
	CrcBeneficiarioSolicitudDAO crcBeneficiarioSolicitudDAO;
	
	private CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO;
	
	public String init(Long idSolicitud) {
		LOGGER.info("Folio solicitud "+idSolicitud);
		crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
		crcBeneficiarioSolicitudDTO = (crcBeneficiarioSolicitudDAO.consultaSolicitudesByFolioSolicitud(idSolicitud));
		return Constantes.RETURN_TARJETA_BENEFICIARIO + Constantes.JSF_REDIRECT;
	}

	public CrcBeneficiarioSolicitudDTO getCrcBeneficiarioSolicitudDTO() {
		return crcBeneficiarioSolicitudDTO;
	}

	public void setCrcBeneficiarioSolicitudDTO(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		this.crcBeneficiarioSolicitudDTO = crcBeneficiarioSolicitudDTO;
	}
}
