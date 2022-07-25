package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatNivelEducativoDTO;

@LocalBean
@Stateless
public class CatNivelEducativoDAO extends IBaseDAO<CatNivelEducativoDTO, Long> {

	@Override
	public CatNivelEducativoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatNivelEducativoDTO> buscarTodos() {
		List<CatNivelEducativoDTO> lstNivel = em.createNamedQuery("CatNivelEducativo.findAll", CatNivelEducativoDTO.class).getResultList();
		return lstNivel;
	}

	@Override
	public List<CatNivelEducativoDTO> buscarPorCriterios(CatNivelEducativoDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatNivelEducativoDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatNivelEducativoDTO e) {
		// TODO Auto-generated method stub
		
	}

}
