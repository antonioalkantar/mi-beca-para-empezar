package mx.gob.cdmx.adip.beca.oauth.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestRolesDTO implements Serializable{

	private static final long serialVersionUID = 6325053019387435797L;
	private long idUsuario;
	private long idSistema;
	
	public RequestRolesDTO() {
	}

	public RequestRolesDTO(long idUsuario, long idSistema) {
		this.idUsuario = idUsuario;
		this.idSistema = idSistema;
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the idSistema
	 */
	public long getIdSistema() {
		return idSistema;
	}

	/**
	 * @param idSistema the idSistema to set
	 */
	public void setIdSistema(long idSistema) {
		this.idSistema = idSistema;
	}
}