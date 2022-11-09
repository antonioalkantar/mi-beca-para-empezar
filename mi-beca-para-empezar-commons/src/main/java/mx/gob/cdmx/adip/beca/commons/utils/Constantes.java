package mx.gob.cdmx.adip.beca.commons.utils;

public final class Constantes {
	
	/**
	 * Constructor por defecto de la clase
	 */
	private Constantes() {
		/** Constructor vacío para que no se pueda instanciar la clase **/
	}
	
	//Constantes utilizadas en el acceso de Llave
	public static final String CONTENT_TYPE = "application/json;charset=utf-8";
	public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
	public final static String STATE_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";
	public static final String PARAM_CLIENT_ID = "client_id";
	public static final String PARAM_REDIRECT = "redirect_url";
	public static final String PARAM_CODE = "code";
	public static final String PARAM_STATE = "state";
	public static final String PROFILE_LOCAL = "local";
	public static final String PERSISTENTE_NAME = "miBecaParaEmpezarDS";
	
    public static final String TOKEN_SESSION = "token";
	
	//Constantes de uso general
    public static final String SEPARADOR = System.getProperty("file.separator");
	public static final String SEPARADOR_RUTA = "/";
    public static final String RETURN_SAME_PAGE = "";    
    public static final String JSF_REDIRECT = "?faces-redirect=true";
    public static final String RETURN_HOME_PAGE = "/home.xhtml";
    public static final String RETURN_WELCOME_PAGE = "/welcome.xhtml";
    public static final String RETURN_INDEX_PAGE = "/index.xhtml";
    
    //Rutas del home para el usuario y backoffice
    public static final String RETURN_HOME_USER_PAGE = "/protected/BandejaSolicitudes.xhtml";
    public static final String RETURN_HOME_BACKOFFICE_PAGE = "/protected/BandejaFuncionario.xhtml";
    public static final String RETURN_HOME_BACKOFFICE_PAGE_ADMIN_CONSULTA = "/protected/Consulta.xhtml";
    
	//Rutas de la administración del perfil de usuario
    public static final String RETURN_ADMIN_PERFIL_PAGE = "/protected/PerfilUsuario.xhtml"; 
    public static final String RETURN_REGISTRO_TUTOR_PAGE = "/protected/RegistroTutor.xhtml"; 
    public static final String RETURN_REGISTRO_BENEFICIARIO_PAGE = "/protected/RegistroBeneficiario.xhtml"; 
    public static final String RETURN_BANDEJA_SOLICITUD = "/protected/BandejaSolicitudes.xhtml"; 
    public static final String RETURN_TARJETA_BENEFICIARIO = "/protected/TarjetaBeneficiario.xhtml"; 
    public static final String RETURN_CAMBIAR_TUTOR = "/protected/CambioTutor.xhtml"; 
    public static final String RETURN_VALIDACION_TUTOR = "/protected/ValidacionTutor.xhtml";
    public static final String RETURN_REVALIDACION_ESTATUS_BENEFICIARIO_PAGE = "/protected/RevalidacionEstatusBeneficiario.xhtml";
 // Rutas bandeja validacion
 	public static final String RETURN_BANDEJA_VALIDACION = "/protected/BandejaValidacion.xhtml";
    
    //Rutas seccion solicitud
    public static final String RETURN_SOLICITUD_TUTOR = "/protected/seccionesTutor/InformacionTutor.xhtml"; 
    public static final String RETURN_SOLICITUD_BENEFICIARIO = "/protected/seccionesBeneficiario/InformacionBeneficiario.xhtml"; 
    public static final String RETURN_SOLICITUD_ENCUESTA = "/protected/seccionesBeneficiario/Encuesta.xhtml"; 
    public static final String RETURN_SOLICITUD_TARJETA = "/protected/seccionesSolicitud/InformacionTarjeta.xhtml"; 
    public static final String RETURN_SOLICITUD_HISTORIAL = "/protected/seccionesSolicitud/InformacionHistorial.xhtml"; 
    public static final String RETURN_SOLICITUD_REGISTRO_COMPLETADO = "/protected/RegistroCompletado.xhtml"; 
    public static final String RETURN_SOLICITUD_ENCUESTA_NUEVO_CICLO = "/protected/seccionesBeneficiario/EncuestaNuevoCiclo.xhtml"; 
    
