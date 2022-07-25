package mx.gob.cdmx.adip.beca.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

import mx.gob.cdmx.adip.beca.common.infra.Environment;

public class JerseyUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyUtil.class);
	
	private static JerseyUtil instance;
	private Client client;
	private Client clientSDKCdmxWithAuth;
	private Client clientINEWithAuth;
	private Client clientCURPWithAuth;
	
	private JerseyUtil() throws NoSuchAlgorithmException {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 30000); //3seg
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		confiarEnTodosLosCertificados();
		
		client = Client.create(clientConfig);
		client.setConnectTimeout(3000); // Establecer a 3 segundos
		client.setReadTimeout(5000); //Establece tiempo de lectura 5 segundos;
		
		clientSDKCdmxWithAuth = Client.create(clientConfig);
		clientSDKCdmxWithAuth.setConnectTimeout(3000); // Establecer a 3 segundos
		clientSDKCdmxWithAuth.setReadTimeout(5000); //Establece tiempo de lectura 5 segundos;
		clientSDKCdmxWithAuth.addFilter(new HTTPBasicAuthFilter(Environment.getUserHttpBasicAuth(), Environment.getPasswordHttpBasicAuth()));
		
		clientINEWithAuth = Client.create(clientConfig);
		clientINEWithAuth.setConnectTimeout(3000); // Establecer a 3 segundos
		clientINEWithAuth.setReadTimeout(5000); //Establece tiempo de lectura 5 segundos;
		clientINEWithAuth.addFilter(new HTTPBasicAuthFilter(Environment.getUserHttpBasicAuthINE(), Environment.getPasswordHttpBasicAuthINE()));
		
		clientCURPWithAuth = Client.create(clientConfig);
		clientCURPWithAuth.setConnectTimeout(2000); // Establecer a 2 segundos
		clientCURPWithAuth.setReadTimeout(5000); //Establece tiempo de lectura 2 segundos;
		clientCURPWithAuth.addFilter(new HTTPBasicAuthFilter(Environment.getUserCurpBasicAuth(), Environment.getPassworCurpBasicAuth()));		
	}
		
	public static JerseyUtil getInstance() throws NoSuchAlgorithmException {
		if(instance == null) {
			instance = new JerseyUtil();
		}
		return instance;
	}
	
	public Client getClient() {
		return client;
	}

	public Client getClientSDKCdmxWithAuth() {
		return clientSDKCdmxWithAuth;
	}
	
	public Client getClientINEWithAuth() {
		return clientINEWithAuth;
	}
	
	public Client getClientCURPWithAuth() {
		return clientCURPWithAuth;
	}


	/** 
	 * Este método lo que causa es que el cliente permita conectarse con servicios que están publicados en HTTPS 
	 * y que la JVM no tiene registrado en el cacerts el certificado de ese dominio donde se encuentra el servicio.
	 *  
	 * Por ejemplo, si no se invoca este método y en la cacerts no se agrega el certificado, marcará el error: 
	 * sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	 */
	private void confiarEnTodosLosCertificados() {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
		    public X509Certificate[] getAcceptedIssuers(){return null;}
		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("TLS");
		    sc.init(null, trustAllCerts, new SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al hacer que se confie en todos los certificados en la JVM:", e);
		}
	}
	
}
