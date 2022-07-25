package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioSemaforoEpDTO implements Serializable{
	private static final long serialVersionUID = -3331358155163927978L;
	
	private String nombre;
	private String color;
	private Integer codigo;
	
	public ServicioSemaforoEpDTO() {
		
	}
	
	public ServicioSemaforoEpDTO(String nombre, String color, Integer codigo) {	
		this.nombre = nombre;
		this.color = color;
		this.codigo = codigo;
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
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	

}