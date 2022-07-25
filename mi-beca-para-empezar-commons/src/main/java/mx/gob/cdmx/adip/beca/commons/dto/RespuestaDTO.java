package mx.gob.cdmx.adip.beca.commons.dto;

import java.util.List;

public class RespuestaDTO {
	
	private int code;
	private List<String> cuentaBeneficiario;
	private List<String> curpBeneficiario;
	private String mensaje;
	private int status;
	
	public RespuestaDTO() {
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<String> getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(List<String> cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public List<String> getCurpBeneficiario() {
		return curpBeneficiario;
	}

	public void setCurpBeneficiario(List<String> curpBeneficiario) {
		this.curpBeneficiario = curpBeneficiario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
