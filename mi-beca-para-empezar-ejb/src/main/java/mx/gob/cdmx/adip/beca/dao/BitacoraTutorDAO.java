package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraTutorDTO;
import mx.gob.cdmx.adip.beca.model.BitacoraTutor;
import mx.gob.cdmx.adip.beca.model.CatEstatus;
import mx.gob.cdmx.adip.beca.model.Tutor;

@Stateless
@LocalBean
public class BitacoraTutorDAO extends IBaseDAO<BitacoraTutorDTO, Long>{

	@Override
	public BitacoraTutorDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraTutorDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraTutorDTO> buscarPorCriterios(BitacoraTutorDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(BitacoraTutorDTO e) {
		// TODO Auto-generated method stub
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void guardar(BitacoraTutorDTO b) {
		BitacoraTutor bitacora = new BitacoraTutor();
		bitacora.setFechaCaptura(b.getFechaCaptura());
		bitacora.setIdUsuarioFidegar(b.getIdUsuarioFidegar());
		bitacora.setTutor(em.getReference(Tutor.class, b.getTutorDTO().getIdUsuarioLlaveCdmx()));
		bitacora.setCatEstatusBitacora(em.getReference(CatEstatus.class, b.getCatEstatusDTO().getIdEstatus()));
		
		em.persist(bitacora);
		em.flush();
	}

}
