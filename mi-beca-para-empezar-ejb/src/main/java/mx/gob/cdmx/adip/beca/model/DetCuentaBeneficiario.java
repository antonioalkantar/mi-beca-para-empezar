package mx.gob.cdmx.adip.beca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "det_cuenta_beneficiario", schema = "mibecaparaempezar")
public class DetCuentaBeneficiario implements java.io.Serializable {

	private static final long serialVersionUID = 7630417298330061784L;
	
	private Long idCuentaBeneficiario;
	private Beneficiario beneficiario;
	private String numeroCuenta;

	public DetCuentaBeneficiario() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cuenta_beneficiario", unique = true, nullable = false)
	public Long getIdCuentaBeneficiario() {
		return this.idCuentaBeneficiario;
	}

	public void setIdCuentaBeneficiario(Long idCuentaBeneficiario) {
		this.idCuentaBeneficiario = idCuentaBeneficiario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_beneficiario")
	public Beneficiario getBeneficiario() {
		return this.beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	@Column(name = "numero_cuenta", length = 25)
	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

}
