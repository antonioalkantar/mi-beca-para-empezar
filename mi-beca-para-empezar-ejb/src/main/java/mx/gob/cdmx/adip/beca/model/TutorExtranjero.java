package mx.gob.cdmx.adip.beca.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "tutor_extranjero", schema = "mibecaparaempezar")
@NamedQueries({
		@NamedQuery(name = "TutorExtranjero.findByCURP", query = "SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.TutorExtranjeroDTO("
				+ "	t.idTutor," 
				+ "	t.curp," 
				+ "	t.nombre," 
				+ "	t.primerApellido," 
				+ "	t.segundoApellido,"
				+ "	t.parentesco," 
				+ "	t.calle," 
				+ "	t.numInt," 
				+ "	t.numExt," 
				+ "	t.cp," 
				+ "	t.correo,"
				+ "	t.telefono" 
				+ ")" 
				+ "	FROM TutorExtranjero t" 
				+ "	WHERE t.curp = :curp") 
		,
		@NamedQuery(name = "TutorExtranjero.consultaHomonimias", query = "SELECT te.curp FROM TutorExtranjero te"
				+ "	WHERE te.curp like :curp")
})
public class TutorExtranjero implements Serializable {

	private static final long serialVersionUID = 5800227524320392693L;

	private Long idTutor;
	private String curp;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String parentesco;
	private String calle;
	private String numInt;
	private String numExt;
	private String cp;
	private String correo;
	private String telefono;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tutor", unique = true, nullable = false)
	public Long getIdTutor() {
		return idTutor;
	}

	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "primer_apellido")
	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	@Column(name = "segundo_apellido")
	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name = "num_int")
	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	@Column(name = "num_ext")
	public String getNumExt() {
		return numExt;
	}

	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}

	@Column(name = "cp")
	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}
	@Column(name = "correo")
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	@Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
