package mx.gob.cdmx.adip.beca.bean;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatPeriodoEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CatCicloEscolarDAO;
import mx.gob.cdmx.adip.beca.dao.CatPeriodoEscolarDAO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

@Named("bandejaValidacionBean")
@SessionScoped
public class BandejaValidacionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4549482679007839050L;
	private static final Logger LOGGER = LoggerFactory.getLogger(BandejaValidacionBean.class);

	@Inject
	private CatCicloEscolarDAO catCicloEscolarDAO;

	@Inject
	private CatPeriodoEscolarDAO catPeriodoEscolarDAO;

	@Inject
	private BeneficiarioDAO beneficiarioDAO;

	private List<CatCicloEscolarDTO> lstCatCicloEscolarDTO;

	private List<CatPeriodoEscolarDTO> lstCatPeriodoEscolarDTO;

	private Long cantidadBeneficiarios;

	private Integer idCicloEscolar;

	private Integer idPeriodoEscolar;

	// TODO Eliminar cuando se sepa a traves de cual boton se accedera
	@PostConstruct
	public void init() {
		consultarCatsCicloPeriodo();
		cantidadBeneficiarios = beneficiarioDAO.countBeneficiarios();
		establecerCicloPeriodoPorDefecto();
	}

	public void establecerCicloPeriodoPorDefecto() {
		
		String periodoActual = obtenerPeriodoActual();
		Optional<CatCicloEscolarDTO> optCiclo = lstCatCicloEscolarDTO.stream()
		.filter(ciclo -> ciclo.getDescripcion().equals(periodoActual))
		.findFirst();
		
		if(optCiclo.isPresent()) {
			idCicloEscolar = optCiclo.get().getIdCicloEscolar();
		}
		
		String mesActual = obtenerMesActual();
		Optional<CatPeriodoEscolarDTO> optPeriodo = lstCatPeriodoEscolarDTO.stream()
		.filter(periodo -> periodo.getDescripcion().equals(mesActual))
		.findFirst();
		
		if(optPeriodo.isPresent()) {
			idPeriodoEscolar = optPeriodo.get().getIdPeriodoEscolar();
		}
	}

	private String obtenerPeriodoActual() {
		LocalDate fechaActual = LocalDate.now();
		String periodoActual = String.valueOf(fechaActual.getYear()) + "-" + String.valueOf(fechaActual.getYear() + 1);
		LOGGER.info("Periodo Actual: ", periodoActual);
		return periodoActual;
	}

	private String obtenerMesActual() {
		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };
		String mesActual = meses[LocalDate.now().getMonthOfYear()-1];
		LOGGER.info("Mes Actual: ", mesActual);
		return mesActual;
	}

//	public String init() {
//		consultarCatsCicloPeriodo();
//		cantidadBeneficiarios = beneficiarioDAO.countBeneficiarios();
//		return Constantes.RETURN_BANDEJA_VALIDACION + Constantes.JSF_REDIRECT;
//	}

	/**
	 * Consulta los catalogos de ciclo escolar y periodo
	 */
	private void consultarCatsCicloPeriodo() {
		lstCatCicloEscolarDTO = catCicloEscolarDAO.buscarTodos();
		lstCatPeriodoEscolarDTO = catPeriodoEscolarDAO.buscarTodos();
	}

	/**
	 * Ejecuta la validacion del ciclo y periodo escolar
	 */
	public void ejecutarValidacion() {
		LOGGER.info("ID CICLO ESCOLAR: "+ idCicloEscolar);
		LOGGER.info("ID PERIODO ESCOLAR: "+ idPeriodoEscolar);
	}

	public List<CatCicloEscolarDTO> getLstCatCicloEscolarDTO() {
		return lstCatCicloEscolarDTO;
	}

	public void setLstCatCicloEscolarDTO(List<CatCicloEscolarDTO> lstCatCicloEscolarDTO) {
		this.lstCatCicloEscolarDTO = lstCatCicloEscolarDTO;
	}

	public List<CatPeriodoEscolarDTO> getLstCatPeriodoEscolarDTO() {
		return lstCatPeriodoEscolarDTO;
	}

	public void setLstCatPeriodoEscolarDTO(List<CatPeriodoEscolarDTO> lstCatPeriodoEscolarDTO) {
		this.lstCatPeriodoEscolarDTO = lstCatPeriodoEscolarDTO;
	}

	public Long getCantidadBeneficiarios() {
		return cantidadBeneficiarios;
	}

	public void setCantidadBeneficiarios(Long cantidadBeneficiarios) {
		this.cantidadBeneficiarios = cantidadBeneficiarios;
	}

	public Integer getIdCicloEscolar() {
		return idCicloEscolar;
	}

	public void setIdCicloEscolar(Integer idCicloEscolar) {
		this.idCicloEscolar = idCicloEscolar;
	}

	public Integer getIdPeriodoEscolar() {
		return idPeriodoEscolar;
	}

	public void setIdPeriodoEscolar(Integer idPeriodoEscolar) {
		this.idPeriodoEscolar = idPeriodoEscolar;
	}
}
