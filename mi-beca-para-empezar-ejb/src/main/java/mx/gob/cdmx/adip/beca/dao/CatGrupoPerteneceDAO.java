package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatGrupoPerteneceDTO;

@LocalBean
@Stateless
public class CatGrupoPerteneceDAO extends IBaseDAO<CatGrupoPerteneceDTO, Long> {

	@Override
	public CatGrupoPerteneceDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatGrupoPerteneceDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatGrupoPertenece.findByEstatusActive", CatGrupoPerteneceDTO.class).getResultList();
	}

	@Override
	public List<CatGrupoPerteneceDTO> buscarPorCriterios(CatGrupoPerteneceDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatGrupoPerteneceDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatGrupoPerteneceDTO e) {
		// TODO Auto-generated method stub
		
	}

}
