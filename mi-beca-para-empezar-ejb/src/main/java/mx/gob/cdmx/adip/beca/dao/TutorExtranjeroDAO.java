package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorExtranjeroDTO;

@Stateless
@LocalBean
public class TutorExtranjeroDAO extends IBaseDAO<TutorExtranjeroDTO, Long>{

	@Override
	public TutorExtranjeroDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TutorExtranjeroDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TutorExtranjeroDTO> buscarPorCriterios(TutorExtranjeroDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(TutorExtranjeroDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(TutorExtranjeroDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TutorExtranjeroDTO buscarPorCURP(String curp) {
		List<TutorExtranjeroDTO> listado = em.createNamedQuery("TutorExtranjero.findByCURP", TutorExtranjeroDTO.class)
				.setParameter("curp", curp).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

}
