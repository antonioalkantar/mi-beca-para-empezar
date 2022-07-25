package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class NotificacionDTO implements Serializable {

	private static final long serialVersionUID = 5813682655039362415L;
	
	private int codigo;
	private boolean activo;
	private String mensaje;
	private String backgroundColor;
	private String href;
	
	public NotificacionDTO() {
	}
	
	public NotificacionDTO(int codigo, boolean activo, String mensaje, String backgroundColor, String href) {
		this.activo = activo;
		this.mensaje = mensaje;
		this.backgroundColor = backgroundColor;
		this.href = href;
	}
	
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return "NotificacionDTO [codigo=" + codigo + ", activo=" + activo + ", mensaje=" + mensaje
				+ ", backgroundColor=" + backgroundColor + ", href=" + href + "]";
	}
}