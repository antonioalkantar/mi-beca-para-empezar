package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoIneDTO;

@LocalBean
@Stateless
public class CatTipoIneDAO extends IBaseDAO<CatTipoIneDTO, Long> {

	@Override
	public CatTipoIneDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatTipoIneDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatTipoIneDTO> buscarPorCriterios(CatTipoIneDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CatTipoIneDTO e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void guardar(CatTipoIneDTO e) {
		// TODO Auto-generated method stub
	}

}
