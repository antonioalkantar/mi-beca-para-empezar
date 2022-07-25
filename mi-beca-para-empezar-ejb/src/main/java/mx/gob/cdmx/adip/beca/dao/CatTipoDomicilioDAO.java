package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoDomicilioDTO;

@LocalBean
@Stateless
public class CatTipoDomicilioDAO extends IBaseDAO<CatTipoDomicilioDTO, Integer> {

	@Override
	public CatTipoDomicilioDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatTipoDomicilioDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("CatTipoDomicilio.findByEstatusActive", CatTipoDomicilioDTO.class).getResultList();
	}

	@Override
	public List<CatTipoDomicilioDTO> buscarPorCriterios(CatTipoDomicilioDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatTipoDomicilioDTO e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void guardar(CatTipoDomicilioDTO e) {
		// TODO Auto-generated method stub
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatTipoDomicilioDTO> consultaTipoDomByEstatusActive() {
		return em.createNamedQuery("CatTipoDomicilio.findByEstatusActive", CatTipoDomicilioDTO.class).getResultList();
	}

}
