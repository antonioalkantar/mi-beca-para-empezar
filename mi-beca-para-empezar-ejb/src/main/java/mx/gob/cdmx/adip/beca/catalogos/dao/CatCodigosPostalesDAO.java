package mx.gob.cdmx.adip.beca.catalogos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatCodigosPostalesDTO;

@Stateless
@LocalBean
public class CatCodigosPostalesDAO extends IBaseDAO<CatCodigosPostalesDTO, Integer>{

	@Override
	public CatCodigosPostalesDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatCodigosPostalesDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatCodigosPostalesDTO> buscarPorCriterios(CatCodigosPostalesDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatCodigosPostalesDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatCodigosPostalesDTO e) {
		// TODO Auto-generated method stub
		
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatCodigosPostalesDTO> buscarPorAsentamiento(int idAsentamiento, int idMunicipio){
		return em.createNamedQuery("CatCodigosPostales.findPorAsentamiento", CatCodigosPostalesDTO.class)
				.setParameter("idMunicipio", idMunicipio)
				.setParameter("idAsentamiento", idAsentamiento)
				.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CatCodigosPostalesDTO> buscarPorCodigoPostal(String codigoPostal){
		return em.createNamedQuery("CatCodigosPostales.findPorCodigoPostal", CatCodigosPostalesDTO.class)
				.setParameter("codigoPostal", codigoPostal)
				.getResultList();
	}
}
