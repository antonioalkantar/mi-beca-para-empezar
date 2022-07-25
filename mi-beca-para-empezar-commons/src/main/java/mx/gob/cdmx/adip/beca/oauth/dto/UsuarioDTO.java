package mx.gob.cdmx.adip.beca.oauth.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1730187096769589039L;

	private long idUsuarioLlaveCdmx;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String curp;
	private String telefono;
	private String correo;
	private Boolean esExtranjero;
	private String sexo;
	private String fechaNacimiento;
	private String lada;
	
	private List<RolesUsuarioDTO> lstRoles;
	
	/**
	 * 
	 */
	public UsuarioDTO() {
		setLstRoles(new ArrayList<RolesUsuarioDTO>());
	}
	
	/**
	 * @param idUsuarioLlaveCdmx
	 */
	public UsuarioDTO(long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	/**
	 * @param idUsuarioLlaveCdmx
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param curp
	 * @param telefono
	 * @param correo
	 */
	public UsuarioDTO(long idUsuarioLlaveCdmx, String nombre, String primerApellido, String segundoApellido,
			String curp, String telefono, String correo) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.curp = curp;
		this.telefono = telefono;
		this.correo = correo;
	}

	/**
	 * @return the idUsuarioLlaveCdmx
	 */
	public long getIdUsuarioLlaveCdmx() {
		return idUsuarioLlaveCdmx;
	}

	/**
	 * @param idUsuarioLlaveCdmx the idUsuarioLlaveCdmx to set
	 */
	public void setIdUsuarioLlaveCdmx(long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * @return the lstRoles
	 */
	public List<RolesUsuarioDTO> getLstRoles() {
		return lstRoles;
	}

	/**
	 * @param lstRoles the lstRoles to set
	 */
	public void setLstRoles(List<RolesUsuarioDTO> lstRoles) {
		this.lstRoles = lstRoles;
	}

	public Boolean getEsExtranjero() {
		return esExtranjero;
	}

	public void setEsExtranjero(Boolean esExtranjero) {
		this.esExtranjero = esExtranjero;
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

	public String getLada() {
		return lada;
	}

	public void setLada(String lada) {
		this.lada = lada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((curp == null) ? 0 : curp.hashCode());
		result = prime * result + (int) (idUsuarioLlaveCdmx ^ (idUsuarioLlaveCdmx >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((primerApellido == null) ? 0 : primerApellido.hashCode());
		result = prime * result + ((segundoApellido == null) ? 0 : segundoApellido.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (curp == null) {
			if (other.curp != null)
				return false;
		} else if (!curp.equals(other.curp))
			return false;
		if (idUsuarioLlaveCdmx != other.idUsuarioLlaveCdmx)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (primerApellido == null) {
			if (other.primerApellido != null)
				return false;
		} else if (!primerApellido.equals(other.primerApellido))
			return false;
		if (segundoApellido == null) {
			if (other.segundoApellido != null)
				return false;
		} else if (!segundoApellido.equals(other.segundoApellido))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}
	
}
