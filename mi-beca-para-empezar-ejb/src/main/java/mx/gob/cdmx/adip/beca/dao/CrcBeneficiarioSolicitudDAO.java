package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.model.Beneficiario;
import mx.gob.cdmx.adip.beca.model.CrcBeneficiarioSolicitud;
import mx.gob.cdmx.adip.beca.model.Solicitud;

@Stateless
@LocalBean
public class CrcBeneficiarioSolicitudDAO extends IBaseDAO<CrcBeneficiarioSolicitudDTO, Long> {

	@Override
	public CrcBeneficiarioSolicitudDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrcBeneficiarioSolicitudDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CrcBeneficiarioSolicitudDTO> buscarPorCriterios(CrcBeneficiarioSolicitudDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(CrcBeneficiarioSolicitudDTO e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void guardar(CrcBeneficiarioSolicitudDTO e) {
		CrcBeneficiarioSolicitud crcBenSol = new CrcBeneficiarioSolicitud();
		crcBenSol.setBeneficiario(em.getReference(Beneficiario.class, e.getBeneficiarioDTO().getIdBeneficiario()));
		crcBenSol.setSolicitud(em.getReference(Solicitud.class, e.getSolicitudDTO().getIdSolicitud()));
		em.persist(crcBenSol);
		em.flush();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CrcBeneficiarioSolicitudDTO> consultaSolicitudesByIdUsuarioLlave(Long idUsuarioLlave) {
		List<CrcBeneficiarioSolicitudDTO> listado = em
				.createNamedQuery("CrcBeneficiarioSolicitud.findByIdLlave", CrcBeneficiarioSolicitudDTO.class)
				.setParameter("idUsuarioLlave", idUsuarioLlave).getResultList();
		// TODO: llenar los objetos correspondientes a beneficiario y tutor
		return listado;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CrcBeneficiarioSolicitudDTO consultaSolicitudesByFolioSolicitud(Long idSolicitud) {
		// TODO: llenar los objetos correspondientes a beneficiario y tutor
		List<CrcBeneficiarioSolicitudDTO> listado = em
				.createNamedQuery("CrcBeneficiarioSolicitud.findByFolioSolicitud", CrcBeneficiarioSolicitudDTO.class)
				.setParameter("idSolicitud", idSolicitud).getResultList();
		return listado != null && listado.size() > 0 ? listado.get(0) : new CrcBeneficiarioSolicitudDTO();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public  List<CrcBeneficiarioSolicitudDTO> buscarNoEnviadoPagatodo() {
		List<CrcBeneficiarioSolicitudDTO> lista = em.createNamedQuery("CrcBeneficiarioSolicitud.findByRechazadoPagatodo", CrcBeneficiarioSolicitudDTO.class)
				.getResultList();
		return lista;
	}
 }
