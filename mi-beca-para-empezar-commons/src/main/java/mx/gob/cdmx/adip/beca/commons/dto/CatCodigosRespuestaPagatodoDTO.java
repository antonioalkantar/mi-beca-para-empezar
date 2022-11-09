package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatCodigosRespuestaPagatodoDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1607610101748886564L;
	
	private Long idCodigoPagatodo;
	private String descripcion;
	private Boolean estatus;
	
	
	/**
	 * 
	 */
	public CatCodigosRespuestaPagatodoDTO() {
	}


	/**
	 * @param idCodigoPagatodo
	 * @param descripcion
	 * @param estatus
	 */
	public CatCodigosRespuestaPagatodoDTO(Long idCodigoPagatodo, String descripcion, Boolean estatus) {
		this.idCodigoPagatodo = idCodigoPagatodo;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}
	
	/**
	 * @param idCodigoPagatodo
	 */
	public CatCodigosRespuestaPagatodoDTO(Long idCodigoPagatodo) {
		this.idCodigoPagatodo = idCodigoPagatodo;
	}


	public Long getIdCodigoPagatodo() {
		return idCodigoPagatodo;
	}


	public void setIdCodigoPagatodo(Long idCodigoPagatodo) {
		this.idCodigoPagatodo = idCodigoPagatodo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Boolean getEstatus() {
		return estatus;
	}


	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	
	
}
