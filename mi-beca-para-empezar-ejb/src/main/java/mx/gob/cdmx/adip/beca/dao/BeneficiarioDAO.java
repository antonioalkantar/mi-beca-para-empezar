package mx.gob.cdmx.adip.beca.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.BeneficiarioDTO;
import mx.gob.cdmx.adip.beca.model.Beneficiario;

@LocalBean
@Stateless
public class BeneficiarioDAO extends IBaseDAO<BeneficiarioDTO, Long> {

	@Override
	public BeneficiarioDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public BeneficiarioDTO buscarPorCurp(String curp) {
		List<BeneficiarioDTO> listado = em.createNamedQuery("Beneficiario.findByCurp", BeneficiarioDTO.class)
				.setParameter("curpBeneficiario", curp).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	@Override
	public List<BeneficiarioDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public long countBeneficiarios() {
		return em.createNamedQuery("Beneficiario.countBeneficiarios", Long.class).getSingleResult().longValue();
	}

	@Override
	public List<BeneficiarioDTO> buscarPorCriterios(BeneficiarioDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(BeneficiarioDTO e) {
		Beneficiario beneficiario = em.getReference(Beneficiario.class, e.getIdBeneficiario());
		beneficiario.setFechaNacimientoBeneficiario(e.getFechaNacimientoBeneficiario());
		em.merge(beneficiario);
		em.flush();
	}

	@Override
	public void guardar(BeneficiarioDTO e) {
		Beneficiario beneficiario =  new Beneficiario();
		beneficiario.setCurpBeneficiario(e.getCurpBeneficiario());
		beneficiario.setNombresBeneficiario(e.getNombresBeneficiario());
		beneficiario.setPrimerApellidoBeneficiario(e.getPrimerApellidoBeneficiario());
		beneficiario.setSegundoApellidoBeneficiario(e.getSegundoApellidoBeneficiario() != null && !e.getSegundoApellidoBeneficiario().isEmpty()? e.getSegundoApellidoBeneficiario():null);
		beneficiario.setFechaNacimientoBeneficiario(e.getFechaNacimientoBeneficiario());
		beneficiario.setEsTutor(e.getEsTutor() != null ? e.getEsTutor() : false);
		beneficiario.setNacionalidad(e.getNacionalidad());
		beneficiario.setIdUsuarioLlaveCdmx(e.getIdUsuarioLlaveCdmx());
		beneficiario.setFechaRegistro(new Date());
		em.persist(beneficiario);
		em.flush();
		e.setIdBeneficiario(beneficiario.getIdBeneficiario());
	}
	
}
