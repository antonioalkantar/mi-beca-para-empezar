package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioIUVClienteDTO implements Serializable{
	private static final long serialVersionUID = -7021720827309644331L;
	
	private String iUV;
	
	public ServicioIUVClienteDTO() {
		
	}
	public String getiUV() {
		return iUV;
	}
	public void setiUV(String iUV) {
		this.iUV = iUV;
	}

}