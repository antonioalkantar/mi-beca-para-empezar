package mx.gob.cdmx.adip.beca.catalogos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMunicipiosDTO;

@Stateless
@LocalBean
public class CatMunicipiosDAO extends IBaseDAO<CatMunicipiosDTO, Integer>{

	@Override
	public CatMunicipiosDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatMunicipiosDTO> buscarTodos() {
		return em.createNamedQuery("CatMunicipios.findAll", CatMunicipiosDTO.class).getResultList();
	}

	@Override
	public List<CatMunicipiosDTO> buscarPorCriterios(CatMunicipiosDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatMunicipiosDTO e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void guardar(CatMunicipiosDTO e) {
		// TODO Auto-generated method stub
	}

}