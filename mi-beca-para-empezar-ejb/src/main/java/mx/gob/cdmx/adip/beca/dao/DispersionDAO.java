package mx.gob.cdmx.adip.beca.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.DispersionDTO;
import mx.gob.cdmx.adip.beca.model.CatCicloEscolar;
import mx.gob.cdmx.adip.beca.model.CatEstatusDispersion;
import mx.gob.cdmx.adip.beca.model.CatPeriodoEscolar;
import mx.gob.cdmx.adip.beca.model.CatTipoDispersion;
import mx.gob.cdmx.adip.beca.model.Dispersion;

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

	public List<DispersionDTO> buscarPorCicloPeriodoAndTipoDispersion(Long idCicloEscolar, Long idPeriodoEscolar, Long idTipoDispersion) {
		return em.createNamedQuery("Dispersion.findByCicloPeriodoAndTipoDispersion", DispersionDTO.class)
				.setParameter("idCicloEscolar", idCicloEscolar)
				.setParameter("idPeriodoEscolar", idPeriodoEscolar)
				.setParameter("idTipoDispersion", idTipoDispersion).getResultList();
	}
	

	
	@Override
	public List<DispersionDTO> buscarPorCriterios(DispersionDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DispersionDTO e) {
		// TODO Auto-generated method stub
		
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

		em.persist(dispersion);
		em.flush();
		
		e.setIdDispersion(dispersion.getIdDispersion());
	}
}