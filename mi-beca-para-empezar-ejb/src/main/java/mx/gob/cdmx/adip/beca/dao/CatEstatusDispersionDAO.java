package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDispersionDTO;

@LocalBean
@Stateless
public class CatEstatusDispersionDAO extends IBaseDAO<CatEstatusDispersionDTO, Long>{

	@Override
	public CatEstatusDispersionDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public List<CatEstatusDispersionDTO> buscarTodos() {
		return em.createNamedQuery("CatEstatusDispersion.findAll", CatEstatusDispersionDTO.class).getResultList();
	}

	@Override
	public List<CatEstatusDispersionDTO> buscarPorCriterios(CatEstatusDispersionDTO e) {
		return null;
	}

	@Override
	public void actualizar(CatEstatusDispersionDTO e) {
	}

	@Override
	public void guardar(CatEstatusDispersionDTO e) {
	}

}
