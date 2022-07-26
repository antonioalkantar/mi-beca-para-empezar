package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.LocalDate;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDispersionDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatPeriodoEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoDispersionDTO;
import mx.gob.cdmx.adip.beca.commons.dto.DispersionDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CatCicloEscolarDAO;
import mx.gob.cdmx.adip.beca.dao.CatEstatusDispersionDAO;
import mx.gob.cdmx.adip.beca.dao.CatPeriodoEscolarDAO;
import mx.gob.cdmx.adip.beca.dao.CatTipoDispersionDAO;
import mx.gob.cdmx.adip.beca.dao.DispersionDAO;

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
	private CatTipoDispersionDAO catTipoDispersionDAO;

	@Inject
	private CatEstatusDispersionDAO catEstatusDispersionDAO;

	@Inject
	private BeneficiarioDAO beneficiarioDAO;

	@Inject
	private DispersionDAO dispersionDAO;

	private List<CatCicloEscolarDTO> lstCatCicloEscolarDTO;

	private List<CatPeriodoEscolarDTO> lstCatPeriodoEscolarDTO;

	private List<CatTipoDispersionDTO> lstCatTipoDispersionDTO;

	private List<CatEstatusDispersionDTO> lstCatEstatusDispersionDTO;

	private List<DispersionDTO> dispersiones;

	private String cantidadBeneficiarios;

	private Long idCicloEscolar;

	private Long idPeriodoEscolar;

	private String mensajeTipoValidacion;

	// TODO Eliminar anotacion PostConstruct y descomentar return cuando se sepa a
	// traves de cual boton se accedera
	@PostConstruct
	public void init() {
		consultarCatalogos();
		establecerCicloConPeriodoPorDefecto();
		consultarCantBeneficiarios();
		consultarDispersiones();
//		return Constantes.RETURN_BANDEJA_VALIDACION + Constantes.JSF_REDIRECT;
	}

	private void consultarCatalogos() {
		lstCatCicloEscolarDTO = catCicloEscolarDAO.buscarTodos();
		lstCatPeriodoEscolarDTO = catPeriodoEscolarDAO.buscarTodos();
		lstCatTipoDispersionDTO = catTipoDispersionDAO.buscarTodos();
		lstCatEstatusDispersionDTO = catEstatusDispersionDAO.buscarTodos();
	}

	private void consultarCantBeneficiarios() {
		cantidadBeneficiarios = obtenerCantidadBeneficiarios();
	}

	private void consultarDispersiones() {
		dispersiones = dispersionDAO.buscarTodos();
	}

	private String obtenerCantidadBeneficiarios() {
		Long cantidadBeneficiarios = beneficiarioDAO.countBeneficiarios();
		String cantidadBeneficiariosConComma = NumberFormat.getNumberInstance(Locale.US).format(cantidadBeneficiarios);
		return cantidadBeneficiariosConComma;
	}

	private void establecerCicloConPeriodoPorDefecto() {
		String periodoActual = obtenerPeriodoActual();
		Optional<CatCicloEscolarDTO> optCiclo = lstCatCicloEscolarDTO.stream()
				.filter(ciclo -> ciclo.getDescripcion().equals(periodoActual)).findFirst();

		if (optCiclo.isPresent()) {
			idCicloEscolar = optCiclo.get().getIdCicloEscolar();
		}

		String mesActual = obtenerMesActual();
		Optional<CatPeriodoEscolarDTO> optPeriodo = lstCatPeriodoEscolarDTO.stream()
				.filter(periodo -> periodo.getDescripcion().equals(mesActual)).findFirst();

		if (optPeriodo.isPresent()) {
			idPeriodoEscolar = optPeriodo.get().getIdPeriodoEscolar();
		}
	}

	private String obtenerPeriodoActual() {
		LocalDate fechaActual = LocalDate.now();
		String periodoActual = String.valueOf(fechaActual.getYear()) + "-" + String.valueOf(fechaActual.getYear() + 1);
		return periodoActual;
	}

	private String obtenerMesActual() {
		String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };
		String mesActual = meses[LocalDate.now().getMonthOfYear() - 1];
		return mesActual;
	}

	public String obtenerDescripcionPeriodo() {
		Optional<CatPeriodoEscolarDTO> optPeriodo = lstCatPeriodoEscolarDTO.stream()
				.filter(periodo -> periodo.getIdPeriodoEscolar().equals(idPeriodoEscolar)).findFirst();
		return optPeriodo.isPresent() ? optPeriodo.get().getDescripcion() : "";
	}

	public String obtenerDescripcionCiclo() {
		Optional<CatCicloEscolarDTO> optCiclo = lstCatCicloEscolarDTO.stream()
				.filter(ciclo -> ciclo.getIdCicloEscolar().equals(idCicloEscolar)).findFirst();
		return optCiclo.isPresent() ? optCiclo.get().getDescripcion() : "";
	}

	public void ejecutarValidacion() {
		List<DispersionDTO> dispersionesOrdinarias = dispersionDAO.buscarPorCicloPeriodoAndTipo(idCicloEscolar,
				idPeriodoEscolar, Constantes.ID_TIPO_DISPERSION_ORDINARIA);
		if (dispersionesOrdinarias.size() == Constantes.SIZE_ARRAY_EMPTY) {
			mensajeTipoValidacion = Constantes.TIPO_VALIDACION_ORDINARIA;
			PrimeFaces.current().executeScript("PF('dlgConfirmacion').show();");
		} else {
			Long idEstatusDispersion = dispersionesOrdinarias.get(0).getCatEstatusDispersion().getIdEstatusDispersion();
			if (idEstatusDispersion == Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO
					|| idEstatusDispersion == Constantes.ID_ESTATUS_DISPERSION_PROCESANDO) {
				PrimeFaces.current().executeScript("PF('dlgValidacion').show();");
			} else if (idEstatusDispersion == Constantes.ID_ESTATUS_DISPERSION_CONCLUIDO) {
				List<DispersionDTO> dispersionesComplementarias = dispersionDAO.buscarPorCicloPeriodoAndTipo(
						idCicloEscolar, idPeriodoEscolar, Constantes.ID_TIPO_DISPERSION_COMPLEMENTARIA);
				if (dispersionesComplementarias.size() == Constantes.SIZE_ARRAY_EMPTY) {
					mensajeTipoValidacion = Constantes.TIPO_VALIDACION_COMPLEMENTARIA;
					PrimeFaces.current().executeScript("PF('dlgConfirmacion').show();");
				} else {
					Long idEstatusDispersionComplementaria = dispersionesComplementarias.get(0)
							.getCatEstatusDispersion().getIdEstatusDispersion();
					if (idEstatusDispersionComplementaria == Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO
							|| idEstatusDispersionComplementaria == Constantes.ID_ESTATUS_DISPERSION_PROCESANDO) {
						PrimeFaces.current().executeScript("PF('dlgValidacion').show();");
					} else if (idEstatusDispersionComplementaria == Constantes.ID_ESTATUS_DISPERSION_CONCLUIDO) {
						mensajeTipoValidacion = Constantes.TIPO_VALIDACION_COMPLEMENTARIA;
						PrimeFaces.current().executeScript("PF('dlgConfirmacion').show();");
					}

				}
			}
		}
	}

	public void insertarDispersion() {
		if (mensajeTipoValidacion.equals(Constantes.TIPO_VALIDACION_ORDINARIA)) {
			insertarDispersionOrdinaria();
			consultarDispersiones();
			PrimeFaces.current().executeScript("PF('dlgConfirmacion').hide();");
		} else if (mensajeTipoValidacion.equals(Constantes.TIPO_VALIDACION_COMPLEMENTARIA)) {
			insertarDispersionComplementaria();
			consultarDispersiones();
			PrimeFaces.current().executeScript("PF('dlgConfirmacion').hide();");
		}
	}

	public void insertarDispersionOrdinaria() {
		DispersionDTO dispersion = new DispersionDTO();
		dispersion.setCatCicloEscolar(new CatCicloEscolarDTO(idCicloEscolar));
		dispersion.setCatPeriodoEscolar(new CatPeriodoEscolarDTO(idPeriodoEscolar));
		dispersion.setCatTipoDispersion(new CatTipoDispersionDTO((long) Constantes.ID_TIPO_DISPERSION_ORDINARIA));
		dispersion.setCatEstatusDispersion(
				new CatEstatusDispersionDTO((long) Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO));
		dispersion.setNumBeneficiarios(beneficiarioDAO.countBeneficiarios());
		dispersion.setFechaEjecucion(new Date());
		dispersion.setIdUsuarioEjecucion(233l);
		dispersionDAO.guardar(dispersion);
	}

	public void insertarDispersionComplementaria() {
		DispersionDTO dispersion = new DispersionDTO();
		dispersion.setCatCicloEscolar(new CatCicloEscolarDTO(idCicloEscolar));
		dispersion.setCatPeriodoEscolar(new CatPeriodoEscolarDTO(idPeriodoEscolar));
		dispersion.setCatTipoDispersion(new CatTipoDispersionDTO(Constantes.ID_TIPO_DISPERSION_COMPLEMENTARIA));
		dispersion.setCatEstatusDispersion(new CatEstatusDispersionDTO(Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO));
		dispersion.setNumBeneficiarios(beneficiarioDAO.countBeneficiarios());
		dispersion.setFechaEjecucion(new Date());
		dispersion.setIdUsuarioEjecucion(233l);
		dispersionDAO.guardar(dispersion);
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

	public String getCantidadBeneficiarios() {
		return cantidadBeneficiarios;
	}

	public void setCantidadBeneficiarios(String cantidadBeneficiarios) {
		this.cantidadBeneficiarios = cantidadBeneficiarios;
	}

	public Long getIdCicloEscolar() {
		return idCicloEscolar;
	}

	public void setIdCicloEscolar(Long idCicloEscolar) {
		this.idCicloEscolar = idCicloEscolar;
	}

	public Long getIdPeriodoEscolar() {
		return idPeriodoEscolar;
	}

	public void setIdPeriodoEscolar(Long idPeriodoEscolar) {
		this.idPeriodoEscolar = idPeriodoEscolar;
	}

	public List<CatTipoDispersionDTO> getLstCatTipoDispersionDTO() {
		return lstCatTipoDispersionDTO;
	}

	public void setLstCatTipoDispersionDTO(List<CatTipoDispersionDTO> lstCatTipoDispersionDTO) {
		this.lstCatTipoDispersionDTO = lstCatTipoDispersionDTO;
	}

	public List<CatEstatusDispersionDTO> getLstCatEstatusDispersionDTO() {
		return lstCatEstatusDispersionDTO;
	}

	public void setLstCatEstatusDispersionDTO(List<CatEstatusDispersionDTO> lstCatEstatusDispersionDTO) {
		this.lstCatEstatusDispersionDTO = lstCatEstatusDispersionDTO;
	}

	public List<DispersionDTO> getDispersiones() {
		return dispersiones;
	}

	public void setDispersiones(List<DispersionDTO> dispersiones) {
		this.dispersiones = dispersiones;
	}

	public String getMensajeTipoValidacion() {
		return mensajeTipoValidacion;
	}

	public void setMensajeTipoValidacion(String mensajeTipoValidacion) {
		this.mensajeTipoValidacion = mensajeTipoValidacion;
	}
}
