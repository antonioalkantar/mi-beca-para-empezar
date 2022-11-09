package mx.gob.cdmx.adip.beca.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.catalogos.dao.CatCodigosPostalesDAO;
import mx.gob.cdmx.adip.beca.client.CurpRestClient;
import mx.gob.cdmx.adip.beca.client.EntidadEducativaSoapClient;
import mx.gob.cdmx.adip.beca.client.ObtenMasRegistrarTutor;
import mx.gob.cdmx.adip.beca.client.ObtenMasToken;
import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.commons.dto.BeneficiarioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatCodigosPostalesDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusBeneficiarioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMunicipiosDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatNivelEducativoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatParentescoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CurpDTO;
import mx.gob.cdmx.adip.beca.commons.dto.DetCuentaBeneficiarioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.PadronBeneficiariosContinuidadDTO;
import mx.gob.cdmx.adip.beca.commons.dto.PadronExternoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.commons.utils.DateUtils;
import mx.gob.cdmx.adip.beca.commons.utils.DateUtils.DateFormatStyle;
import mx.gob.cdmx.adip.beca.dao.BeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CatEstatusBeneficiarioDAO;
import mx.gob.cdmx.adip.beca.dao.CatParentescoDAO;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.PadronBeneficiariosContinuidadDAO;
import mx.gob.cdmx.adip.beca.dao.PadronExternoDAO;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;
import mx.gob.cdmx.adip.beca.util.Mensajes;
import mx.gob.cdmx.adip.beca.util.WebResources;
import mx.gob.cdmx.adip.beca.facade.BeneficiarioFacade;
import mx.gob.cdmx.adip.beca.soap.client.aefcm.MciResponse;

/**
 * @author Gabriel Moreno <gabriemos0309@gmail.com>
 */
@Named
@SessionScoped
public class RegistroBeneficiarioBean implements Serializable {
	
