package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.PadronExternoDTO;

@LocalBean
@Stateless
public class PadronExternoDAO  extends IBaseDAO<PadronExternoDTO, Long>{

	@Override
	public PadronExternoDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public List<PadronExternoDTO> buscarTodos() {
		return null;
	}

	@Override
	public List<PadronExternoDTO> buscarPorCriterios(PadronExternoDTO e) {
		return null;
	}

	@Override
	public void actualizar(PadronExternoDTO e) {
		
	}

	@Override
	public void guardar(PadronExternoDTO e) {
		
	}
	public List<PadronExternoDTO> buscarPorCurp(String curp) {
		List<PadronExternoDTO> beneficiario =  em.createNamedQuery("PadronExterno.findByCurp", PadronExternoDTO.class)
				.setParameter("curp", curp).getResultList();
		return beneficiario != null && !beneficiario.isEmpty() ? beneficiario : null;
	}

}
