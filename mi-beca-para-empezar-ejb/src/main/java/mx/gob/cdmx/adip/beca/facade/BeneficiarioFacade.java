package mx.gob.cdmx.adip.beca.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.EncuestaDAO;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Stateless
@LocalBean
public class BeneficiarioFacade {
	
	@Inject
	private BeneficiarioDAO beneficiarioDAO;
	
	@Inject
	private SolicitudDAO solicitudDAO;
	
	@Inject
	private CrcBeneficiarioSolicitudDAO crcBeneficiarioSolicitudDAO;
	
	@Inject 
	private EncuestaDAO encuestaDAO;
	
	@Inject 
	private TutorDAO tutorDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrNuevoBeneficiario(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		beneficiarioDAO.guardar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO());
		solicitudDAO.guardar(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		solicitudDAO.actualizarFolio(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setIdBeneficiario(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getIdBeneficiario());
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setIdSolicitud(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getIdSolicitud());
		crcBeneficiarioSolicitudDAO.guardar(crcBeneficiarioSolicitudDTO);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registraEncuesta(CrcBeneficiarioSolicitudDTO solicitud) {
		encuestaDAO.guardar(solicitud.getEncuestaDTO());
		//si el estatus del tutor es el primer beneficiario debemos actualizar el estatus del tutor para que sea mostrado en bandeja fidegar
		if (solicitud.getSolicitudDTO().getTutorDTO().getCatEstatusDTO().getIdEstatus()==Constantes.ID_ESTATUS_EN_PROCESO) {
			solicitud.getSolicitudDTO().getTutorDTO().getCatEstatusDTO().setIdEstatus(Constantes.ID_ESTATUS_PENDIENTE_VALIDACION);
	        tutorDAO.actualizaEstatusTutor(solicitud.getSolicitudDTO().getTutorDTO());
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizaBeneficiario(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		beneficiarioDAO.actualizar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO());
		solicitudDAO.actualizarParentescoSolicitud(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
	}
}
