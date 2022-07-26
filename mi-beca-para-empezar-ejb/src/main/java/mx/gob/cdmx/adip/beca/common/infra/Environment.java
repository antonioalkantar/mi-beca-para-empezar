package mx.gob.cdmx.adip.beca.common.infra;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

/**
 * Clase utilizada para poder tener valores del profile en codigo Java. Tiene
 * Dependencia del archivo META-INF/env.properties
 * 
 * @author raul.soto
 *
 */
public final class Environment {

	/**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);
    
    /**
     * Indica el profile utilizado para la construccion de la aplicacion
     */
    private static String appProfile = "";
    
    /**
     * Indica si mostrara los mensajes de tiempo de respuesta de los EJB
     * Stateless
     */
    private static boolean ejbLog = false;   
    
    /**
     * Indica si esta habilitado la escritura de fases JSF
     */
    private static boolean jsfLifeCycle = false;

    /**
     * Indica si mostrara los mensajes de tiempo de respuesta de las peticiones HTTP
     */
    private static boolean webLog = false;
    
    /**
     * Indica la URL a la cual este sistema redireccionará para que la autenticación se realice mediante Llave CDMX
     */
    private static String urlLoginCdmx = "";
    
	/**
     * Indica la URL a la cual el SDK de Autenticación de la CDMX debe redireccionar trás haber iniciado sesión el usuario.
     */
    private static String urlRedirectLoginCdmx = "";
    
    private static String appId = "";
    
    private static String secretKey = "";
    		
    private static String userHttpBasicAuth = "";
    
    private static String passwordHttpBasicAuth = "";
    
    private static String urlServiceGetToken;
    
    private static String urlServiceGetDatosUsuario;
    
    private static String urlServiceGetRolesUsuario;
    
    private static String urlServiceLogout;
    
    /**
	 * Indica la URL para cincillo amarillo y sección de widget.
	 */

	private static String urlServicioClima = "";

	private static String urlServicioClimaOpen = "";

	private static String urlServicioSemaforoEpidemiologico = "";

	private static String urlHoyNoCirculaCDMX = "";
	
	/**
	 * Indica las credenciales para WS INE
	 */
	
	private static String urlServicioINE = "";
	
	/**
	 * Indica la url para el servicio de paga todo
	 */
	
	private static String clientSecret = "";
	
	private static String clientId = "";
	
	private static String urlObtenMas = "";
	
	private static String urlObtenMasRequestToken = "";
	
	private static String urlObtenMasRegistrarTutor = "";
	
	private static String urlObtenMasReasignarTutor = "";
	
	private static String urlObtenMasConsultarTutor = "";
	
	private static String userHttpBasicAuthINE = "";

	private static String passwordHttpBasicAuthINE = "";
	
	private static String urlServicioCurp= "";
	
    private static String userCurpBasicAuth = "";
    
    private static String passworCurpBasicAuth = "";
    
    //Ruta para guardar documentos
    private static String pathDocumentos;
    
    /*
     * url para consultar documentos de tutor extranjero
     */
    private static String urlServicioCDMXDocumento = "";

    private static String serviceAEFCMUser = "";
    private static String serviceAEFCMPassword = "";
    
    /*
     * url para enviar validación de documentos a llave
     */
    private static String urlServicioCDMXValidaDocumento = "";
    
    /**
     * Url que especifica la ruta donde estan almacenados los archivos de dispersion
     */
    private static String rutaArchivosDispersiones = "";
    
    /*
     * Url que especifica la ruta donde estan almacenados los archivos temporales
     */
    private static String pathDocumentosTemporales = "";

