package mx.gob.cdmx.adip.beca.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "beneficiario", schema = "mibecaparaempezar")
@NamedQueries({
@NamedQuery(name = "Beneficiario.findByCurp"
	,query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.BeneficiarioDTO"
			+ "( "
			+ "b.idBeneficiario"
			+ ") "
			+ "FROM Beneficiario b "
			+ "WHERE b.curpBeneficiario = :curpBeneficiario"),
@NamedQuery(name = "Beneficiario.countBeneficiarios",
			query = "SELECT COUNT(b) from Beneficiario b " + 
					"JOIN b.detCuentaBeneficiario dcb " + 
					"JOIN b.crcBeneficiarioSolicitud cbs " + 
					"JOIN cbs.solicitud s " + 
					"JOIN s.tutor t " + 
					"WHERE s.catEstatusBeneficiario.idEstatusBeneficiario = 1 ")
})
public class Beneficiario implements java.io.Serializable {

	private static final long serialVersionUID = -6723705781084220196L;
	private Long idBeneficiario;
	private String curpBeneficiario;
	private String nombresBeneficiario;
	private String primerApellidoBeneficiario;
	private String segundoApellidoBeneficiario;
	private Date fechaNacimientoBeneficiario;
	private Boolean esTutor;
	private String nacionalidad;
	private Long idUsuarioLlaveCdmx;
	private Date fechaRegistro;
	private DetCuentaBeneficiario detCuentaBeneficiario;
	private CrcBeneficiarioSolicitud crcBeneficiarioSolicitud;

	public Beneficiario() {
	}

	public Beneficiario(Long idBeneficiario, String curpBeneficiario, String nombresBeneficiario,
			String primerApellidoBeneficiario, Date fechaNacimientoBeneficiario, String nacionalidad, int edad) {
		this.idBeneficiario = idBeneficiario;
		this.curpBeneficiario = curpBeneficiario;
		this.nombresBeneficiario = nombresBeneficiario;
		this.primerApellidoBeneficiario = primerApellidoBeneficiario;
		this.fechaNacimientoBeneficiario = fechaNacimientoBeneficiario;
		this.nacionalidad = nacionalidad;
	}

	public Beneficiario(Long idBeneficiario, String curpBeneficiario, String nombresBeneficiario,
			String primerApellidoBeneficiario, String segundoApellidoBeneficiario, Date fechaNacimientoBeneficiario,
			Boolean esTutor, String nacionalidad, int edad) {
		this.idBeneficiario = idBeneficiario;
		this.curpBeneficiario = curpBeneficiario;
		this.nombresBeneficiario = nombresBeneficiario;
		this.primerApellidoBeneficiario = primerApellidoBeneficiario;
		this.segundoApellidoBeneficiario = segundoApellidoBeneficiario;
		this.fechaNacimientoBeneficiario = fechaNacimientoBeneficiario;
		this.esTutor = esTutor;
		this.nacionalidad = nacionalidad;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_beneficiario", unique = true, nullable = false)
	public Long getIdBeneficiario() {
		return this.idBeneficiario;
	}

	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	@Column(name = "curp_beneficiario", nullable = false, length = 18)
	public String getCurpBeneficiario() {
		return this.curpBeneficiario;
	}

	public void setCurpBeneficiario(String curpBeneficiario) {
		this.curpBeneficiario = curpBeneficiario;
	}

	@Column(name = "nombres_beneficiario", nullable = false, length = 100)
	public String getNombresBeneficiario() {
		return this.nombresBeneficiario;
	}

	public void setNombresBeneficiario(String nombresBeneficiario) {
		this.nombresBeneficiario = nombresBeneficiario;
	}

	@Column(name = "primer_apellido_beneficiario", nullable = false, length = 100)
	public String getPrimerApellidoBeneficiario() {
		return this.primerApellidoBeneficiario;
	}

	public void setPrimerApellidoBeneficiario(String primerApellidoBeneficiario) {
		this.primerApellidoBeneficiario = primerApellidoBeneficiario;
	}

	@Column(name = "segundo_apellido_beneficiario", length = 100)
	public String getSegundoApellidoBeneficiario() {
		return this.segundoApellidoBeneficiario;
	}

	public void setSegundoApellidoBeneficiario(String segundoApellidoBeneficiario) {
		this.segundoApellidoBeneficiario = segundoApellidoBeneficiario;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento_beneficiario", nullable = false)
	public Date getFechaNacimientoBeneficiario() {
		return this.fechaNacimientoBeneficiario;
	}

	public void setFechaNacimientoBeneficiario(Date fechaNacimientoBeneficiario) {
		this.fechaNacimientoBeneficiario = fechaNacimientoBeneficiario;
	}

	@Column(name = "es_tutor")
	public Boolean getEsTutor() {
		return this.esTutor;
	}

	public void setEsTutor(Boolean esTutor) {
		this.esTutor = esTutor;
	}

	@Column(name = "nacionalidad", nullable = false, length = 50)
	public String getNacionalidad() {
		return this.nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	@Column(name = "id_usuario_llave_cdmx", nullable = false)
	public Long getIdUsuarioLlaveCdmx() {
		return idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(Long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable = false)
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "beneficiario")
	public DetCuentaBeneficiario getDetCuentaBeneficiario() {
		return detCuentaBeneficiario;
	}

	public void setDetCuentaBeneficiario(DetCuentaBeneficiario detCuentaBeneficiario) {
		this.detCuentaBeneficiario = detCuentaBeneficiario;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "beneficiario")
	public CrcBeneficiarioSolicitud getCrcBeneficiarioSolicitud() {
		return crcBeneficiarioSolicitud;
	}

	public void setCrcBeneficiarioSolicitud(CrcBeneficiarioSolicitud crcBeneficiarioSolicitud) {
		this.crcBeneficiarioSolicitud = crcBeneficiarioSolicitud;
	}
	
	

}
