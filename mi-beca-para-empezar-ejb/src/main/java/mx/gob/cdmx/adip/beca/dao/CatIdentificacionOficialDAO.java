package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatIdentificacionOficialDTO;

@LocalBean
@Stateless
public class CatIdentificacionOficialDAO extends IBaseDAO<CatIdentificacionOficialDTO, Long>   {

	@Override
	public CatIdentificacionOficialDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatIdentificacionOficialDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatIdentificacionOficialDTO> buscarPorCriterios(CatIdentificacionOficialDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatIdentificacionOficialDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatIdentificacionOficialDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatIdentificacionOficialDTO> consultaIdentificacionByActive(){
		return em.createNamedQuery(
				"CatIdentificacionOficial.findByEstatusActive", CatIdentificacionOficialDTO.class).getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatIdentificacionOficialDTO> consultaIdentificacionByNacionalidad(boolean extranjero){
		return em.createNamedQuery(extranjero ?
				"CatIdentificacionOficial.findExtranjero" :"CatIdentificacionOficial.findMexicano" , CatIdentificacionOficialDTO.class).getResultList();
	}

}
