package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMontoApoyoDTO;

@LocalBean
@Stateless
public class CatMontoApoyoDAO extends IBaseDAO<CatMontoApoyoDTO, Integer>{

	@Override
	public CatMontoApoyoDTO buscarPorId(Integer id) {
		return null;
	}

	@Override
	public List<CatMontoApoyoDTO> buscarTodos() {
		List<CatMontoApoyoDTO> lstMontoApoyo = em.createNamedQuery("CatMontoApoyo.findAll", CatMontoApoyoDTO.class).getResultList();
		return lstMontoApoyo != null && !lstMontoApoyo.isEmpty() ? lstMontoApoyo : null;
	}

	@Override
	public List<CatMontoApoyoDTO> buscarPorCriterios(CatMontoApoyoDTO e) {
		return null;
	}

	@Override
	public void actualizar(CatMontoApoyoDTO e) {
	}

	@Override
	public void guardar(CatMontoApoyoDTO e) {
	}

	
	
}
