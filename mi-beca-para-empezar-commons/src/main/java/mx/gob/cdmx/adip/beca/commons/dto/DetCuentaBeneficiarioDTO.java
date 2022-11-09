package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class DetCuentaBeneficiarioDTO implements Serializable {

	
	private static final long serialVersionUID = -5351851761524621795L;
	
	private Long idCuentaBeneficiario;
	private BeneficiarioDTO beneficiarioDTO;
	private String numeroCuenta;
	
	
	
	public DetCuentaBeneficiarioDTO() {
	}
	public DetCuentaBeneficiarioDTO(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public DetCuentaBeneficiarioDTO(Long idCuentaBeneficiario, String numeroCuenta ) {
		this.numeroCuenta = numeroCuenta;
		this.idCuentaBeneficiario = idCuentaBeneficiario;
	}
	
	
	
	/**
	 * @param beneficiarioDTO
	 * @param numeroCuenta
	 */
	public DetCuentaBeneficiarioDTO(BeneficiarioDTO beneficiarioDTO, String numeroCuenta) {
		this.beneficiarioDTO = beneficiarioDTO;
		this.numeroCuenta = numeroCuenta;
	}
	public Long getIdCuentaBeneficiario() {
		return idCuentaBeneficiario;
	}
	public void setIdCuentaBeneficiario(Long idCuentaBeneficiario) {
		this.idCuentaBeneficiario = idCuentaBeneficiario;
	}
	public BeneficiarioDTO getBeneficiarioDTO() {
		return beneficiarioDTO;
	}
	public void setBeneficiarioDTO(BeneficiarioDTO beneficiarioDTO) {
		this.beneficiarioDTO = beneficiarioDTO;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	
	
}
