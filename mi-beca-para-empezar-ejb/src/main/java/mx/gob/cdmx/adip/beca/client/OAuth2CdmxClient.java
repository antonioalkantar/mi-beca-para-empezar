package mx.gob.cdmx.adip.beca.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.oauth.dto.RequestRolesDTO;
import mx.gob.cdmx.adip.beca.oauth.dto.RequestTokenDTO;
import mx.gob.cdmx.adip.beca.oauth.dto.RolesUsuarioDTO;
import mx.gob.cdmx.adip.beca.oauth.dto.UsuarioDTO;

public class OAuth2CdmxClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2CdmxClient.class);

	/**
	 * Método que obtiene un Token de Llave mediante un objeto RequestTokenDTO
	 * @param requestToken
	 * @return
	 */
	public String obtenerToken(RequestTokenDTO requestToken) {
		String token = null;
		try {
//			LOGGER.info("Environment.getUrlServiceGetToken()"+Environment.getUrlServiceGetToken());
			URI uri = new URI(Environment.getUrlServiceGetToken());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", Constantes.CONTENT_TYPE)
					.post(ClientResponse.class, requestToken);
			
			token = response.getEntity(String.class);
			if(token.contains("error")) {
				LOGGER.warn("Token con error:"+token);
				token = null;
			}
		} catch (URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token porque la URI no es correcta:",e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
		} 
		return token;
	}
	
	/**
	 * Método que obtiene los datos del usuario de Llave, enviando un token de acceso
	 * @param token
	 * @return
	 */
	public UsuarioDTO obtenerDatosUsuarioPorToken(String token) {
		String respuesta = null;
		UsuarioDTO datosUsuario = new UsuarioDTO();
		try {
			URI uri = new URI(Environment.getUrlServiceGetDatosUsuario());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("accessToken", token)
					.get(ClientResponse.class);
			
			respuesta = response.getEntity(String.class);

			if(respuesta.contains("error")) {
				datosUsuario = null;
			} else {
				JSONObject jsonObj =  new JSONObject(respuesta);
				datosUsuario.setIdUsuarioLlaveCdmx(Long.parseLong(jsonObj.get("idUsuario").toString()));
				datosUsuario.setNombre(jsonObj.get("nombre").toString());
				datosUsuario.setPrimerApellido(jsonObj.get("primerApellido").toString());
				datosUsuario
						.setSegundoApellido(jsonObj.get("segundoApellido").toString().equalsIgnoreCase("null") ? null
								: jsonObj.get("segundoApellido").toString());
				datosUsuario.setCurp(jsonObj.get("curp").toString().equalsIgnoreCase("null") ? null
						: jsonObj.get("curp").toString());
				datosUsuario.setCorreo(jsonObj.get("login").toString());
				datosUsuario.setEsExtranjero((Boolean) jsonObj.get("esExtranjero"));
				datosUsuario.setLada(jsonObj.get("ladaExtranjero").toString().equalsIgnoreCase("null") ? null
						: jsonObj.get("ladaExtranjero").toString());
				datosUsuario
						.setTelefono(jsonObj.get("telVigente").toString().equalsIgnoreCase("null")
								? jsonObj.get("telefonoExtranjero").toString().equalsIgnoreCase("null") ? null
										: jsonObj.get("telefonoExtranjero").toString()
								: jsonObj.get("telVigente").toString());
				datosUsuario.setSexo(jsonObj.get("sexo").toString().equalsIgnoreCase("null") ? null
						: jsonObj.get("sexo").toString());
				datosUsuario
						.setFechaNacimiento(jsonObj.get("fechaNacimiento").toString().equalsIgnoreCase("null") ? null
								: jsonObj.get("fechaNacimiento").toString());
			}
		} catch(URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para obtener los datos del usuario por el Token porque la URI no es correcta:",e);
		} catch (JSONException e) {
			LOGGER.error("No fue posible obtener los datos del usuario del Json de respuesta", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
		} 
		return datosUsuario;
	}
	
	/**
	 * Método que obtiene los roles de un usuario logueado con llave
	 * @param roles
	 * @return
	 */
	public List<RolesUsuarioDTO> obtenerRolesUsuario(RequestRolesDTO roles, String token) {
		String respuesta = null;
		List<RolesUsuarioDTO> lstRoles = new ArrayList<RolesUsuarioDTO>();
		try {
//			URI uri = new URI("https://llave-dev.cdmx.gob.mx/rest/oauth/roles");
//			URI uri = new URI("https://llave.cdmx.gob.mx/rest/oauth/roles");
//			URI uri = new URI("http://10.19.104.18:8081/rest/oauth/roles");
			URI uri = new URI(Environment.getUrlServiceGetRolesUsuario());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("accessToken", token)
					.header("Content-Type", Constantes.CONTENT_TYPE).post(ClientResponse.class, roles);

			respuesta = response.getEntity(String.class);

			if (respuesta.contains("Mensaje")) {
				lstRoles = null;
			} else {
				JSONArray arrayJson = new JSONArray(respuesta);
				for (int i = Constantes.INT_VALOR_CERO; i < arrayJson.length(); i++) {
					JSONObject jsonObj = arrayJson.getJSONObject(i);
					RolesUsuarioDTO rolesDTO = new RolesUsuarioDTO();
					rolesDTO.setIdRol(jsonObj.getInt("idRol"));
					rolesDTO.setRol(jsonObj.get("rol").toString());
					lstRoles.add(rolesDTO);
				}
				/*for (RolesUsuarioDTO rolesUsuarioDTO : lstRoles) {
					LOGGER.info("List tamanio: " + lstRoles.size());
				}*/
			}
		} catch (URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para obtener los rolesdel usuario porque la URI no es correcta:", e);
		} catch (JSONException e) {
			LOGGER.error("No fue posible obtener los datos de los roles desde el Json de respuesta", e);
		}  catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
		} 
		return lstRoles;
	}
	
	public boolean cerrarSesionConLlaveCDMX(String token) throws URISyntaxException, NoSuchAlgorithmException {
		boolean logoutSucces = true;
		try {
			//LOGGER.info("Token: " + token);
			URI uri = new URI(Environment.getUrlServiceLogout());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource
					.header("accessToken", token)
					.header("Content-Type", Constantes.CONTENT_TYPE)
					.post(ClientResponse.class);
			
			if(response.getStatus() != Status.OK.getStatusCode()) {
				logoutSucces = false;
			}
		} catch (URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para realizar el logouit del usuario porque la URI no es correcta: ", e);
			logoutSucces = false;
			throw new URISyntaxException("Error", "No fue posible conectarse al servicio para realizar el logouit del usuario porque la URI no es correcta" );
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para realizar el logouit del usuario, posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
			logoutSucces = false;
			throw new NoSuchAlgorithmException("No fue posible conectarse al servicio para realizar el logouit del usuario, posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó");
		}
		return logoutSucces;
	}
}
