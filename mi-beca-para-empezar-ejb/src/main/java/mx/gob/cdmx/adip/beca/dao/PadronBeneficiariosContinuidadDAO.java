package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.PadronBeneficiariosContinuidadDTO;
import mx.gob.cdmx.adip.beca.model.PadronBeneficiariosContinuidad;

@LocalBean
@Stateless
public class PadronBeneficiariosContinuidadDAO extends IBaseDAO<PadronBeneficiariosContinuidadDTO, Long> {

	@Override
	public PadronBeneficiariosContinuidadDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public List<PadronBeneficiariosContinuidadDTO> buscarTodos() {
		return null;
	}

	@Override
	public List<PadronBeneficiariosContinuidadDTO> buscarPorCriterios(PadronBeneficiariosContinuidadDTO e) {
		return null;
	}

	@Override
	public void actualizar(PadronBeneficiariosContinuidadDTO e) {
		
	}

	@Override
	public void guardar(PadronBeneficiariosContinuidadDTO e) {
		
	}
	
	
	public PadronBeneficiariosContinuidadDTO buscarPorCurp(String curp) {
		List<PadronBeneficiariosContinuidadDTO> lstBeneficiarios = em.createNamedQuery("PadronBeneficiariosContinuidad.findByCurp", PadronBeneficiariosContinuidadDTO.class)
				.setParameter("curp", curp).getResultList();
		return lstBeneficiarios != null && !lstBeneficiarios.isEmpty() ? lstBeneficiarios.get(0) : null;
	}
	
	public void actualizarCurpTutor(PadronBeneficiariosContinuidadDTO e ) {
		PadronBeneficiariosContinuidad beneficiariosContinuidad = em.getReference(PadronBeneficiariosContinuidad.class, e.getIdBeneficiarioContinuidad());
		beneficiariosContinuidad.setCurpTutor(e.getCurpTutor());
		em.merge(beneficiariosContinuidad);
	}
	
	

}
