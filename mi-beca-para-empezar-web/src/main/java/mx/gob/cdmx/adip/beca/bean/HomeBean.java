package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusBeneficiarioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMontoApoyoDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CatCicloEscolarDAO;
import mx.gob.cdmx.adip.beca.dao.CatMontoApoyoDAO;

@Named("homeBean")
@SessionScoped 
public class HomeBean implements Serializable{
	
	private static final long serialVersionUID = -8730597702580294091L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeBean.class);
	
	@Inject
	private CatMontoApoyoDAO catMontoApoyoDAO;
	
	private CatMontoApoyoDTO preescolar;
	private CatMontoApoyoDTO primaria;
	private CatMontoApoyoDTO CentroAtencionMultiple;
	String cicloEscolar;
	
	private List<CatMontoApoyoDTO> lstMontoApoyo;
	
	
	@PostConstruct
    public void init() {
		inicializarValores();
	}
	
	public void inicializarValores() {	
		lstMontoApoyo = new ArrayList<>();
		lstMontoApoyo = catMontoApoyoDAO.buscarTodos();
		
		cicloEscolar = lstMontoApoyo.get(0).getCatCicloEscolarDTO().getDescripcion();		
		preescolar =obtenerMontoApoyo(Constantes.ID_PREESCOLAR);
		primaria = obtenerMontoApoyo(Constantes.ID_PRIMARIA);
		CentroAtencionMultiple = obtenerMontoApoyo(Constantes.ID_CAM_PRIMARIA);
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

	public CatMontoApoyoDTO getPreescolar() {
		return preescolar;
	}

	public void setPreescolar(CatMontoApoyoDTO preescolar) {
		this.preescolar = preescolar;
	}

	public CatMontoApoyoDTO getPrimaria() {
		return primaria;
	}

	public void setPrimaria(CatMontoApoyoDTO primaria) {
		this.primaria = primaria;
	}

	public CatMontoApoyoDTO getCentroAtencionMultiple() {
		return CentroAtencionMultiple;
	}

	public void setCentroAtencionMultiple(CatMontoApoyoDTO centroAtencionMultiple) {
		CentroAtencionMultiple = centroAtencionMultiple;
	}

	public String getCicloEscolar() {
		return cicloEscolar;
	}

	public void setCicloEscolar(String cicloEscolar) {
		this.cicloEscolar = cicloEscolar;
	}



	
	


	
	
}
