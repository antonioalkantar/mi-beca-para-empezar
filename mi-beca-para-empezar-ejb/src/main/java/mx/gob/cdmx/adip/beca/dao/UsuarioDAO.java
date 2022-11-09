package mx.gob.cdmx.adip.beca.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.model.Usuario;
import mx.gob.cdmx.adip.beca.commons.dto.UserDTO;

@Stateless
@LocalBean
public class UsuarioDAO extends IBaseDAO<UserDTO, Long> {

	@Override
	public UserDTO buscarPorId(Long id) {
		List<UserDTO> lstUsuario = em.createNamedQuery("Usuario.findByIdUsuarioLlaveCDMX", UserDTO.class)
				.setParameter("idUsuarioLlave", id)
				.getResultList();
		return lstUsuario != null && !lstUsuario.isEmpty() ? lstUsuario.get(0) : new UserDTO();
	}

	@Override
	public List<UserDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> buscarPorCriterios(UserDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(UserDTO user) {
		Usuario us = em.getReference(Usuario.class, user.getIdUsuarioLlaveCdmx());
		us.setNombre(user.getNombre());
		us.setPrimerApellido(user.getPrimerApellido());
		us.setSegundoApellido(user.getSegundoApellido());
		us.setCurp(user.getCurp());
		us.setTelefono(user.getTelefono());
		us.setCorreo(user.getCorreo());
		us.setFechaNacimiento(user.getFechaNacimiento());
		us.setSexo(user.getSexo());
		us.setFechaActualizacion(new Date());
		em.merge(us);
		em.flush();
	}

	@Override
	public void guardar(UserDTO user) {
		final Usuario us = new Usuario();
		us.setIdUsuarioLlaveCdmx(user.getIdUsuarioLlaveCdmx());
		us.setNombre(user.getNombre());
		us.setPrimerApellido(user.getPrimerApellido());
		us.setSegundoApellido(user.getSegundoApellido());
		us.setCurp(user.getCurp());
		us.setTelefono(user.getTelefono());
		us.setCorreo(user.getCorreo());
		us.setFechaNacimiento(user.getFechaNacimiento());
		us.setSexo(user.getSexo());
		us.setFechaCreacion(new Date());
		em.persist(us);
		em.flush();
	}

}
