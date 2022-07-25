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
import mx.gob.cdmx.adip.ine.dto.IneDTO;

public class IneRESTClient implements Serializable {
	private static final long serialVersionUID = -7429923359220837195L;
	private static final Logger LOGGER = LoggerFactory.getLogger(IneRESTClient.class);

	public IneDTO obtenerDatosINE(String cic, String claveElector, String emision, String OCR, int tipoINE)
			throws URISyntaxException, ConnectException, JSONException {
		IneDTO ineResp = null;
		StringBuilder urlQuery = new StringBuilder();
		urlQuery.append(Environment.getUrlServicioINE());
		urlQuery.append("?");
		ClientResponse response = null;
		switch (tipoINE) {
		case 3:
			urlQuery.append("ocr");
			urlQuery.append(OCR);
			urlQuery.append("&");
			urlQuery.append("claveElector");
			urlQuery.append(claveElector);
			urlQuery.append("&");
			urlQuery.append("numeroEmisionCredencial");
			urlQuery.append(emision);
			break;
		default:
			urlQuery.append("cic");
			urlQuery.append("=");
			urlQuery.append(cic);
			break;
		}

		URI uri = null;
		String respuesta = null;
		try {
			uri = new URI(urlQuery.toString());
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url: ", e);
			throw new URISyntaxException("", "La sintaxis de la url no es correcta.");
		}

		WebResource webResource = null;
		try {
			webResource = JerseyUtil.getInstance().getClientINEWithAuth().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio web de INE: ", e);
		}

		try {
			response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			respuesta = response.getEntity(String.class);
			JSONObject jsonINE = new JSONObject(respuesta);
			ineResp = new IneDTO();
			if (jsonINE.getJSONObject("respuestaSituacionRegistral") != null) {
				ineResp.setTipoSituacionRegistral(
						jsonINE.getJSONObject("respuestaSituacionRegistral").get("tipoSituacionRegistral").toString());
			} else {
				throw new JSONException(
						"No fue posible obtener los datos de la INE del JSON de respuesta - Respuesta del servicio web: ");
			}

			LOGGER.debug("Respuesta del servico INE : " + respuesta + " " + response.getStatus());
		} catch (ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio WEB:", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio web de INE");
		}
		 finally {
			 if(response != null) {response.close();}
		}

		return ineResp;
	}

}
