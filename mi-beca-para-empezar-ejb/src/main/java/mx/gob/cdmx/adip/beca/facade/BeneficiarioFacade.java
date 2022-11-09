package mx.gob.cdmx.adip.beca.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import mx.gob.cdmx.adip.beca.client.ObtenMasRegistrarTutor;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.DetCuentaBeneficiarioDAO;
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
		
	@Inject
	private DetCuentaBeneficiarioDAO detCuentaBeneficiarioDAO;

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObtenMasRegistrarTutor.class);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrNuevoBeneficiario(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {		
		
		beneficiarioDAO.guardar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO());
		solicitudDAO.guardar(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		solicitudDAO.actualizarFolio(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setIdBeneficiario(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getIdBeneficiario());
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setIdSolicitud(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getIdSolicitud());
		crcBeneficiarioSolicitudDAO.guardar(crcBeneficiarioSolicitudDTO);
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO().getBeneficiarioDTO().setIdBeneficiario(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getIdBeneficiario()); 
		detCuentaBeneficiarioDAO.guardar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO());
		
		TutorDTO tutorEnviadoPagatodo = tutorDAO.buscarPorId(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getIdUsuarioLlaveCdmx());
		 if (tutorEnviadoPagatodo.isEnviadoAPagatodo() == false) {		
			 crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setFechaEnvioAPagatodo(new Date());
			 crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setEnviadoAPagatodo(true);
			tutorDAO.actualizaEnviadoAPagatodo(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO());
		}
		
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrBeneficiarioCompleto(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {

		beneficiarioDAO.guardar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO());
		solicitudDAO.guardar(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		solicitudDAO.actualizarFolio(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setIdBeneficiario(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getIdBeneficiario());
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setIdSolicitud(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getIdSolicitud());
		crcBeneficiarioSolicitudDAO.guardar(crcBeneficiarioSolicitudDTO);
		crcBeneficiarioSolicitudDTO.getEncuestaDTO().setSolicitudDTO(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		encuestaDAO.guardar(crcBeneficiarioSolicitudDTO.getEncuestaDTO());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO().getBeneficiarioDTO().setIdBeneficiario(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getIdBeneficiario()); 
		detCuentaBeneficiarioDAO.guardar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO());
		//si el estatus del tutor es el primer beneficiario debemos actualizar el estatus del tutor para que sea mostrado en bandeja fidegar
		if (crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCatEstatusDTO().getIdEstatus()==Constantes.ID_ESTATUS_EN_PROCESO) {
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCatEstatusDTO().setIdEstatus(Constantes.ID_ESTATUS_PENDIENTE_VALIDACION);
	        tutorDAO.actualizaEstatusTutor(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO());
		}
		TutorDTO tutorEnviadoPagatodo = tutorDAO.buscarPorId(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getIdUsuarioLlaveCdmx());
		 if (tutorEnviadoPagatodo.isEnviadoAPagatodo() == false) {		
			 crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setFechaEnvioAPagatodo(new Date());
			 crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setEnviadoAPagatodo(true);
			tutorDAO.actualizaEnviadoAPagatodo(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO());
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizaEnvioPagatodo(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		
	}
	
}
