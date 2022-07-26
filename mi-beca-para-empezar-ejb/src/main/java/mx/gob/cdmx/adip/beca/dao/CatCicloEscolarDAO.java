package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;

@LocalBean
@Stateless
public class CatCicloEscolarDAO extends IBaseDAO<CatCicloEscolarDTO, Long>{

	@Override
	public CatCicloEscolarDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public List<CatCicloEscolarDTO> buscarTodos() {
		return em.createNamedQuery("CatCicloEscolar.findAll", CatCicloEscolarDTO.class).getResultList();
	}

	@Override
	public List<CatCicloEscolarDTO> buscarPorCriterios(CatCicloEscolarDTO e) {
		return null;
	}

	@Override
	public void actualizar(CatCicloEscolarDTO e) {
	}

	@Override
	public void guardar(CatCicloEscolarDTO e) {
	}

	public CatCicloEscolarDTO buscarCicloVigente() {
		List<CatCicloEscolarDTO> lstDatos = em.createNamedQuery("CatCicloEscolar.findCicloVigente", CatCicloEscolarDTO.class)
		.getResultList();
		return lstDatos != null && !lstDatos.isEmpty() ? lstDatos.get(0) : null;
	}
}
