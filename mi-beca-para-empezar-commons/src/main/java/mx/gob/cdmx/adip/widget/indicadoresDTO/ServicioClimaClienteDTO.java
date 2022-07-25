package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioClimaClienteDTO  implements Serializable{
	private static final long serialVersionUID = -5117239045831917415L;
	
	private String temperaturaProm;
	private String temperaturaMax;
	private String temperaturaMin;
	private String mensajeHoy;
	
	private String temperaturaFarenheit;
	
	public ServicioClimaClienteDTO() {
		
	}
	
	public String getTemperaturaProm() {
		return temperaturaProm;
	}
	public void setTemperaturaProm(String temperaturaProm) {
		this.temperaturaProm = temperaturaProm;
	}
	public String getTemperaturaMax() {
		return temperaturaMax;
	}
	public void setTemperaturaMax(String temperaturaMax) {
		this.temperaturaMax = temperaturaMax;
	}
	public String getTemperaturaMin() {
		return temperaturaMin;
	}
	public void setTemperaturaMin(String temperaturaMin) {
		this.temperaturaMin = temperaturaMin;
	}
	public String getMensajeHoy() {
		return mensajeHoy;
	}
	public void setMensajeHoy(String mensajeHoy) {
		this.mensajeHoy = mensajeHoy;
	}
	public String getTemperaturaFarenheit() {
		return temperaturaFarenheit;
	}
	public void setTemperaturaFarenheit(String temperaturaFarenheit) {
		this.temperaturaFarenheit = temperaturaFarenheit;
	}

}