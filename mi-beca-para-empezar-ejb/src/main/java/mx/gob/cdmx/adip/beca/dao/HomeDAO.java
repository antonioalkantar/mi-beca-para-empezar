package mx.gob.cdmx.adip.beca.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.common.util.BeanUtils;
import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMontoApoyoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatNivelEducativoDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

@Stateless
@LocalBean
public class HomeDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeDAO.class);
	
	public List<CatMontoApoyoDTO> consultaMontos() throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" c.id_monto_apoyo, c.monto, ");
		strQuery.append(" cne.id_nivel , cne.descripcion as descripcionCicloEscolar, ");
		strQuery.append(" cce.id_ciclo_escolar, cce.descripcion as descripcionNivel");
		strQuery.append(" FROM mibecaparaempezar.cat_monto_apoyo c ");
		strQuery.append(" JOIN mibecaparaempezar.cat_ciclo_escolar cce on c.id_ciclo_escolar = cce .id_ciclo_escolar ");
		strQuery.append(" JOIN mibecaparaempezar.cat_nivel_educativo cne on c.id_nivel_educativo = cne.id_nivel ");
		strQuery.append(" where c.estatus =true");
		strQuery.append(" and cce .estatus =true ");
		strQuery.append(" and cne.estatus =true ");
	
		List<CatMontoApoyoDTO> lstMontoApoyo = new ArrayList<CatMontoApoyoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);
		
		for (Object[] row : rows) {
			CatMontoApoyoDTO montoApoyo = new CatMontoApoyoDTO();
			montoApoyo.setIdMontoApoyo(Integer.valueOf(String.valueOf(row[0])));
			montoApoyo.setMonto(Double.parseDouble(String.valueOf(row[1])));
			montoApoyo.setCatCicloEscolarDTO(new CatCicloEscolarDTO((Integer)(row[4]), (String) row[5]));
			montoApoyo.setCatNivelEducativoDTO(new CatNivelEducativoDTO((Integer)(row[2]), (String) row[3]));			
			lstMontoApoyo.add(montoApoyo);
		}
		
		return lstMontoApoyo != null && !lstMontoApoyo.isEmpty() ? lstMontoApoyo : null;
	}
	
	
	/**
	 * Método auxiliar que realiza la ejecución de la consulta enviada.
	 * @param consulta
	 * @param nombreParametro
	 * @param valorParametro
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> ejecutarConsulta(String consulta, String nombreParametro, Long valorParametro) throws Exception {
		List<Object[]> rows = null; 
				
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(consulta);	
			if(BeanUtils.isNotNull(nombreParametro)) {
				query.setParameter(nombreParametro, valorParametro);
			}
			rows = query.getResultList();
			
			entityManager.getTransaction().commit();

		} catch (Throwable e) {
			if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("Error:  ", e);
			throw new Exception("Error en consulta. " + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

		return rows;
	}	
}
