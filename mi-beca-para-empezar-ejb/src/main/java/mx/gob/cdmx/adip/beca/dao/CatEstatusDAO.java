package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;

@LocalBean
@Stateless
public class CatEstatusDAO extends IBaseDAO<CatEstatusDTO, Long>{

	@Override
	public CatEstatusDTO buscarPorId(Long id) {		
		return null;
	}

	@Override
	public List<CatEstatusDTO> buscarTodos() {	
		List<CatEstatusDTO> lstEstatus = em.createNamedQuery("CatEstatus.findAll", CatEstatusDTO.class).getResultList();
		return lstEstatus;
	}
	
	public List<CatEstatusDTO> buscarEstatusTutor() {	
		List<CatEstatusDTO> lstEstatus = em.createNamedQuery("CatEstatus.findAllEstatusTutor", CatEstatusDTO.class).getResultList();
		return lstEstatus;
	}

	@Override
	public List<CatEstatusDTO> buscarPorCriterios(CatEstatusDTO e) {	
		return null;
	}

	@Override
	public void actualizar(CatEstatusDTO e) {		
	}

	@Override
	public void guardar(CatEstatusDTO e) {		
	}

}
