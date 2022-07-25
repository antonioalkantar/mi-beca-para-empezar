package mx.gob.cdmx.adip.beca.oauth.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RolesUsuarioDTO implements Serializable{

	private static final long serialVersionUID = -4952453659090103317L;
	private int idRol;
	private String rol;
	
	public RolesUsuarioDTO() {
	}

	public RolesUsuarioDTO(int idRol, String rol) {
		this.idRol = idRol;
		this.rol = rol;
	}	

	/**
	 * @return the idRol
	 */
	public int getIdRol() {
		return idRol;
	}

	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
}