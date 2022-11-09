package mx.gob.cdmx.adip.beca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "usuario", schema = "mibecaparaempezar")
@NamedQueries({
		@NamedQuery(name = "Usuario.findByIdUsuarioLlaveCDMX", query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.UserDTO"
				+ "(" 
				+ "	u.idUsuarioLlaveCdmx," 
				+ "	u.nombre," 
				+ "	u.primerApellido," 
				+ "	u.segundoApellido,"
				+ "	u.curp," 
				+ "	u.telefono," 
				+ "	u.correo," 
				+ "	u.fechaNacimiento," 
				+ "	u.sexo," 
				+ "	u.fechaCreacion" 
				+ ")" 
				+ "	FROM Usuario u"
				+ "	WHERE u.idUsuarioLlaveCdmx = :idUsuarioLlave") })
public class Usuario implements Serializable {

	private static final long serialVersionUID = -3409641028173687063L;

	private Long idUsuarioLlaveCdmx;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String curp;
	private String telefono;
	private String correo;
	private String fechaNacimiento;
	private String sexo;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	public Usuario() {
	}

	public Usuario(Long idUsuarioLlaveCdmx) {
		super();
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	@Id
	@Column(name = "id_usuario_llave_cdmx", unique = true, nullable = false, updatable = false)
	public Long getIdUsuarioLlaveCdmx() {
		return idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(Long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	@Column(name = "nombre", length = 60)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "primer_apellido", length = 60)
	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	@Column(name = "segundo_apellido", length = 60)
	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	@Column(name = "curp", length = 18)
	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Column(name = "telefono", length = 15)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "correo", length = 60)
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Column(name = "fecha_nacimiento", length = 10)
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Column(name = "sexo", length = 10)
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
