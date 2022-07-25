package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioGralDTO implements Serializable{
	private static final long serialVersionUID = -1800215978093168879L;
	
	private String servicioInterrumpido;
	private String corteVial;
	private String obrasPublicas;
	
	public ServicioGralDTO() {
		
	}
	
	public String getServicioInterrumpido() {
		return servicioInterrumpido;
	}
	public void setServicioInterrumpido(String servicioInterrumpido) {
		this.servicioInterrumpido = servicioInterrumpido;
	}
	public String getCorteVial() {
		return corteVial;
	}
	public void setCorteVial(String corteVial) {
		this.corteVial = corteVial;
	}
	public String getObrasPublicas() {
		return obrasPublicas;
	}
	public void setObrasPublicas(String obrasPublicas) {
		this.obrasPublicas = obrasPublicas;
	}
	
}