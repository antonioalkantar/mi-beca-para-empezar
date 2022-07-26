package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatPeriodoEscolarDTO;

@LocalBean
@Stateless
public class CatPeriodoEscolarDAO extends IBaseDAO<CatPeriodoEscolarDTO, Long>{

	@Override
	public CatPeriodoEscolarDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatPeriodoEscolarDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatPeriodoEscolar.findAll", CatPeriodoEscolarDTO.class).getResultList();
	}

	@Override
	public List<CatPeriodoEscolarDTO> buscarPorCriterios(CatPeriodoEscolarDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatPeriodoEscolarDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatPeriodoEscolarDTO e) {
		// TODO Auto-generated method stub
		
	}
}