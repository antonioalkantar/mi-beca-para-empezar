package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CurpDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1856254074629063580L;
	private String curp;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String sexo;
	private String fechaNacimiento;
	private String estadoNacimiento;
	private String statusCurp;
	private String error;
	private String nacionalidad;
	private boolean curpValido;

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

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEstadoNacimiento() {
		return estadoNacimiento;
	}

	public void setEstadoNacimiento(String estadoNacimiento) {
		this.estadoNacimiento = estadoNacimiento;
	}

	public String getStatusCurp() {
		return statusCurp;
	}

	public void setStatusCurp(String statusCurp) {
		this.statusCurp = statusCurp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isCurpValido() {
		return curpValido;
	}

	public void setCurpValido(boolean curpValido) {
		this.curpValido = curpValido;
	}	

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	@Override
	public String toString() {
		return "CurpDTO [curp= " + curp + ", nombre= " + nombre + ", primerApellido= " + primerApellido
				+ ", segundoApellido= " + segundoApellido + ", sexo= " + sexo + ", fechaNacimiento= " + fechaNacimiento
				+ ", estadoNacimiento= " + estadoNacimiento + ", curpValido= " + curpValido + "]";
	}

}
