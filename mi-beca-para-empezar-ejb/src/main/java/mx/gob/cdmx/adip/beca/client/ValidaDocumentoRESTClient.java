package mx.gob.cdmx.adip.beca.client;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;

public class ValidaDocumentoRESTClient implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidaDocumentoRESTClient.class);

	/*
	 * Método para enviar a Llave la información de la documentación recuperada
	 */
	public Integer enviaEstatusDocumento(Long idUsuarioLlave, Long idUsuarioValidador, Integer idDocumento,
			boolean estatusDocumento, String token, Integer idMotivoRechazo, String observacionesRechazo){
		Integer codeResponse = null;
		URI uri = null;
		String respuesta = null;
		ClientResponse response = null;
		WebResource webResource = null;

		try {
			uri = new URI(Environment.getUrlServicioCDMXValidaDocumento());
			webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());

			Long idSistema = Long.valueOf(Environment.getAppId());
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("idUsuario", idUsuarioLlave);
			body.put("idUsuarioValidador", idUsuarioValidador);
			body.put("idSistema", idSistema);
			body.put("idDocumento", idDocumento);
			body.put("estatusDocumento", estatusDocumento);

			if (!estatusDocumento) {
				body.put("idMotivoRechazo", idMotivoRechazo);
				body.put("observacionesRechazo", observacionesRechazo);
			}

			response = webResource.accept(MediaType.APPLICATION_JSON).type("application/json")
					.post(ClientResponse.class, new Gson().toJson(body));
			respuesta = response.getEntity(String.class);

			JsonObject jsonDocument = new Gson().fromJson(respuesta, JsonObject.class);
			
			if (jsonDocument.get("codeResponse") != null|| jsonDocument.get("codeError") != null) {
				codeResponse =jsonDocument.get("codeResponse") != null ? jsonDocument.get("codeResponse").getAsInt() : jsonDocument.get("codeError").getAsInt();
			}else {
				LOGGER.warn("No fue posible obtener los datos de la CDMX del JSON de respuesta");
				LOGGER.error("Respuesta del servico CDMX : " + respuesta + ", Status:" + response.getStatus());
			}

		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url: ", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio web de CDMX: ", e);
		} catch (ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio WEB:", ex);
		} finally {
			if(response != null) {try {response.close();} catch (Exception e) {LOGGER.warn("ERROR cierre response: ", e);}}
		}

		return codeResponse;
	}

}
