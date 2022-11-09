package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.LocalDate;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.BeanUtils;
import mx.gob.cdmx.adip.beca.common.util.FileUtils;
import mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDispersionDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatNivelEducativoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatPeriodoEscolarDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoDispersionDTO;
import mx.gob.cdmx.adip.beca.commons.dto.DispersionDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CatCicloEscolarDAO;
import mx.gob.cdmx.adip.beca.dao.CatEstatusDispersionDAO;
import mx.gob.cdmx.adip.beca.dao.CatPeriodoEscolarDAO;
import mx.gob.cdmx.adip.beca.dao.CatTipoDispersionDAO;
import mx.gob.cdmx.adip.beca.dao.DispersionDAO;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;

@Named("bandejaValidacionBean")
@SessionScoped
public class BandejaValidacionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4549482679007839050L;
	private static final Logger LOGGER = LoggerFactory.getLogger(BandejaValidacionBean.class);

	@Inject
	private FacesContext facesContext;

	@Inject
	private AuthenticatorBean authenticatorBean;

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
	
	@Inject
	private SolicitudDAO solicitudDAO;

	private List<CatCicloEscolarDTO> lstCatCicloEscolarDTO;

	private List<CatPeriodoEscolarDTO> lstCatPeriodoEscolarDTO;

	private List<CatTipoDispersionDTO> lstCatTipoDispersionDTO;

	private List<CatEstatusDispersionDTO> lstCatEstatusDispersionDTO;

	private List<DispersionDTO> dispersiones;

	private String cantidadBeneficiarios;

	private Integer idCicloEscolar;

	private Integer idPeriodoEscolar;

	private String mensajeTipoValidacion;

	private Integer idCicloEscolarSel;

	private Integer idPeriodoSel;

	private Integer idTipoSel;

	private Integer idEstatusSel;

	private DispersionDTO dispersionSel;

	@PostConstruct
	public void inicializar() {
		try {
			if (authenticatorBean.getRolAdministrador()) {
				consultarCatalogos();
				establecerCicloConPeriodoPorDefecto();
				consultarCantBeneficiarios();
				consultarDispersiones();
			} else {
				facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT);
			}
		} catch (Exception e) {
			LOGGER.error("Error al inicializar.");
		}
	}

	public String init() {
		consultarCatalogos();
		establecerCicloConPeriodoPorDefecto();
		consultarCantBeneficiarios();
		consultarDispersiones();
		return Constantes.RETURN_BANDEJA_VALIDACION + Constantes.JSF_REDIRECT;
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
	
	private Long consultarSolicitudes() {
		SolicitudDTO criterios = new SolicitudDTO();
		criterios.getTutorDTO().setCurp("");
		criterios.getBeneficiarioDTO().setCurpBeneficiario("");
		criterios.setFolioSolicitud("");
		criterios.setCct("");
		criterios.getCatEstatusDTO().setIdEstatus(null);
		criterios.getCatMunicipiosDTO().setIdMunicipio(null);
		criterios.setFechaInicio(null);
		criterios.setFechaFin(null);
		criterios.setCatNivelEducativoDTO(new CatNivelEducativoDTO(null, "", false));
		criterios.setEsNuevoRegistro(null);
		List<SolicitudDTO> lstSolicitudes = solicitudDAO.buscarPorCriterios(criterios, 0);
		return (long) lstSolicitudes.size();
	}

	private String obtenerCantidadBeneficiarios() {
		String cantidadBeneficiariosConComma = NumberFormat.getNumberInstance(Locale.US).format(consultarSolicitudes());
		return cantidadBeneficiariosConComma;
	}

	public String obtenerFormatoNumero(long cantidad) {
		return NumberFormat.getNumberInstance(Locale.US).format(cantidad);
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
		List<DispersionDTO> dispersionesOrdinarias = dispersionDAO.buscarPorCicloPeriodoAndTipo(idCicloEscolar,idPeriodoEscolar, Constantes.ID_TIPO_DISPERSION_ORDINARIA);
		if (dispersionesOrdinarias.size() == Constantes.SIZE_ARRAY_EMPTY) {
			mensajeTipoValidacion = Constantes.TIPO_VALIDACION_ORDINARIA;
			PrimeFaces.current().executeScript("PF('dlgConfirmacion').show();");
		} else {
			Integer idEstatusDispersion = dispersionesOrdinarias.get(0).getCatEstatusDispersion().getIdEstatusDispersion();
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
					Integer idEstatusDispersionComplementaria = dispersionesComplementarias.get(0)
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

	public void ejecutarValidacionComplementaria() {

		List<DispersionDTO> dispersionesComplementarias = dispersionDAO.buscarPorCicloPeriodoAndTipo(
				dispersionSel.getCatCicloEscolar().getIdCicloEscolar(),
				dispersionSel.getCatPeriodoEscolar().getIdPeriodoEscolar(),
				Constantes.ID_TIPO_DISPERSION_COMPLEMENTARIA);
		if (dispersionesComplementarias.size() == Constantes.SIZE_ARRAY_EMPTY) {
			mensajeTipoValidacion = Constantes.TIPO_VALIDACION_COMPLEMENTARIA;
			PrimeFaces.current().executeScript("PF('dlgConfirmacion').show();");
		} else {
			Integer idEstatusDispersionComplementaria = dispersionesComplementarias.get(0).getCatEstatusDispersion()
					.getIdEstatusDispersion();
			if (idEstatusDispersionComplementaria == Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO
					|| idEstatusDispersionComplementaria == Constantes.ID_ESTATUS_DISPERSION_PROCESANDO) {
				PrimeFaces.current().executeScript("PF('dlgValidacion').show();");
			} else if (idEstatusDispersionComplementaria == Constantes.ID_ESTATUS_DISPERSION_CONCLUIDO) {
				mensajeTipoValidacion = Constantes.TIPO_VALIDACION_COMPLEMENTARIA;
				PrimeFaces.current().executeScript("PF('dlgConfirmacion').show();");
			}

		}

	}

	public StreamedContent descargarReporte() {
		LOGGER.info("Se descarga reporte.");
		List<String> rutas = obtenerRutasArchivos(dispersionSel);
		String nombreArchivo = dispersionSel.getCatCicloEscolar().getDescripcion() + "_"
				+ dispersionSel.getCatPeriodoEscolar().getDescripcion() + "_"
				+ dispersionSel.getCatTipoDispersion().getDescripcion() + "_" + Constantes.NOMBRE_ARCHIVO_REPORTE
				+ Constantes.EXTENSION_ZIP;
		StreamedContent archivoReporte = DefaultStreamedContent.builder().name(nombreArchivo)
				.contentType(Constantes.CONTENT_TYPE_ZIP).stream(() -> FileUtils.obtenerZipInputStream(rutas)).build();
		dispersionDAO.actualizar(dispersionSel);
		FacesContext.getCurrentInstance().responseComplete();
		return archivoReporte;

	}

	private List<String> obtenerRutasArchivos(DispersionDTO dispersion) {
		List<String> rutas = new ArrayList<>();
		String rutaArchivosDispersiones = Environment.getRutaArchivosDispersiones();
		if (BeanUtils.isNotNull(dispersion.getRutaArchivoPreescolar())) {
			rutas.add(rutaArchivosDispersiones + dispersion.getRutaArchivoPreescolar());
		}
		if (BeanUtils.isNotNull(dispersion.getRutaArchivoPrimaria())) {
			rutas.add(rutaArchivosDispersiones + dispersion.getRutaArchivoPrimaria());
		}
		if (BeanUtils.isNotNull(dispersion.getRutaArchivoSecundaria())) {
			rutas.add(rutaArchivosDispersiones + dispersion.getRutaArchivoSecundaria());
		}
		if (BeanUtils.isNotNull(dispersion.getRutaArchivoLaboral())) {
			rutas.add(rutaArchivosDispersiones + dispersion.getRutaArchivoLaboral());
		}
		return rutas;
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
		dispersion.setCatTipoDispersion(new CatTipoDispersionDTO(Constantes.ID_TIPO_DISPERSION_ORDINARIA));
		dispersion.setCatEstatusDispersion(
				new CatEstatusDispersionDTO(Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO));
		dispersion.setNumBeneficiarios(consultarSolicitudes());
		dispersion.setFechaEjecucion(new Date());
		dispersion.setIdUsuarioEjecucion(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		dispersion.setPermiteEjecucion(true);
		dispersionDAO.guardar(dispersion);
	}

	public void insertarDispersionComplementaria() {
		DispersionDTO dispersion = new DispersionDTO();
		dispersion.setCatCicloEscolar(new CatCicloEscolarDTO(idCicloEscolar));
		dispersion.setCatPeriodoEscolar(new CatPeriodoEscolarDTO(idPeriodoEscolar));
		dispersion.setCatTipoDispersion(new CatTipoDispersionDTO(Constantes.ID_TIPO_DISPERSION_COMPLEMENTARIA));
		dispersion.setCatEstatusDispersion(new CatEstatusDispersionDTO(Constantes.ID_ESTATUS_DISPERSION_EN_PROCESO));
		dispersion.setNumBeneficiarios(consultarSolicitudes());
		dispersion.setFechaEjecucion(new Date());
		dispersion.setIdUsuarioEjecucion(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		dispersion.setPermiteEjecucion(true);
		dispersionDAO.guardar(dispersion);
	}

	public void filtrarDispersiones() {
		DispersionDTO dispersionFiltro = new DispersionDTO();
		dispersionFiltro.setCatCicloEscolar(idCicloEscolarSel == 0l ? null : new CatCicloEscolarDTO(idCicloEscolarSel));
		dispersionFiltro.setCatPeriodoEscolar(idPeriodoSel == 0l ? null : new CatPeriodoEscolarDTO(idPeriodoSel));
		dispersionFiltro.setCatTipoDispersion(idTipoSel == 0l ? null : new CatTipoDispersionDTO(idTipoSel));
		dispersionFiltro.setCatEstatusDispersion(idEstatusSel == 0l ? null : new CatEstatusDispersionDTO(idEstatusSel));
		dispersiones = dispersionDAO.buscarPorCriterios(dispersionFiltro);
	}

	public void limpiarFiltro() {
		idCicloEscolarSel = 0;
		idPeriodoSel = 0;
		idTipoSel = 0;
		idEstatusSel = 0;
		consultarDispersiones();
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

	public Integer getIdCicloEscolarSel() {
		return idCicloEscolarSel;
	}

	public void setIdCicloEscolarSel(Integer idCicloEscolarSel) {
		this.idCicloEscolarSel = idCicloEscolarSel;
	}

	public Integer getIdPeriodoSel() {
		return idPeriodoSel;
	}

	public void setIdPeriodoSel(Integer idPeriodoSel) {
		this.idPeriodoSel = idPeriodoSel;
	}

	public Integer getIdTipoSel() {
		return idTipoSel;
	}

	public void setIdTipoSel(Integer idTipoSel) {
		this.idTipoSel = idTipoSel;
	}

	public Integer getIdEstatusSel() {
		return idEstatusSel;
	}

	public void setIdEstatusSel(Integer idEstatusSel) {
		this.idEstatusSel = idEstatusSel;
	}

	public DispersionDTO getDispersionSel() {
		return dispersionSel;
	}

	public void setDispersionSel(DispersionDTO dispersionSel) {
		this.dispersionSel = dispersionSel;
	}
}
