package mx.gob.cdmx.adip.beca.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "solicitud", schema = "mibecaparaempezar")
@NamedQueries({
	@NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s")
	,
	@NamedQuery(name = "Solicitud.findById", query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO "
			+"( "
			+ "s.idSolicitud, "
			+ "s.folioSolicitud, "
			+ "t.idUsuarioLlaveCdmx, "
			+ "t.nombre, "
			+ "t.primerApellido, "
			+ "t.segundoApellido, "
			+ "t.curp, "
			+ "b.idBeneficiario, "
			+ "b.nombresBeneficiario, "
			+ "b.primerApellidoBeneficiario, "
			+ "b.segundoApellidoBeneficiario, "
			+ "b.nacionalidad, "
			+ "b.curpBeneficiario, "
			+ "te.idEstatus, "
			+ "te.descripcion, "
			+ "ceb.idEstatusBeneficiario, "
			+ "ceb.descripcion, "
			+ "ccr.idCodigoPagatodo "
			+") "
			+"FROM CrcBeneficiarioSolicitud crc "
			+"JOIN crc.beneficiario b "
			+"JOIN crc.solicitud s "  
			+"JOIN s.tutor t "
			+"JOIN t.catEstatus te "
			+"JOIN s.catEstatusBeneficiario ceb "
			+"JOIN s.catMunicipios cm "
			+"JOIN s.catCodigosRespuestaPagatodo ccr "
			+"where s.idSolicitud = :idSolicitud ")
	,
	@NamedQuery(name = "Solicitud.findByIdTutor", query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO "
			+"( "			
			+ "ccr.idCodigoPagatodo, "
			+ "t.idUsuarioLlaveCdmx "
			+") "
			+"FROM CrcBeneficiarioSolicitud crc "
			+"JOIN crc.beneficiario b "
			+"JOIN crc.solicitud s "  
			+"JOIN s.tutor t "
			+"JOIN t.catEstatus te "
			+"JOIN s.catEstatusBeneficiario ceb "
			+"JOIN s.catMunicipios cm "
			+"JOIN s.catCodigosRespuestaPagatodo ccr "
			+"where t.idUsuarioLlaveCdmx = :idTutor "
			+ "and ceb.idEstatusBeneficiario =:idEstatusBeneficiario")
//	,
//	@NamedQuery(name = "Solicitud.actualizaObtenMasRegistrarTutor",	query = "UPDATE Solicitud s "
//			+ "SET s.pagatodoEnvioExitoso =true "			
//			+ "WHERE s.tutor.idUsuarioLlaveCdmx =:idTutor ")
})
public class Solicitud implements java.io.Serializable {
	
	private static final long serialVersionUID = 1938283627957991307L;
	private Long idSolicitud;
	private CatEstatusBeneficiario catEstatusBeneficiario;
	private CatParentesco catParentesco;
	private CatMunicipios catMunicipios;
	private CatCodigosRespuestaPagatodo catCodigosRespuestaPagatodo;
	private Tutor tutor;
	private String folioSolicitud;
	private Date fechaSolicitud;
	private String respuestaAutoridadEducativa;
	private String cct;
	private String nombre;
	private String calle;
	private String colonia;
	private String codigopostal;
	private String turno;
	private CatNivelEducativo catNivelEducativo;
	private String gradoEscolar;
	private boolean pagatodoEnvioExitoso;
	private boolean esNuevoRegistro;
	private boolean externo;
	private Date pagatodoFechaEnvio;
	private Set<Encuesta> encuestas = new HashSet<Encuesta>(0);

	public Solicitud() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_solicitud", unique = true, nullable = false)
	public Long getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_beneficiario", nullable = false)
	public CatEstatusBeneficiario getCatEstatusBeneficiario() {
		return this.catEstatusBeneficiario;
	}

	public void setCatEstatusBeneficiario(CatEstatusBeneficiario catEstatusBeneficiario) {
		this.catEstatusBeneficiario = catEstatusBeneficiario;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pagatodo_codigo_respuesta")
	public CatCodigosRespuestaPagatodo getCatCodigosRespuestaPagatodo() {
		return this.catCodigosRespuestaPagatodo;
	}

	public void setCatCodigosRespuestaPagatodo(CatCodigosRespuestaPagatodo catCodigosRespuestaPagatodo) {
		this.catCodigosRespuestaPagatodo = catCodigosRespuestaPagatodo;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_parentesco", nullable = false)
	public CatParentesco getCatParentesco() {
		return this.catParentesco;
	}

	public void setCatParentesco(CatParentesco catParentesco) {
		this.catParentesco = catParentesco;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_llave_cdmx", nullable = false)
	public Tutor getTutor() {
		return this.tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	@Column(name = "folio_solicitud", nullable = false, length = 15)
	public String getFolioSolicitud() {
		return this.folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_solicitud", nullable = false, length = 29)
	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	@Column(name = "respuesta_autoridad_educativa", nullable = false)
	public String getRespuestaAutoridadEducativa() {
		return this.respuestaAutoridadEducativa;
	}

	public void setRespuestaAutoridadEducativa(String respuestaAutoridadEducativa) {
		this.respuestaAutoridadEducativa = respuestaAutoridadEducativa;
	}

	@Column(name = "cct", length = 15)
	public String getCct() {
		return this.cct;
	}

	public void setCct(String cct) {
		this.cct = cct;
	}

	@Column(name = "nombre", length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "calle", length = 200)
	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name = "colonia", length = 100)
	public String getColonia() {
		return this.colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	@Column(name = "codigopostal", length = 5)
	public String getCodigopostal() {
		return this.codigopostal;
	}

	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}

	@Column(name = "turno", length = 100)
	public String getTurno() {
		return this.turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nivel_educativo")
	public CatNivelEducativo getCatNivelEducativo() {
		return catNivelEducativo;
	}

	public void setCatNivelEducativo(CatNivelEducativo catNivelEducativo) {
		this.catNivelEducativo = catNivelEducativo;
	}

	@Column(name = "grado_escolar", length = 50)
	public String getGradoEscolar() {
		return this.gradoEscolar;
	}	

	public void setGradoEscolar(String gradoEscolar) {
		this.gradoEscolar = gradoEscolar;
	}

	@Column(name = "pagatodo_envio_exitoso", nullable = false)
	public boolean isPagatodoEnvioExitoso() {
		return this.pagatodoEnvioExitoso;
	}

	public void setPagatodoEnvioExitoso(boolean pagatodoEnvioExitoso) {
		this.pagatodoEnvioExitoso = pagatodoEnvioExitoso;
	}
	
	
	@Column(name = "es_nuevo_registro", nullable = false)
	public boolean isEsNuevoRegistro() {
		return this.esNuevoRegistro;
	}

	public void setEsNuevoRegistro(boolean esNuevoRegistro) {
		this.esNuevoRegistro = esNuevoRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitud")
	public Set<Encuesta> getEncuestas() {
		return this.encuestas;
	}

	public void setEncuestas(Set<Encuesta> encuestas) {
		this.encuestas = encuestas;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_alcaldia", nullable = false)
	public CatMunicipios getCatMunicipios() {
		return this.catMunicipios;
	}

	public void setCatMunicipios(CatMunicipios catMunicipios) {
		this.catMunicipios = catMunicipios;
	}
	
	@Column(name = "externo", nullable = false)
	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pagatodo_fecha_envio")
	public Date getPagatodoFechaEnvio() {
		return this.pagatodoFechaEnvio;
	}

	public void setPagatodoFechaEnvio(Date pagatodoFechaEnvio) {
		this.pagatodoFechaEnvio = pagatodoFechaEnvio;
	}	
}