    //titulos seccion solicitud
    public static final String RETURN_SOLICITUD_TITULO_TUTOR = "Información del tutor";
    public static final String RETURN_SOLICITUD_TITULO_BENEFICIARIO = "Información del beneficiario";
    public static final String RETURN_SOLICITUD_TITULO_ENCUESTA = "Encuesta";
    public static final String RETURN_SOLICITUD_TITULO_TARJETA = "Tarjeta";
    public static final String RETURN_SOLICITUD_TITULO_HISTORIAL = "Historial de Tutor";
	
    public static final int FIRST_INDEX_LIST = 0;
    public static final int INT_VALOR_CERO = 0;
	public static final int SIZE_ARRAY_EMPTY = 0;
	public static final String EMPTY_STRING = "";
    public static final String ESPACIO = " ";
    public static final int COMBO_OPCION_SELECCIONAR = 0;
    
    public static final Object OBJETO_NULO = null;
    public static final Object NULL = null;
    public static final String STRING_NULO = null;
            
    public static final int MAX_RESULT = 50;
    public static final int TAMAÑO_BUFFER = 1024;
    public static final int MILISECONDS_BY_DAY = 86400000;
    public static final int DIAS_ANIO = 365;
    
    public static final String FALSE= "false";
    
    public static final Integer ROL_ADMINISTRADOR = 368;
    public static final Integer ROL_VALIDADOR = 369;
    public static final Integer ROL_CONSULTA = 370;
    
    public static final int ID_IDENTIFICACION_OFICIAL = 1;
    
    public static final String NACIONALIDAD_MEXICANA = "MEXICANA";
    public static final String NACIONALIDAD_EXTRANJERA = "EXTRANJERA";
    public static final String RUTA_IDENTIFICACION_OFICIAL = "IdentificacionOficial";
    public static final String RUTA_COMPROBANTE_DOMICILIO = "ComprobanteDomicilio";
    public static final String RUTA_CAMBIO_TUTOR = "DoctoCambioTutor";
    
    public static final long VALOR_03_MB = 3145728; 
    public static final long VALOR_04_MB = 4194304; 
    public static final int ID_ESTATUS_EN_PROCESO = 1;
    public static final int ID_ESTATUS_APROBADA = 6;
    public static final int ID_ESTATUS_ACLARACION_POR_CIRCUNSTANCIA = 4;
    public static final int ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO = 7;
    public static final int ID_ESTATUS_CORREGIDA_POR_PARTE_CIUDADANO = 3;
    public static final int ID_ESTATUS_SUSPENDIDAS = 5;
    public static final String ESTATUS_VIGENTE_INE = "VIGENTE";
    
    public static final int ID_ESTATUS_BENEFICIARIO = 7;
    public static final int ID_ESTATUS_PENDIENTE_VALIDACION = 2;
    public static final String ID_CLAVE_FOLIO_SOLICITUD = "FIBIEN_BB";
    public static final String SEPARADOR_FOLIO = "_";
    public static final String BENEFICIARIO_ACTIVO = "Activo";
    public static final String BENEFICIARIO_ACTIVO_PAD_EXT = "ACTIVO";
    public static final String BENEFICIARIO_INACTIVO_PAD_EXT = "INACTIVO";
    public static final String BENEFICIARIO_ESCUELA_PRIVADA = "privada";
    public static final String BENEFICIARIO_NO_LOCALIZADO = "localizado";

    // Bandeja validacion
 	public static final int ID_TIPO_DISPERSION_ORDINARIA = 1;
 	public static final int ID_TIPO_DISPERSION_COMPLEMENTARIA = 2;
 	public static final int ID_ESTATUS_DISPERSION_EN_PROCESO = 1;
 	public static final int ID_ESTATUS_DISPERSION_PROCESANDO = 2;
 	public static final int ID_ESTATUS_DISPERSION_CONCLUIDO = 3;
 	public static final String TIPO_VALIDACION_ORDINARIA = "ordinaria";
 	public static final String TIPO_VALIDACION_COMPLEMENTARIA = "complementaria";
 	public static final String NOMBRE_ARCHIVO_REPORTE = "reporte_dispersion";
 	public static final String EXTENSION_ZIP = ".zip";
 	public static final String CONTENT_TYPE_ZIP = "application/zip";
    public static final String UTILES_ESCOLARES = "Útiles escolares";
    public static final String ROPA = "Ropa";
    public static final String ZAPATOS = "Zapatos";
    public static final String COMIDA = "Comida";
    public static final String JUGUETES = "Juguetes";
    public static final String OTRO = "Otro";
    
