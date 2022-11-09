package mx.gob.cdmx.adip.beca.client;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;
import mx.gob.cdmx.adip.beca.commons.dto.CurpDTO;

public class CurpRestClient implements Serializable{

	private static final long serialVersionUID = -138083616509843364L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CurpRestClient.class);

	public CurpDTO obtenerDatosCurp(String curp) throws URISyntaxException, ConnectException, JSONException {
		CurpDTO curpDTO = null;
		StringBuilder urlQuery = new StringBuilder();
		urlQuery.append(Environment.getUrlServicioCurp());
		urlQuery.append(curp);	

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
			webResource = JerseyUtil.getInstance().getClientCURPWithAuth().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio web de CURP: ", e);
		}

		try {
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			respuesta = response.getEntity(String.class);
			LOGGER.debug("Respuesta del servico curp : " + respuesta + " " + response.getStatus());
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de CURP:", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio web de CURP");
		}
		JSONObject jsonRespuesta = new JSONObject(respuesta);

		if (jsonRespuesta.get("statusOper").toString().compareTo("EXITOSO") == 0) {
			JSONObject jsonCurp = new JSONObject(respuesta);
			curpDTO = new CurpDTO();
			curpDTO.setCurp(jsonCurp.get("CURP").toString());
			curpDTO.setNombre(jsonCurp.get("nombres").toString());
			curpDTO.setPrimerApellido(jsonCurp.get("apellido1").toString());
			curpDTO.setSegundoApellido(jsonCurp.get("apellido2").toString());
			curpDTO.setNacionalidad(jsonCurp.get("nacionalidad").toString());
			curpDTO.setSexo(jsonCurp.get("sexo").toString());
			curpDTO.setFechaNacimiento(jsonCurp.get("fechNac").toString());
			curpDTO.setEstadoNacimiento(jsonCurp.get("cveEntidadNac").toString());
			curpDTO.setStatusCurp(jsonCurp.get("statusCurp").toString());
			curpDTO.setError(jsonCurp.get("TipoError").toString());
			curpDTO.setCurpValido(isCurpValido(curpDTO.getStatusCurp()));
		} else {
			throw new JSONException(
					"No fue posible obtener los datos de la CURP del JSON de respuesta - Respuesta del servicio web: "
							+ jsonRespuesta.get("statusOper").toString());
		}
		return curpDTO;
	}

	private boolean isCurpValido(String estatusCurp) {
		boolean isCurpValida = true;
		if (estatusCurp.toUpperCase().contains("BD")) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("CURP de persona fallecida");
			isCurpValida = false;
		}
		return isCurpValida;
	}

//	public static void main(String args[]) {
//	CurpRestClient curpClient = new CurpRestClient();
//		try {
//			CurpDTO curpDTO = curpClient.obtenerDatosCurp("AUCA801124HTLGRN07");
//			if(curpDTO != null)
//				System.out.println(curpDTO.toString());
//		} catch (ConnectException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
