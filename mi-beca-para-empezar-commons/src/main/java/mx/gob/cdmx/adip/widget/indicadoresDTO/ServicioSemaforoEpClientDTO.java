package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioSemaforoEpClientDTO implements Serializable {
	private static final long serialVersionUID = 8788794616796226544L;
	
	private String nombre;
	private String color;
	
	public ServicioSemaforoEpClientDTO() {
		
	}
	
	public ServicioSemaforoEpClientDTO(String nombre, String color) {
		this.nombre = nombre;
		this.color = color;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}