package mx.gob.cdmx.adip.beca.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BeneficiarioDispersion generated by Antonio Alcantar Valencia
 */
@Entity
@Table(name = "beneficiario_sin_dispersion", schema = "mibecaparaempezar")
public class BeneficiarioSinDispersion implements java.io.Serializable {
	
	private static final long serialVersionUID = 1938283627957991307L;
	private Long idBeneficiarioSinDispersion;
	private Dispersion dispersion;
	private Beneficiario beneficiario;
	private CatCicloEscolar catCicloEscolar;
	private CatPeriodoEscolar catPeriodoEscolar;
	private CatNivelEducativo catNivelEducativo;
	private CatMotivoNoDispersion catMotivoNoDispersion;
	private CatMontoApoyo montoApoyo;
	private Date fechaCreacion;
	private Long idBeneficiarioDispersion;

	public BeneficiarioSinDispersion() {
	}

	public BeneficiarioSinDispersion(Long idBeneficiarioSinDispersion, Dispersion dispersion, Beneficiario beneficiario,
			CatCicloEscolar catCicloEscolar, CatPeriodoEscolar catPeriodoEscolar, CatNivelEducativo catNivelEducativo,
			CatMotivoNoDispersion catMotivoNoDispersion, CatMontoApoyo montoApoyo, Date fechaCreacion,
			Long idBeneficiarioDispersion) {
		this.idBeneficiarioSinDispersion = idBeneficiarioSinDispersion;
		this.dispersion = dispersion;
		this.beneficiario = beneficiario;
		this.catCicloEscolar = catCicloEscolar;
		this.catPeriodoEscolar = catPeriodoEscolar;
		this.catNivelEducativo = catNivelEducativo;
		this.catMotivoNoDispersion = catMotivoNoDispersion;
		this.montoApoyo = montoApoyo;
		this.fechaCreacion = fechaCreacion;
		this.idBeneficiarioDispersion = idBeneficiarioDispersion;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_beneficiario_sin_dispersion", unique = true, nullable = false)
	public Long getIdBeneficiarioSinDispersion() {
		return this.idBeneficiarioSinDispersion;
	}

	public void setIdBeneficiarioSinDispersion(Long idBeneficiarioSinDispersion) {
		this.idBeneficiarioSinDispersion = idBeneficiarioSinDispersion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dispersion", nullable = false)
	public Dispersion getDispersion() {
		return this.dispersion;
	}

	public void setDispersion(Dispersion dispersion) {
		this.dispersion = dispersion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curp_beneficiario", nullable = false)
	public Beneficiario getBeneficiario() {
		return this.beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ciclo_escolar", nullable = false)
	public CatCicloEscolar getCatCicloEscolar() {
		return this.catCicloEscolar;
	}

	public void setCatCicloEscolar(CatCicloEscolar catCicloEscolar) {
		this.catCicloEscolar = catCicloEscolar;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodo_escolar", nullable = false)
	public CatPeriodoEscolar getCatPeriodoEscolar() {
		return this.catPeriodoEscolar;
	}

	public void setCatPeriodoEscolar(CatPeriodoEscolar catPeriodoEscolar) {
		this.catPeriodoEscolar = catPeriodoEscolar;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nivel_educativo", nullable = false)
	public CatNivelEducativo getCatNivelEducativo() {
		return this.catNivelEducativo;
	}

	public void setCatNivelEducativo(CatNivelEducativo catNivelEducativo) {
		this.catNivelEducativo = catNivelEducativo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motivo_no_dispersion", nullable = false)
	public CatMotivoNoDispersion getCatMotivoNoDispersion() {
		return this.catMotivoNoDispersion;
	}

	public void setCatMotivoNoDispersion(CatMotivoNoDispersion catMotivoNoDispersion) {
		this.catMotivoNoDispersion = catMotivoNoDispersion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_monto_apoyo", nullable = false)
	public CatMontoApoyo getMontoApoyo() {
		return this.montoApoyo;
	}

	public void setMontoApoyo(CatMontoApoyo montoApoyo) {
		this.montoApoyo = montoApoyo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	@Column(name = "id_beneficiario_dispersion", nullable = true)
	public Long getIdBeneficiarioDispersion() {
		return this.idBeneficiarioDispersion;
	}

	public void setIdBeneficiarioDispersion(Long idBeneficiarioDispersion) {
		this.idBeneficiarioDispersion = idBeneficiarioDispersion;
	}

}
