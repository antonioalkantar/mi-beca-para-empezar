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
@Table(name = "padron_externo", schema = "mibecaparaempezar")
@NamedQueries({
	  @NamedQuery(name = "PadronExterno.findByCurp"
	, query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.PadronExternoDTO ( "
			+ "pe.curp,"
			+ "pe.nombres,"
			+ "pe.primerApellido, "
			+ "pe.segundoApellido, "
			+ "pe.cct,pe.nombreCct, "
			+ "pe.calle, "
			+ "pe.numeroExterior, "
			+ "pe.colonia, "
			+ "pe.idMunicipio, "
			+ "pe.municipio, "
			+ "pe.codigoPostal, "
			+ "pe.turno, "
			+ "pe.nivelEducativo, "
			+ "pe.gradoEscolar, "
			+ "pe.estatus, "
			+ "pe.tipoEscuela ) "
			+ "FROM PadronExterno pe "
			+ "WHERE pe.curp = :curp ")	
})
public class PadronExterno implements Serializable {

	private static final long serialVersionUID = 7400324439967906378L;
	private Long idBeneficiarioExt;
	private String curp;
	private String nombres;
	private String primerApellido;
	private String segundoApellido;
	private String cct;
	private String nombreCct;
	private String calle;
	private String numeroExterior;
	private String colonia;
	private int idMunicipio;
	private String municipio;
	private String codigoPostal;
	private String turno;
	private String nivelEducativo;
	private String gradoEscolar;
	private String estatus;
	private String tipoEscuela;

	public PadronExterno() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_beneficiario_ext", unique = true, nullable = false)
	public Long getIdBeneficiarioExt() {
		return this.idBeneficiarioExt;
	}

	public void setIdBeneficiarioExt(Long idBeneficiarioExt) {
		this.idBeneficiarioExt = idBeneficiarioExt;
	}

	@Column(name = "curp", nullable = false, length = 18)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Column(name = "nombres", nullable = false, length = 100)
	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
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

	@Column(name = "nombre_cct", length = 200)
	public String getNombreCct() {
		return this.nombreCct;
	}

	public void setNombreCct(String nombreCct) {
		this.nombreCct = nombreCct;
	}

	@Column(name = "calle", length = 200)
	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name = "numero_exterior", length = 15)
	public String getNumeroExterior() {
		return this.numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	@Column(name = "colonia", length = 100)
	public String getColonia() {
		return this.colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	@Column(name = "id_municipio", nullable = false)
	public int getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	@Column(name = "municipio", length = 50)
	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	@Column(name = "codigo_postal", nullable = false, length = 10)
	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Column(name = "turno", nullable = false, length = 10)
	public String getTurno() {
		return this.turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	@Column(name = "nivel_educativo", length = 100)
	public String getNivelEducativo() {
		return this.nivelEducativo;
	}

	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	@Column(name = "grado_escolar", length = 50)
	public String getGradoEscolar() {
		return this.gradoEscolar;
	}

	public void setGradoEscolar(String gradoEscolar) {
		this.gradoEscolar = gradoEscolar;
	}

	@Column(name = "estatus", length = 15)
	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	@Column(name = "tipo_escuela", length = 15)
	public String getTipoEscuela() {
		return this.tipoEscuela;
	}

	public void setTipoEscuela(String tipoEscuela) {
		this.tipoEscuela = tipoEscuela;
	}

}
