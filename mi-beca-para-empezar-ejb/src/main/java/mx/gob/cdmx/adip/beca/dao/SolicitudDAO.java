package mx.gob.cdmx.adip.beca.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.cdmx.adip.beca.common.util.BeanUtils;
import mx.gob.cdmx.adip.beca.common.util.JPQL;
import mx.gob.cdmx.adip.beca.common.util.ListPaginador;
import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.model.Solicitud;
import mx.gob.cdmx.adip.beca.model.Tutor;
import mx.gob.cdmx.adip.beca.model.CatMunicipios;
import mx.gob.cdmx.adip.beca.model.CatNivelEducativo;
import mx.gob.cdmx.adip.beca.model.CatParentesco;
import mx.gob.cdmx.adip.beca.model.CatEstatusBeneficiario;

@Stateless
@LocalBean
public class SolicitudDAO extends IBaseDAO<SolicitudDTO, Long> {
	
	private static final int MAX_RESULTADOS_POR_PAGINA = 8;

	@Override
	public SolicitudDTO buscarPorId(Long id) {
		List<SolicitudDTO> listado = em.createNamedQuery("Solicitud.findById", SolicitudDTO.class)
				.setParameter("idSolicitud", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	public List<SolicitudDTO> buscarTodos() {		
		return null;
	}
	
	@Override
	public List<SolicitudDTO> buscarPorCriterios(SolicitudDTO e) {
		return null;
	}
	
	public List<SolicitudDTO> buscarPorCriterios(SolicitudDTO e, int pagina) {
		
		// 1. Se arma los segmentos de la consulta (Select, from, where, order by) por separado para después poderlas ejecutarlas aparte.
		JPQL jpqlQuerySelectCount = new JPQL();
		JPQL jpqlQuerySelectFields = new JPQL();
		JPQL jpqlQueryFromWhere = new JPQL();
		JPQL jpqlQueryOrderby = new JPQL();
		jpqlQuerySelectCount.append("  SELECT count(*) ");
		
		jpqlQuerySelectFields.append(" SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO ");
		jpqlQuerySelectFields.append("        (s.idSolicitud, s.folioSolicitud, ");
		jpqlQuerySelectFields.append("         t.idUsuarioLlaveCdmx, t.nombre, t.primerApellido, t.segundoApellido, ");
		jpqlQuerySelectFields.append("         b.nombresBeneficiario, b.primerApellidoBeneficiario, b.segundoApellidoBeneficiario, ");
		jpqlQuerySelectFields.append("         s.cct, ce.idEstatus, ce.descripcion, ceb.idEstatusBeneficiario, ceb.descripcion )");
		
		jpqlQueryFromWhere.append(" FROM CrcBeneficiarioSolicitud crc  ");
		jpqlQueryFromWhere.append("     JOIN crc.beneficiario b  ");
		jpqlQueryFromWhere.append("     JOIN crc.solicitud s  ");
		jpqlQueryFromWhere.append("     JOIN s.tutor t  ");
		jpqlQueryFromWhere.append("     JOIN t.catEstatus ce  ");
		jpqlQueryFromWhere.append("     JOIN s.catMunicipios cm  ");
		jpqlQueryFromWhere.append("     JOIN s.encuestas e ");
		jpqlQueryFromWhere.append("     JOIN s.catEstatusBeneficiario ceb ");
		jpqlQueryFromWhere.append(" WHERE ce.idEstatus !=  1  ");
		
		jpqlQueryFromWhere.append(e.getTutorDTO().getCurp() != null  &&  !e.getTutorDTO().getCurp().isEmpty(),
				                  "     and t.curp like :curpTutor ", "curpTutor", e.getTutorDTO().getCurp().toUpperCase()+"%");
		
		jpqlQueryFromWhere.append(e.getBeneficiarioDTO().getCurpBeneficiario() != null && !e.getBeneficiarioDTO().getCurpBeneficiario().isEmpty(),
								  "     and b.curpBeneficiario like :curpBeneficiario ", "curpBeneficiario", e.getBeneficiarioDTO().getCurpBeneficiario().toUpperCase()+"%");
		
		jpqlQueryFromWhere.append(e.getFolioSolicitud() != null && !e.getFolioSolicitud().isEmpty(),
								  "     and s.folioSolicitud like :folio ", "folio", e.getFolioSolicitud().toUpperCase()+"%");
		
		jpqlQueryFromWhere.append(e.getCct() != null && !e.getCct().isEmpty(),
				                  "     and s.cct like :cct ", "cct", e.getCct().toUpperCase() + "%");

		jpqlQueryFromWhere.append(e.getCatEstatusDTO() != null && e.getCatEstatusDTO().getIdEstatus() !=  null,
                                  "     and ce.idEstatus = :idEstatus ", "idEstatus", e.getCatEstatusDTO().getIdEstatus());
		
		jpqlQueryFromWhere.append(e.getCatNivelEducativoDTO() !=  null && e.getCatNivelEducativoDTO().getIdNivel() != null,
								  "     and s.catNivelEducativo.idNivel = :idNivelEducativo ", "idNivelEducativo", e.getCatNivelEducativoDTO().getIdNivel());
		
		jpqlQueryFromWhere.append(e.getCatMunicipiosDTO().getIdMunicipio() !=  null,
				 				  "     and cm.idMunicipio = :idMunicipio", "idMunicipio", e.getCatMunicipiosDTO().getIdMunicipio());
		
		jpqlQueryFromWhere.append(e.getFechaInicio() != null,
								  "     and s.fechaSolicitud >=  :fechaInicio", "fechaInicio", e.getFechaInicio());
		
		jpqlQueryFromWhere.append(e.getFechaFin() !=  null,
				  				  "     and s.fechaSolicitud <=  :fechaFin ", "fechaFin", e.getFechaFin());
		
		jpqlQueryOrderby.append("   ORDER BY s.idSolicitud ASC ");
		
		// 2. Se obtiene el total de registros haciendo un count para pasarselo al paginador
		JPQL jpqlQueryFullContador = new JPQL(jpqlQuerySelectCount.toString()+ jpqlQueryFromWhere.toString(), jpqlQueryFromWhere.getParams());
		Query query = em.createQuery(jpqlQueryFullContador.toString());
		jpqlQueryFullContador.setParameters(query);
		
		Long totalResultados = (Long)query.getSingleResult();
		
		// 3. Se obtienen los datos de los registros para la página X del paginador
		
		JPQL jpqlQueryFullFields = new JPQL(jpqlQuerySelectFields.toString() + jpqlQueryFromWhere.toString() + jpqlQueryOrderby.toString(), jpqlQueryFromWhere.getParams());
		query = em.createQuery(jpqlQueryFullFields.toString());
		jpqlQueryFullFields.setParameters(query);
		
		@SuppressWarnings("unchecked")
		List<SolicitudDTO> lstSolicitudes = query.setFirstResult( (pagina == 0 ? 0 : (pagina * MAX_RESULTADOS_POR_PAGINA) ) )
				              .setMaxResults(MAX_RESULTADOS_POR_PAGINA)
				              .getResultList();
		
		// 4. Se retorma un ListPaginador para "engañar" a la Datatable y pinte un paginador con N páginas (De acuerdo al total de resutlados) pero realmente tiene una lista con pocos registros
		return new ListPaginador<>(lstSolicitudes, totalResultados.intValue(), MAX_RESULTADOS_POR_PAGINA);
	}

	@Override
	public void actualizar(SolicitudDTO e) {		

	}

	public void actualizarParentescoSolicitud(SolicitudDTO solicitudDTO) {
		Solicitud solicitud = em.getReference(Solicitud.class, solicitudDTO.getIdSolicitud());
		solicitud.setCatParentesco(em.getReference(CatParentesco.class, solicitudDTO.getCatParentescoDTO().getIdParentesco()));
		em.merge(solicitud);
		em.flush();
	}

	@Override
	public void guardar(SolicitudDTO e) {	
		Solicitud solicitud = new Solicitud();
	
		solicitud.setCatParentesco(em.getReference(CatParentesco.class, e.getCatParentescoDTO().getIdParentesco()));
		solicitud.setCatMunicipios(em.getReference(CatMunicipios.class, e.getCatMunicipiosDTO().getIdMunicipio()));
		solicitud.setTutor(em.getReference(Tutor.class, e.getTutorDTO().getIdUsuarioLlaveCdmx()));
		solicitud.setFolioSolicitud(e.getFolioSolicitud());
		solicitud.setFechaSolicitud(new Date());
		solicitud.setRespuestaAutoridadEducativa("");
		solicitud.setCct(e.getCct());
		solicitud.setNombre(e.getNombre());
		solicitud.setCalle(e.getCalle());
		solicitud.setColonia(e.getColonia());
		solicitud.setAlcaldia(e.getAlcaldia());
		solicitud.setCodigopostal(e.getCodigopostal());
		solicitud.setTurno(e.getTurno());
		solicitud.setCatNivelEducativo(e.getCatNivelEducativoDTO().getIdNivel() != null ? em.getReference(CatNivelEducativo.class, e.getCatNivelEducativoDTO().getIdNivel()) : null);
		solicitud.setGradoEscolar(e.getGradoEscolar());
		solicitud.setCatEstatusBeneficiario(em.getReference(CatEstatusBeneficiario.class, e.getCatEstatusBeneficiarioDTO().getIdEstatusBeneficiario()));
		solicitud.setEnvioExitoso(e.getEnvioExitoso());
		em.persist(solicitud);
		em.flush();
		e.setIdSolicitud(solicitud.getIdSolicitud());
	}
	
	public void actualizarTutor(SolicitudDTO e) {	
		Solicitud solicitud = em.getReference(Solicitud.class, e.getIdSolicitud());
		solicitud.setTutor(em.getReference(Tutor.class, e.getTutorDTO().getIdUsuarioLlaveCdmx()));
		em.merge(solicitud);
		em.flush();
	}

	public void actualizarFolio(SolicitudDTO e) {	
		Solicitud solicitud = em.getReference(Solicitud.class, e.getIdSolicitud());
		e.setFolioSolicitud(BeanUtils.generaFolioBeneficiario(e.getIdSolicitud()));
		solicitud.setFolioSolicitud(e.getFolioSolicitud());
		em.merge(solicitud);
		em.flush();
	}
	
	//metodo Actualizar el estatus del beneficiario desde Validaciones
		public void actualizarEstatusBeneficiario(SolicitudDTO e) {	
			Solicitud solicitud = em.getReference(Solicitud.class, e.getIdSolicitud());
			solicitud.setCatEstatusBeneficiario(em.getReference(CatEstatusBeneficiario.class, e.getCatEstatusBeneficiarioDTO().getIdEstatusBeneficiario()));
			em.merge(solicitud);
			em.flush();
		}

}
