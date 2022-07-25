package mx.gob.cdmx.adip.beca.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.common.infra.Environment;

public class ObtenMasToken implements Serializable{

	private static final long serialVersionUID = 4122223737782887457L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ObtenMasToken.class);

	static String content = "client_id=ApiPRE&client_secret=186f50f4-fea5-4288-9455-9ba5fe4ff002&scope=ApiPRE&grant_type=client_credentials";
	static String strUrl = Environment.getUrlObtenMas()+Environment.getUrlObtenMasRequestToken();

	public String obtenerToken(){
        String token = "";
        
        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;  
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader streamReader = null;
        try {
        	
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            outputStream = urlConnection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();           
            inputStream = urlConnection.getInputStream();
            inputStreamReader =  new InputStreamReader (inputStream, "UTF-8");
            streamReader = new BufferedReader(inputStreamReader);
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null){
                responseStrBuilder.append(inputStr);
            }
            JSONObject jsonObject;
			
			jsonObject = new JSONObject(responseStrBuilder.toString());
			token = jsonObject.get("access_token").toString();
			 
            
        } catch (IOException e) {
        	LOGGER.error("Ocurrio un error IOException" ,e);
        } catch (JSONException e) {
        	LOGGER.error("Ocurrio un error JSONException" ,e);
		}finally {        	
			if (streamReader != null) { try { streamReader.close();} catch (IOException e) { LOGGER.warn("Ocurrio un error al cerrar el streamReader", e ); }}
			if (inputStreamReader != null) { try { inputStreamReader.close(); } catch (IOException e) { LOGGER.warn("Ocurrio un error al cerrar el inputStreamReader", e );}}
			if (inputStream != null)  {	try {inputStream.close();  } catch (IOException e) { LOGGER.warn("Ocurrio un error al cerrar el inputStream", e );}	}
			if (outputStream !=null) { try {outputStream.close(); } catch (IOException e) { LOGGER.warn("Ocurrio un error al cerrar el outoputStream", e );}}        	
			if(urlConnection != null) { urlConnection.disconnect(); } 
        }
        return token;
    }

}
