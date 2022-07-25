package mx.gob.cdmx.adip.widget.client;

import java.io.Serializable;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.common.util.BeanUtils;
import mx.gob.cdmx.adip.widget.indicadoresDTO.ServicioClimaOpenClienteDTO;

public class ServicioClimaOpenCliente  implements Serializable{
	
	private static final long serialVersionUID = -2285223638155251058L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioClimaOpenCliente.class);
	
	public ServicioClimaOpenClienteDTO consultaClimaOpen() {
		ServicioClimaOpenClienteDTO climaOpen = new ServicioClimaOpenClienteDTO();
		
		JSONObject jsonObj = null;
		Client restClient = null;
		ClientResponse cr = null;
		try {
			restClient = Client.create();
			restClient.setConnectTimeout(5000);
			restClient.setReadTimeout(5000);
			WebResource webResource = restClient.resource(Environment.getUrlServicioClimaOpen());
			
			cr = webResource.accept("application/json").get(ClientResponse.class);
			String output = cr.getEntity(String.class);
			
			jsonObj = new JSONObject(output);
//			LOGGER.info("-------------->JSON clima:"+output);
		
			String weather = null;
			weather = jsonObj.get("weather").toString();
//			LOGGER.info("weather:"+weather);
					
		 	JSONArray jsonweatheArray =  new JSONArray(weather);
//		 	LOGGER.info("weather ARRAY:"+jsonweatheArray);
		 	if(jsonweatheArray != null && jsonweatheArray.length() > 0) {	
		 		String data = jsonweatheArray.getString(0);
		 		
		 		JSONObject jsonWeathertoOBj =  new JSONObject(data);
				String main = jsonWeathertoOBj.get("main").toString();
				String description = jsonWeathertoOBj.get("description").toString();
				String icon = jsonWeathertoOBj.get("icon").toString();		
				
				climaOpen.setMain(main);
				climaOpen.setDescription(BeanUtils.capitalizeFirstLetter( description));
				climaOpen.setIcon("https://openweathermap.org/img/w/"+icon+".png");
		 	}
		 	climaOpen.setCodigo(200);
		}catch(JSONException e) {
			climaOpen.setCodigo(400);
			LOGGER.error("Ocurrió un error al leer el JSON del Servicio de Clima:", e);
			LOGGER.error("Json clima openwheater: "+jsonObj);
		}catch(Exception e) {
			climaOpen.setCodigo(500);
			LOGGER.error("Ocurrió un error inesperado al consultar el Servicio de Clima", e);
		}finally {
			try{ if(cr!=null) { cr.close(); } } catch(Exception e) {LOGGER.warn("No se pudo cerrar correctamente el resource", e);}
			try{ if(restClient != null) { restClient.destroy(); } } catch(Exception e) {LOGGER.warn("No se pudo cerrar correctamente el restClient", e);}
		}
		return climaOpen;
	}
}