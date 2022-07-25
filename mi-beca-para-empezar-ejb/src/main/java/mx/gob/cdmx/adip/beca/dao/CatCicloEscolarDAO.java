package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;

@LocalBean
@Stateless
public class CatCicloEscolarDAO extends IBaseDAO<CatCicloEscolarDTO, Integer>{

	@Override
	public CatCicloEscolarDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatCicloEscolarDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatCicloEscolar.findAll", CatCicloEscolarDTO.class).getResultList();
	}

	@Override
	public List<CatCicloEscolarDTO> buscarPorCriterios(CatCicloEscolarDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatCicloEscolarDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatCicloEscolarDTO e) {
		// TODO Auto-generated method stub
		
	}
}