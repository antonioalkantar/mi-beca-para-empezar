package mx.gob.cdmx.adip.beca.acceso.bean;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.event.CloseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.bean.BandejaFuncionarioBean;
import mx.gob.cdmx.adip.beca.bean.BandejaTutorBean;
import mx.gob.cdmx.adip.beca.bean.BandejaValidacionBean;
import mx.gob.cdmx.adip.beca.client.OAuth2CdmxClient;
import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.BeanUtils;
import mx.gob.cdmx.adip.beca.commons.dto.UserDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.commons.utils.StringUtils;
import mx.gob.cdmx.adip.beca.dao.UsuarioDAO;
import mx.gob.cdmx.adip.beca.oauth.dto.RequestRolesDTO;
import mx.gob.cdmx.adip.beca.oauth.dto.RequestTokenDTO;
import mx.gob.cdmx.adip.beca.oauth.dto.RolesUsuarioDTO;
import mx.gob.cdmx.adip.beca.oauth.dto.UsuarioDTO;
import mx.gob.cdmx.adip.beca.util.WebResources;

/**
 * @autor Raúl Soto
 */
@Named
@SessionScoped
public class AuthenticatorBean implements Serializable {

	private static final long serialVersionUID = 6048100734819950909L;	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatorBean.class);
	private static final String PARAM_LISTLLAVE = "listllave";
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private BandejaValidacionBean bandejaValidacionBean;
	
	@Inject
	private BandejaTutorBean bandejaTutorBean;

	@Inject
	private BandejaFuncionarioBean bandejaFuncionarioBean;
	
	@Inject
	private UsuarioDAO usuarioDAO;

	private String urlInicio = Environment.getUrlRedirectLoginCdmx();
	private String stateOauth2 = Constantes.EMPTY_STRING;
	private String codeOauth;
	private String tokenOauth;
	private String etiquetaLogueo;
	private UsuarioDTO usuarioLogueado;
	private UserDTO userDTO;
	
	private boolean ingresaDesdeLlave;
	private boolean mostrarNotificacion = true;
	private boolean manifiesto = false;
	
	
	private Boolean rolAdministrador;
	private Boolean rolValidador;
	private Boolean rolConsulta;
	private Boolean rolTutor;
	private Boolean esExtranjero;
	

	public void inicializar() {
		usuarioLogueado =  new UsuarioDTO();
		ingresaDesdeLlave = false;		
	}
	/**
	 * Método inicial que construye la ULR con la que se solicitará a Llave el acceso
	 */
	public void redirectUrlLoginCDMX() {		
		StringBuilder urlLoginLlaveCDMx = new StringBuilder();
        stateOauth2 = StringUtils.randomChars().toString();
		try {
			urlLoginLlaveCDMx.append(Environment.getUrlLoginCdmx()).append("?")
				.append(Constantes.PARAM_CLIENT_ID).append("=").append(Environment.getAppId()).append("&")
				.append(Constantes.PARAM_REDIRECT).append("=").append(URLEncoder.encode(Environment.getUrlRedirectLoginCdmx(), "UTF-8")).append("&")
//				.append(Constantes.PARAM_STATE).append("=").append(Utils.randomChars().toString()).toString();
				.append(Constantes.PARAM_STATE).append("=").append(stateOauth2).toString();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Ocurrio un error al realizar el encode de :", e);
		}
		try {
			facesContext.getExternalContext().redirect(urlLoginLlaveCDMx.toString());
		} catch (IOException e) {
			LOGGER.error("Ocurrio un error al generar el redirect al Login CDMX:", e);
		}
	}	
	
    private boolean validaRespuestaLoginCdmx() {
        boolean respuestaCorrecta = false;
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        if (params.get(Constantes.PARAM_STATE) == null
                || params.get(Constantes.PARAM_STATE).compareTo(Constantes.EMPTY_STRING) == 0
                || params.get(Constantes.PARAM_STATE).compareTo(stateOauth2) != 0) {
            WebResources.addValidationMessage("login.state_incorrecto", true);
        } else if (params.get(Constantes.PARAM_CODE) == null
                || params.get(Constantes.PARAM_CODE).compareTo(Constantes.EMPTY_STRING) == 0) {
            WebResources.addValidationMessage("login.code_incorrecto", true);
        } else {
            respuestaCorrecta = true;
        }
        return respuestaCorrecta;
    }
    
	public void revisaRespuestaLoginCdmx() {		
				
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
				
//			if (params.get(Constantes.PARAM_CODE) == null
//					|| params.get(Constantes.PARAM_CODE).compareTo(Constantes.EMPTY_STRING) == 0) {
				//usuarioLogueado = new PersonaDTO();
//				WebResources.addValidationMessage("login.code_incorrecto", true);
//				LOGGER.info("Code incorrecto:" + params.get(Constantes.PARAM_CODE));
			if (!validaRespuestaLoginCdmx()) {
				try {
					facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT);
				} catch (IOException e) {
					LOGGER.error("Error al redireccionar al index.");
				}
			} else {
				try {
					codeOauth = params.get(Constantes.PARAM_CODE);
					tokenOauth = cambiaCodePorToken(codeOauth);
					LOGGER.debug("Token recibido de Llave CDMX:" + tokenOauth);
					if (tokenOauth != null && !codeOauth.isEmpty()) {
						usuarioLogueado = cambiarTokenPorDatosUsuario(tokenOauth);
						if(usuarioLogueado != null) {	
							insertaDatosUsuario(usuarioLogueado);
							ingresaDesdeLlave = false;
							obtenerRolesUsuario(usuarioLogueado.getIdUsuarioLlaveCdmx(), Long.parseLong(Environment.getAppId()), tokenOauth);
							crearSesionUsuario(tokenOauth);
							LOGGER.debug("Usuario recibido de Llave:" + usuarioLogueado.getNombre());
							LOGGER.debug("usuario id: " + usuarioLogueado.getIdUsuarioLlaveCdmx());
							esExtranjero = usuarioLogueado.getEsExtranjero();
							if (rolAdministrador) {
								facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + bandejaValidacionBean.init());
							}
							else if (rolTutor) {
								facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + bandejaTutorBean.init());
							} else if (rolValidador||rolConsulta) {
								facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + bandejaFuncionarioBean.init());								
							}
						}
					} else {
						facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT);
						WebResources.addValidationMessage("login.token_incorrecto", true);
					}					
				} catch (Exception e) {
					WebResources.addValidationMessage("msj_error_general_llavecdmx", false);
					LOGGER.error("Ocurrio un error al iniciar sesión: ", e);
				}
			}
		}
	}
	
	/**
	 * Método que se utiliza para validar si la URL de inicio cuenta con el parámetro 
	 * listllave, lo que identifica que viene redireccionado desde llave.
	 */
	public void revisaRedirectUrlInicio() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if(!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
			setIngresaDesdeLlave(false);
			if(usuarioLogueado == null) {
				usuarioLogueado = new UsuarioDTO();
			}
			if(params.get(PARAM_LISTLLAVE) == null 
					|| params.get(PARAM_LISTLLAVE).trim().compareTo(Constantes.EMPTY_STRING) == 0) {
				setIngresaDesdeLlave(false);
			} else {
				if(params.get(PARAM_LISTLLAVE).compareToIgnoreCase("true") == 0 
						|| params.get(PARAM_LISTLLAVE).compareToIgnoreCase("false") == 0) {
					try {
						setIngresaDesdeLlave(Boolean.parseBoolean(params.get(PARAM_LISTLLAVE)));
					} catch (Exception e) {
						setIngresaDesdeLlave(false);
					}
				}
			}
		}
	}
	
	/**
	 * Método que obtiene un token mediante el code generado
	 * @param code
	 * @return
	 */
	private String cambiaCodePorToken(final String code) {
		RequestTokenDTO requestToken = new RequestTokenDTO();
		requestToken.setClientId(Environment.getAppId());
		requestToken.setClientSecret(Environment.getSecretKey());
		requestToken.setCode(code);
		requestToken.setGrantType(Constantes.GRANT_TYPE_AUTHORIZATION_CODE);
		requestToken.setRedirectUri(Environment.getUrlRedirectLoginCdmx());
//		LOGGER.info("Request Token:"+requestToken);
		
		OAuth2CdmxClient oautCdmxClient = new OAuth2CdmxClient();
		String token = oautCdmxClient.obtenerToken(requestToken);
		return token;
	}
	
	/**
	 * Método que obtiene la información del usuario logueado para el 
	 * inicio de la sesión
	 * @param token
	 * @return
	 */
	private UsuarioDTO cambiarTokenPorDatosUsuario(final String token) {
		OAuth2CdmxClient oauthCdmxClient = new OAuth2CdmxClient();
		return oauthCdmxClient.obtenerDatosUsuarioPorToken( getAccessToken() );
	}

	private void obtenerRolesUsuario(long idPersona, long idSistema, String token) {
		String strToken = "";
		rolAdministrador = false;
		rolValidador = false;
		rolConsulta = false;
		rolTutor = false;

		if(token != null) {
			try {
				JSONObject tokenObj = new JSONObject(token);
				strToken = tokenObj.get("accessToken").toString();
			} catch (JSONException e) {
				LOGGER.error("Error al crear el json del token");
			}
		}
		RequestRolesDTO requestRolesDTO =  new RequestRolesDTO(idPersona, idSistema);
		OAuth2CdmxClient oauthCdmxClient = new OAuth2CdmxClient();
		List<RolesUsuarioDTO> lstRolesUsuario = oauthCdmxClient.obtenerRolesUsuario(requestRolesDTO, strToken);
		if(BeanUtils.isNotNull(lstRolesUsuario)) {
			verificarRoles(lstRolesUsuario);
			usuarioLogueado.setLstRoles(lstRolesUsuario);
			//LOGGER.info("Roles: " + usuarioLogueado.getLstRoles().get(0).getRol());
			//LOGGER.info("Roles Id: " + usuarioLogueado.getLstRoles().get(0).getIdRol());
		} else {
			rolTutor = true;
		}
	}

	public void verificarRoles(List<RolesUsuarioDTO> lstRolesUsuario) {
		if(lstRolesUsuario != null) {
			for (RolesUsuarioDTO rolesUsuarioDTO : lstRolesUsuario) {
				if(rolesUsuarioDTO.getIdRol() == Constantes.ROL_ADMINISTRADOR)
					setRolAdministrador(true);
				if(rolesUsuarioDTO.getIdRol() == Constantes.ROL_VALIDADOR)
					setRolValidador(true);
				if(rolesUsuarioDTO.getIdRol() == Constantes.ROL_CONSULTA)
					setRolConsulta(true);
			}
		}
		obtenerNombresRoles(lstRolesUsuario);
	}

	public void obtenerNombresRoles(List<RolesUsuarioDTO> lstRolesUsuario) {
		setEtiquetaLogueo("");
		if(lstRolesUsuario != null) {
			int numeroRoles = lstRolesUsuario.size();
			int separacion = 0;
			for (int i = 0; i < numeroRoles; i++) {
				separacion = i;
				setEtiquetaLogueo(getEtiquetaLogueo() + lstRolesUsuario.get(i).getRol() + (++separacion == numeroRoles ? "" : " - "));	
			}
		}
	}
	
	/**
	 * Método que crea la sesión del usuario con la información recuperada de Llave CDMX
	 * @param token
	 */
	private void crearSesionUsuario(final String token) {	
		// Invalidar cualquier sesión que tenga el usuario.
//		HttpSession oldSession = request.getSession(false);
//		if (oldSession != null) {
//			LOGGER.debug("Invalidar sesión en caso de que exista...");
//			oldSession.invalidate();
//		}
		// Generar nueva sesión de usuario
		HttpSession newSession = request.getSession(true);
		newSession.setAttribute(Constantes.TOKEN_SESSION, token);
		// Guardar token en cookie para Single SignOn
		Cookie message = new Cookie("idCDMX", token);
		((javax.servlet.http.HttpServletResponse) facesContext.getExternalContext().getResponse()).addCookie(message);
	}
	
	/**
	 * Método que realiza el cierre del aplicativo.
	 * @return
	 */
	public String cerrarSesionUsuario() {
		OAuth2CdmxClient clienteOAuth2Cdmx = new OAuth2CdmxClient();
		try {
			//LOGGER.info("Token: " + tokenOauth);
			if(tokenOauth != null) {
				JSONObject tokenObj = new JSONObject(tokenOauth);
				String strToken = tokenObj.get("accessToken").toString();
				clienteOAuth2Cdmx.cerrarSesionConLlaveCDMX(strToken);
			}
		} catch (URISyntaxException | NoSuchAlgorithmException e) {
			LOGGER.warn("Problemas el servicio logout de Llave CDMX: ", e);
		} catch (JSONException e) {
			LOGGER.error("Error al crear el json del token");
		} 
		String respuesta = "";
		HttpSession cerrarSesion = request.getSession(false);
		cerrarSesion.invalidate();
		respuesta = Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT;
		return respuesta;
	}

	/**
	 * Método que inicializa la vista del Home.
	 * @return
	 */
	public String incializarHome() {
		return Constantes.RETURN_HOME_PAGE + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Método que se utiliza para que se pinte el meta de noindex.
	 * @return
	 */
	public String parametroMetaRobots() {
		return Environment.getAppProfile().compareTo("dev") == 0 ? "noindex" : "all";
	}
	
	/**
	 * Método que cierra las notificación del cintillo de vacunación
	 * @param event
	 */
	public void onCloseNotificacionHeader(CloseEvent event) {
		mostrarNotificacion = false;
	}
	
	/*
	 * Método para consultar si está en modo desarrollo
	 */
	public boolean esDesarrollo() {
		return Environment.getAppProfile().compareTo("local") == 0;
	}
	
	/*
	 * Método para Inserar/actualizar datos del usuario que inicia sesión 
	 */
	public void insertaDatosUsuario(UsuarioDTO usuario) {
		userDTO = usuarioDAO.buscarPorId(usuario.getIdUsuarioLlaveCdmx());
		
		if (userDTO.getIdUsuarioLlaveCdmx() == null) {
			userDTO.setIdUsuarioLlaveCdmx(usuario.getIdUsuarioLlaveCdmx());
			userDTO.setNombre(usuario.getNombre());
			userDTO.setPrimerApellido(usuario.getPrimerApellido());
			userDTO.setSegundoApellido(usuario.getSegundoApellido());
			userDTO.setCurp(usuario.getCurp());
			userDTO.setTelefono(usuario.getTelefono());
			userDTO.setCorreo(usuario.getCorreo());
			userDTO.setFechaNacimiento(usuario.getFechaNacimiento());
			userDTO.setSexo(usuario.getSexo());
			usuarioDAO.guardar(userDTO);
		} else {
			userDTO.setNombre(usuario.getNombre());
			userDTO.setPrimerApellido(usuario.getPrimerApellido());
			userDTO.setSegundoApellido(usuario.getSegundoApellido());
			userDTO.setCurp(usuario.getCurp());
			userDTO.setTelefono(usuario.getTelefono());
			userDTO.setCorreo(usuario.getCorreo());
			userDTO.setFechaNacimiento(usuario.getFechaNacimiento());
			userDTO.setSexo(usuario.getSexo());
			usuarioDAO.actualizar(userDTO);
		}
	}
	
	public String getAccessToken() {
		String accessToken = null;
		try {
			accessToken = new JSONObject( getTokenOauth() ).getString("accessToken");
		} catch (JSONException e) {
			LOGGER.error("No se pudo obtener el accessToken del token obtenido de Llave", e);
		}
		return accessToken;
	}
	
	/**
	 * @return the usuarioLogueado
	 */
	public UsuarioDTO getUsuarioLogueado() {
		return usuarioLogueado;
	}
	
	/**
	 * @return the mostrarNotificacion
	 */
	public boolean isMostrarNotificacion() {
		return mostrarNotificacion;
	}
	
	/**
	 * @param mostrarNotificacion the mostrarNotificacion to set
	 */
	public void setMostrarNotificacion(boolean mostrarNotificacion) {
		this.mostrarNotificacion = mostrarNotificacion;
	}
	
	/**
	 * @return the ingresaDesdeLlave
	 */
	public boolean isIngresaDesdeLlave() {
		return ingresaDesdeLlave;
	}
	
	/**
	 * @param ingresaDesdeLlave the ingresaDesdeLlave to set
	 */
	public void setIngresaDesdeLlave(boolean ingresaDesdeLlave) {
		this.ingresaDesdeLlave = ingresaDesdeLlave;
	}
	
	/**
	 * @return the urlInicio
	 */
	public String getUrlInicio() {
		return urlInicio;
	}	
	
	public boolean isManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(boolean manifiesto) {
		this.manifiesto = manifiesto;
	}
	public String getStateOauth2() {
		return stateOauth2;
	}
	public void setStateOauth2(String stateOauth2) {
		this.stateOauth2 = stateOauth2;
	}
	public String getEtiquetaLogueo() {
		return etiquetaLogueo;
	}
	public void setEtiquetaLogueo(String etiquetaLogueo) {
		this.etiquetaLogueo = etiquetaLogueo;
	}
	public Boolean getRolAdministrador() {
		return rolAdministrador;
	}
	public void setRolAdministrador(Boolean rolAdministrador) {
		this.rolAdministrador = rolAdministrador;
	}
	public Boolean getRolValidador() {
		return rolValidador;
	}
	public void setRolValidador(Boolean rolValidador) {
		this.rolValidador = rolValidador;
	}
	public Boolean getRolConsulta() {
		return rolConsulta;
	}
	public void setRolConsulta(Boolean rolConsulta) {
		this.rolConsulta = rolConsulta;
	}
	public Boolean getEsExtranjero() {
		return esExtranjero;
	}
	public void setEsExtranjero(Boolean esExtranjero) {
		this.esExtranjero = esExtranjero;
	}
	public Boolean getRolTutor() {
		return rolTutor;
	}
	public void setRolTutor(Boolean rolTutor) {
		this.rolTutor = rolTutor;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public String getTokenOauth() {
		return tokenOauth;
	}
	
}