	/**
     * Inicializacion de variables utilizando como entrada el archivo
     * "META-INF/env.properties"
     */
    static {
        final Properties properties = new Properties();
        try {
            LOGGER.info(":: E n v i r o n m e n t  C o n f i g ::\n");
            properties.load(Environment.class.getClassLoader().getResourceAsStream("META-INF/env.properties"));
            
            appProfile = properties.getProperty("app.profile", "dev");
            LOGGER.info("ENV [appProfile:\t{}]", appProfile);
            
            ejbLog = Boolean.valueOf(properties.getProperty("ejb.log", Constantes.FALSE));
            LOGGER.info("ENV [ejbLog:\t\t{}]", ejbLog);
            
            jsfLifeCycle = Boolean.valueOf(properties.getProperty("jsf.lifeCycle", Constantes.FALSE));
            LOGGER.info("ENV [jsfLifeCycle:\t{}]", jsfLifeCycle);
            
            webLog = Boolean.valueOf(properties.getProperty("web.log", "true"));
            LOGGER.info("ENV [webLog:\t\t{}]", webLog);            
            
            urlLoginCdmx = properties.getProperty("login.urlLoginCdmx");
            LOGGER.info("ENV [login.urlLoginCdmx:\t\t{}]", urlLoginCdmx);
            
            urlRedirectLoginCdmx = properties.getProperty("login.urlRedirectLoginCdmx");
            LOGGER.info("ENV [login.urlRedirectLoginCdmx:\t\t{}]", urlRedirectLoginCdmx);
            
            appId = properties.getProperty("login.appId");
            LOGGER.info("ENV [login.appId:\t\t{}]", appId);
            
            secretKey = properties.getProperty("login.secretKey");
            LOGGER.info("ENV [login.secretKey:\t\t{}]", secretKey);
            
            userHttpBasicAuth = properties.getProperty("login.userHttpBasicAuth");
            LOGGER.info("ENV [login.userHttpBasicAuth:\t\t{}]", userHttpBasicAuth);
            
            passwordHttpBasicAuth = properties.getProperty("login.passwordHttpBasicAuth");
            LOGGER.info("ENV [login.passwordHttpBasicAuth:\t\t{}]", passwordHttpBasicAuth);
            
            /**
             * Servicios de Llave CDMX
             */
            urlServiceGetToken = properties.getProperty("services.cdmx.getToken");
            LOGGER.info("ENV [services.cdmx.getToken:\t\t{}]", urlServiceGetToken);
            
            urlServiceGetDatosUsuario = properties.getProperty("services.cdmx.getDatosUsuario");
            LOGGER.info("ENV [services.cdmx.getDatosUsuario:\t\t{}]", urlServiceGetDatosUsuario);
            
            urlServiceGetRolesUsuario = properties.getProperty("services.cdmx.getRolesUsuario");
            LOGGER.info("ENV [services.cdmx.getRolesUsuario:\t\t{}]", urlServiceGetRolesUsuario);
            
            urlServiceLogout = properties.getProperty("services.cdmx.logout");
			LOGGER.info("ENV [services.cdmx.logout:\t\t{}]", urlServiceLogout);

			urlServicioCDMXDocumento = properties.getProperty("services.cdmx.documentos.url");
            LOGGER.info("ENV [services.cdmx.documentos.url:\t\t{}]", urlServicioCDMXDocumento);
            
            urlServicioCDMXValidaDocumento = properties.getProperty("services.cdmx.valida.documentos.url");
            LOGGER.info("ENV [services.cdmx.valida.documentos.url:\t\t{}]", urlServicioCDMXValidaDocumento);
            
            /**
			 * SERVICIO INE
			 */
			urlServicioINE = properties.getProperty("services.cdmx.ine.url");
            LOGGER.info("ENV [services.cdmx.ine.url:\t\t{}]", urlServicioINE);
			
			userHttpBasicAuthINE = properties.getProperty("services.cdmx.ine.user");
            LOGGER.info("ENV [services.cdmx.ine.user:\t\t{}]", userHttpBasicAuthINE);
            
            passwordHttpBasicAuthINE = properties.getProperty("services.cdmx.ine.password");
            LOGGER.info("ENV [services.cdmx.ine.password:\t\t{}]", passwordHttpBasicAuthINE);
            
            /**
             * SERVICIO CURP
             */
            urlServicioCurp = properties.getProperty("services.cdmx.curp.url");
            LOGGER.info("ENV [services.cdmx.curp.url:\t\t{}]", urlServicioCurp);
            
            userCurpBasicAuth = properties.getProperty("services.cdmx.curp.user");
            LOGGER.info("ENV [services.cdmx.curp.user:\t\t{}]", userCurpBasicAuth);
            
            passworCurpBasicAuth = properties.getProperty("services.cdmx.curp.password");
            LOGGER.info("ENV [services.cdmx.curp.password:\t\t{}]", passworCurpBasicAuth);
			
			/** SERVICIOS DEL WIDGET **/
			urlServicioSemaforoEpidemiologico = properties.getProperty("services.cdmx.semaforoepidemiologico.url");
			LOGGER.info("ENV [services.cdmx.semaforoepidemiologico.url:\t\t{}]", urlServicioSemaforoEpidemiologico);

			urlHoyNoCirculaCDMX = properties.getProperty("services.cdmx.hoynocircula.url");
			LOGGER.info("ENV [services.cdmx.hoynocircula.url:\t\t{}]", urlHoyNoCirculaCDMX);	
			
			urlServicioClima = properties.getProperty("services.cdmx.simat.clima.url");
			LOGGER.info("ENV [services.cdmx.simat.clima.url:\t\t{}]", urlServicioClima);

			urlServicioClimaOpen = properties.getProperty("services.climaopen.url");
			LOGGER.info("ENV [services.climaopen.url:\t\t{}]", urlServicioClimaOpen);
            
            /**
			 * SERVICIO Pagatodo
			 */
            clientSecret = properties.getProperty("services.obtenmas.clientSecret");
            LOGGER.info("ENV [services.obtenmas.clientSecret:\t\t{}]", clientSecret);
            
            clientId = properties.getProperty("services.obtenmas.clientId");
            LOGGER.info("ENV [services.obtenmas.clientId:\t\t{}]", clientId);
            
        	urlObtenMas = properties.getProperty("services.obtenmas.url");
            LOGGER.info("ENV [services.obtenmas.url:\t\t{}]", urlObtenMas);
			
            urlObtenMasRequestToken = properties.getProperty("services.obtenmas.endpoint.token");
            LOGGER.info("ENV [services.obtenmas.endpoint.token:\t\t{}]", urlObtenMasRequestToken);
            
            urlObtenMasRegistrarTutor = properties.getProperty("services.obtenmas.endpoint.registrar.tutor");
            LOGGER.info("ENV [services.obtenmas.endpoint.registrar.tutor:\t\t{}]", urlObtenMasRegistrarTutor);
            
            urlObtenMasReasignarTutor = properties.getProperty("services.obtenmas.endpoint.reasignar.tutor");
            LOGGER.info("ENV [services.obtenmas.endpoint.reasignar.tutor:\t\t{}]", urlObtenMasReasignarTutor);
            
            urlObtenMasConsultarTutor = properties.getProperty("services.obtenmas.endpoint.consultar.tutor");
            LOGGER.info("ENV [services.obtenmas.endpoint.consultar.tutor:\t\t{}]", urlObtenMasConsultarTutor);

            /**
             * Servicios Autoridad Educativa de la CDMX
             */
            serviceAEFCMUser = properties.getProperty("services.aefcm.user");
            LOGGER.info("ENV [services.aefcm.user:\t\t{}]", serviceAEFCMUser);
            
            serviceAEFCMPassword = properties.getProperty("services.aefcm.password");
            LOGGER.info("ENV [services.aefcm.password:\t\t{}]", serviceAEFCMPassword);
            
            
            /**
             * Rutas del filesystem usadas por el sistema
             */
            pathDocumentos = properties.getProperty("beca.pathDocumentos");
            LOGGER.info("ENV [beca.pathDocumentos:\t\t{}]", pathDocumentos);
            
            rutaArchivosDispersiones = properties.getProperty("beca.pathDispersiones");
            LOGGER.info("ENV [beca.pathDispersiones:\t\t{}]", rutaArchivosDispersiones);
            
			/*
			 * Rutas del filesystem usadas por el sistema
			 */
			pathDocumentosTemporales = properties.getProperty("beca.pathDocumentosTemporales");
			LOGGER.info("ENV [beca.pathDocumentosTemporales:\t\t{}]", pathDocumentosTemporales);
            
            
        } catch (IOException e) {
            LOGGER.error("No se pueden cargar los valores de entorno de ejecución:", e);
        }
    }

