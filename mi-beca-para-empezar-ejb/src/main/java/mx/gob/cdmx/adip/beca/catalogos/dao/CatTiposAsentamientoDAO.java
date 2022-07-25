package mx.gob.cdmx.adip.beca.catalogos.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTiposAsentamientoDTO;

@LocalBean
@Stateless
public class CatTiposAsentamientoDAO extends IBaseDAO<CatTiposAsentamientoDTO, Long> {

	@Override
	public CatTiposAsentamientoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatTiposAsentamientoDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatTiposAsentamientoDTO> buscarPorCriterios(CatTiposAsentamientoDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatTiposAsentamientoDTO e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar(CatTiposAsentamientoDTO e) {
		// TODO Auto-generated method stub
		
	}

}
