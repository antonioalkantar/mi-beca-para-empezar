package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatIngresosFamiliaDTO;

@LocalBean
@Stateless
public class CatIngresosFamiliaDAO extends IBaseDAO<CatIngresosFamiliaDTO, Long> {

	@Override
	public CatIngresosFamiliaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatIngresosFamiliaDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatIngresosFamilia.findByEstatusActive", CatIngresosFamiliaDTO.class).getResultList();
	}

	@Override
	public List<CatIngresosFamiliaDTO> buscarPorCriterios(CatIngresosFamiliaDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatIngresosFamiliaDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatIngresosFamiliaDTO e) {
		// TODO Auto-generated method stub
		
	}

}
