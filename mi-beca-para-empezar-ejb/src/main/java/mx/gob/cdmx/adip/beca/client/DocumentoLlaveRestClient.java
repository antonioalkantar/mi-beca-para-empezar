package mx.gob.cdmx.adip.beca.client;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;
import mx.gob.cdmx.adip.beca.commons.dto.DocumentoLlaveDTO;

public class DocumentoLlaveRestClient implements Serializable {

	private static final long serialVersionUID = -6221635585019209715L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoLlaveRestClient.class);

	public DocumentoLlaveDTO consultaDocumento(List<Integer> idTiposDoc, List<Integer> ordenPrioridad, boolean documentoValidado, String token){
		DocumentoLlaveDTO documentoLlave = null;
		ClientResponse response = null;
		String respuesta = null;
		try {
			URI uri = new URI(Environment.getUrlServicioCDMXDocumento());

			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());

			Map<String, Object> body = new HashMap<String, Object>();
			body.put("idSubtipoDocumento", idTiposDoc);
			body.put("soloDocumentosValidados", documentoValidado);
			body.put("ordenPrioridad", ordenPrioridad);
			response = webResource.accept(MediaType.APPLICATION_JSON).type("application/json")
					.header("accessToken", token).post(ClientResponse.class, new Gson().toJson(body));

			respuesta = response.getEntity(String.class);
			JsonObject jsonDocument = new Gson().fromJson(respuesta, JsonObject.class);

			if (jsonDocument.get("codeResponse") != null && jsonDocument.get("codeResponse").getAsInt() == 201) {
				documentoLlave = new Gson().fromJson(jsonDocument, DocumentoLlaveDTO.class);
				LOGGER.debug("Respuesta del servico CDMX : " + respuesta + " " + response.getStatus());
			} else if (response.getStatus() != 200) {
				LOGGER.debug("Respuesta del servico CDMX : " + respuesta + ", Status:" + response.getStatus(), respuesta);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url: ", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio web de CDMX: ", e);
		} catch (ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio WEB:", ex);
		} catch (com.google.gson.JsonSyntaxException e){
			LOGGER.error("Error al obtener un documento desde Llave CDMX. Respuesta obtenida:'"+respuesta+"'. Error obtenido:", e);
		} finally { 
			if (response != null) { try { response.close(); } catch (Exception e) { LOGGER.warn("No se pudo cerrar response en DocumentRESTClient: ", e); } }
		}
		return documentoLlave;
	}
	
	
	/**
	 * Método para enviar a Llave la información de la documentación recuperada
	 */
	public Integer enviaEstatusDocumento(Long idUsuarioLlave, Long idUsuarioValidador, Integer idDocumento,
			boolean estatusDocumento, Integer idMotivoRechazo, String observacionesRechazo){
		Integer codeResponse = null;
		ClientResponse response = null;

		try {
			URI uri = new URI(Environment.getUrlServicioCDMXValidaDocumento());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());

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
			String respuesta = response.getEntity(String.class);

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
