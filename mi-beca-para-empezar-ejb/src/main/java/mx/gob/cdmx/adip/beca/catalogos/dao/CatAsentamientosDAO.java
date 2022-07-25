package mx.gob.cdmx.adip.beca.catalogos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatAsentamientosDTO;

@Stateless
@LocalBean
public class CatAsentamientosDAO extends IBaseDAO<CatAsentamientosDTO, Integer>{

	@Override
	public CatAsentamientosDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<CatAsentamientosDTO> buscarTodos() {
		return em.createNamedQuery("CatAsentamientos.findAll", CatAsentamientosDTO.class).getResultList();
	}

	@Override
	public List<CatAsentamientosDTO> buscarPorCriterios(CatAsentamientosDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatAsentamientosDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatAsentamientosDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatAsentamientosDTO> buscarPorAlcaldia(int idAlcaldia) {
		return em.createNamedQuery("CatAsentamientos.findPorAlcaldia", CatAsentamientosDTO.class)
				.setParameter("idMunicipio", idAlcaldia)
				.getResultList();
	}

}
