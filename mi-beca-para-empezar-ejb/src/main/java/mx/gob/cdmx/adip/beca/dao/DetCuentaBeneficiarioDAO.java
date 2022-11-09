package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.DetCuentaBeneficiarioDTO;
import mx.gob.cdmx.adip.beca.model.Beneficiario;
import mx.gob.cdmx.adip.beca.model.DetCuentaBeneficiario;
import mx.gob.cdmx.adip.beca.model.Solicitud;

@Stateless
@LocalBean
public class DetCuentaBeneficiarioDAO extends IBaseDAO<DetCuentaBeneficiarioDTO, Long>{

	@Override
	public DetCuentaBeneficiarioDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public List<DetCuentaBeneficiarioDTO> buscarTodos() {
		return null;
	}

	@Override
	public List<DetCuentaBeneficiarioDTO> buscarPorCriterios(DetCuentaBeneficiarioDTO e) {
		return null;
	}

	@Override
	public void actualizar(DetCuentaBeneficiarioDTO d) {
		DetCuentaBeneficiario detCuentaBeneficiario = em.getReference(DetCuentaBeneficiario.class, d.getIdCuentaBeneficiario());
		detCuentaBeneficiario.setNumeroCuenta(d.getNumeroCuenta());
		em.merge(detCuentaBeneficiario);
		em.flush();
	}

	@Override
	public void guardar(DetCuentaBeneficiarioDTO e) {
		DetCuentaBeneficiario detCuentaBeneficiario = new DetCuentaBeneficiario();
		detCuentaBeneficiario.setBeneficiario(em.getReference(Beneficiario.class, e.getBeneficiarioDTO().getIdBeneficiario()));
		detCuentaBeneficiario.setNumeroCuenta(e.getNumeroCuenta()!= null ? e.getNumeroCuenta() : "");
		em.persist(detCuentaBeneficiario);
		em.flush();
	}

}
