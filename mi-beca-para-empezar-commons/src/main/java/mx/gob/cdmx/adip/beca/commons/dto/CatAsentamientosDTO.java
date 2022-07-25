package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatAsentamientosDTO implements Serializable{

	private static final long serialVersionUID = -1404037432876166635L;
	private Integer idAsentamiento;
	private CatEstadosDTO catEstadosDTO;
	private CatMunicipiosDTO catMunicipiosDTO;
	private String descripcion;
	
	public CatAsentamientosDTO() {}
	
	//Constructor para namedquery = findPorAlcaldia
	public CatAsentamientosDTO(Integer idAsentamiento , String descripcion) {
		this.idAsentamiento = idAsentamiento;
		this.descripcion = descripcion;
	}
	
	public CatAsentamientosDTO(Integer idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}
	
	//Constructor para namedquery = findALL
	public CatAsentamientosDTO(Integer idAsentamiento , String descripcion, Integer idMunicipio , Integer idEstado) {
		this.idAsentamiento = idAsentamiento;
		this.catEstadosDTO = new CatEstadosDTO(idEstado, null, null);
		this.catMunicipiosDTO = new CatMunicipiosDTO(idMunicipio, null, null, idEstado);
		this.descripcion = descripcion;
	}

	/**
	 * @return the idAsentamiento
	 */
	public Integer getIdAsentamiento() {
		return idAsentamiento;
	}

	/**
	 * @param idAsentamiento the idAsentamiento to set
	 */
	public void setIdAsentamiento(Integer idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}

	/**
	 * @return the catEstadosDTO
	 */
	public CatEstadosDTO getCatEstadosDTO() {
		return catEstadosDTO;
	}

	/**
	 * @param catEstadosDTO the catEstadosDTO to set
	 */
	public void setCatEstadosDTO(CatEstadosDTO catEstadosDTO) {
		this.catEstadosDTO = catEstadosDTO;
	}

	/**
	 * @return the catMunicipiosDTO
	 */
	public CatMunicipiosDTO getCatMunicipiosDTO() {
		return catMunicipiosDTO;
	}

	/**
	 * @param catMunicipiosDTO the catMunicipiosDTO to set
	 */
	public void setCatMunicipiosDTO(CatMunicipiosDTO catMunicipiosDTO) {
		this.catMunicipiosDTO = catMunicipiosDTO;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