    public static final int ID_PARENTESCO_TUTOR = 9;
    public static final int ID_ESTATUS_BENEFICIARIO_OTRO = 99;
    public static final String DESCRIPCION_ESTATUS_BENEFICIARIO_OTRO = "OTRO";
    
    public static final String MENSAJE_BENEFICIARIO="El beneficiario con CURP ";
    public static final String MENSAJE_VALIDA_CURP_BENEFICIARIO = " ya tiene un registro activo. Por favor, verifica la información ingresada. Para realizar una aclaración o cambio de tutor deberás agenda una cita para acudir a las oficinas de FIBIEN. Da clic <a href=\"https://citas.cdmx.gob.mx/\" target=\"_blank\"\r\n"
    		+ "									style=\"color: #007bff\"> aquí</a> para agendarla.";
    public static final String MENSAJE_VALIDA_CURP_BENEFICIARIO_TUTOR = " ya tiene un registro asociado a otro Tutor. Por favor, verifica la información ingresada. Para realizar una aclaración o cambio de tutor, agenda una cita para acudir a las oficinas de FIBIEN. Da clic <a href=\"https://citas.cdmx.gob.mx/\" target=\"_blank\"\r\n"
    		+ "									style=\"color: #007bff\"> aquí</a> para agendarla.";
    public static final String MENSAJE_CAPTURA_DATOS_BENEFICIARIO = "Antes de continuar con la encuesta es necesario capturar los datos del beneficiario.";
  //public static final String MENSAJE_BENEFICIARIO_NO_LOCALIZADO = "No es posible dar de alta al beneficiario ingresado porque no fue localizado en una institución educativa";
    public static final String MENSAJE_BENEFICIARIO_NO_LOCALIZADO = "No es posible dar de alta al beneficiario ingresado porque no fue localizado en una institución educativa. Si tu beneficiario es de nuevo ingreso, cambió de nivel o realizó un cambio de escuela privada a pública intenta realizar su registro 5 días hábiles después de su inscripción";
    public static final String MENSAJE_BENEFICIARIO_SUSPENDIDO = "No es posible dar de alta al beneficiario ingresado porque se encuentra suspendido.";
    public static final String MENSAJE_BENEFICIARIO_ESCUELA_PRIVADA = "No es posible dar de alta al beneficiario ingresado porque se encuentra inscrito en una escuela privada.";
    public static final String MENSAJE_BENEFICIARIO_NIVEL_EDUCATIVO_NO_VALIDO = "No es posible dar de alta al beneficiario ingresado porque no pertenece al nivel o grado establecido en la convocatoria del programa social.";
    public static final String MENSAJE_BENEFICIARIO_ESCUELA_PUBLICA = "El beneficiario está inscrito en una escuela pública y puede proceder con el registro en este sitio.";
    public static final String MENSAJE_BENEFICIARIO_ERROR_VALIDACION = "No es posible validar los datos proporcionados en este momento, intente mas tarde.";
    public static final String MENSAJE_BENEFICIARIO_ERROR_CURP_RENAPO = "No existe información del beneficiario ingresado. Verifica la información ingresada e inténtalo nuevamente.";
    public static final String MENSAJE_ENCUESTA_ERROR_VALIDACION = "Seleccionar un valor mayor a cero";
    public static final String MENSAJE_ENCUESTA_ERROR_VALIDACION_PERS_TRABAJAN = "El número de personas que trabajan no puede ser mayor al de las personas que viven en el domicilio";
    public static final String MENSAJE_BENEFICIARIO_CORREO_TUTOR = "El correo electrónico está asociado al registro de otro tutor por lo que no puedes utilizarlo para tu registro. Actualiza tu correo en Llave CDMX para continuar con el registro.";
    public static final String MENSAJE_BENEFICIARIO_TELEFONO_TUTOR = "El teléfono está asociado al registro de otro tutor por lo que no puedes utilizarlo para tu registro. Actualiza tu teléfono en Llave CDMX para continuar con el registro.";
    public static final String MENSAJE_BENEFICIARIO_CORREO_Y_TELEFONO_TUTOR = "El correo electrónico y el teléfono están asociados al registro de otro tutor por lo que no puedes utilizarlos para tu registro. Actualiza tus métodos de contacto en Llave CDMX para continuar con el registro.";
    
