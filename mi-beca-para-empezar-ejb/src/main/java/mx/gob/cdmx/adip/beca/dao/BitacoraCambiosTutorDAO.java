package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO;
import mx.gob.cdmx.adip.beca.model.BitacoraCambiosTutor;
import mx.gob.cdmx.adip.beca.model.Solicitud;
import mx.gob.cdmx.adip.beca.model.Tutor;

@LocalBean
@Stateless
public class BitacoraCambiosTutorDAO extends IBaseDAO<BitacoraCambiosTutorDTO, Long> {

	@Override
	public BitacoraCambiosTutorDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraCambiosTutorDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraCambiosTutorDTO> buscarPorCriterios(BitacoraCambiosTutorDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(BitacoraCambiosTutorDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(BitacoraCambiosTutorDTO e) {
		BitacoraCambiosTutor bitacora = new BitacoraCambiosTutor();
		bitacora.setTutorByIdTutorAnterior(em.getReference(Tutor.class, e.getTutorByIdTutorAnteriorDTO().getIdUsuarioLlaveCdmx()));
		bitacora.setTutorByIdTutorNuevo(em.getReference(Tutor.class, e.getTutorByIdTutorNuevoDTO().getIdUsuarioLlaveCdmx()));
		bitacora.setFecha(e.getFecha());
		bitacora.setSolicitud(em.getReference(Solicitud.class, e.getSolicitudDTO().getIdSolicitud()));
		bitacora.setIdUsuario(e.getIdUsuario());
		bitacora.setObservaciones(e.getObservaciones());
		em.persist(bitacora);
		em.flush();
	}
	
	public void guardarBitacora(BitacoraCambiosTutorDTO e, String observaciones) {
		BitacoraCambiosTutor bitacora = new BitacoraCambiosTutor();
		bitacora.setTutorByIdTutorAnterior(em.getReference(Tutor.class, e.getTutorByIdTutorAnteriorDTO().getIdUsuarioLlaveCdmx()));
		bitacora.setTutorByIdTutorNuevo(em.getReference(Tutor.class, e.getTutorByIdTutorNuevoDTO().getIdUsuarioLlaveCdmx()));
		bitacora.setFecha(e.getFecha());
		bitacora.setSolicitud(em.getReference(Solicitud.class, e.getSolicitudDTO().getIdSolicitud()));
		bitacora.setIdUsuario(e.getIdUsuario());
		bitacora.setObservaciones(observaciones);
		em.persist(bitacora);
		em.flush();
	}
	
	public List<BitacoraCambiosTutorDTO> buscarPorIdSolicitud(Long id) {
		List<BitacoraCambiosTutorDTO> listado = em.createNamedQuery("BitacoraCambiosTutor.BuscarPorIdSolicitud", BitacoraCambiosTutorDTO.class)
				.setParameter("idSolicitud", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

}
