package mx.gob.cdmx.adip.beca.facade;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.dao.BitacoraCambiosTutorDAO;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Stateless
@LocalBean
public class SolicitudFacade {
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject
	private SolicitudDAO solicitudDAO;
	
	@Inject
	private BitacoraCambiosTutorDAO bitacoraCambiosTutorDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarCambioTutor(TutorDTO tutorNuevo, SolicitudDTO solicitud, Long idUsuario, String observaciones) {
		TutorDTO tutorAnterior = solicitud.getTutorDTO();
		BitacoraCambiosTutorDTO bitacora = new BitacoraCambiosTutorDTO();
		bitacora.setTutorByIdTutorAnteriorDTO(tutorAnterior);
		bitacora.setTutorByIdTutorNuevoDTO(tutorNuevo);
		bitacora.setFecha(new Date());
		bitacora.setSolicitudDTO(solicitud);
		bitacora.setIdUsuario(idUsuario);
		
		bitacoraCambiosTutorDAO.guardarBitacora(bitacora, observaciones);
		
		solicitud.getTutorDTO().setIdUsuarioLlaveCdmx(tutorNuevo.getIdUsuarioLlaveCdmx());
		
		solicitudDAO.actualizarTutor(solicitud);		
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean actualizarEstatusSolicitudTutor(Long idSolicitud, TutorDTO tutorDTO) {
		tutorDAO.envioCorreccionTutor(tutorDTO);
		//solicitudDAO.actualizarEstatusSolicitud(idSolicitud, tutorDTO.getCatEstatusDTO().getIdEstatus());
		return true;
	}

}
