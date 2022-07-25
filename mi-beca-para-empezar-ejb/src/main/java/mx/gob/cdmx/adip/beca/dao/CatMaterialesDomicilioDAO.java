package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMaterialesDomicilioDTO;

@LocalBean
@Stateless
public class CatMaterialesDomicilioDAO extends IBaseDAO<CatMaterialesDomicilioDTO, Long> {

	@Override
	public CatMaterialesDomicilioDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatMaterialesDomicilioDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatMaterialesDomicilio.findByEstatusActive", CatMaterialesDomicilioDTO.class).getResultList();
	}

	@Override
	public List<CatMaterialesDomicilioDTO> buscarPorCriterios(CatMaterialesDomicilioDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatMaterialesDomicilioDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatMaterialesDomicilioDTO e) {
		// TODO Auto-generated method stub
		
	}

}
