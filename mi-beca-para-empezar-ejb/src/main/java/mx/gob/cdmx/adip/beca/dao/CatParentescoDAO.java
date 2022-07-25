package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatParentescoDTO;

@LocalBean
@Stateless
public class CatParentescoDAO extends IBaseDAO<CatParentescoDTO, Long> {

	@Override
	public CatParentescoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatParentescoDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatParentesco.findByEstatusActive", CatParentescoDTO.class).getResultList();
	}

	@Override
	public List<CatParentescoDTO> buscarPorCriterios(CatParentescoDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatParentescoDTO e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void guardar(CatParentescoDTO e) {
		// TODO Auto-generated method stub
	}

}
