package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioNoCirculaClienteDTO implements Serializable{
	private static final long serialVersionUID = -7153594007496037287L;
	
	private String color;
	private String HoyNC;
	private String color2;
	private String HoyNC2;
	private String HNCS_H1;
	private String HNCS_H2;
	
	public ServicioNoCirculaClienteDTO() {
		
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getHoyNC() {
		return HoyNC;
	}
	public void setHoyNC(String hoyNC) {
		HoyNC = hoyNC;
	}
	public String getColor2() {
		return color2;
	}
	public void setColor2(String color2) {
		this.color2 = color2;
	}
	public String getHoyNC2() {
		return HoyNC2;
	}
	public void setHoyNC2(String hoyNC2) {
		HoyNC2 = hoyNC2;
	}
	public String getHNCS_H1() {
		return HNCS_H1;
	}
	public void setHNCS_H1(String hNCS_H1) {
		HNCS_H1 = hNCS_H1;
	}
	public String getHNCS_H2() {
		return HNCS_H2;
	}
	public void setHNCS_H2(String hNCS_H2) {
		HNCS_H2 = hNCS_H2;
	}
	
}