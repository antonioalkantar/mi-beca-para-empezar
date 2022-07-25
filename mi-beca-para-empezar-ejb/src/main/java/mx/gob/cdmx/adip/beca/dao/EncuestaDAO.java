package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.EncuestaDTO;
import mx.gob.cdmx.adip.beca.model.CatGrupoPertenece;
import mx.gob.cdmx.adip.beca.model.CatIngresosFamilia;
import mx.gob.cdmx.adip.beca.model.CatMaterialesDomicilio;
import mx.gob.cdmx.adip.beca.model.CatTipoDomicilio;
import mx.gob.cdmx.adip.beca.model.Encuesta;
import mx.gob.cdmx.adip.beca.model.Solicitud;

@LocalBean
@Stateless
public class EncuestaDAO extends IBaseDAO<EncuestaDTO, Long> {

	@Override
	public EncuestaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EncuestaDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EncuestaDTO> buscarPorCriterios(EncuestaDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(EncuestaDTO e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void guardar(EncuestaDTO e) {
		Encuesta encuesta = new Encuesta();
		encuesta.setCatTipoDomicilio(em.getReference(CatTipoDomicilio.class, e.getCatTipoDomicilioDTO().getIdTipoDomicilio()));
		encuesta.setCatMaterialesDomicilio(em.getReference(CatMaterialesDomicilio.class, e.getCatMaterialesDomicilioDTO().getIdMaterialesDomicilio()));
		encuesta.setNumeroHabitantes(e.getNumeroHabitantes());
		encuesta.setHabitantesTrabajadores(e.getHabitantesTrabajadores());
		encuesta.setCatIngresosFamilia(em.getReference(CatIngresosFamilia.class, e.getCatIngresosFamiliaDTO().getIdIngresosFamilia()));
		encuesta.setCatGrupoPertenece(em.getReference(CatGrupoPertenece.class, e.getCatGrupoPerteneceDTO().getIdGrupoPertenece()));
		encuesta.setUtilesEscolares(e.getUtilesEscolares());
		encuesta.setRopa(e.getRopa());
		encuesta.setZapatos(e.getZapatos());
		encuesta.setComida(e.getComida());
		encuesta.setJuguetes(e.getJuguetes());
		encuesta.setOtro(e.getOtro());
		encuesta.setEspecificaOtro(e.getEspecificaOtro());
		encuesta.setSolicitud(em.getReference(Solicitud.class, e.getSolicitudDTO().getIdSolicitud()));
		encuesta.setOtroGrupo(e.getOtroGrupo());
		em.persist(encuesta);
		em.flush();
	}
	
	public EncuestaDTO buscarPorIdSolicitud(Long id) {
		List<EncuestaDTO> listado = em.createNamedQuery("Encuesta.findByIdSolicitud", EncuestaDTO.class)
				.setParameter("idSolicitud", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

}

