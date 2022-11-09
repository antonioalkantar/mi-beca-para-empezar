package mx.gob.cdmx.adip.beca.client;

import java.io.Serializable;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.*;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.ine.dto.IneDTO;

public class IneRestClient implements Serializable {
	
	private static final long serialVersionUID = -7429923359220837195L;
	private static final Logger LOGGER = LoggerFactory.getLogger(IneRestClient.class);

	public IneDTO obtenerDatosINE(final String cic, final String claveElector, final String emision, final String ocr,final int tipoIne, String curp) throws IneException {
		IneDTO ineResp = null;

		String respuesta = null;
		ClientResponse response = null;
		try {
			URI uri = new URI( armaUrlServicioIne(cic, claveElector, emision, ocr, tipoIne, curp) );
			WebResource webResource = JerseyUtil.getInstance().getClientINEWithAuth().resource(uri.toString());
			
			response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			respuesta = response.getEntity(String.class);
			if (response.getStatus() == 200) {
				JSONObject jsonINE = new JSONObject(respuesta);
//				LOGGER.info("Respuesta:" + respuesta);
				
				if (jsonINE.getJSONObject("respuestaSituacionRegistral") != null  && jsonINE.getJSONObject("respuestaSituacionRegistral").get("tipoSituacionRegistral") != null) {
					
					if(jsonINE.getJSONObject("respuestaComparacion") != null 
						&& ( Boolean.valueOf(jsonINE.getJSONObject("respuestaComparacion").get("curp").toString()) == null
						     || Boolean.valueOf(jsonINE.getJSONObject("respuestaComparacion").get("curp").toString())  ) == false) {
						throw new IneException("msj_datos_incorrectos_ine");
						
					}else {
						ineResp = new IneDTO( jsonINE.getJSONObject("respuestaSituacionRegistral").getString("tipoSituacionRegistral"),
								Boolean.valueOf(jsonINE.getJSONObject("respuestaComparacion").get("curp").toString()));
					}
				} else {
					LOGGER.error("El Webservice del INE contestó con Estatus 200 pero no tenía el objeto 'respuestaSituacionRegistral'. Parámetros: cic="+cic+", claveElector="+claveElector+", emision="+emision+", OCR="+ocr+",tipoINE:"+tipoIne+", CURP:"+curp+".  Esta fue la respuesta obtenida: "+respuesta);
					throw new IneException("msj_no_conexion_ine");
				}
			} else if (response.getStatus() == 400) {
				// Los errores 400 son errores del lado del cliente, es decir de esta programación, por ejemplo un dato que mandamos que no cumple las validaciones de longitud, de obligatoriedad, etc.
				// Si eso pasa se le mandará al ciudadano el mensaje de que sus datos son incorrectos para que los vuelva a capturar. Pero esto no debería de estarle apareciendo al ciudadano.
				LOGGER.warn("El Webservice del INE nos devolvió un código: "+response.getStatus() +". Revisar que validación faltó del lado del front. Parámetros: cic="+cic+", claveElector="+claveElector+", emision="+emision+", OCR="+ocr+",tipoINE:"+tipoIne+", CURP:"+curp+".  Esta fue la respuesta obtenida: "+respuesta);
				throw new IneException("msj_datos_incorrectos_ine");
			} else  {
				LOGGER.error("El Webservice del INE nos devolvió un código de error: "+response.getStatus() +". Parámetros: cic="+cic+", claveElector="+claveElector+", emision="+emision+", OCR="+ocr+",tipoINE:"+tipoIne+", CURP:"+curp+".  Esta fue la respuesta obtenida: "+respuesta);
				throw new IneException("msj_no_conexion_ine");
			} 
		} catch (URISyntaxException e) {
			LOGGER.error("Error al armar la URL del INE. Parámetros: cic="+cic+", claveElector="+claveElector+", emision="+emision+", OCR="+ocr+",tipoINE:"+tipoIne+", CURP:"+curp, e);
			throw new IneException("msj_no_conexion_ine");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio web de INE: ", e);
			throw new IneException("msj_no_conexion_ine");
		} catch (ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio WEB del INE: ", ex);
			throw new IneException("msj_no_conexion_ine");
		} catch (JSONException e) {
			LOGGER.error("ERROR al convertir JSON del INE: ", e);
		} finally {
			 if(response != null) {try{response.close();}catch(Exception e) {LOGGER.warn("No es posible cerrar el objeto response",e);}}
		}
		return ineResp;
	}
	
	private String armaUrlServicioIne(final String cic, final String claveElector, final String emision, final String OCR,final int tipoINE, String CURP) {
		StringBuilder urlQuery = new StringBuilder();
		urlQuery.append(Environment.getUrlServicioINE());
		urlQuery.append("?");
		
		switch (tipoINE) {
			case 3:
				urlQuery.append("ocr=").append(OCR.replace(Constantes.ESPACIO, Constantes.EMPTY_STRING));
				urlQuery.append("&claveElector=").append(claveElector.replace(Constantes.ESPACIO, Constantes.EMPTY_STRING));
				urlQuery.append("&numeroEmisionCredencial=").append(emision.replace(Constantes.ESPACIO, Constantes.EMPTY_STRING));
				urlQuery.append("&curp=").append(CURP.replace(Constantes.ESPACIO, Constantes.EMPTY_STRING));
				break;
			default:
				urlQuery.append("cic=").append(cic.replace(Constantes.ESPACIO, Constantes.EMPTY_STRING));
				urlQuery.append("&curp=").append(CURP.replace(Constantes.ESPACIO, Constantes.EMPTY_STRING));
				break;
		}
		return urlQuery.toString();
	}
	
	public static class IneException extends Exception {

		private static final long serialVersionUID = -4246678461721944770L;
		
		public IneException(String mensaje) {
			super(mensaje);
		}
	}

}
