package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioCalAireClienteDTO implements Serializable{
	private static final long serialVersionUID = -1327599789406858891L;
	
	private String riesgo;
	private String Nriesgo;
	private String recomendacion_1;
	private String recomendacion_2;
	
	public ServicioCalAireClienteDTO() {}
	
	public String getRiesgo() {
		return riesgo;
	}
	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}
	public String getNriesgo() {
		return Nriesgo;
	}
	public void setNriesgo(String nriesgo) {
		Nriesgo = nriesgo;
	}
	public String getRecomendacion_1() {
		return recomendacion_1;
	}
	public void setRecomendacion_1(String recomendacion_1) {
		this.recomendacion_1 = recomendacion_1;
	}
	public String getRecomendacion_2() {
		return recomendacion_2;
	}
	public void setRecomendacion_2(String recomendacion_2) {
		this.recomendacion_2 = recomendacion_2;
	}

}