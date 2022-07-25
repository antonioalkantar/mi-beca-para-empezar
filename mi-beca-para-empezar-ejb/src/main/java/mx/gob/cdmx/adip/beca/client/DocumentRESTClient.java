package mx.gob.cdmx.adip.beca.client;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
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
import mx.gob.cdmx.adip.beca.commons.dto.DocumentoExtranjeroDTO;

public class DocumentRESTClient implements Serializable {

	private static final long serialVersionUID = -6221635585019209715L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRESTClient.class);

	public DocumentoExtranjeroDTO consultaDocumento(List<Integer> idTiposDoc, List<Integer> ordenPrioridad,
			boolean documentoValidado, String token){
		DocumentoExtranjeroDTO documentoExtranjero = null;
		URI uri = null;
		String respuesta = null;
		ClientResponse response = null;
		WebResource webResource = null;

		try {
			uri = new URI(Environment.getUrlServicioCDMXDocumento());

			webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());

			Map<String, Object> body = new HashMap<String, Object>();
			body.put("idSubtipoDocumento", idTiposDoc);
			body.put("soloDocumentosValidados", documentoValidado);
			body.put("ordenPrioridad", ordenPrioridad);
			response = webResource.accept(MediaType.APPLICATION_JSON).type("application/json")
					.header("accessToken", token).post(ClientResponse.class, new Gson().toJson(body));

			respuesta = response.getEntity(String.class);
			JsonObject jsonDocument = new Gson().fromJson(respuesta, JsonObject.class);

			if (jsonDocument.get("codeResponse") != null && jsonDocument.get("codeResponse").getAsInt() == 201) {
				documentoExtranjero = new Gson().fromJson(jsonDocument, DocumentoExtranjeroDTO.class);
				LOGGER.debug("Respuesta del servico CDMX : " + respuesta + " " + response.getStatus());
			} else if (response.getStatus() != 200) {
				LOGGER.info("Respuesta del servico CDMX : " + respuesta + ", Status:" + response.getStatus(),
						respuesta);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url: ", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio web de CDMX: ", e);
		} catch (ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio WEB:", ex);
		} finally { 
			if (response != null) { try { response.close(); } catch (Exception e) { LOGGER.warn("ERROR cirre response: ", e); } }
		}

		return documentoExtranjero;
	}

}