    /**
     * Constructor privado para que no se instancíe esta clase desde afuera
     */
    private Environment() {
    }
    
    public static String getAppProfile() {
        return appProfile;
    }

    public static boolean isEJBLog() {
        return ejbLog;
    }

    public static boolean isJSFLifeCycle() {
        return jsfLifeCycle;
    }

    public static boolean isWEBLog() {
        return webLog;
    }

	public static String getUrlRedirectLoginCdmx() {
		return urlRedirectLoginCdmx;
	}

	public static String getUrlLoginCdmx() {
		return urlLoginCdmx;
	}

	public static String getUrlServiceGetToken() {
		return urlServiceGetToken;
	}
	
	public static String getUrlServiceGetDatosUsuario() {
		return urlServiceGetDatosUsuario;
	}
	
	public static String getUrlServiceGetRolesUsuario() {
		return urlServiceGetRolesUsuario;
	}
	
	public static String getUrlServiceLogout() {
		return urlServiceLogout;
	}
	
	public static String getAppId() {
		return appId;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public static String getUserHttpBasicAuth() {
		return userHttpBasicAuth;
	}

	public static String getPasswordHttpBasicAuth() {
		return passwordHttpBasicAuth;
	}
	
	public static String getUrlServicioClima() {
		return urlServicioClima;
	}

	public static String getUrlServicioClimaOpen() {
		return urlServicioClimaOpen;
	}

	public static String getUrlServicioSemaforoEpidemiologico() {
		return urlServicioSemaforoEpidemiologico;
	}

	public static String getUrlHoyNoCirculaCDMX() {
		return urlHoyNoCirculaCDMX;
	}

	public static String getUrlServicioINE() {
		return urlServicioINE;
	}
	
	public static String getUserHttpBasicAuthINE() {
		return userHttpBasicAuthINE;
	}

	public static String getPasswordHttpBasicAuthINE() {
		return passwordHttpBasicAuthINE;
	}

	public static String getUrlServicioCurp() {
		return urlServicioCurp;
	}

	public static String getUserCurpBasicAuth() {
		return userCurpBasicAuth;
	}

	public static String getPassworCurpBasicAuth() {
		return passworCurpBasicAuth;
	}	
	
	public static String getPathDocumentos() {
		return pathDocumentos;
	}

	public static String getUrlServicioCDMXDocumento() {
		return urlServicioCDMXDocumento;
	}

	public static String getServiceAEFCMUser() {
		return serviceAEFCMUser;
	}

	public static void setServiceAEFCMUser(String serviceAEFCMUser) {
		Environment.serviceAEFCMUser = serviceAEFCMUser;
	}

	public static String getServiceAEFCMPassword() {
		return serviceAEFCMPassword;
	}

	public static void setServiceAEFCMPassword(String serviceAEFCMPassword) {
		Environment.serviceAEFCMPassword = serviceAEFCMPassword;
	}
	
	public static String getClientSecret() {
		return clientSecret;
	}
	
	public static String getClientId() {
		return clientId;
	}

	public static String getUrlObtenMas() {
		return urlObtenMas;
	}

	public static String getUrlObtenMasRequestToken() {
		return urlObtenMasRequestToken;
	}

	public static String getUrlObtenMasRegistrarTutor() {
		return urlObtenMasRegistrarTutor;
	}

	public static String getUrlObtenMasReasignarTutor() {
		return urlObtenMasReasignarTutor;
	}

	public static String getUrlObtenMasConsultarTutor() {
		return urlObtenMasConsultarTutor;
	}
	
	public static String getUrlServicioCDMXValidaDocumento() {
		return urlServicioCDMXValidaDocumento;
	}

	public static String getRutaArchivosDispersiones() {
		return rutaArchivosDispersiones;
	}
	
	public static String getPathDocumentosTemporales() {
		return pathDocumentosTemporales;
	}

}
