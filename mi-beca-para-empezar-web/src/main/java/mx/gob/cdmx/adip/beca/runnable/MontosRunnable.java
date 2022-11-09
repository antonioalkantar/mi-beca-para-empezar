package mx.gob.cdmx.adip.beca.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.application.MontosBean;
import mx.gob.cdmx.adip.beca.commons.dto.CatMontoApoyoDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.HomeDAO;

public class MontosRunnable implements Runnable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MontosRunnable.class);
	
	
	private final MontosBean montosBean;
	private List<CatMontoApoyoDTO> lstMontoApoyo;
	
	
	public MontosRunnable(final MontosBean montosBean) {
		this.montosBean = montosBean;
	}
	
	@Override
	public void run() {
		try {			
			HomeDAO homeDAO = new HomeDAO();
			lstMontoApoyo = new ArrayList<>();
			lstMontoApoyo = homeDAO.consultaMontos();
			montosBean.setPreescolar(obtenerMontoApoyo(Constantes.ID_PREESCOLAR));
			montosBean.setPrimaria(obtenerMontoApoyo(Constantes.ID_PRIMARIA));
			montosBean.setCentroAtencionMultiple(obtenerMontoApoyo(Constantes.ID_CAM_PRIMARIA));
			montosBean.setCicloEscolar(lstMontoApoyo.get(0).getCatCicloEscolarDTO().getDescripcion());
		} catch (Exception e) {
			LOGGER.error("------------------> Montos apooyo: Error al consultar los montos", e);
		}
		
	}
	/*
	 * Metodo para buscar en la lista los montos por nivel educativo
	 */
	public CatMontoApoyoDTO obtenerMontoApoyo(int IdNivelEdu) {
		Optional<CatMontoApoyoDTO> tmpMonto = lstMontoApoyo.stream().filter(
				monto -> monto.getCatNivelEducativoDTO().getIdNivel()== IdNivelEdu)
				.findAny();
		if (tmpMonto.isPresent()) {
			return  tmpMonto.get();
		}else {
			return null;
		}
		
	}

}
