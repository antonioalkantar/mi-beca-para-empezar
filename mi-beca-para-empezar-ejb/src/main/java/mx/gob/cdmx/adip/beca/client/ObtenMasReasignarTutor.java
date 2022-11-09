package mx.gob.cdmx.adip.beca.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.ws.http.HTTPException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;

public class ObtenMasReasignarTutor implements Serializable{

	private static final long serialVersionUID = -4842837993992358686L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ObtenMasReasignarTutor.class);
	
	public RespuestaDTO reasignarTutor(String token, String curpTutor, String curpBeneficiario){
		RespuestaDTO respuesta = new RespuestaDTO();	
		
		HttpURLConnection http = null;
		OutputStream stream = null;
		InputStreamReader inputStream = null;
		BufferedReader br = null;
		try {
			URL url = new URL(Environment.getUrlObtenMas() + Environment.getUrlObtenMasReasignarTutor());
			http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer " + token + "");
			http.setRequestProperty("Content-Type", "application/json");
			StringBuilder str = new StringBuilder();
			str.append("{ \"tutorCurp\": \"" + curpTutor + "\", \"studenCurp\": \"" + curpBeneficiario + "\"");
			str.append("}");

			String data = str.toString();

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			stream = http.getOutputStream();

			stream.write(out);
			switch (http.getResponseCode()) {
			case 200:
				inputStream = new InputStreamReader(http.getInputStream());
				br = new BufferedReader(inputStream);
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				JSONObject jsonObj = new JSONObject(sb.toString());
				JSONObject dataJson = new JSONObject(jsonObj.get("data").toString());
				respuesta.setCode(Math.round(Integer.parseInt(jsonObj.get("code").toString())));
				respuesta.setMensaje(dataJson.get("message").toString());
				break;
			case 400:
            	if (http.getErrorStream()!= null) {
            		inputStream = new InputStreamReader(http.getErrorStream());
                    br = new BufferedReader(inputStream);
                    sb = new StringBuilder();
                    String line2;
                    while ((line2 = br.readLine()) != null) {
                        sb.append(line2+"\n");
                    }                
                    jsonObj = new JSONObject(sb.toString());
        			respuesta.setCode(Math.round(Integer.parseInt(jsonObj.get("status").toString())));
        			respuesta.setStatus(Math.round(Integer.parseInt(jsonObj.get("status").toString())));        			
                    respuesta.setMensaje("La transacción no pudo realizarse");
        		break;
				}
			default:
				respuesta.setCode(99);
				respuesta.setMensaje("La transacción no pudo realizarse");
				break;
			}
		
		} catch (HTTPException e) {
			LOGGER.error("Error to RespuestaDTO --> HTTPException",e);
		} catch (IOException e) {
			LOGGER.error("Error to RespuestaDTO --> IOException", e);
		} catch (JSONException e) {
			LOGGER.error("Error al leer el JSON de reasignado de tutor -> JSONException", e);
		} finally {
				if (br != null) {try {br.close();} catch (IOException e) {LOGGER.warn("No se cerro el BufferedReader", e);}				}
				if (inputStream != null) {try {	inputStream.close();} catch (IOException e) {LOGGER.warn("No se cerro el inputStream", e);}	}
				if (stream != null) {try {stream.close();} catch (IOException e) {LOGGER.warn("No se cerro el stream", e);}}				
				if (http!=null) {try {http.disconnect();} catch (Exception e) {LOGGER.warn("Error al cerrar Buffer ",e);}}
		}		
		return respuesta;
	}

}
