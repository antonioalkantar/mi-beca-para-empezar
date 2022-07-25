package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatComprobanteDomicilioDTO;

@Stateless
@LocalBean
public class CatComprobanteDomicilioDAO extends IBaseDAO<CatComprobanteDomicilioDTO, Integer>{

	@Override
	public CatComprobanteDomicilioDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatComprobanteDomicilioDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatComprobanteDomicilioDTO> buscarPorCriterios(CatComprobanteDomicilioDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatComprobanteDomicilioDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatComprobanteDomicilioDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatComprobanteDomicilioDTO> consultaComprobanteDomByEstatusActive() {
		return em.createNamedQuery("CatComprobanteDomicilio.findByEstatusActive", CatComprobanteDomicilioDTO.class).getResultList();
	}

}
