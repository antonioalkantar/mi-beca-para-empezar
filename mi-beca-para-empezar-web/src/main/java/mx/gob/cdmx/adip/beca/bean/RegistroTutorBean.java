package mx.gob.cdmx.adip.beca.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.acceso.bean.AuthenticatorBean;
import mx.gob.cdmx.adip.beca.catalogos.dao.CatCodigosPostalesDAO;
import mx.gob.cdmx.adip.beca.client.DocumentoLlaveRestClient;
import mx.gob.cdmx.adip.beca.client.IneRestClient;
import mx.gob.cdmx.adip.beca.client.IneRestClient.IneException;
import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CatComprobanteDomicilioDAO;
import mx.gob.cdmx.adip.beca.dao.CatIdentificacionOficialDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;
import mx.gob.cdmx.adip.beca.dao.TutorExtranjeroDAO;
import mx.gob.cdmx.adip.beca.dao.UsuarioDAO;
import mx.gob.cdmx.adip.beca.facade.TutorFacade;
import mx.gob.cdmx.adip.beca.commons.dto.CatAsentamientosDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatCodigosPostalesDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatComprobanteDomicilioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatIdentificacionOficialDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMunicipiosDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoIneDTO;
import mx.gob.cdmx.adip.beca.commons.dto.DocumentoLlaveDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorExtranjeroDTO;
import mx.gob.cdmx.adip.beca.commons.dto.UserDTO;
import mx.gob.cdmx.adip.ine.dto.IneDTO;
import mx.gob.cdmx.adip.beca.util.BeanUtils;
import mx.gob.cdmx.adip.beca.util.Mensajes;
import mx.gob.cdmx.adip.beca.util.WebResources;

/**
 * @author Gabriel Moreno <gabriemos0309@gmail.com>
 */
@Named
@SessionScoped
public class RegistroTutorBean implements Serializable {

