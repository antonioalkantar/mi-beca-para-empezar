package mx.gob.cdmx.adip.beca.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.cdmx.adip.beca.common.util.BeanUtils;
import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.DispersionDTO;
import mx.gob.cdmx.adip.beca.model.CatCicloEscolar;
import mx.gob.cdmx.adip.beca.model.CatEstatusDispersion;
import mx.gob.cdmx.adip.beca.model.CatPeriodoEscolar;
import mx.gob.cdmx.adip.beca.model.CatTipoDispersion;
import mx.gob.cdmx.adip.beca.model.Dispersion;
import mx.gob.cdmx.adip.beca.model.Solicitud;
import mx.gob.cdmx.adip.beca.model.Tutor;

@LocalBean
@Stateless
public class DispersionDAO extends IBaseDAO<DispersionDTO, Long>{

	@Override
	public DispersionDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DispersionDTO> buscarTodos() {
		return em.createNamedQuery("Dispersion.findAll", DispersionDTO.class).getResultList();
	}

	public List<DispersionDTO> buscarPorCicloPeriodoAndTipo(Integer idCicloEscolar, Integer idPeriodoEscolar, Integer idTipoDispersion) {
		return em.createNamedQuery("Dispersion.findByCicloPeriodoAndTipo", DispersionDTO.class)
				.setParameter("idCicloEscolar", idCicloEscolar)
				.setParameter("idPeriodoEscolar", idPeriodoEscolar)
				.setParameter("idTipoDispersion", idTipoDispersion)		
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DispersionDTO> buscarPorCriterios(DispersionDTO dispersion) {
		final StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.DispersionDTO ");
		strQuery.append("( ");
		strQuery.append("d.idDispersion, ");
		strQuery.append("cce.idCicloEscolar, ");
		strQuery.append("cce.descripcion, ");
		strQuery.append("cpe.idPeriodoEscolar, ");
		strQuery.append("cpe.descripcion, ");
		strQuery.append("ctd.idTipoDispersion, ");
		strQuery.append("ctd.descripcion, ");
		strQuery.append("d.numBeneficiarios, ");
		strQuery.append("d.fechaEjecucion, ");
		strQuery.append("d.idUsuarioEjecucion, ");
		strQuery.append("d.fechaConclusion, ");
		strQuery.append("ced.idEstatusDispersion, ");
		strQuery.append("ced.descripcion, ");
		strQuery.append("d.aplicaDispersionPorcentaje, ");
		strQuery.append("d.aplicaDispersionNumero, ");
		strQuery.append("d.noAplicaDispersionPorcentaje, ");
		strQuery.append("d.noAplicaDispersionNumero, ");
		strQuery.append("d.fechaDescarga, ");
		strQuery.append("d.permiteEjecucion, ");
		strQuery.append("d.rutaArchivoPreescolar, ");
		strQuery.append("d.rutaArchivoPrimaria, ");
		strQuery.append("d.rutaArchivoSecundaria, ");
		strQuery.append("d.rutaArchivoLaboral ");
		strQuery.append(") ");
		strQuery.append("FROM Dispersion d ");
		strQuery.append("JOIN d.catCicloEscolar cce ");
		strQuery.append("JOIN d.catPeriodoEscolar cpe ");
		strQuery.append("JOIN d.catTipoDispersion ctd ");
		strQuery.append("JOIN d.catEstatusDispersion ced ");
		strQuery.append("WHERE 1 = 1 ");

		if (dispersion != null) {
			if (!BeanUtils.isNull(dispersion.getCatCicloEscolar())
					&& !BeanUtils.isNull(dispersion.getCatCicloEscolar().getIdCicloEscolar())) {
				strQuery.append(" AND cce.idCicloEscolar =:idCicloEscolar ");
			}
			if (!BeanUtils.isNull(dispersion.getCatPeriodoEscolar())
					&& !BeanUtils.isNull(dispersion.getCatPeriodoEscolar().getIdPeriodoEscolar())) {
				strQuery.append(" AND cpe.idPeriodoEscolar =:idPeriodoEscolar ");
			}
			if (!BeanUtils.isNull(dispersion.getCatTipoDispersion())
					&& !BeanUtils.isNull(dispersion.getCatTipoDispersion().getIdTipoDispersion())) {
				strQuery.append(" AND ctd.idTipoDispersion =:idTipoDispersion ");
			}
			if (!BeanUtils.isNull(dispersion.getCatEstatusDispersion())
					&& !BeanUtils.isNull(dispersion.getCatEstatusDispersion().getIdEstatusDispersion())) {
				strQuery.append(" AND ced.idEstatusDispersion =:idEstatusDispersion ");
			}
			strQuery.append(" ORDER by d.fechaEjecucion DESC ");
		}

		Query query = em.createQuery(strQuery.toString(), DispersionDTO.class);

		if (dispersion != null) {

			if (!BeanUtils.isNull(dispersion.getCatCicloEscolar())
					&& !BeanUtils.isNull(dispersion.getCatCicloEscolar().getIdCicloEscolar())) {
				query.setParameter("idCicloEscolar", dispersion.getCatCicloEscolar().getIdCicloEscolar());
			}
			if (!BeanUtils.isNull(dispersion.getCatPeriodoEscolar())
					&& !BeanUtils.isNull(dispersion.getCatPeriodoEscolar().getIdPeriodoEscolar())) {
				query.setParameter("idPeriodoEscolar", dispersion.getCatPeriodoEscolar().getIdPeriodoEscolar());
			}
			if (!BeanUtils.isNull(dispersion.getCatTipoDispersion())
					&& !BeanUtils.isNull(dispersion.getCatTipoDispersion().getIdTipoDispersion())) {
				query.setParameter("idTipoDispersion", dispersion.getCatTipoDispersion().getIdTipoDispersion());
			}
			if (!BeanUtils.isNull(dispersion.getCatEstatusDispersion())
					&& !BeanUtils.isNull(dispersion.getCatEstatusDispersion().getIdEstatusDispersion())) {
				query.setParameter("idEstatusDispersion", dispersion.getCatEstatusDispersion().getIdEstatusDispersion());
			}
		}

		return query.getResultList();
	}

	@Override
	public void actualizar(DispersionDTO e) {
		Dispersion dispersion= em.getReference(Dispersion.class, e.getIdDispersion());
		dispersion.setFechaDescarga(new Date());
		
		em.merge(dispersion);
		em.flush();
	}

	@Override
	public void guardar(DispersionDTO e) {
		Dispersion dispersion= new Dispersion();
		
		dispersion.setCatCicloEscolar(em.getReference(CatCicloEscolar.class, e.getCatCicloEscolar().getIdCicloEscolar()));
		dispersion.setCatPeriodoEscolar(em.getReference(CatPeriodoEscolar.class, e.getCatPeriodoEscolar().getIdPeriodoEscolar()));
		dispersion.setCatTipoDispersion(em.getReference(CatTipoDispersion.class, e.getCatTipoDispersion().getIdTipoDispersion()));
		dispersion.setCatEstatusDispersion(em.getReference(CatEstatusDispersion.class, e.getCatEstatusDispersion().getIdEstatusDispersion()));
		dispersion.setNumBeneficiarios(e.getNumBeneficiarios());
		dispersion.setFechaEjecucion(e.getFechaEjecucion());
		dispersion.setIdUsuarioEjecucion(e.getIdUsuarioEjecucion());
		dispersion.setFechaConclusion(e.getFechaConclusion());
		dispersion.setAplicaDispersionPorcentaje(e.getAplicaDispersionPorcentaje());
		dispersion.setAplicaDispersionNumero(e.getAplicaDispersionNumero());
		dispersion.setNoAplicaDispersionPorcentaje(e.getNoAplicaDispersionPorcentaje());
		dispersion.setNoAplicaDispersionNumero(e.getNoAplicaDispersionNumero());
		dispersion.setFechaDescarga(e.getFechaDescarga());
		dispersion.setPermiteEjecucion(e.getPermiteEjecucion());
		dispersion.setRutaArchivoPreescolar(e.getRutaArchivoPreescolar());
		dispersion.setRutaArchivoPrimaria(e.getRutaArchivoPrimaria());
		dispersion.setRutaArchivoSecundaria(e.getRutaArchivoSecundaria());
		dispersion.setRutaArchivoLaboral(e.getRutaArchivoLaboral());

		em.persist(dispersion);
		em.flush();
		
		e.setIdDispersion(dispersion.getIdDispersion());
	}
}