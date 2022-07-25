package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusBeneficiarioDTO;

@LocalBean
@Stateless
public class CatEstatusBeneficiarioDAO extends IBaseDAO<CatEstatusBeneficiarioDTO, Long>  {

	@Override
	public CatEstatusBeneficiarioDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatEstatusBeneficiarioDTO> buscarTodos() {
		return em.createNamedQuery("CatEstatusBeneficiario.findAll", CatEstatusBeneficiarioDTO.class).getResultList();
	}

	@Override
	public List<CatEstatusBeneficiarioDTO> buscarPorCriterios(CatEstatusBeneficiarioDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatEstatusBeneficiarioDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatEstatusBeneficiarioDTO e) {
		// TODO Auto-generated method stub
		
	}

}
