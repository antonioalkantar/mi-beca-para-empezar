package mx.gob.cdmx.adip.widget.client;

import java.io.Serializable;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.JerseyUtil;
import mx.gob.cdmx.adip.widget.indicadoresDTO.ServicioSemaforoEpDTO;

public class ServicioEpidemiologicoClient implements Serializable{
	private static final long serialVersionUID = -5549079697541754440L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioEpidemiologicoClient.class);
	
	public ServicioSemaforoEpDTO consultaSemaforoEpidemiologico() {
		ServicioSemaforoEpDTO servicioSemaforo = new ServicioSemaforoEpDTO();
		String output = "";
		ClientResponse cr = null;
		try {
			WebResource webResource = JerseyUtil.getInstance().getClient().resource(Environment.getUrlServicioSemaforoEpidemiologico());
			cr = webResource.accept("application/json").get(ClientResponse.class);
			output = cr.getEntity(String.class);
			
			JSONObject jsonObj = new JSONObject(output);
			String codigo = jsonObj.get("codigo").toString();
		  	String nombre = jsonObj.get("nombre").toString();
		 	String color = jsonObj.get("color").toString();
		 	
			servicioSemaforo.setNombre(nombre);
			servicioSemaforo.setColor(color);
			servicioSemaforo.setCodigo(Integer.parseInt(codigo));
		}catch(JSONException e) {
			servicioSemaforo.setCodigo(400);
			LOGGER.error("Ocurrio un error al leer el JSON de respuesta del WS del Semaforo epidemiologico:", e);
			LOGGER.error("JSON obtenido: "+output);
		}catch(Exception e) {
			servicioSemaforo.setCodigo(500);
			LOGGER.error("Ocurrio un error al consultar el servicio del Semaforo epidemiologico: ", e);
		}finally {
			try{ if(cr!=null) { cr.close(); } } catch(Exception e) {LOGGER.warn("No se pudo cerrar correctamente el resource", e);}
		}
		return servicioSemaforo;
	}
}