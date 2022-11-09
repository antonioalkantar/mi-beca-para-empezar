package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambioContactoDTO;
import mx.gob.cdmx.adip.beca.model.BitacoraCambioContacto;

@Stateless
@LocalBean
public class BitacoraCambioContactoDAO extends IBaseDAO<BitacoraCambioContactoDTO, Long> {

	@Override
	public BitacoraCambioContactoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraCambioContactoDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BitacoraCambioContactoDTO> buscarPorCriterios(BitacoraCambioContactoDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(BitacoraCambioContactoDTO e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar(BitacoraCambioContactoDTO e) {
		// TODO Auto-generated method stub
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarBitacora(BitacoraCambioContactoDTO bitacoraDTO) {
		BitacoraCambioContacto bitacora = new BitacoraCambioContacto();
		bitacora.setIdUsuarioLlaveCdmx(bitacoraDTO.getIdUsuarioLlaveCdmx());
		bitacora.setIdUsuarioFidegar(bitacoraDTO.getIdUsuarioFidegar());
		bitacora.setCorreoAnterior(bitacoraDTO.getCorreoAnterior());
		bitacora.setCorreoNuevo(bitacoraDTO.getCorreoNuevo());
		bitacora.setTelefonoAnterior(bitacoraDTO.getTelefonoAnterior());
		bitacora.setTelefonoNuevo(bitacoraDTO.getTelefonoNuevo());
		bitacora.setFechaModificacion(bitacoraDTO.getFechaModificacion());
		bitacora.setEnviadoAPagatodo(bitacoraDTO.isEnviadoAPagatodo());
		em.persist(bitacora);
		em.flush();
	}

}
