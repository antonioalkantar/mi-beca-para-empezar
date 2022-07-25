package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.extensions.model.layout.LayoutOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.catalogos.dao.CatMunicipiosDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMunicipiosDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatNivelEducativoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CatEstatusDAO;
import mx.gob.cdmx.adip.beca.dao.CatNivelEducativoDAO;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;

@Named
@SessionScoped
public class BandejaFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 971533412084788573L;
	private static final Logger LOGGER = LoggerFactory.getLogger(BandejaFuncionarioBean.class);
	
	@Inject
	private SolicitudDAO solicitudDAO;
	
	@Inject
	private CatEstatusDAO catEstatusDAO;
	
	@Inject
	private CatMunicipiosDAO catMunicipiosDAO;
	
	@Inject
	private CatNivelEducativoDAO catNivelEducativoDAO;
	
	private LayoutOptions layoutOptionsOne;
	
	private List<SolicitudDTO> lstSolicitudes;
	private List<CatEstatusDTO> lstCatEstatusTutor;
	private List<CatMunicipiosDTO> lstCatMunicipios ;
	private List<CatNivelEducativoDTO> lstCatNiveles;
	
	//Filtros
	private String txtCurpTutor;
	private String txtCurpBeneficiario;
	private String txtFolioDeSolicitud;
	private String txtNumeroTarjeta;
	private String txtCct;
	private Integer filtroEstatusBeneficiario;
	private Integer filtroAlcaldias;
	private Integer filtroNivelEducativo;
	private Date filtroFechaInicio;
	private Date filtroFechaFin;
	private Date maxDate;
	
	//Colores de los Estatus de solicitud
	private String enProceso = "#54B0BC";
	private String pendienteValidacion = "#54B0BC";
	private String correccionParteCiudadano = "#ED9511";
	private String corregidaPorCiudadano = "#6EC844";
	private String aclaracionCircunstancia = "#4479C8";
	private String suspendida = "#ED9511";
	private String aprobadas = "#6EC844";
	
	

	public String init() {
		LOGGER.info("Inicia Bandeja del Funcionario");
		this.limpiarFiltro();
		inicializaOpcionesPanel();
		buscarSolicitudes(0);
		return Constantes.RETURN_HOME_BACKOFFICE_PAGE + Constantes.JSF_REDIRECT;
	}

	public void inicializaOpcionesPanel() {
		//1. set options for first layout
        layoutOptionsOne = new LayoutOptions();

        // options for all panes (center and west)
        LayoutOptions panes = new LayoutOptions();
        panes.addOption("slidable", false);
        panes.addOption("resizeWhileDragging", true);
        layoutOptionsOne.setPanesOptions(panes);

        // options for west pane
        final LayoutOptions west = new LayoutOptions();
        west.addOption("size", 150);
        west.addOption("minSize", 40);
        west.addOption("maxSize", 300);
        layoutOptionsOne.setWestOptions(west);
	}
	
	public void limpiarFiltro() {
		txtCurpTutor = "";
		txtCurpBeneficiario = "";
		txtFolioDeSolicitud = "";
		txtNumeroTarjeta = "";
		txtCct = "";
		filtroEstatusBeneficiario = null;
		filtroAlcaldias = null;
		filtroNivelEducativo = null;
		filtroFechaInicio = null;
		filtroFechaFin = null;
		maxDate = new Date();
		lstCatEstatusTutor = new ArrayList<CatEstatusDTO>();
		lstCatMunicipios = new ArrayList<CatMunicipiosDTO>();
		lstCatNiveles = new ArrayList<CatNivelEducativoDTO>();
		lstCatEstatusTutor = catEstatusDAO.buscarTodos();
		lstCatMunicipios = catMunicipiosDAO.buscarTodos();		
		lstCatNiveles = catNivelEducativoDAO.buscarTodos();
		
		this.buscarSolicitudes(0);
		
		//PrimeFaces.current().scrollTo("messages");
	}
	
	public void buscarSolicitudes(int pagina) {	
		lstSolicitudes = new ArrayList<SolicitudDTO>();		
		SolicitudDTO criterios = new SolicitudDTO();
		criterios.getTutorDTO().setCurp(txtCurpTutor);
		criterios.getBeneficiarioDTO().setCurpBeneficiario(txtCurpBeneficiario);
		criterios.setFolioSolicitud(txtFolioDeSolicitud);
		//criterios.getTarjetaDTO().setNumeroTarjeta(txtNumeroTarjeta);
		criterios.setCct(txtCct);
		criterios.getCatEstatusDTO().setIdEstatus(filtroEstatusBeneficiario);
		criterios.getCatMunicipiosDTO().setIdMunicipio(filtroAlcaldias);
		criterios.setFechaInicio(filtroFechaInicio);
		criterios.setFechaFin(filtroFechaFin);
		criterios.setCatNivelEducativoDTO(new CatNivelEducativoDTO(filtroNivelEducativo, "", false));
		
		lstSolicitudes = solicitudDAO.buscarPorCriterios(criterios, pagina);	
		//PrimeFaces.current().scrollTo("messages");
	}
	
	public void buscarSolicitudesCambioPagina(org.primefaces.event.data.PageEvent pageEvent) {
		this.buscarSolicitudes(pageEvent.getPage());
	}

	public LayoutOptions getLayoutOptionsOne() {
		return layoutOptionsOne;
	}

	public void setLayoutOptionsOne(LayoutOptions layoutOptionsOne) {
		this.layoutOptionsOne = layoutOptionsOne;
	}

	public List<SolicitudDTO> getLstSolicitudes() {
		return lstSolicitudes;
	}

	public void setLstSolicitudes(List<SolicitudDTO> lstSolicitudes) {
		this.lstSolicitudes = lstSolicitudes;
	}

	public String getTxtCurpTutor() {
		return txtCurpTutor;
	}

	public void setTxtCurpTutor(String txtCurpTutor) {
		this.txtCurpTutor = txtCurpTutor;
	}

	public String getTxtCurpBeneficiario() {
		return txtCurpBeneficiario;
	}

	public void setTxtCurpBeneficiario(String txtCurpBeneficiario) {
		this.txtCurpBeneficiario = txtCurpBeneficiario;
	}

	public String getTxtFolioDeSolicitud() {
		return txtFolioDeSolicitud;
	}

	public void setTxtFolioDeSolicitud(String txtFolioDeSolicitud) {
		this.txtFolioDeSolicitud = txtFolioDeSolicitud;
	}

	public String getTxtNumeroTarjeta() {
		return txtNumeroTarjeta;
	}

	public void setTxtNumeroTarjeta(String txtNumeroTarjeta) {
		this.txtNumeroTarjeta = txtNumeroTarjeta;
	}

	public String getTxtCct() {
		return txtCct;
	}

	public void setTxtCct(String txtCct) {
		this.txtCct = txtCct;
	}

	public List<CatEstatusDTO> getLstCatEstatusTutor() {
		return lstCatEstatusTutor;
	}

	public void setLstCatEstatusTutor(List<CatEstatusDTO> lstCatEstatusTutor) {
		this.lstCatEstatusTutor = lstCatEstatusTutor;
	}

	public List<CatMunicipiosDTO> getLstCatMunicipios() {
		return lstCatMunicipios;
	}

	public void setLstCatMunicipios(List<CatMunicipiosDTO> lstCatMunicipios) {
		this.lstCatMunicipios = lstCatMunicipios;
	}

	public Integer getFiltroEstatusBeneficiario() {
		return filtroEstatusBeneficiario;
	}

	public void setFiltroEstatusBeneficiario(Integer filtroEstatusBeneficiario) {
		this.filtroEstatusBeneficiario = filtroEstatusBeneficiario;
	}

	public Integer getFiltroAlcaldias() {
		return filtroAlcaldias;
	}

	public void setFiltroAlcaldias(Integer filtroAlcaldias) {
		this.filtroAlcaldias = filtroAlcaldias;
	}

	public Integer getFiltroNivelEducativo() {
		return filtroNivelEducativo;
	}

	public void setFiltroNivelEducativo(Integer filtroNivelEducativo) {
		this.filtroNivelEducativo = filtroNivelEducativo;
	}

	public Date getFiltroFechaInicio() {
		return filtroFechaInicio;
	}

	public void setFiltroFechaInicio(Date filtroFechaInicio) {
		this.filtroFechaInicio = filtroFechaInicio;
	}

	public Date getFiltroFechaFin() {
		return filtroFechaFin;
	}

	public void setFiltroFechaFin(Date filtroFechaFin) {
		this.filtroFechaFin = filtroFechaFin;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getPendienteValidacion() {
		return pendienteValidacion;
	}

	public String getCorreccionParteCiudadano() {
		return correccionParteCiudadano;
	}
	public String getCorregidaPorCiudadano() {
		return corregidaPorCiudadano;
	}

	public void setCorregidaPorCiudadano(String corregidaPorCiudadano) {
		this.corregidaPorCiudadano = corregidaPorCiudadano;
	}

	public String getAclaracionCircunstancia() {
		return aclaracionCircunstancia;
	}

	public String getSuspendida() {
		return suspendida;
	}

	public void setSuspendida(String suspendida) {
		this.suspendida = suspendida;
	}

	public String getAprobadas() {
		return aprobadas;
	}

	public void setAprobadas(String aprobadas) {
		this.aprobadas = aprobadas;
	}

	public List<CatNivelEducativoDTO> getLstCatNiveles() {
		return lstCatNiveles;
	}

	public void setLstCatNiveles(List<CatNivelEducativoDTO> lstCatNiveles) {
		this.lstCatNiveles = lstCatNiveles;
	}

	public String getEnProceso() {
		return enProceso;
	}

	public void setEnProceso(String enProceso) {
		this.enProceso = enProceso;
	}

	public void setPendienteValidacion(String pendienteValidacion) {
		this.pendienteValidacion = pendienteValidacion;
	}

	public void setCorreccionParteCiudadano(String correccionParteCiudadano) {
		this.correccionParteCiudadano = correccionParteCiudadano;
	}

	public void setCorreccionPorCiudadano(String correccionPorCiudadano) {
		this.corregidaPorCiudadano = correccionPorCiudadano;
	}

	public void setAclaracionCircunstancia(String aclaracionCircunstancia) {
		this.aclaracionCircunstancia = aclaracionCircunstancia;
	}
}
