package mx.gob.cdmx.adip.beca.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.http.HTTPException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.commons.dto.BeneficiarioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;

public class ObtenMasRegistrarTutor implements Serializable{
	
	private static final long serialVersionUID = -74838539782729734L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ObtenMasRegistrarTutor.class);
	
	public RespuestaDTO registrarTutor(String token, TutorDTO tutorDTO, List<BeneficiarioDTO> lstBeneficiario) throws IOException{
		
		RespuestaDTO respuesta = new RespuestaDTO();
		BufferedReader br = null;
		HttpURLConnection http = null;
		OutputStream stream = null;
        StringBuilder sb = null;
		StringBuilder str = null;
        JSONObject jsonObj = null;
		JSONArray dataJson = null;
		JSONObject itemObj = null;
		InputStreamReader ist = null;
		try {
			URL url = new URL(Environment.getUrlObtenMas()+Environment.getUrlObtenMasRegistrarTutor());
			http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer "+token+"");
			http.setRequestProperty("Content-Type", "application/json");
			str = new StringBuilder();
			str.append("{ \"curp\": \""+tutorDTO.getCurp()+"\", \"phone\": \""+tutorDTO.getTelefono()+"\", \"email\": \""+tutorDTO.getCorreo()+"\", ");
			if(tutorDTO.isEsExtranjero()) {
				str.append("\"name\": \""+tutorDTO.getNombre()+"\", \"lastName\": \""+tutorDTO.getPrimerApellido()+"\", \"motherLastName\": \""+tutorDTO.getSegundoApellido()+"\", ");	
				str.append("\"isForeign\": "+tutorDTO.isEsExtranjero()+", ");	
			}
			str.append("\"students\": [");
			for(int i = 0; i < lstBeneficiario.size(); i++) {
				str.append("{");		
				str.append("\"curp\": \""+lstBeneficiario.get(i).getCurpBeneficiario()+"\" ");
				if(lstBeneficiario.get(i).getEsExtranjero()) {
					str.append(",");
					str.append("\"name\": \""+lstBeneficiario.get(i).getNombresBeneficiario()+"\" ,");
					str.append("\"lastName\": \""+lstBeneficiario.get(i).getPrimerApellidoBeneficiario()+"\" ,");
					str.append("\"motherLastName\": \""+lstBeneficiario.get(i).getSegundoApellidoBeneficiario()+"\" ,");
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
					String strDate = dateFormat.format(lstBeneficiario.get(i).getFechaNacimientoBeneficiario());  
					
					str.append("\"birthday\": \""+strDate+"\" ,");
					//str.append("\"gender\": \""+lstBeneficiario.get(i).get+"\" ,");
					str.append("\"isForeign\": "+lstBeneficiario.get(i).getEsExtranjero()+" ");
				}
				str.append("}");	
				if (i + 1 != lstBeneficiario.size()) {
					str.append(",");
				}
			}
			str.append("]");	
			str.append("}");	
			
			String data = str.toString();
			
			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			stream = http.getOutputStream();
			stream.write(out);

			switch (http.getResponseCode()) {
            case 200:
            	ist = new InputStreamReader(http.getInputStream());
                br = new BufferedReader(ist);
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                
                jsonObj = new JSONObject(sb.toString());
    			dataJson = jsonObj.getJSONArray("data");
    			
    			List<String> cuentaBeneficiariolst = new ArrayList<String>();
    			List<String> curpBeneficiariolst = new ArrayList<String>();
    			respuesta.setCode(Math.round(Integer.parseInt(jsonObj.get("code").toString())));
    			for (int i = 0; i < dataJson.length(); i ++)
        		{
    				itemObj = (JSONObject)dataJson.get(i);
        			cuentaBeneficiariolst.add(itemObj.get("account").toString());
        			curpBeneficiariolst.add(itemObj.get("curp").toString());
        		}
                respuesta.setCuentaBeneficiario(cuentaBeneficiariolst);
                respuesta.setCurpBeneficiario(curpBeneficiariolst);
    			
			}
		} catch (HTTPException e) {
			LOGGER.error("Error to RespuestaDTO --> HTTPException",e);
		} catch (IOException e) {
			LOGGER.error("Error to RespuestaDTO --> IOException",e);
		} catch (JSONException e) {
			LOGGER.error("Error to RespuestaDTO --> JSONException",e);
		} 
		finally {
			
			if (br != null) {try {br.close();} catch (Exception e) {LOGGER.error("Error al cerrar Buffer ",e);}}
			if (ist != null) {try {ist.close();} catch (Exception e) {LOGGER.error("Error al cerrar InputStreamReadar ",e);}}			
			if (stream != null) {try {stream.close();} catch (Exception e) {LOGGER.error("Error al cerrar stream ",e);}}
			if (http != null) {try {http.disconnect();} catch (Exception e) {LOGGER.error("Error al cerrar Buffer ",e);}}
		}
	return respuesta;
	}	

}
