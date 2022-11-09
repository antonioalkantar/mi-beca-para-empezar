package mx.gob.cdmx.adip.beca.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "padron_beneficiarios_continuidad", schema = "mibecaparaempezar")
@NamedQueries({
	  @NamedQuery(name = "PadronBeneficiariosContinuidad.findByCurp"
	, query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.PadronBeneficiariosContinuidadDTO ("
			+ "p.curp, p.curpTutor, p.idBeneficiarioContinuidad "
			+ ") "
			+ "FROM PadronBeneficiariosContinuidad p "
			+ "WHERE p.curp = :curp ")
})
public class PadronBeneficiariosContinuidad implements Serializable {

	private static final long serialVersionUID = 6162746696523123174L;
	private Long idBeneficiarioContinuidad;
	private String curp;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String cct;
	private String nivel;
	private String grado;
	private String alcaldia;
	private Double monto;
	private String genero;
	private String cuenta;
	private String folio;
	private String dispersion;
	private String curpTutor;


	public PadronBeneficiariosContinuidad() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_beneficiario_continuidad", unique = true, nullable = false)
	public Long getIdBeneficiarioContinuidad() {
		return this.idBeneficiarioContinuidad;
	}

	public void setIdBeneficiarioContinuidad(Long idBeneficiarioContinuidad) {
		this.idBeneficiarioContinuidad = idBeneficiarioContinuidad;
	}

	@Column(name = "curp", nullable = false, length = 18)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "primer_apellido", length = 100)
	public String getPrimerApellido() {
		return this.primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	@Column(name = "segundo_apellido", length = 100)
	public String getSegundoApellido() {
		return this.segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	@Column(name = "cct", length = 18)
	public String getCct() {
		return this.cct;
	}

	public void setCct(String cct) {
		this.cct = cct;
	}

	@Column(name = "nivel", length = 100)
	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	@Column(name = "grado", length = 50)
	public String getGrado() {
		return this.grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	@Column(name = "alcaldia", length = 50)
	public String getAlcaldia() {
		return this.alcaldia;
	}

	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}

	@Column(name = "monto", precision = 131089, scale = 0)
	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	@Column(name = "genero", length = 10)
	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Column(name = "cuenta", length = 15)
	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Column(name = "folio", length = 15)
	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	@Column(name = "dispersion", length = 20)
	public String getDispersion() {
		return this.dispersion;
	}

	public void setDispersion(String dispersion) {
		this.dispersion = dispersion;
	}
	
	@Column(name = "curp_tutor", length = 18)
	public String getCurpTutor() {
		return this.curpTutor;
	}

	public void setCurpTutor(String curpTutor) {
		this.curpTutor = curpTutor;
	}

}