	private static final long serialVersionUID = -3083658363017912194L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroBeneficiarioBean.class);
	
	@Inject
	private BeneficiarioFacade beneficiarioFacade;

	@Inject 
	private TutorDAO tutorDAO;

	@Inject
	private SolicitudDAO solicitudDAO;
	
	@Inject
	private BeneficiarioDAO beneficiarioDAO;
	
	@Inject
	private CatParentescoDAO catParentescoDAO;
	
	@Inject
	private CatCodigosPostalesDAO catCodigosPostalesDAO;
	
	@Inject
	private CatEstatusBeneficiarioDAO catEstatusBeneficiarioDAO;
	
	@Inject
	private CrcBeneficiarioSolicitudDAO crcBeneficiarioSolicitudDAO;
	
	@Inject
	private PadronExternoDAO padronExternoDAO;
	
	@Inject
	private ConsultaBean consultaBean;
	
	@Inject 
	private BandejaTutorBean bandejaTutorBean;
	
	@Inject 
	private AuthenticatorBean authenticatorBean;

	@Inject 
	private BandejaFuncionarioBean bandejaFuncionarioBean;
	
	@Inject
	private EncuestaBeneficiarioBean encuestaBeneficiarioBean;
	
	@Inject 
	private PadronBeneficiariosContinuidadDAO padronBeneficiariosContinuidadDAO;
	
	private List<CatParentescoDTO> lstParentesco;
	private List<CatCodigosPostalesDTO> lstCatCodigosPostalesDTO;
	private List<CatEstatusBeneficiarioDTO> lstEstatusBeneficiarioDTO;
	private List<PadronExternoDTO> lstPadronExternoDTO;
	
	private TutorDTO tutor;
	private BeneficiarioDTO beneficiarioDTO;
	private CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO;
	private PadronBeneficiariosContinuidadDTO beneficiariosContinuidadDTO; 
	
	private String msjSeccionDos;
	private String msjSeccionTres;
	private Long idUsuarioLlaveCdmx;
	
	private boolean isValidador;
	private boolean soloLectura;
	
	private boolean manifiesto;
	private boolean revalidacion;
	
	private boolean infoBeneficiario;
	private boolean infoEncuesta;
	private boolean actualizacion;
	
	PadronExternoDTO padronExterno;
	
	private ObtenMasRegistrarTutor obtenMasRegistrarTutor;
	private ObtenMasToken obtenMasToken;

	/**
	 * Método que inicializa la pantalla para el registro del beneficiario
	 * 
	 * @return url a la que se va a direccionar
	 */
	public String init() {		
		crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
		tutor=tutorDAO.buscarPorId(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTutorDTO(tutor);
		actualizaSeccion(1);
		actualizacion = false;
		soloLectura=false;
		isValidador = false;
		manifiesto=false;
		msjSeccionDos=null; 
    	msjSeccionTres=null;  
    	revalidacion = false;
		cargaCatalogos();
		return Constantes.RETURN_REGISTRO_BENEFICIARIO_PAGE + Constantes.JSF_REDIRECT;
	}

	public String consulta(Long idSolicitud) {
		initConsulta(idSolicitud);
		return Constantes.RETURN_REGISTRO_BENEFICIARIO_PAGE + Constantes.JSF_REDIRECT;
	}
	
	public String consultaRevalidacion(Long idSolicitud) {
		initConsulta(idSolicitud);
		cargaCatalogos();
		revalidacion = true;
		return Constantes.RETURN_REVALIDACION_ESTATUS_BENEFICIARIO_PAGE + Constantes.JSF_REDIRECT;
	}

	public void consultaEncuestaNuevoCiclo(Long idSolicitud) {
		initConsulta(idSolicitud);
    	infoBeneficiario = false;
    	infoEncuesta = true;
    	soloLectura = false;
    	encuestaBeneficiarioBean.init();
    }

	public void initConsulta(Long idSolicitud) {
		
		crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
		crcBeneficiarioSolicitudDTO = crcBeneficiarioSolicitudDAO.consultaSolicitudesByFolioSolicitud(idSolicitud);
		if (crcBeneficiarioSolicitudDTO != null) {
			lstCatCodigosPostalesDTO = new ArrayList<CatCodigosPostalesDTO>();
			lstCatCodigosPostalesDTO = catCodigosPostalesDAO.buscarPorCodigoPostal(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCodigopostal());
			if (lstCatCodigosPostalesDTO != null && lstCatCodigosPostalesDTO.size() > 0)
				crcBeneficiarioSolicitudDTO.getSolicitudDTO().setEntidad(lstCatCodigosPostalesDTO.get(0).getCatMunicipiosDTO().getCatEstadosDTO().getDescripcion());
			else
				crcBeneficiarioSolicitudDTO.getSolicitudDTO().setEntidad(Constantes.ENTIDAD_FORANEO);
		}
		tutor=tutorDAO.buscarPorId(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTutorDTO(tutor);
		lstParentesco = new ArrayList<CatParentescoDTO>();
		lstParentesco = catParentescoDAO.buscarTodos();
		actualizacion = true;
    	infoBeneficiario = true;
    	infoEncuesta = false;
    	soloLectura=true;
    	manifiesto=false;
    	msjSeccionDos=null; 
    	msjSeccionTres=null;
    	revalidacion = false;
    	encuestaBeneficiarioBean.consulta(idSolicitud);
    	crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEdad(DateUtils.getEdadByFechaNacimiento(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getFechaNacimientoBeneficiario()));
	}	
	public String initFidegar(Long idTutor) {
		revalidacion = false;
		isValidador = true;
		soloLectura=false;
		crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
		tutor=tutorDAO.buscarPorId(idTutor);
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTutorDTO(tutor);
		actualizaSeccion(1);	
		actualizacion = false;
		manifiesto=false;
		msjSeccionDos=null; 
    	msjSeccionTres=null;
		cargaCatalogos();
		return Constantes.RETURN_REGISTRO_BENEFICIARIO_PAGE + Constantes.JSF_REDIRECT;
		
	}
	
	
	public void cargaCatalogos() {
		//se oculta la vista a modo consulta
		consultaBean.setConsulta(false);
		lstParentesco = new ArrayList<CatParentescoDTO>();
		lstParentesco = catParentescoDAO.buscarTodos();
		lstEstatusBeneficiarioDTO = catEstatusBeneficiarioDAO.buscarTodos();		
	}
	
	
	
    /**
     * Método para actualizar mostrar secciones dependiendo del panel seleccionado
     */
    public void actualizaSeccion(int seccion) {
        switch (seccion) {
        case 2:
        	if (isValidador) {
        		if (!validaCampos() || crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario() == null) {
        			msjSeccionDos =Constantes.MENSAJE_CAPTURA_DATOS_BENEFICIARIO;
        			break;
        		}
        	}
        	else {
        		if (crcBeneficiarioSolicitudDTO.getSolicitudDTO().getIdSolicitud() == null) {
        			msjSeccionDos =Constantes.MENSAJE_CAPTURA_DATOS_BENEFICIARIO;
        			break;
        		}
        	}
        	
			msjSeccionDos = null;
        	infoBeneficiario = false;
            infoEncuesta = true;
            encuestaBeneficiarioBean.init();
            break;
        default:
        	infoBeneficiario = true;
        	infoEncuesta = false;
        	if (crcBeneficiarioSolicitudDTO.getSolicitudDTO().getIdSolicitud() != null)
        		consulta(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getIdSolicitud());
            break;
        }
    }
    /*
     * Metodo para validar autoridad educativa y renapo (Nuevos Registros)
     */
    public void consultaCurpTutor() {
    	crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO()
		.setIdUsuarioLlaveCdmx(isValidador
				? crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getIdUsuarioLlaveCdmx()
				: bandejaTutorBean.getTutorDTO().getIdUsuarioLlaveCdmx());
    }
    /*
     * Metodo que revisa el padron de beneficiarios de continuidad si ya existe (Beneficiario Continuidad).
     */
    public boolean esBeneficiarioContinuidad(String curp) {    	
    	beneficiariosContinuidadDTO = consultaBeneficiarioContinuidad(curp);    	
    	return beneficiariosContinuidadDTO != null ? false : true;
    }
    /*
     * Metodo que valida que empaten curp del tutor del beneficiario registrado sea el mismo con la curp del Tutor de continuidad.
     * Es decir, valida que sea el mismo tutor del año pasado ya que si intenta registrarlo otro tutor tendrían que hacer el trámite de "cambio de tutor"  
     */
    public boolean validaCurpTutorContinuidad(String curpBeneficiario){
		boolean result = true;
		if (!crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp()
				.equals(beneficiariosContinuidadDTO.getCurpTutor())) {
			msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO	+ curpBeneficiario	+ Constantes.MENSAJE_VALIDA_CURP_BENEFICIARIO_TUTOR;
			result = false;
		}
		return result;
    }
    
    /*
     * Metodo para validar autoridad educativa y renapo (Nuevos Registros)
     */
    public void validarCurp() {    	
    	crcBeneficiarioSolicitudDTO.getSolicitudDTO().setEsNuevoRegistro(esBeneficiarioContinuidad(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario()));    	
    	
    	if (!crcBeneficiarioSolicitudDTO.getSolicitudDTO().getEsNuevoRegistro()) {
    		if (!validaCurpTutorContinuidad(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario())) {
				return;
			}
    	}
    	
		if (consultaAutoridadEducativa(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario(),
				crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsTutor())) {
			if (crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsExtranjero() == null
					|| crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsExtranjero() == false) {
				try {
					consultaCurp(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario(),
							crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsTutor());
				} catch (ConnectException e) {
					LOGGER.error("Error de conexion con el servicio CURP: ", e);
					lstParentesco = new ArrayList<CatParentescoDTO>();
					WebResources.addValidationMessage("msg_problemas_servicio_curp", true);
				} catch (URISyntaxException e) {
					LOGGER.error("Error en URL del servicio CURP: ", e);
					lstParentesco = new ArrayList<CatParentescoDTO>();
					WebResources.addValidationMessage("msg_problemas_servicio_curp", true);
				} catch (JSONException e) {
					crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEsExtranjero(true);
					crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
							.setNacionalidad(Constantes.NACIONALIDAD_EXTRANJERA);
				} catch (ParseException e) {
					LOGGER.error("Error de conexion con el servicio CURP: ", e);
				}
			}			

		}
		else {
			crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTutorDTO(tutor);
			msjSeccionTres = null;
		}
		
	}
    /**
     * busqueda de curp basada en la curp del tutor que inicio sesion
     */
	public void buscaTutorEsBeneficiario() {
		
		if (crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsTutor()) {
			    //Consulta si el registro ya existe en el padron de continuidad
				crcBeneficiarioSolicitudDTO.getSolicitudDTO().setEsNuevoRegistro(esBeneficiarioContinuidad(
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp()));
				//Si es de continuidad debe ser el mismo Tutor registrado en PadronContinuidad
				if (!crcBeneficiarioSolicitudDTO.getSolicitudDTO().getEsNuevoRegistro()) {
					if (!validaCurpTutorContinuidad(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp())) {
						return;
					}
				}	
    			if (consultaAutoridadEducativa(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp(),
						 crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsTutor())) {
    				crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatParentescoDTO().setIdParentesco(Constantes.ID_PARENTESCO_TUTOR);
    				if (crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsExtranjero() == null || crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsExtranjero() == false) {
    					try {
							consultaCurp(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp(), crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsTutor());
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatParentescoDTO().setIdParentesco(Constantes.ID_PARENTESCO_TUTOR);	    					
    					} catch (ConnectException e) {
    						LOGGER.error("Error de conexion con el servicio CURP: ", e);		
    						lstParentesco = new ArrayList<CatParentescoDTO>();
    						WebResources.addValidationMessage("msg_problemas_servicio_curp", true);
    					} catch (URISyntaxException e) {		
    						LOGGER.error("Error en URL del servicio CURP: ", e);
    						lstParentesco = new ArrayList<CatParentescoDTO>();
    						WebResources.addValidationMessage("msg_problemas_servicio_curp", true);
    					} catch (JSONException e) {	
    						//Si no devuelve nada la curp colocar como extrangero y liberar campos
    						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEsExtranjero(true);
    						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setNacionalidad(Constantes.NACIONALIDAD_EXTRANJERA);
					} catch (ParseException e) {
						LOGGER.error("Error de conexion con el servicio CURP: ", e);
					}
				}

			}
		} 
		else {
			crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTutorDTO(tutor);
			msjSeccionTres = null;
			msjSeccionDos = null;
		}

	}

	/*
	 * Metodo para consultar si el beneficiario es continuidad ademas y conocer su
	 * tutor registrado
	 */

	public PadronBeneficiariosContinuidadDTO consultaBeneficiarioContinuidad(String curp) {
		return padronBeneficiariosContinuidadDAO.buscarPorCurp(curp);
	}

	/*
	 * Metodo para actualizar/revalidar datos del beneficiario previamente cargado
	 */
	public void actualizaDatosCurp() {
		Date fnacimiento = crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getFechaNacimientoBeneficiario();
		if (consultaAutoridadEducativa(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario(),
				crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getEsTutor())) {
			solicitudDAO.actualizarEstatusBeneficiario(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		}
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setFechaNacimientoBeneficiario(fnacimiento);
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEdad(DateUtils.getEdadByFechaNacimiento(fnacimiento));

	}

	public boolean consultaAutoridadEducativa(String curpBusqueda, Boolean esTutor) {
		msjSeccionDos = null;
		msjSeccionTres = null;
		crcBeneficiarioSolicitudDTO.setBeneficiarioDTO(new BeneficiarioDTO());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEsTutor(esTutor);
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setExterno(false);
		if (curpBusqueda.length() < 18) {
			WebResources.addErrorMessage("msg_curp_validador", "form:curpBenef", false);
			return false;
		} else if (beneficiarioDAO.buscarPorCurp(curpBusqueda) != null && revalidacion != true) {
			// Se valida la curp no	tenga algun registro
			msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO + curpBusqueda
					+ Constantes.MENSAJE_VALIDA_CURP_BENEFICIARIO;
			return false;
		} else {
			try {
				MciResponse mciEntidadEducativaResponse = EntidadEducativaSoapClient.consultarCurp(curpBusqueda);
				padronExterno = new PadronExternoDTO();
				if (mciEntidadEducativaResponse.getEstatus().contains(Constantes.BENEFICIARIO_NO_LOCALIZADO)) {
					// revisamos en la bd el padron externo
					lstPadronExternoDTO = padronExternoDAO.buscarPorCurp(curpBusqueda);
					if (lstPadronExternoDTO != null) {
						padronExterno = lstPadronExternoDTO.get(0);
						if (padronExterno.getEstatus().equals(Constantes.BENEFICIARIO_INACTIVO_PAD_EXT)) {
							msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_SUSPENDIDO;
							return false;
						}
						if (revalidacion) {
							msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_SUSPENDIDO;
							return false;
						}
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setCurpBeneficiario(curpBusqueda);
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
								.setNombresBeneficiario(padronExterno.getNombres());
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
								.setPrimerApellidoBeneficiario(padronExterno.getPrimerApellido());
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
								.setSegundoApellidoBeneficiario(padronExterno.getSegundoApellido());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setRespuestaAutoridadEducativa(Constantes.ORIGEN_PADRON_EXTERNO);
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCct(padronExterno.getCct());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setNombre(padronExterno.getNombreCct());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCalle(padronExterno.getCalle());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setColonia(padronExterno.getColonia());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatMunicipiosDTO(new CatMunicipiosDTO(
								padronExterno.getIdMunicipio() == Constantes.CODIGO_MUNICIPIO_ZERO ? Constantes.ID_MUNICIPIO_FORANEO : padronExterno.getIdMunicipio()));
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCodigopostal(padronExterno.getCodigoPostal());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTurno(padronExterno.getTurno());
						switch (padronExterno.getNivelEducativo()) {
							case Constantes.DESC_PREESCOLAR:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO()
										.setCatNivelEducativoDTO(new CatNivelEducativoDTO(Constantes.ID_PREESCOLAR));
								break;
							case Constantes.DESC_PRIMARIA:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO()
										.setCatNivelEducativoDTO(new CatNivelEducativoDTO(Constantes.ID_PRIMARIA));
								break;
							case Constantes.DESC_SECUNDARIA:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO()
										.setCatNivelEducativoDTO(new CatNivelEducativoDTO(Constantes.ID_SECUNDARIA));
								break;
							case Constantes.DESC_PRIMARIA_ADULTOS_PAD_EXT:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatNivelEducativoDTO(
										new CatNivelEducativoDTO(Constantes.ID_PRIMARIA_ADULTOS));
								break;
							case Constantes.DESC_SECUNDARIA_ADULTOS_PAD_EXT:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatNivelEducativoDTO(
										new CatNivelEducativoDTO(Constantes.ID_SECUNDARIA_ADULTOS));
								break;
							case Constantes.DESC_CAM_PREESCOLAR_PAD_EXT:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatNivelEducativoDTO(
										new CatNivelEducativoDTO(Constantes.ID_CAM_PREESCOLAR));
								break;
							case Constantes.DESC_CAM_PRIMARIA_PAD_EXT:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO()
										.setCatNivelEducativoDTO(new CatNivelEducativoDTO(Constantes.ID_CAM_PRIMARIA));
								break;
							case Constantes.DESC_CAM_SECUNDARIA_PAD_EXT:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatNivelEducativoDTO(
										new CatNivelEducativoDTO(Constantes.ID_CAM_SECUNDARIA));
								break;
							case Constantes.DESC_CAM_LABORAL_PAD_EXT:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO()
										.setCatNivelEducativoDTO(new CatNivelEducativoDTO(Constantes.ID_CAM_LABORAL));
								break;
							default:
								crcBeneficiarioSolicitudDTO.getSolicitudDTO()
										.setCatNivelEducativoDTO(new CatNivelEducativoDTO(Constantes.ID_OTRO));
								break;
						}
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatNivelEducativoDTO()
								.setDescripcion(padronExterno.getNivelEducativo());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO()
								.setGradoEscolar((padronExterno.getGradoEscolar()));

						Optional<CatEstatusBeneficiarioDTO> tmpEstatus = lstEstatusBeneficiarioDTO.stream().filter(
								estatus -> estatus.getDescripcion().equalsIgnoreCase(padronExterno.getEstatus()))
								.findAny();

						if (tmpEstatus.isPresent()) {
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setIdEstatusBeneficiario(tmpEstatus.get().getIdEstatusBeneficiario());
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setDescripcion(tmpEstatus.get().getDescripcion());
						} else {
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setIdEstatusBeneficiario(Constantes.ID_ESTATUS_BENEFICIARIO_OTRO);
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setDescripcion(Constantes.DESCRIPCION_ESTATUS_BENEFICIARIO_OTRO);
						}
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setExterno(true);
					} else {
						msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_NO_LOCALIZADO;
						return false;
					}
				}

				if (crcBeneficiarioSolicitudDTO.getSolicitudDTO().getExterno()) {
					if (!padronExterno.getEstatus().contains(Constantes.BENEFICIARIO_ACTIVO_PAD_EXT)) {
						msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_SUSPENDIDO;
						return false;
					}
					msjSeccionTres = Constantes.MENSAJE_BENEFICIARIO_ESCUELA_PUBLICA;
					return true;
				} else {

					if (!mciEntidadEducativaResponse.getEstatus().contains(Constantes.BENEFICIARIO_ACTIVO)) {
						msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_SUSPENDIDO;
						return false;
					}

					if (mciEntidadEducativaResponse.getTipoEscuela()
							.contains(Constantes.BENEFICIARIO_ESCUELA_PRIVADA)) {
						msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_ESCUELA_PRIVADA;
						return false;
					}
					if (mciEntidadEducativaResponse.getNivelEducativoFIBIEDCDMXId() != Constantes.ID_OTRO) {
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setCurpBeneficiario(curpBusqueda);
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
								.setNombresBeneficiario(mciEntidadEducativaResponse.getNombres());
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
								.setPrimerApellidoBeneficiario(mciEntidadEducativaResponse.getPrimerApellido());
						crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
								.setSegundoApellidoBeneficiario(mciEntidadEducativaResponse.getSegundoApellido());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO()
								.setRespuestaAutoridadEducativa(mciEntidadEducativaResponse.toString());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCct(mciEntidadEducativaResponse.getCct());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setNombre(mciEntidadEducativaResponse.getNombreCCT());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCalle(mciEntidadEducativaResponse.getCalle());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setColonia(mciEntidadEducativaResponse.getColonia());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO()
								.setCatMunicipiosDTO(new CatMunicipiosDTO(CatMunicipiosDTO
										.getIdMunicipioByIdAlcaldiaAEFCM(mciEntidadEducativaResponse.getAlcaldiaId())));
						crcBeneficiarioSolicitudDTO.getSolicitudDTO()
								.setCodigopostal(mciEntidadEducativaResponse.getCodigoPostal());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTurno(mciEntidadEducativaResponse.getTurno());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatNivelEducativoDTO(
								new CatNivelEducativoDTO(mciEntidadEducativaResponse.getNivelEducativoFIBIEDCDMXId()));
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatNivelEducativoDTO()
								.setDescripcion(mciEntidadEducativaResponse.getNivelEducativo());
						crcBeneficiarioSolicitudDTO.getSolicitudDTO()
								.setGradoEscolar(String.valueOf(mciEntidadEducativaResponse.getGradoEscolar()));

						Optional<CatEstatusBeneficiarioDTO> tmpEstatus = lstEstatusBeneficiarioDTO.stream()
								.filter(estatus -> estatus.getDescripcion()
										.equalsIgnoreCase(mciEntidadEducativaResponse.getEstatus()))
								.findAny();

						if (tmpEstatus.isPresent()) {
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setIdEstatusBeneficiario(tmpEstatus.get().getIdEstatusBeneficiario());
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setDescripcion(tmpEstatus.get().getDescripcion());
						} else {
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setIdEstatusBeneficiario(Constantes.ID_ESTATUS_BENEFICIARIO_OTRO);
							crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusBeneficiarioDTO()
									.setDescripcion(Constantes.DESCRIPCION_ESTATUS_BENEFICIARIO_OTRO);
						}

						msjSeccionTres = Constantes.MENSAJE_BENEFICIARIO_ESCUELA_PUBLICA;
						return true;
						
					}else{
						//Los niveles que devuleva como (99) se consideran como no aplica
						msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_NIVEL_EDUCATIVO_NO_VALIDO;
						return false;
					}
				}

			} catch (Exception e) {
				msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_ERROR_VALIDACION;
				LOGGER.error("Error de conexion con el servicio de la autoridad educativa: ", e);
				return false;
			}

		}

	}

	public void consultaCurp(String curpBusqueda, Boolean esTutor)
			throws URISyntaxException, ConnectException, JSONException, ParseException {
		CurpRestClient curpRestClient = new CurpRestClient();
		CurpDTO curpDTO = curpRestClient.obtenerDatosCurp(curpBusqueda);
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setNombresBeneficiario(curpDTO.getNombre());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setCurpBeneficiario(curpDTO.getCurp());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setPrimerApellidoBeneficiario(curpDTO.getPrimerApellido());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setSegundoApellidoBeneficiario(curpDTO.getSegundoApellido());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setNacionalidad(curpDTO.getNacionalidad());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setFechaNacimientoBeneficiario(
				DateUtils.convertStringToDate(curpDTO.getFechaNacimiento(), DateFormatStyle.DMY_WITH_SLASH));
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEsTutor(esTutor);
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setEdad(DateUtils.getEdadByFechaNacimiento(
				crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getFechaNacimientoBeneficiario()));
		lstParentesco = new ArrayList<CatParentescoDTO>();
		lstParentesco = catParentescoDAO.buscarTodos();
	}

	public String guardarBeneficiario() {
		if (validaCampos()) {
			if (!actualizacion && !isValidador) {
				if (enviaDatosPagatodo()) {
					crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
							.setIdUsuarioLlaveCdmx(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());

					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatEstatusDTO()
							.setIdEstatus(Constantes.ID_ESTATUS_EN_PROCESO);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO()
							.setIdUsuarioLlaveCdmx(isValidador
									? crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO()
											.getIdUsuarioLlaveCdmx()
									: bandejaTutorBean.getTutorDTO().getIdUsuarioLlaveCdmx());
					crcBeneficiarioSolicitudDTO.getSolicitudDTO()
							.setFolioSolicitud(Constantes.ID_CLAVE_FOLIO_SOLICITUD);
					crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO()
							.setNumeroCuenta(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
									.getDetCuentaBeneficiarioDTO().getNumeroCuenta() != null
											? crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
													.getDetCuentaBeneficiarioDTO().getNumeroCuenta()
											: "");
					beneficiarioFacade.registrNuevoBeneficiario(crcBeneficiarioSolicitudDTO);
					actualizaSeccion(2);
				} else {
					crcBeneficiarioSolicitudDTO = new CrcBeneficiarioSolicitudDTO();
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setTutorDTO(tutor);
					msjSeccionTres = null;
				}
			} else if (!actualizacion && isValidador) {
				crcBeneficiarioSolicitudDTO.getBeneficiarioDTO()
						.setIdUsuarioLlaveCdmx(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
				crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setIdUsuarioLlaveCdmx(
						crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getIdUsuarioLlaveCdmx());
				crcBeneficiarioSolicitudDTO.getSolicitudDTO().setFolioSolicitud(Constantes.ID_CLAVE_FOLIO_SOLICITUD);
				actualizaSeccion(2);
			} else {
//				LOGGER.info("Entra a actualizar");
				beneficiarioFacade.actualizaBeneficiario(crcBeneficiarioSolicitudDTO);
				actualizaSeccion(2);
			}
			
		}
		return Constantes.RETURN_REGISTRO_BENEFICIARIO_PAGE + Constantes.JSF_REDIRECT;
	}

	/**
	 * Valida campos antes de guardar
	 */
	public boolean validaCampos() {
		boolean resultado = true;
		if (crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario() == null
				|| crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario() == null) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:curpBenef", false);
			resultado = false;
		} else if (crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getFechaNacimientoBeneficiario() == null) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:txtFechaNac", true);
			msjSeccionDos = Constantes.MENSAJE_CAPTURA_DATOS_BENEFICIARIO;
			resultado = false;
		} else if (crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatParentescoDTO().getIdParentesco() == 0) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:soParentesco", false);
			resultado = false;
		} else if (beneficiarioDAO
				.buscarPorCurp(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario()) != null
				&& !soloLectura) {// Se valida la curp no tenga algun registro
			msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO
					+ crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario()
					+ Constantes.MENSAJE_VALIDA_CURP_BENEFICIARIO;
			resultado = false;
		}
		return resultado;
	}

	public boolean enviaDatosPagatodo() {
		List<BeneficiarioDTO> lstBeneficiarios = new ArrayList<>();
		lstBeneficiarios.add(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO());
		boolean resultado = false;
		RespuestaDTO respuestaPagatodo = new RespuestaDTO();
		obtenMasRegistrarTutor = new ObtenMasRegistrarTutor();
		obtenMasToken = new ObtenMasToken();
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoFechaEnvio(new Date());
		crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().setDetCuentaBeneficiarioDTO(
				new DetCuentaBeneficiarioDTO(new BeneficiarioDTO(),""));
		try {
			respuestaPagatodo = obtenMasRegistrarTutor.registrarTutor(obtenMasToken.obtenerToken(),
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO(), lstBeneficiarios);
			switch (respuestaPagatodo.getCode()) {
				case 0:
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(true);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());					
					crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO().setNumeroCuenta(respuestaPagatodo.getCuentaBeneficiario().get(0));
					resultado = true;
					break;
				case 7:
					msjSeccionTres = null;
					msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_TELEFONO_TUTOR;
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					resultado = false;
					break;
				case 11:
					msjSeccionTres = null;
					msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_CORREO_TUTOR;
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					resultado = false;
					break;
				case 10:
					msjSeccionTres = null;
					msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO	+ crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario() + Constantes.MENSAJE_VALIDA_CURP_BENEFICIARIO_TUTOR;
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					resultado = false;
					break;
				default:
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					msjSeccionDos = Constantes.MENSAJE_BENEFICIARIO_ERROR_VALIDACION;
					LOGGER.error("Error Servicio obtenMas (PAGATODO), retorno un codigo: "+respuestaPagatodo.getStatus()+" "+
					respuestaPagatodo.getMensaje()+" Tutor:"+ crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp());
					resultado = false;
					break;
			}
		} catch (IOException e) {
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
					.setIdCodigoPagatodo((long) Constantes.CODIGO_PAGATODO_NO_ENVIADO);
			LOGGER.error("Error servicio pagatodo Beneficiario: "
					+ crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario() +" Tutor:"+ crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp()+ " --> IOException", e);
			resultado = true;
		}
		return resultado;

	}

	public boolean visualizarCaptcha() {
		return Environment.getAppProfile().compareTo(Constantes.PROFILE_LOCAL) == Constantes.INT_VALOR_CERO ? false : true;
	}

	/**
	 * Metodo para redireccionar dependiendo del origen de la vista
	 */
	public void redirect() {
		try {
			if (authenticatorBean.getRolTutor()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect(bandejaTutorBean.init());
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect(bandejaFuncionarioBean.init());
			}
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			LOGGER.error(WebResources.getBundleMsg("msj_error_rerireccionar"), e);
		}
	}

	/**
	 * Getters and Setters
	 */
	public boolean isInfoBeneficiario() {
		return infoBeneficiario;
	}

	public void setInfoBeneficiario(boolean infoBeneficiario) {
		this.infoBeneficiario = infoBeneficiario;
	}

	public boolean isInfoEncuesta() {
		return infoEncuesta;
	}

	public void setInfoEncuesta(boolean infoEncuesta) {
		this.infoEncuesta = infoEncuesta;
	}

	public String getMsjSeccionDos() {
		return msjSeccionDos;
	}

	public void setMsjSeccionDos(String msjSeccionDos) {
		this.msjSeccionDos = msjSeccionDos;
	}

	public String getMsjSeccionTres() {
		return msjSeccionTres;
	}

	public void setMsjSeccionTres(String msjSeccionTres) {
		this.msjSeccionTres = msjSeccionTres;
	}

	public Long getIdUsuarioLlaveCdmx() {
		return idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(Long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	public List<CatParentescoDTO> getLstParentesco() {
		return lstParentesco;
	}

	public void setLstParentesco(List<CatParentescoDTO> lstParentesco) {
		this.lstParentesco = lstParentesco;
	}

	public CrcBeneficiarioSolicitudDTO getCrcBeneficiarioSolicitudDTO() {
		return crcBeneficiarioSolicitudDTO;
	}

	public void setCrcBeneficiarioSolicitudDTO(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		this.crcBeneficiarioSolicitudDTO = crcBeneficiarioSolicitudDTO;
	}

	public boolean getActualizacion() {
		return actualizacion;
	}

	public void setActualizacion(boolean actualizacion) {
		this.actualizacion = actualizacion;
	}

	public boolean isValidador() {
		return isValidador;
	}

	public void setValidador(boolean isValidador) {
		this.isValidador = isValidador;
	}

	public boolean isSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public BeneficiarioDTO getBeneficiarioDTO() {
		return beneficiarioDTO;
	}

	public void setBeneficiarioDTO(BeneficiarioDTO beneficiarioDTO) {
		this.beneficiarioDTO = beneficiarioDTO;
	}

	public List<CatCodigosPostalesDTO> getLstCatCodigosPostalesDTO() {
		return lstCatCodigosPostalesDTO;
	}

	public void setLstCatCodigosPostalesDTO(List<CatCodigosPostalesDTO> lstCatCodigosPostalesDTO) {
		this.lstCatCodigosPostalesDTO = lstCatCodigosPostalesDTO;
	}

	public boolean isManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(boolean manifiesto) {
		this.manifiesto = manifiesto;
	}
}