    public static final String ENTIDAD_FORANEO = "Foráneo";
    public static final String ENTIDAD_CIUDAD_DE_MEXICO = "Cuidad de México";
    
    //NIVELES EDUCATIVOS
    public static final String DESC_LACTANTE = "LACTANTE";
    public static final String DESC_MATERNAL = "MATERNAL";
	public static final int ID_PREESCOLAR = 1;
	public static final String DESC_PREESCOLAR = "PREESCOLAR";
	public static final int ID_PRIMARIA = 2;
	public static final String DESC_PRIMARIA = "PRIMARIA";
	public static final int ID_SECUNDARIA = 3;
	public static final String DESC_SECUNDARIA = "SECUNDARIA";
	public static final int ID_PRIMARIA_ADULTOS = 4;
	public static final String DESC_PRIMARIA_ADULTOS = "PRIMARIA PARA ADULTOS";
	public static final int ID_SECUNDARIA_ADULTOS = 5;
	public static final String DESC_SECUNDARIA_ADULTOS = "SECUNDARIA PARA ADULTOS";
	public static final int ID_CAM_PREESCOLAR = 6;
	public static final String DESC_CAM_PREESCOLAR = "CENTRO DE ATENCIÓN MÚLTIPLE PREESCOLAR";
	public static final int ID_CAM_PRIMARIA = 7;
	public static final String DESC_CAM_PRIMARIA = "CENTRO DE ATENCIÓN MÚLTIPLE PRIMARIA";
	public static final int ID_CAM_SECUNDARIA = 8;
	public static final String DESC_CAM_SECUNDARIA = "CENTRO DE ATENCIÓN MÚLTIPLE SECUNDARIA";
	public static final int ID_CAM_LABORAL = 9;
	public static final String DESC_CAM_LABORAL = "CENTRO DE ATENCIÓN MÚLTIPLE LABORAL";
	public static final int ID_OTRO = 99;
	public static final String DESC_OTRO = "OTRO";
	
	//NIVELES EDUCATIVOS PADRON EXTERNO	
	public static final String DESC_CAM_PREESCOLAR_PAD_EXT = "CAM PREESCOLAR";
	public static final String DESC_CAM_PRIMARIA_PAD_EXT = "CAM PRIMARIA";
	public static final String DESC_CAM_SECUNDARIA_PAD_EXT = "CAM SECUNDARIA";
	public static final String DESC_CAM_LABORAL_PAD_EXT  = "CAM LABORAL";
	public static final String DESC_SECUNDARIA_ADULTOS_PAD_EXT = "SECUNDARIA ADULTOS";
	public static final String DESC_PRIMARIA_ADULTOS_PAD_EXT = "PRIMARIA ADULTOS";
	
	
	public static final String LADA_NACIONAL = "52";
	
    public static final int ESTATUS_BENEFICIARIO_ACTIVO = 1;
    
    public static final String ORIGEN_PADRON_EXTERNO = "padron externo";
    
    public static final Integer CODIGO_MUNICIPIO_ZERO = 0;
    
    public static final Integer ID_MUNICIPIO_FORANEO = 18;
    
    public static final Integer CODIGO_PAGATODO_NO_ENVIADO = 99;
    
    public static final String SEXO_HOMBRE = "HOMBRE";
    public static final String SEXO_MUJER = "MUJER";
    
    public static final int RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA = 7;
    public static final int RESPUESTA_CORREO_ASOCIADO_A_CUENTA = 11;
    public static final int RESPUESTA_PAGATODO_REGISTRO_CORRECTO = 0;
    public static final int RESPUESTA_TUTOR_MENOR_DE_EDAD = 12;
}