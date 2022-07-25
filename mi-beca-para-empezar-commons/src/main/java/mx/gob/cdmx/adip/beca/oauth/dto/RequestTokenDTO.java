package mx.gob.cdmx.adip.beca.oauth.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestTokenDTO implements Serializable{

	private static final long serialVersionUID = -9042552079931191059L;
	
	private String grantType;
	private String code;
	private String redirectUri;
	private String clientId;
	private String clientSecret;
	
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public boolean parametrosCorrectos() {
		return grantType != null && !grantType.isEmpty() && code != null && !code.isEmpty() 
				&& clientId != null && !clientId.isEmpty() && clientSecret != null && !clientSecret.isEmpty();
	}
	
	@Override
	public String toString() {
		return "RequestTokenDTO [grantType=" + grantType + ", code=" + code + ", redirectUri=" + redirectUri
				+ ", clientId=" + clientId + ", clientSecret=" + clientSecret + "]";
	}
	
}