	private static final long serialVersionUID = 2198197462917339642L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroTutorBean.class);
	
	@Inject
	private TutorFacade tutorFacade;
	
	@Inject
	private CatIdentificacionOficialDAO catIdentificacionOficialDAO;
	
	@Inject
	private CatComprobanteDomicilioDAO catComprobanteDomicilioDAO;
	
	@Inject
	private CatCodigosPostalesDAO catCodigosPostalesDAO;
	
	@Inject
	private TutorExtranjeroDAO tutorExtranjeroDAO;
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject 
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private BandejaTutorBean bandejaTutorBean;
	
	@Inject
	private UsuarioDAO usuarioDAO;
		

	private List<CatIdentificacionOficialDTO> lstIdentificacionOficial;
	private List<CatComprobanteDomicilioDTO> lstCatComprobanteDomicilioDTO;
	private List<CatAsentamientosDTO> listColonia;
	
	private TutorDTO tutorDTO;

	private Integer ID_IDENTIFICACION_OFICIAL = Constantes.ID_IDENTIFICACION_OFICIAL;
	private Integer ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA = Constantes.ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA;
	private Integer ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO = Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO;
	private Integer ID_ESTATUS_EN_PROCESO = Constantes.ID_ESTATUS_EN_PROCESO;
	private Integer ID_ESTATUS_APROBADA = Constantes.ID_ESTATUS_APROBADA;
	private Integer ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO = Constantes.ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO;
	private Integer ID_ESTATUS_PENDIENTE_VALIDACION = Constantes.ID_ESTATUS_PENDIENTE_VALIDACION;
	private Integer ID_ESTATUS_SUSPENDIDAS = Constantes.ID_ESTATUS_SUSPENDIDAS;
	
	private boolean validarRegistro;
	private boolean viewDocIdentifica;
	private boolean viewDocDomicilio;
	private boolean disableInformacionGeneral;
	private boolean disableInformacionDireccion;
	private boolean capturaTutor;
	private boolean avisoPrivacidad;
	private boolean viewAvisoPrivacidad;
	private Boolean curpExtranjero;
    private String curpExtranjeroSt;

	private int modeloINE;
	private int idEstatus;
	private Long idSolicitud;
	private UserDTO userDTO;
	
	/**
	 * Método que inicializa la pantalla para el registro del tutor desde bandeja de
	 * tutor
	 * 
	 * @return url a la que se va a direccionar
	 */
	public String initBandejaTutor(Long idUsuarioLaveCdmx) {
		capturaTutor = true;
		viewDocIdentifica = true;
		viewDocDomicilio = true;
		disableInformacionGeneral = false;
		disableInformacionDireccion = false;
		tutorDTO = bandejaTutorBean.getTutorDTO();
		tutorDTO.setIdUsuarioLlaveCdmx(idUsuarioLaveCdmx);
		tutorDTO.setCatMunicipiosDTO(new CatMunicipiosDTO());
		consultaCatalogos();
		curpExtranjeroSt = null;

		asignaDocumentoDesdeLlave();
		if (tutorDTO.isDocumentoLlave()) {
			disableInformacionGeneral = true;
			viewDocIdentifica = false;
		}

		viewAvisoPrivacidad = true;
		avisoPrivacidad = false;
		idEstatus = 0;
		return Constantes.RETURN_REGISTRO_TUTOR_PAGE + Constantes.JSF_REDIRECT;
	}
	

	/**
	 * Método que inicializa la pantalla para el registro del tutor únicamente para
	 * consulta
	 * 
	 * @return url a la que se va a direccionar
	 */
	public String consultaTutor(Long idUsuarioLaveCdmx) {
		capturaTutor = false;
		viewDocIdentifica = false;
		viewDocDomicilio = false;
		disableInformacionGeneral = true;
		disableInformacionDireccion = true;
		tutorDTO = bandejaTutorBean.getTutorDTO();
		consultaCatalogos();
		consultaCodigosPostales();
		if(tutorDTO.getCatEstatusDTO().getIdEstatus() == ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO) {
			disableInformacionGeneral = tutorDTO.getInformacionGralCorrecto() != null ? tutorDTO.getInformacionGralCorrecto() : true;
			disableInformacionDireccion = tutorDTO.getDomicilioCorrecto() != null ? tutorDTO.getDomicilioCorrecto() : true;
			viewDocIdentifica = !disableInformacionGeneral;
			viewDocDomicilio = !disableInformacionDireccion;
		}
		avisoPrivacidad = true;
		viewAvisoPrivacidad = false;
		idEstatus = 0;
		return Constantes.RETURN_REGISTRO_TUTOR_PAGE + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Método que inicializa la pantalla para el registro del tutor únicamente para
	 * consulta con todo deshabilitado
	 * 
	 * @return url a la que se va a direccionar
	 */
	public void consultaTutorDeshabilitado(Long idUsuarioLaveCdmx) {
		capturaTutor = false;
		viewDocIdentifica = false;
		viewDocDomicilio = false;
		disableInformacionGeneral = true;
		disableInformacionDireccion = true;
		tutorDTO = tutorDAO.buscarPorId(idUsuarioLaveCdmx);
		consultaCatalogos();
		consultaCodigosPostales();	
		avisoPrivacidad = true;
		viewAvisoPrivacidad = false;
		idEstatus = 0;
	}
	

	/**
	 * Método que inicializa la pantalla para el registro del tutor para la validación de información
	 * 
	 * @return url a la que se va a direccionar
	 */
	public String validaTutor(Long idUsuarioLaveCdmx, Long idSolicitudBandeja) {
		capturaTutor = false;
		validarRegistro = true;
		viewDocIdentifica = true;
		viewDocDomicilio = true;
		disableInformacionGeneral = false;
		disableInformacionDireccion = false;
		idSolicitud = idSolicitudBandeja;
		tutorDTO = tutorDAO.buscarPorId(idUsuarioLaveCdmx);
		consultaCatalogos();
		consultaCodigosPostales();
		avisoPrivacidad = true;
		viewAvisoPrivacidad = false;
		idEstatus = 0;
		return Constantes.RETURN_VALIDACION_TUTOR + Constantes.JSF_REDIRECT;
	}

	/**
	 * Método para consultar catálogos en DB
	 */
	public void consultaCatalogos() {
		lstIdentificacionOficial = catIdentificacionOficialDAO.consultaIdentificacionByNacionalidad(tutorDTO.isEsExtranjero());
		lstCatComprobanteDomicilioDTO = catComprobanteDomicilioDAO.consultaComprobanteDomByEstatusActive();
	}

	/**
	 * Método para ejecutar dialog cuando seleccione INE como Identificación oficial
	 */
	public void verificaIdentificacion() {
		modeloINE = 0;
		inicializaDatosINE();
		if (tutorDTO.getCatIdentificacionOficialDTO().getIdIdentificacion() == ID_IDENTIFICACION_OFICIAL) {
			PrimeFaces.current().ajax().update("formINE:pgDialogINE");
			PrimeFaces.current().executeScript("PF('dialogINE').show();");
		}
	}

	/**
	 * Método para ejecutar dialog cuando seleccione INE como Identificación oficial
	 */
	public void validaINE() {
		if (modeloINE != 0) {
			if (modeloINE == 3) {
				if(tutorDTO.getClaveElector().length() < 18) {
					WebResources.addErrorMessage("msj_erro_ce_size", "formINE:pgMsgDialogINE", false);
					return;
				}else if(tutorDTO.getNumeroEmision().length() < 2) {
					WebResources.addErrorMessage("msj_erro_cne_size", "formINE:pgMsgDialogINE", false);
					return;
				}else if(tutorDTO.getOcr() == null ) {
					WebResources.addErrorMessage("msj_erro_ocr_size", "formINE:pgMsgDialogINE", false);
					return;
				}
				tutorDTO.setCic(null);
			} else {
				tutorDTO.setClaveElector(null);
				tutorDTO.setNumeroEmision(null);
				tutorDTO.setOcr(null);
			}
			IneDTO ine = null;
			try {
				IneRestClient ineClient = new IneRestClient();
				ine = ineClient.obtenerDatosINE(tutorDTO.getCic(), tutorDTO.getClaveElector(),
						tutorDTO.getNumeroEmision(), tutorDTO.getOcr(), modeloINE, tutorDTO.getCurp());
				if (ine != null && ine.getTipoSituacionRegistral() != null
						&& ine.getTipoSituacionRegistral().equalsIgnoreCase(Constantes.ESTATUS_VIGENTE_INE)
						&& ine.curpCorrecto() != null && ine.curpCorrecto() == true) {
					tutorDTO.setCatTipoIneDTO(new CatTipoIneDTO(modeloINE));
					PrimeFaces.current().executeScript("PF('dialogINE').hide();");
					PrimeFaces.current().executeScript("PF('dialogDatosCorrectos').show();");
					PrimeFaces.current().executeScript("rcDatosCorrectos()");
				} else {
					WebResources.addErrorMessage("msj_datos_incorrectos_ine", "formINE:pgMsgDialogINE", false);
				}
			} catch (IneException e) {
				//LOGGER.error("Ocurrió un error al consultar el servicio del INE: ", e); //No es necesario imprimir en el log porque cuando marca una IneException es por una regla de Negocio y si fue error inesperado ya lo imprimió en el log
				WebResources.addErrorMessage(e.getMessage(), "formINE:pgMsgDialogINE", false);
			}
		} else {
			WebResources.addErrorMessage("msj_seleccionar_ine", "formINE:pgMsgDialogINE", false);
		}
	}

	/**
	 * Método para inicializar datos INE
	 */
	public void inicializaDatosINE() {
		tutorDTO.setCic(null);
		tutorDTO.setClaveElector(null);
		tutorDTO.setNumeroEmision(null);
		tutorDTO.setOcr(null);
	}

	/**
	 * Método accionado por el botón cancelar del dialog de INE
	 */
	public void cancelarDialogINE() {
		tutorDTO.setCatIdentificacionOficialDTO(new CatIdentificacionOficialDTO());
		inicializaDatosINE();
	}

	/**
	 * Método que realiza la consulta de colonia y alcaldía mediante el código
	 * postal.
	 */
	public void consultaCodigosPostales() {
		listColonia = new ArrayList<CatAsentamientosDTO>();
		tutorDTO.setCatMunicipiosDTO(new CatMunicipiosDTO());

		if (tutorDTO.getCodigoPostal() != null && tutorDTO.getCodigoPostal().length() == 5) {
			List<CatCodigosPostalesDTO> listado = catCodigosPostalesDAO
					.buscarPorCodigoPostal(tutorDTO.getCodigoPostal());
			listColonia = new ArrayList<>();
			if (listado.size() == 0) {
				tutorDTO.setCodigoPostal(null);
				tutorDTO.setCatAsentamientosDTO(new CatAsentamientosDTO());
				tutorDTO.setCatMunicipiosDTO(new CatMunicipiosDTO());
				WebResources.addErrorMessage("msg_no_existe_cp", "form:txtCP", false);
			} else {
				listado.forEach(cp -> {
					Optional<CatAsentamientosDTO> optColonia = listColonia.stream()
							.filter(col -> col.getIdAsentamiento() == cp.getCatAsentamientosDTO().getIdAsentamiento())
							.findAny();
					if (!optColonia.isPresent()) {
						listColonia.add(cp.getCatAsentamientosDTO());
					}
				});
				if (listColonia.size() == 1) {
					tutorDTO.setCatAsentamientosDTO(listColonia.get(0));
				} else {
					if (tutorDTO.getCatAsentamientosDTO() != null
							&& tutorDTO.getCatAsentamientosDTO().getIdAsentamiento() != null) {
						Optional<CatAsentamientosDTO> optCatAsentamiento = listColonia.stream()
								.filter(asentamiento -> asentamiento.getIdAsentamiento().compareTo(
										tutorDTO.getCatAsentamientosDTO().getIdAsentamiento().intValue()) == 0)
								.findFirst();
						if (optCatAsentamiento.isPresent()) {
							tutorDTO.setCatAsentamientosDTO(optCatAsentamiento.get());
						}
					}
				}
				
				tutorDTO.setCatMunicipiosDTO(
						listado.size() > 0 ? listado.get(0).getCatMunicipiosDTO() : new CatMunicipiosDTO());
			}
		} else {
			tutorDTO.setCodigoPostal(null);
			tutorDTO.setCatAsentamientosDTO(new CatAsentamientosDTO());
			tutorDTO.setCatMunicipiosDTO(new CatMunicipiosDTO());
			WebResources.addErrorMessage("msg_cp_incorrecto", "form:txtCP", false);
		}
	}

	/**
	 * Método auxiliar que asigna el documento adjunto para la identificación oficial
	 * 
	 * Por tema de rendimiento cuando el sistema tiene miles de usuarios conectados, el archivo se baja a disco lo antes posible y se libera de la memoria
	 */
	public void agregaIdentificacionOficial(FileUploadEvent event) {
		if (event.getFile() != null) {
			if (event.getFile().getContent().length <= Constantes.VALOR_04_MB) {
				StringBuilder path = new StringBuilder();
				path.append(Environment.getPathDocumentosTemporales());
				path.append(tutorDTO.getIdUsuarioLlaveCdmx());
				path.append(Constantes.SEPARADOR_RUTA);
				path.append(Constantes.RUTA_IDENTIFICACION_OFICIAL);
				path.append(Constantes.SEPARADOR_RUTA);
				path.append(event.getFile().getFileName());
				
				tutorDTO.setArchivoIdentificacion(path.toString());
				tutorDTO.setDocumentoLlave(false);
				tutorDTO.setIdDocumentoLlave(null);
				if(!guardaArchivoSeleccionadoEnTemporales(tutorDTO.getArchivoIdentificacion(), event.getFile().getContent())) {
					tutorDTO.setArchivoIdentificacion(null);
					tutorDTO.setDocumentoLlave(false);
					tutorDTO.setIdDocumentoLlave(null);
					WebResources.addErrorMessage("msg_error_carga_archivo", "form:uploadIdenOf", false);
				}
			} else {
				WebResources.addErrorMessage("msj_documento_oversize", "form:uploadIdenOf", false);
			}
		} else {
			WebResources.addErrorMessage("msg_error_carga_archivo", "form:uploadIdenOf", false);
		}
	}

	/**
	 * Método que asigna el documento adjunto para el comprobante de domicilio
	 * 
	 * Por tema de rendimiento cuando el sistema tiene miles de usuarios conectados, el archivo se baja a disco lo antes posible y se libera de la memoria
	 */
	public void agregaComprobanteDomicilio(FileUploadEvent event) {
		if (event.getFile() != null) {
			if (event.getFile().getContent().length <= Constantes.VALOR_04_MB) {
				StringBuilder path = new StringBuilder();
				path.append(Environment.getPathDocumentosTemporales());
				path.append(tutorDTO.getIdUsuarioLlaveCdmx());
				path.append(Constantes.SEPARADOR_RUTA);
				path.append(Constantes.RUTA_COMPROBANTE_DOMICILIO);
				path.append(Constantes.SEPARADOR_RUTA);
				path.append(event.getFile().getFileName());
				
				tutorDTO.setArchivoComprobanteDomicilio(path.toString());
				if(!guardaArchivoSeleccionadoEnTemporales(tutorDTO.getArchivoComprobanteDomicilio(), event.getFile().getContent())){
					tutorDTO.setArchivoComprobanteDomicilio(null);
					WebResources.addErrorMessage("msg_error_carga_archivo", "form:uploadComprobanteDom", false);
				}
			} else {
				WebResources.addErrorMessage("msj_documento_oversize", "form:uploadComprobanteDom", false);
			}
		} else {
			WebResources.addErrorMessage("msg_error_carga_archivo", "form:uploadComprobanteDom", false);
		}
	}

	/**
	 * Método para guardar Tutor
	 */
	public String guardarTutor() {
		if(tutorDTO.getCurp() == null || (BeanUtils.isEmpty(tutorDTO.getCurp()) || (tutorDTO.getCurp() != null && tutorDTO.getCurp().length() < 18))) {
			WebResources.validationMessage("msg_error_curp_requerida", false);
			return null;
		}

		if(tutorDTO.getArchivoComprobanteDomicilio() == null) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:uploadComprobanteDom", false);
			return null;
		}
		
		if (tutorDTO.getArchivoIdentificacion() == null) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:uploadIdenOf", false);
			return null;
		}

		try {
			tutorDTO.setCatEstatusDTO(new CatEstatusDTO(Constantes.ID_ESTATUS_EN_PROCESO));

			int codigoRespuesta = tutorFacade.registroNuevoTutor(tutorDTO);
			if (codigoRespuesta == Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA) {
				WebResources.validationMessage("msg_error_pagatodo_telefono_registrado", false);
				return null;
			} else if (codigoRespuesta == Constantes.RESPUESTA_CORREO_ASOCIADO_A_CUENTA) {
				WebResources.validationMessage("msg_error_pagatodo_correo_registrado", false);
				return null;
			} else if (codigoRespuesta == Constantes.RESPUESTA_TUTOR_MENOR_DE_EDAD) {
				WebResources.validationMessage("msj_error_menor_edad", false);
				return null;
			} else if (codigoRespuesta != Constantes.RESPUESTA_PAGATODO_REGISTRO_CORRECTO) {
				WebResources.validationMessage("msg_error_pagatodo_error_inesperado", false);
				return null;
			}
			
		} catch (Exception ex) {
			LOGGER.error("ERROR AL GUARDAR TUTOR: ", ex);
			WebResources.validationMessage("msj_error_registro", false);
			return null;
		}
		return bandejaTutorBean.init();
	}
	
	
	/**
	 * Método para Enviar correcciones
	 */
	public String envioCorreccionTutor() {
		try {
			tutorDTO.setCatEstatusDTO(new CatEstatusDTO(Constantes.ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO));
			if (tutorFacade.envioCorreccionTutor(tutorDTO, null)) {
				return bandejaTutorBean.init();
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR AL GUARDAR TUTOR: ", ex);
		}
		WebResources.validationMessage("msj_error_registro", false);
		return null;
	}

	/**
	 * Método para cambio de estatus en botones
	 */
	public void actualizaBotones(int seccion, int estatus) {
		
		if (seccion == 1) {
			tutorDTO.setInformacionGeneralObservaciones(null);
			tutorDTO.setInformacionGralCorrecto(estatus == 1
					? tutorDTO.getInformacionGralCorrecto() == null || tutorDTO.getInformacionGralCorrecto() == false
							? true
							: null
					: (tutorDTO.getInformacionGralCorrecto() == null || tutorDTO.getInformacionGralCorrecto() == true)
							? false
							: null);
			return;
		}

		tutorDTO.setDomicilioCorrecto(estatus == 1
				? tutorDTO.getDomicilioCorrecto() == null || tutorDTO.getDomicilioCorrecto() == false ? true : null
				: (tutorDTO.getDomicilioCorrecto() == null || tutorDTO.getDomicilioCorrecto() == true) ? false : null);
		tutorDTO.setDomicilioObservaciones(null);

	}

	/**
	 * Método para guardar/actualizar mediante la validación del tutor Estatus aprobado
	 */
	public void guardaValidaTutor() {
		if(!enviaValidacionDocumentoLlave()) {
			return;
		}
		try {
			tutorDTO.setCatEstatusDTO(new CatEstatusDTO(idEstatus));
			tutorFacade.envioCorreccionTutor(tutorDTO,  authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()); 
				
			PrimeFaces.current().executeScript("PF('dialogTutorAprobado').show();");
			PrimeFaces.current().executeScript("rcBandejaTutor()");
		} catch (IOException e) {
			LOGGER.error("Ocurrió un error en guardaValidaTutor:", e);
			WebResources.addErrorMessage("msj_error_valida_tutor", "messages", false);
		} 
	}
	
	public void actualizaEstatusAprobado(Integer estatus) {		
		idEstatus = estatus;
		PrimeFaces.current().executeScript("PF('dialogValidaTutor').show();");		
	}
	
	/**
	 * Método para actualizar un tutor con mas de tres beneficiarios  mediante la validación del tutor
	 */
	public void actualizarMasTresBeneficiarios() {
		try {
			tutorFacade.envioCorreccionTutor(tutorDTO , authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
			
			PrimeFaces.current().executeScript("PF('dialogTutorActualizarNumbeneficiarios').show();");
			PrimeFaces.current().executeScript("rcRechazoDatosCorrectos()");
		} catch (IOException e) {
			LOGGER.error("Ocurrió un error IOException", e);
			WebResources.addErrorMessage("msj_error_valida_tutor", "messages", false);
		}
	}
	
	public void seleccionaOneTreBeneficiarios(){
		if(!tutorDTO.isEsMayorTresBeneficiarios()) {
			tutorDTO.setEsCasaHogar(false);
			tutorDTO.setNombreCasaHogar(null);
			tutorDTO.setMotivoMayorTresBeneficiarios(null);
		}
	}
	
	public void seleccionaOneCaseHogar() {		
		tutorDTO.setMotivoMayorTresBeneficiarios(null);		
		tutorDTO.setNombreCasaHogar(null);
	}
	
	/**
	 * Método para guardar/actualizar mediante la validación del tutor
	 */
	public void envioCorreccion() {
		if(!enviaValidacionDocumentoLlave()) {
			return;
		}
		try {			
			tutorDTO.setCatEstatusDTO(new CatEstatusDTO(idEstatus));
			tutorFacade.envioCorreccionTutor(tutorDTO , authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
			
			PrimeFaces.current().executeScript("PF('dialogTutorRechazado').show();");
			PrimeFaces.current().executeScript("rcRechazoDatosCorrectos()"); 
		} catch (IOException e) {
			LOGGER.error("Ocurrió un error en envioCorreccion", e);
			WebResources.addErrorMessage("msj_error_valida_tutor", "messages", false);
		}
	}
	
	public boolean enviaValidacionDocumentoLlave(){
		boolean pasoCorrecto = true;
		// Envío documento de identificación a Llave
		if (tutorDTO.getInformacionGralCorrecto() != null && tutorDTO.isDocumentoLlave()
				&& tutorDTO.getCatEstatusDTO().getIdEstatus() == ID_ESTATUS_PENDIENTE_VALIDACION) {
			DocumentoLlaveRestClient validaDoc = new DocumentoLlaveRestClient();
			Integer codeResponse = validaDoc.enviaEstatusDocumento(tutorDTO.getIdUsuarioLlaveCdmx(),
					authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx(), tutorDTO.getIdDocumentoLlave(),
					tutorDTO.getInformacionGralCorrecto(), 99,
					tutorDTO.getInformacionGeneralObservaciones());

			switch (codeResponse) {
				case 201:
					//LOGGER.info("La validación del documento se realizó correctamente");
					break;
				case 408:
					//LOGGER.info("El documento ya se encuentra validado. Solo se pueden validar documentos que aún no hayan sido validados.");
					break;
				case 406:
					//LOGGER.info("El idDocumento enviado en la petición no existe o no es correcto");
					break;
				default:
					pasoCorrecto = false;
					LOGGER.error( "Servicio web documentos LLaveCDMX" + "\n" + "Usuario....... " + tutorDTO.getIdUsuarioLlaveCdmx() + "\n "
							   + "ID Documento......." + tutorDTO.getIdDocumentoLlave() + "\n" 
							   + "Informacion General Observaciones......." + tutorDTO.getInformacionGeneralObservaciones() + "\n" 
							   + "Codigo de Respuesta.........." + codeResponse);
					WebResources.addErrorMessage(Mensajes.MSJ_ERROR_SERVICIO, "messages", false);
					break;
			}
		}
		return pasoCorrecto;
	}

	public void actualizaEstatusRechazo(Integer estatus) {
		if ((tutorDTO.getInformacionGeneralObservaciones() != null
				&& !tutorDTO.getInformacionGeneralObservaciones().isEmpty())
				|| (tutorDTO.getDomicilioObservaciones() != null && !tutorDTO.getDomicilioObservaciones().isEmpty())) {

			idEstatus = estatus;
			PrimeFaces.current().executeScript("PF('dialogRechazoTutor').show();");
			return;
		}
		if (tutorDTO.getInformacionGralCorrecto() == false && (tutorDTO.getInformacionGeneralObservaciones() == null
				|| BeanUtils.isEmpty(tutorDTO.getInformacionGeneralObservaciones()))) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:pgValidaDatosG", false);
		}
		if (tutorDTO.getDomicilioCorrecto() == false 
				&& (tutorDTO.getDomicilioObservaciones() == null 
				|| BeanUtils.isEmpty(tutorDTO.getDomicilioObservaciones()))) {
			WebResources.addErrorMessage(Mensajes.MSJ_CAMPO_OBLIGATORIO, "form:pgValidaDomicilio", false);
		}
	}
	
	
	
	public void verificaCURP() {
		if (tutorDTO != null && tutorDTO.isEsExtranjero() && tutorDTO.getCurp() == null) {
			tutorDTO.setEsExtranjero(true);
			PrimeFaces.current().executeScript("PF('dialogCapturaCURP').show()");
		}
	}
    
    public void actualizacomprobacioCURP(int estatus) {
        if(estatus == 1) {
            curpExtranjero = curpExtranjero == null || !curpExtranjero ? true : null;
            return;
        }
        curpExtranjero = curpExtranjero == null || curpExtranjero ? false : null;
    }
    
    public void validaCURPExtranjero() {
        if (BeanUtils.isNotNull(curpExtranjeroSt) && BeanUtils.isNotEmpty(curpExtranjeroSt.trim())) {
            if (curpExtranjeroSt.trim().length() == 18) {
            	TutorExtranjeroDTO tutorExt = null;
            	tutorExt = tutorExtranjeroDAO.buscarPorCURP(curpExtranjeroSt);
            	
				if (tutorExt != null) {
					tutorDTO.setCurp(tutorExt.getCurp());
					tutorDTO.setTelefono(tutorExt.getTelefono());
					tutorDTO.setCodigoPostal(tutorExt.getCp());
					tutorDTO.setCalle(tutorExt.getCalle());
					tutorDTO.setNumExt(tutorExt.getNumExt());
					tutorDTO.setNumInt(tutorExt.getNumInt());

					consultaCodigosPostales();

					PrimeFaces.current().executeScript("PF('dialogCapturaCURP').hide()");
					PrimeFaces.current().ajax().update("form");
					return;
				}
				WebResources.addErrorMessage("msg_error_curp_no_encontrada", "form:txtCampoCURP", false);
            } else {
            	WebResources.addErrorMessage("msg_error_valida_curp", "form:txtCampoCURP", false);
            }
        }
        WebResources.addErrorMessage("msg_error_valida_curp", "form:txtCampoCURP", false);
    }    
	
	/**
	 * Por tema de rendimiento cuando el sistema tiene miles de usuarios conectados, el archivo se baja a disco lo antes posible y se libera de la memoria
	 */
	private void asignaDocumentoDesdeLlave() {
		DocumentoLlaveDTO documento = consultaDocumentoIdentificacionLlave();
		if (documento != null) {

			StringBuilder path = new StringBuilder();
			path.append(Environment.getPathDocumentosTemporales());
			path.append(tutorDTO.getIdUsuarioLlaveCdmx());
			path.append(Constantes.SEPARADOR_RUTA);
			path.append(Constantes.RUTA_IDENTIFICACION_OFICIAL);
			path.append(Constantes.SEPARADOR_RUTA);
			path.append(documento.getNombreDocumento());

			tutorDTO.setArchivoIdentificacion(path.toString());
			if(guardaArchivoSeleccionadoEnTemporales(path.toString(), Base64.decodeBase64(documento.getDocumentoBase64()))) {
				int idCatIdentifica = documento.getIdSubtipoDocumento();
				tutorDTO.setCatIdentificacionOficialDTO(lstIdentificacionOficial.stream()
						.filter(identificaOf -> identificaOf.getIdCatLlave().intValue() == idCatIdentifica).findAny()
						.orElse(null));

				tutorDTO.setDocumentoLlave(true);
				tutorDTO.setIdDocumentoLlave(documento.getIdDocumento());

			}else {
				WebResources.addErrorMessage("msg_error_carga_archivo", "form:uploadIdenOf", false);
			}
		}
	}
	
	private DocumentoLlaveDTO consultaDocumentoIdentificacionLlave() {
		List<Integer> lstIdentifica = new ArrayList<>();

		lstIdentificacionOficial.forEach(identifica -> {
			lstIdentifica.add(identifica.getIdCatLlave());
		});

		Collections.sort(lstIdentifica);
		
		/*
		 *  TODO WARN: Si ya pasó mucho tiempo entre que inició sesión el usuario y se ingresa a lapantalla para registrar el tutor,
		 *  puede ser que el token ya haya expirado y podría marcar error al usarlo
		 */
		DocumentoLlaveRestClient documentoLlave = new DocumentoLlaveRestClient();
		return documentoLlave.consultaDocumento(lstIdentifica, lstIdentifica, false, authenticatorBean.getAccessToken());
	}
	
	public void generarCURP() {
		try {
			String nombre = removeDiacriticalMarks(tutorDTO.getNombre());
			String primerAp = removeDiacriticalMarks(tutorDTO.getPrimerApellido());
			String segundoAp = tutorDTO.getSegundoApellido() != null ? removeDiacriticalMarks(tutorDTO.getSegundoApellido()) : "";
			String sexo = authenticatorBean.getUsuarioLogueado().getSexo().toUpperCase();
			String fNac = authenticatorBean.getUsuarioLogueado().getFechaNacimiento();

			if (Arrays.asList(nombre.split(" ")).size() > 1) {
				nombre = normalizaNombre(nombre);
			}

			List<String> listPAP = Arrays.asList(primerAp.split(" "));
			primerAp = listPAP.size() > 1 ? compruebaPCC(listPAP.get(0)) ? listPAP.get(1) : listPAP.get(0) : primerAp;

			List<String> listSAP = Arrays.asList(segundoAp.split(" "));
			segundoAp = listSAP.size() > 1 ? compruebaPCC(listSAP.get(0)) ? listSAP.get(1) : listSAP.get(0) : segundoAp;

			char[] curp = new char[18];

			// La letra inicial
			curp[0] = primerAp.charAt(0) == 'Ñ' ? 'X' : primerAp.charAt(0);
			// la primera vocal interna del primer apellido
			curp[1] = consultaVocal(normalizaTexto(primerAp));

			// la letra inicial del segundo apellido
			curp[2] = segundoAp.isEmpty() || segundoAp.charAt(0) == 'Ñ' ? 'X' : segundoAp.charAt(0);
			// la primera letra del nombre
			curp[3] = nombre.charAt(0) == 'Ñ' ? 'X' : nombre.charAt(0);

			if (validaAltisonante(String.valueOf(curp[0]) + curp[1] + curp[2] + curp[3])) {
				curp[1] = 'X';
			}

			curp[4] = fNac.charAt(8);
			curp[5] = fNac.charAt(9);
			curp[6] = fNac.charAt(3);
			curp[7] = fNac.charAt(4);
			curp[8] = fNac.charAt(0);
			curp[9] = fNac.charAt(1);

			// Sexo M para mujer y H para hombre
			curp[10] = sexo.equalsIgnoreCase("HOMBRE") ? 'H' : 'M';

			// NACIDO EN EL EXTRANJERO
			curp[11] = 'N';
			curp[12] = 'E';

			// consonantes internas del primer apellido
			curp[13] = consultaConsonante(normalizaTexto(primerAp));
			// consonantes internas del segundo apellido
			curp[14] = segundoAp.isEmpty() ? 'X' : consultaConsonante(normalizaTexto(segundoAp));
			// consonantes internas del nombre
			curp[15] = consultaConsonante(normalizaTexto(nombre));

			// CONSULTA HOMONIMIA
			List<String> listado = tutorDAO.consultaHomonimia(String.valueOf(curp).substring(0, 16));
			Collections.sort(listado, Collections.reverseOrder());
			// CONSECUTIVOS

			// 1-9 para fechas de nacimiento hasta el aæo 1999
			// A-Z para fechas de nacimiento a partir de 2000
			// CONSULTAR HOMONIMIA
			curp[16] = '0';
			curp[17] = '0';
			if (!listado.isEmpty()) {
				List<Integer> listInt = new ArrayList<>();
				int homo1 = Integer.valueOf(fNac.substring(6));
				if (homo1 <= 1999) {
					boolean valorValido = false;
					// validar que el la posicion 17 no sea mayor a 8
					int cons = Integer.valueOf(listado.get(0).substring(16, 17));
					cons = 9;

					if (cons == 9) {
						// Si el dato es igual a 9, buscar si existe espacio en la lista de curps
						for (String val : listado) {
							listInt.add(Integer.valueOf(String.valueOf(val.toCharArray()[16])));
						}
						for (int i = 0; i <= 9; i++) {
							if (!listInt.contains(i)) {
								cons = i;
								valorValido = true;
								break;
							}
						}
						curp[16] = valorValido ? Integer.valueOf(cons).toString().toCharArray()[0] : '0';
					}
					// se manda el consecutivo al caracter 18
					// Dígito verificador
					if (!valorValido) {
						listInt.clear();
						for (String val : listado) {
							listInt.add(Integer.valueOf(String.valueOf(val.toCharArray()[17])));
						}
						for (int i = 0; i <= 9; i++) {
							if (!listInt.contains(i)) {
								cons = i;
								valorValido = true;
								break;
							}
						}
						curp[17] = valorValido ? Integer.valueOf(cons).toString().toCharArray()[0] : curp[17];
					}
				} else {
					boolean valorValido = false;
					List<String> listString = new ArrayList<>();

					for (String val : listado) {
						listString.add(String.valueOf(val.toCharArray()[17]));
					}

					for (char letra = 'A'; letra <= 'Z'; letra++) {
						if (!listString.contains(letra)) {
							curp[16] = letra;
							valorValido = true;
							break;
						}
					}

					if (!valorValido) {
						int cons = 0;
						listInt.clear();
						for (String val : listado) {
							listInt.add(Integer.valueOf(String.valueOf(val.toCharArray()[17])));
						}
						for (int i = 0; i <= 9; i++) {
							if (!listInt.contains(i)) {
								cons = i;
								valorValido = true;
								break;
							}
						}
						curp[17] = valorValido ? Integer.valueOf(cons).toString().toCharArray()[0] : curp[17];
					}

				}
			}

			if (listado.contains(String.valueOf(curp))) {
				WebResources.addErrorMessage("msg_error_genera_curp", "form:mensajeCURP", false);
				return;
			}

			tutorDTO.setCurp(String.valueOf(curp));

			PrimeFaces.current().executeScript("PF('dialogCapturaCURP').hide()");
			PrimeFaces.current().ajax().update("form:lblCURP");

		} catch (Exception ex) {
			LOGGER.error("Ocurrió un error en generar CURP:", ex);
			WebResources.addErrorMessage("msg_error_genera_curp", "form:mensajeCURP", false);
		}
	}

	private char consultaVocal(String texto) {
		char caracter = 'X';
		for (int x = 0; x < texto.length(); x++) {
			if (esVocal(texto.charAt(x)) && x > 0) {
				caracter = compruebaCaracterEspecial(texto.charAt(x)) ? 'X' : texto.charAt(x);
				break;
			}
		}
		return caracter;
	}

	private char consultaConsonante(String texto) {
		char caracter = 'X';
		for (int x = 0; x < texto.length(); x++) {
			if (!esVocal(texto.charAt(x)) && x > 0) {
				caracter = compruebaCaracterEspecial(texto.charAt(x)) ? 'X' : texto.charAt(x);
				break;
			}
		}
		return caracter;
	}

	private boolean esVocal(char letra) {
		return "AEIOU".contains(String.valueOf(letra)) || compruebaCaracterEspecial(letra);
	}

	private static boolean compruebaCaracterEspecial(char val) {
		return String.valueOf(val).matches("[^\\w]") || val == 'Ñ';
	}

	/**
	 * Método que quita acentos de una palabra dada
	 * 
	 * @param string
	 * @return
	 */
	private String removeDiacriticalMarks(String valor) {
		return Normalizer.normalize(valor, Normalizer.Form.NFD).replaceAll("[\u0300-\u0301]", "");
	}

	private boolean compruebaPCC(String valor) {
		String s = "DA DAS DE DEL DER DI DIE DD EL LA LOS LAS LE LES MAC MC VAN VON Y";
		List<String> listado = new ArrayList<String>(Arrays.asList(s.split("\\s+")));
		if (listado.contains(valor)) {
			return true;
		}
		return false;
	}

	private boolean validaAltisonante(String valor) {
		String s = "BACA BAKA BUEI BUEY CACA CACO CAGA CAGO CAKA CAKO COGE COGI COJA COJE COJI COJO COLA CULO FALO FETO "
				+ "GETA GUEI GUEY JETA JOTO KACA KACO KAGA KAGO KAKA KAKO KOGE KOGI KOJA KOJE KOJI KOJO KOLA KULO LILO LOCA "
				+ "LOCO LOKA LOKO MAME MAMO MEAR MEAS MEON MIAR MION MOCO MOKO MULA MULO NACA NACO PEDA PEDO PENE PIPI PITO POPO PUTA "
				+ "PUTO QULO RATA ROBA ROBE ROBO RUIN SENO TETA VACA VAGA VAGO VAKA VUEI VUEY WUEI WUEY";
		List<String> listado = new ArrayList<String>(Arrays.asList(s.split("\\s+")));
		if (listado.contains(valor)) {
			return true;
		}
		return false;
	}

	private String normalizaNombre(String nombre) {
		String s = "MARIA MA. MA JOSE J J.";
		List<String> listN = Arrays.asList(nombre.split(" "));
		List<String> listado = new ArrayList<String>(Arrays.asList(s.split("\\s+")));
		StringBuilder nombreComp = new StringBuilder();
		if (listado.contains(listN.get(0))) {
			for (String val : listN) {
				if (!compruebaPCC(val) && !listado.contains(val)) {
					nombreComp.append(val).append(" ");
				}
			}
			return nombreComp.toString().trim();
		}
		return nombre;
	}

	private String normalizaTexto(String valor) {
		StringBuilder texto = new StringBuilder();
		int aux = 0;
		for (char ch : valor.toCharArray()) {
			if ("̃".charAt(0) == ch) {
				texto.replace(aux - 1, aux, "X");
				continue;
			}
			texto.append(ch);
			aux++;
		}
		return texto.toString();
	}
	
	public byte[] construyeDocumento(String ruta) {
		byte[] arreglo = null;
		try {
			arreglo = FileUtils.readFileToByteArray(new File(ruta));
		} catch (IOException e) {
			LOGGER.error("Error al construir array", e);
		}
		return arreglo;
	}
	
	public void revisaInformacionContacto() {
		userDTO = authenticatorBean.getRolTutor() ? authenticatorBean.getUserDTO()
				: usuarioDAO.buscarPorId(tutorDTO.getIdUsuarioLlaveCdmx());

		if (!userDTO.getCorreo().equalsIgnoreCase(tutorDTO.getCorreo())
				|| !userDTO.getTelefono().equalsIgnoreCase(tutorDTO.getTelefono())) {
			PrimeFaces.current().executeScript("PF('dialogConsultaDatosContacto').show();");
		} else {
			PrimeFaces.current().executeScript("PF('dialogPasosDatosContacto').show();");
		}
	}
	
	public void actualizaDatosContacto() {

		if (tutorFacade.actualizaContacto(tutorDTO, userDTO,
				authenticatorBean.getRolTutor() != null && authenticatorBean.getRolTutor() ? null
						: authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx())) {
			PrimeFaces.current().ajax().update("form:txtTelefono");
			PrimeFaces.current().ajax().update("form:txtCorreo");
		} else {
			WebResources.validationMessage("msg_error_guardar_datos_contacto", false);
		}
		PrimeFaces.current().executeScript("PF('dialogConsultaDatosContacto').hide();");
	}
	
	/**
	 * Por tema de optimización de la memoria, cada que un usuario cargue un archivo al sistema, lo antes posible se guarda en la 
	 * carpeta de "temporales" del sistema, para no tenerlo en Memoria. Recordar que este sistema tiene una alta concurrencia de usuarios.
	 * 
	 * Una vez que el registro se mande a guardar, el archivo se moverá a la carpeta de documenos finales y se borrará de la carpeta de temporales.
	 */
	private boolean guardaArchivoSeleccionadoEnTemporales(String url, byte[] content) {
		try {
			File faFiles = new File(url.substring(0, url.lastIndexOf(Constantes.SEPARADOR_RUTA)));
			if (faFiles.isDirectory() && faFiles.exists()) {
				// Se elimina el directorio correspondiente
				FileUtils.forceDelete(faFiles);
			}
			// Se guarda documento en ruta temporal
			FileUtils.writeByteArrayToFile(new File(url), content);
			return true;
		} catch (IOException e) {
			LOGGER.error("No es posible guardar el archivo en la carpeta de temporales", e);
			return false;
		}
	}
	
	public void numeroValido(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        if (value == null   || !String.valueOf(value).matches("\\d*")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, WebResources.getBundleMsg("msj_error_solo_numeros")));
        }
    }
	public void cadenaValida(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		if (value == null   || !String.valueOf(value).toLowerCase().matches("[a­zA­Z]*")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, WebResources.getBundleMsg("msj_error_solo_letras")));
		}
	}
	public void cadenaNumeroValida(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
		if (value == null   || !Pattern.compile("[a-zA-Z0-9]*").matcher(String.valueOf(value)).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, WebResources.getBundleMsg("msj_error_solo_letras_numeros")));
		}
	}

	/**
	 * Gettes and Setters
	 */
	public int getModeloINE() {
		return modeloINE;
	}

	public void setModeloINE(int modeloINE) {
		this.modeloINE = modeloINE;
	}

	public TutorDTO getTutorDTO() {
		return tutorDTO;
	}

	public void setTutorDTO(TutorDTO tutorDTO) {
		this.tutorDTO = tutorDTO;
	}

	public List<CatIdentificacionOficialDTO> getLstIdentificacionOficial() {
		return lstIdentificacionOficial;
	}

	public void setLstIdentificacionOficial(List<CatIdentificacionOficialDTO> lstIdentificacionOficial) {
		this.lstIdentificacionOficial = lstIdentificacionOficial;
	}

	public boolean isValidarRegistro() {
		return validarRegistro;
	}

	public void setValidarRegistro(boolean validarRegistro) {
		this.validarRegistro = validarRegistro;
	}

	public List<CatComprobanteDomicilioDTO> getLstCatComprobanteDomicilioDTO() {
		return lstCatComprobanteDomicilioDTO;
	}

	public void setLstCatComprobanteDomicilioDTO(List<CatComprobanteDomicilioDTO> lstCatComprobanteDomicilioDTO) {
		this.lstCatComprobanteDomicilioDTO = lstCatComprobanteDomicilioDTO;
	}

	public boolean isViewDocIdentifica() {
		return viewDocIdentifica;
	}

	public void setViewDocIdentifica(boolean viewDocIdentifica) {
		this.viewDocIdentifica = viewDocIdentifica;
	}

	public boolean isViewDocDomicilio() {
		return viewDocDomicilio;
	}

	public void setViewDocDomicilio(boolean viewDocDomicilio) {
		this.viewDocDomicilio = viewDocDomicilio;
	}

	public List<CatAsentamientosDTO> getListColonia() {
		return listColonia;
	}

	public void setListColonia(List<CatAsentamientosDTO> listColonia) {
		this.listColonia = listColonia;
	}

	public Integer getID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA() {
		return ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA;
	}

	public Integer getID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO() {
		return ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO;
	}

	public Integer getID_IDENTIFICACION_OFICIAL() {
		return ID_IDENTIFICACION_OFICIAL;
	}

	public Integer getID_ESTATUS_EN_PROCESO() {
		return ID_ESTATUS_EN_PROCESO;
	}

	public Integer getID_ESTATUS_APROBADA() {
		return ID_ESTATUS_APROBADA;
	}

	public boolean isDisableInformacionGeneral() {
		return disableInformacionGeneral;
	}

	public void setDisableInformacionGeneral(boolean disableInformacionGeneral) {
		this.disableInformacionGeneral = disableInformacionGeneral;
	}

	public boolean isDisableInformacionDireccion() {
		return disableInformacionDireccion;
	}

	public void setDisableInformacionDireccion(boolean disableInformacionDireccion) {
		this.disableInformacionDireccion = disableInformacionDireccion;
	}

	public boolean isCapturaTutor() {
		return capturaTutor;
	}

	public void setCapturaTutor(boolean capturaTutor) {
		this.capturaTutor = capturaTutor;
	}

	public Boolean getCurpExtranjero() {
		return curpExtranjero;
	}

	public void setCurpExtranjero(Boolean curpExtranjero) {
		this.curpExtranjero = curpExtranjero;
	}

	public String getCurpExtranjeroSt() {
		return curpExtranjeroSt;
	}

	public void setCurpExtranjeroSt(String curpExtranjeroSt) {
		this.curpExtranjeroSt = curpExtranjeroSt;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Integer getID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO() {
		return ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO;
	}

	public Integer getID_ESTATUS_PENDIENTE_VALIDACION() {
		return ID_ESTATUS_PENDIENTE_VALIDACION;
	}

	public void setID_ESTATUS_PENDIENTE_VALIDACION(Integer iD_ESTATUS_PENDIENTE_VALIDACION) {
		ID_ESTATUS_PENDIENTE_VALIDACION = iD_ESTATUS_PENDIENTE_VALIDACION;
	}

	public Integer getID_ESTATUS_SUSPENDIDAS() {
		return ID_ESTATUS_SUSPENDIDAS;
	}

	public void setID_ESTATUS_SUSPENDIDAS(Integer iD_ESTATUS_SUSPENDIDAS) {
		ID_ESTATUS_SUSPENDIDAS = iD_ESTATUS_SUSPENDIDAS;
	}

	public void setID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA(Integer iD_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA) {
		ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA = iD_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA;
	}

	public void setID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO(Integer iD_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO) {
		ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO = iD_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO;
	}

	public void setID_ESTATUS_EN_PROCESO(Integer iD_ESTATUS_EN_PROCESO) {
		ID_ESTATUS_EN_PROCESO = iD_ESTATUS_EN_PROCESO;
	}

	public void setID_ESTATUS_APROBADA(Integer iD_ESTATUS_APROBADA) {
		ID_ESTATUS_APROBADA = iD_ESTATUS_APROBADA;
	}

	public void setID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO(Integer iD_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO) {
		ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO = iD_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO;
	}

	public boolean isAvisoPrivacidad() {
		return avisoPrivacidad;
	}

	public void setAvisoPrivacidad(boolean avisoPrivacidad) {
		this.avisoPrivacidad = avisoPrivacidad;
	}

	public boolean isViewAvisoPrivacidad() {
		return viewAvisoPrivacidad;
	}

	public void setViewAvisoPrivacidad(boolean viewAvisoPrivacidad) {
		this.viewAvisoPrivacidad = viewAvisoPrivacidad;
	}

	public int getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(int idEstatus) {
		this.idEstatus = idEstatus;
	}
}