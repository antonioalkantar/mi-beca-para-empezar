package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public class HoyNoCirculaDTO implements Serializable {

	private static final long serialVersionUID = -1370320582210797261L;

	private String codigo;
	private Boolean hoyHayRestriccion;
	private String hoyEtiquetaColorNoCircula;
	private String hoyHexadecimalColorNoCircula;
	
	private String hoyTextoCorto;
	private String hoyTextoLargo;
	
	private String proximoSabadoTextoCorto;
	private String proximoSabadoTextoLargo;
	private String proximoSabadoColor;
	private String proximoSabadoHexadecimalColor;

	private HashMap<String, Integer>[] hoyIdHologramas;
	private HashMap<String, Integer>[] hoyUltimosDigitosH1;
	private HashMap<String, Integer>[] hoyUltimosDigitosH2;
	private HashMap<String, Integer>[] proximoSabadoUltimosDigitosH1;
	private HashMap<String, Integer>[] proximoSabadoUltimosDigitosH2;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getHoyHayRestriccion() {
		return hoyHayRestriccion;
	}

	public void setHoyHayRestriccion(Boolean hoyHayRestriccion) {
		this.hoyHayRestriccion = hoyHayRestriccion;
	}

	public String getHoyEtiquetaColorNoCircula() {
		return hoyEtiquetaColorNoCircula;
	}

	public void setHoyEtiquetaColorNoCircula(String hoyEtiquetaColorNoCircula) {
		this.hoyEtiquetaColorNoCircula = hoyEtiquetaColorNoCircula;
	}

	public String getHoyHexadecimalColorNoCircula() {
		return hoyHexadecimalColorNoCircula;
	}

	public void setHoyHexadecimalColorNoCircula(String hoyHexadecimalColorNoCircula) {
		this.hoyHexadecimalColorNoCircula = hoyHexadecimalColorNoCircula;
	}

	public HashMap<String, Integer>[] getHoyIdHologramas() {
		return hoyIdHologramas;
	}

	public void setHoyIdHologramas(HashMap<String, Integer>[] hoyIdHologramas) {
		this.hoyIdHologramas = hoyIdHologramas;
	}

	public HashMap<String, Integer>[] getHoyUltimosDigitosH1() {
		return hoyUltimosDigitosH1;
	}

	public void setHoyUltimosDigitosH1(HashMap<String, Integer>[] hoyUltimosDigitosH1) {
		this.hoyUltimosDigitosH1 = hoyUltimosDigitosH1;
	}

	public HashMap<String, Integer>[] getHoyUltimosDigitosH2() {
		return hoyUltimosDigitosH2;
	}

	public void setHoyUltimosDigitosH2(HashMap<String, Integer>[] hoyUltimosDigitosH2) {
		this.hoyUltimosDigitosH2 = hoyUltimosDigitosH2;
	}

	public HashMap<String, Integer>[] getProximoSabadoUltimosDigitosH1() {
		return proximoSabadoUltimosDigitosH1;
	}

	public void setProximoSabadoUltimosDigitosH1(HashMap<String, Integer>[] proximoSabadoUltimosDigitosH1) {
		this.proximoSabadoUltimosDigitosH1 = proximoSabadoUltimosDigitosH1;
	}

	public HashMap<String, Integer>[] getProximoSabadoUltimosDigitosH2() {
		return proximoSabadoUltimosDigitosH2;
	}

	public void setProximoSabadoUltimosDigitosH2(HashMap<String, Integer>[] proximoSabadoUltimosDigitosH2) {
		this.proximoSabadoUltimosDigitosH2 = proximoSabadoUltimosDigitosH2;
	}

	public int sizeProximoSabadoUltimosDigitosH1() {
		return proximoSabadoUltimosDigitosH1 == null ? 0 : proximoSabadoUltimosDigitosH1.length;
	}
	
	public int sizeProximoSabadoUltimosDigitosH2() {
		return proximoSabadoUltimosDigitosH2 == null ? 0 :  proximoSabadoUltimosDigitosH2.length;
	}
	
	public boolean esParH1() {
		if (proximoSabadoUltimosDigitosH1 != null && proximoSabadoUltimosDigitosH1.length != 0 && proximoSabadoUltimosDigitosH1[0].get("ultimoDigito") == 0) {
			return true;
		}
		return false;
	}
	
	public boolean esQuintoSabado() {
		if (sizeProximoSabadoUltimosDigitosH1() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HoyNoCirculaDTO [codigo=");
		builder.append(codigo);
		builder.append(", hoyHayRestriccion=");
		builder.append(hoyHayRestriccion);
		builder.append(", hoyEtiquetaColorNoCircula=");
		builder.append(hoyEtiquetaColorNoCircula);
		builder.append(", hoyHexadecimalColorNoCircula=");
		builder.append(hoyHexadecimalColorNoCircula);
		builder.append(", hoyIdHologramas=");
		builder.append(Arrays.toString(hoyIdHologramas));
		builder.append(", hoyUltimosDigitosH1=");
		builder.append(Arrays.toString(hoyUltimosDigitosH1));
		builder.append(", hoyUltimosDigitosH2=");
		builder.append(Arrays.toString(hoyUltimosDigitosH2));
		builder.append(", proximoSabadoUltimosDigitosH1=");
		builder.append(Arrays.toString(proximoSabadoUltimosDigitosH1));
		builder.append(", proximoSabadoUltimosDigitosH2=");
		builder.append(Arrays.toString(proximoSabadoUltimosDigitosH2));
		builder.append("]");
		return builder.toString();
	}

	public String getHoyTextoCorto() {
		return hoyTextoCorto;
	}

	public void setHoyTextoCorto(String hoyTextoCorto) {
		this.hoyTextoCorto = hoyTextoCorto;
	}

	public String getHoyTextoLargo() {
		return hoyTextoLargo;
	}

	public void setHoyTextoLargo(String hoyTextoLargo) {
		this.hoyTextoLargo = hoyTextoLargo;
	}

	public String getProximoSabadoTextoCorto() {
		return proximoSabadoTextoCorto;
	}

	public void setProximoSabadoTextoCorto(String proximoSabadoTextoCorto) {
		this.proximoSabadoTextoCorto = proximoSabadoTextoCorto;
	}

	public String getProximoSabadoTextoLargo() {
		return proximoSabadoTextoLargo;
	}

	public void setProximoSabadoTextoLargo(String proximoSabadoTextoLargo) {
		this.proximoSabadoTextoLargo = proximoSabadoTextoLargo;
	}

	public String getProximoSabadoColor() {
		return proximoSabadoColor;
	}

	public void setProximoSabadoColor(String proximoSabadoColor) {
		this.proximoSabadoColor = proximoSabadoColor;
	}

	public String getProximoSabadoHexadecimalColor() {
		return proximoSabadoHexadecimalColor;
	}

	public void setProximoSabadoHexadecimalColor(String proximoSabadoHexadecimalColor) {
		this.proximoSabadoHexadecimalColor = proximoSabadoHexadecimalColor;
	}

}
