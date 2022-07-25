package mx.gob.cdmx.adip.widget.client;

import java.io.Serializable;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;
import mx.gob.cdmx.adip.widget.indicadoresDTO.NotificacionDTO;

public class NotificacionesCdmxClient implements Serializable{
	
	private static final long serialVersionUID = -5549079697541754440L;
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionesCdmxClient.class);
	
	public NotificacionDTO consultaNotificaciones() {
		NotificacionDTO notificacionDTO = new NotificacionDTO();
		String output = "";
		ClientResponse cr = null;
		try {
			WebResource webResource = JerseyUtil.getInstance().getClient().resource("https://www.cdmx.gob.mx/rest/notificaciones/consultar");
			cr = webResource.accept("application/json").get(ClientResponse.class);
			output = cr.getEntity(String.class);
			
			JSONObject jsonObj = new JSONObject(output);
			String codigo = jsonObj.get("codigo").toString();
			String activo = jsonObj.get("activo").toString();
		  	String mensaje = jsonObj.get("mensaje").toString();
		 	String color = jsonObj.get("background-color").toString();
		 	String href = jsonObj.get("href").toString();
		 	
		 	notificacionDTO = new NotificacionDTO(Integer.parseInt(codigo), Boolean.parseBoolean(activo), mensaje, color, href); 
		} catch(JSONException e) {
			notificacionDTO.setCodigo(400);
			LOGGER.error("Ocurrio un error al leer el JSON de respuesta del WS de Notificaciones CDMX:", e);
			LOGGER.error("JSON obtenido: "+output);
		} catch(Exception e) {
			notificacionDTO.setCodigo(500);
			LOGGER.error("Ocurrio un error al consultar el servicio de Notificaciones CDMX: ", e);
		} finally {
			try{ if(cr!=null) { cr.close(); } } catch(Exception e) {LOGGER.warn("No se pudo cerrar correctamente el resource", e);}
		}
		return notificacionDTO;
	}
}