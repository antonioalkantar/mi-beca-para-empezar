package mx.gob.cdmx.adip.beca.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.commons.utils.DateUtils;
import mx.gob.cdmx.adip.beca.commons.utils.DateUtils.DateFormatStyle;

public class ObtenMasRegistrarTutor implements Serializable{
	
	private static final long serialVersionUID = -74838539782729734L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ObtenMasRegistrarTutor.class);
	
	public RespuestaDTO registrarTutor(String token, TutorDTO tutorDTO, List<BeneficiarioDTO> lstBeneficiario) throws IOException{
		
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setCode(99);
		respuesta.setStatus(99);
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
				str.append("\"name\": \""+tutorDTO.getNombre()+"\", \"lastName\": \""+tutorDTO.getPrimerApellido()+"\","); 
				str.append("\"motherLastName\": \"");
				if(tutorDTO.getSegundoApellido() != null) {
					str.append(tutorDTO.getSegundoApellido());
				}
				str.append("\", ");
				str.append("\"isForeign\": "+tutorDTO.isEsExtranjero()+", ");	
			}
			str.append("\"students\": [");
			for(int i = 0; i < lstBeneficiario.size(); i++) {
				str.append("{");		
				str.append("\"curp\": \""+lstBeneficiario.get(i).getCurpBeneficiario()+"\" ");				
				lstBeneficiario.get(i).setEsExtranjero(lstBeneficiario.get(i).getNacionalidad().equals("MEX") ? false : true);
				if(lstBeneficiario.get(i).getEsExtranjero()) {
					str.append(",");
					str.append("\"name\": \""+lstBeneficiario.get(i).getNombresBeneficiario()+"\" ,");
					str.append("\"lastName\": \""+lstBeneficiario.get(i).getPrimerApellidoBeneficiario()+"\" ,");
					str.append("\"motherLastName\": \"");
					if(lstBeneficiario.get(i).getSegundoApellidoBeneficiario() != null) {
						str.append(lstBeneficiario.get(i).getSegundoApellidoBeneficiario());
					}
					str.append("\", ");
					
					str.append("\"birthday\": \""+DateUtils.convertDateToString(lstBeneficiario.get(i).getFechaNacimientoBeneficiario(), DateFormatStyle.YMD_WITH_DASH )+"\" ,");
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
    			respuesta.setStatus(Math.round(Integer.parseInt(jsonObj.get("code").toString())));
    			for (int i = 0; i < dataJson.length(); i ++)
        		{
    				itemObj = (JSONObject)dataJson.get(i);
        			cuentaBeneficiariolst.add(itemObj.get("account").toString());
        			curpBeneficiariolst.add(itemObj.get("curp").toString());
        		}
                respuesta.setCuentaBeneficiario(cuentaBeneficiariolst);
                respuesta.setCurpBeneficiario(curpBeneficiariolst);
                break;
            case 400:
            	if (http.getErrorStream()!= null) {
            		ist = new InputStreamReader(http.getErrorStream());
                    br = new BufferedReader(ist);
                    sb = new StringBuilder();
                    String line2;
                    while ((line2 = br.readLine()) != null) {
                        sb.append(line2+"\n");
                    }                
                    jsonObj = new JSONObject(sb.toString());
        			respuesta.setCode(Math.round(Integer.parseInt(jsonObj.get("status").toString())));
        			respuesta.setStatus(Math.round(Integer.parseInt(jsonObj.get("status").toString())));
        			respuesta.setMensaje(jsonObj.get("detail").toString());
        			
        			if(Integer.parseInt(jsonObj.get("status").toString()) != Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA && Integer.parseInt(jsonObj.get("status").toString()) != Constantes.RESPUESTA_CORREO_ASOCIADO_A_CUENTA ) {
        				LOGGER.error("1-> El servicio web de pagatodo para registrar un tutor arrojó un Error 400. Peticion: "+str.toString()+", respuesta:"+jsonObj);
        			}
				}
            	break;    
              default:
            	LOGGER.error("El servicio web de pagatodo para registrar un tutor completo arrojó un Código de error desconocido:"+http.getResponseCode());
			}
		} catch (HTTPException e) {
			LOGGER.error("Error to RespuestaDTO --> HTTPException",e);
		} catch (JSONException e) {
			LOGGER.error("Error to RespuestaDTO --> JSONException",e);
		}  catch (Exception e) {
			LOGGER.error("Error to RespuestaDTO --> ",e);
		} 
		finally {
			if (br != null) {try {br.close();} catch (Exception e) {LOGGER.error("Error al cerrar Buffer ",e);}}
			if (ist != null) {try {ist.close();} catch (Exception e) {LOGGER.error("Error al cerrar InputStreamReadar ",e);}}			
			if (stream != null) {try {stream.close();} catch (Exception e) {LOGGER.error("Error al cerrar stream ",e);}}
			if (http != null) {try {http.disconnect();} catch (Exception e) {LOGGER.error("Error al cerrar Buffer ",e);}}
		}
		return respuesta;
	}
	
	
	public RespuestaDTO registrarSoloTutor(String token, TutorDTO tutorDTO){
		
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setCode(99);
		respuesta.setStatus(99);
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
			str.append("{ \"curp\": \""+tutorDTO.getCurp()+"\", ");
			str.append("\"phone\": \""+tutorDTO.getTelefono()+"\", ");
			str.append("\"email\": \""+tutorDTO.getCorreo()+"\", ");
			
			if(tutorDTO.isEsExtranjero()) {
				str.append("\"name\": \""+tutorDTO.getNombre()+"\", ");
				str.append("\"lastName\": \""+tutorDTO.getPrimerApellido()+"\", ");
				str.append("\"motherLastName\": \"");
				if(tutorDTO.getSegundoApellido() != null) {
					str.append(tutorDTO.getSegundoApellido());
				}
				str.append("\", ");
				str.append("\"birthday\": \""+DateUtils.convertDateToString(  DateUtils.convertStringToDate( tutorDTO.getFechaNacimiento(), DateFormatStyle.DMY_WITH_SLASH) , DateFormatStyle.YMD_WITH_DASH )+"\", ");
				str.append("\"gender\": \""+ (tutorDTO.getSexo().equalsIgnoreCase(Constantes.SEXO_HOMBRE) ? "Male" : "Female")+"\", ");
				str.append("\"isForeign\": true , ");
			}
			
			str.append("\"students\": [");			
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
    			respuesta.setStatus(Math.round(Integer.parseInt(jsonObj.get("code").toString())));
    			for (int i = 0; i < dataJson.length(); i ++)
        		{
    				itemObj = (JSONObject)dataJson.get(i);
        			cuentaBeneficiariolst.add(itemObj.get("account").toString());
        			curpBeneficiariolst.add(itemObj.get("curp").toString());
        		}
                respuesta.setCuentaBeneficiario(cuentaBeneficiariolst);
                respuesta.setCurpBeneficiario(curpBeneficiariolst);
                break;
                
            case 400:
            	if (http.getErrorStream()!= null) {
            		ist = new InputStreamReader(http.getErrorStream());
                    br = new BufferedReader(ist);
                    sb = new StringBuilder();
                    String line2;
                    while ((line2 = br.readLine()) != null) {
                        sb.append(line2+"\n");
                    }                
                    jsonObj = new JSONObject(sb.toString());
        			respuesta.setCode(Math.round(Integer.parseInt(jsonObj.get("status").toString()))); //Cuando marca error 400 no trae un objeto "code" por eso se coloca el status
        			respuesta.setStatus(Math.round(Integer.parseInt(jsonObj.get("status").toString())));
        			
        			if(Integer.parseInt(jsonObj.get("status").toString()) != Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA && Integer.parseInt(jsonObj.get("status").toString()) != Constantes.RESPUESTA_CORREO_ASOCIADO_A_CUENTA ) {
        				LOGGER.error("1-> El servicio web de pagatodo para registrar un tutor arrojó un Error 400. Peticion: "+str.toString()+", respuesta:"+jsonObj);
        			}
				} else if(http.getInputStream() != null) {
					ist = new InputStreamReader(http.getInputStream());
                    br = new BufferedReader(ist);
                    sb = new StringBuilder();
                    String line2;
                    while ((line2 = br.readLine()) != null) {
                        sb.append(line2+"\n");
                    }                
        			respuesta.setCode(400); //Cuando marca error 400 no trae un objeto "code" por eso se coloca el status
        			respuesta.setStatus(400);
        			
        			LOGGER.error("2-> El servicio web de pagatodo para registrar un tutor arrojó un Error 400. Peticion: "+str.toString()+", respuesta:"+sb.toString());
				} else {
					LOGGER.error("El servicio web de pagatodo para registrar un tutor arrojó un Error 400. Peticion: "+str.toString()+", respuesta: SIN RESPUESTA");
				}
            	break;
            default:
            	LOGGER.error("El servicio web de pagatodo para registrar un tutor arrojó un Código de error desconocido:"+http.getResponseCode() + ", " + tutorDTO.toString());
            	    
			}
		} catch (HTTPException e) {
			LOGGER.error("Error to RespuestaDTO --> HTTPException",e);
		} catch (IOException e) {
			LOGGER.error("Error to RespuestaDTO --> IOException",e);
		} catch (JSONException e) {
			LOGGER.error("Error to RespuestaDTO --> JSONException",e);
		} catch (ParseException e) {
			LOGGER.error("Error al parsear la fecha de nacimiento --> ParseException",e);
		} catch (Exception e) {
			LOGGER.error("Error to RespuestaDTO --> ",e);
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
