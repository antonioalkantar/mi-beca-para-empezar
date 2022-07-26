package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoDispersionDTO;

@LocalBean
@Stateless
public class CatTipoDispersionDAO extends IBaseDAO<CatTipoDispersionDTO, Long>{

	@Override
	public CatTipoDispersionDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public List<CatTipoDispersionDTO> buscarTodos() {
		return em.createNamedQuery("CatTipoDispersion.findAll", CatTipoDispersionDTO.class).getResultList();
	}

	@Override
	public List<CatTipoDispersionDTO> buscarPorCriterios(CatTipoDispersionDTO e) {
		return null;
	}

	@Override
	public void actualizar(CatTipoDispersionDTO e) {
	}

	@Override
	public void guardar(CatTipoDispersionDTO e) {
	}

}
