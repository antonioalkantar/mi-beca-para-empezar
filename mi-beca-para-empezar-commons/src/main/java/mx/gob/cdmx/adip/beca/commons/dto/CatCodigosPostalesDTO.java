package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class CatCodigosPostalesDTO implements Serializable{

	private static final long serialVersionUID = 6949432993664273616L;
	private Integer idCodigoPostal;
	private CatAsentamientosDTO catAsentamientosDTO;
	private CatEstadosDTO catEstadosDTO;
	private CatMunicipiosDTO catMunicipiosDTO;
	private CatTiposAsentamientoDTO catTiposAsentamientoDTO;
	private String codigoPostal;
	
	public CatCodigosPostalesDTO() {}
	
	public CatCodigosPostalesDTO(Integer idCodigoPostal, String codigoPostal) {
		this.idCodigoPostal = idCodigoPostal;
		this.codigoPostal = codigoPostal;
	}

	public CatCodigosPostalesDTO(Integer idCodigoPostal, String codigoPostal, CatAsentamientosDTO catAsentamientosDTO,
			CatMunicipiosDTO catMunicipiosDTO, CatEstadosDTO catEstadosDTO,
			CatTiposAsentamientoDTO catTiposAsentamientoDTO) {
		this.idCodigoPostal = idCodigoPostal;
		this.codigoPostal = codigoPostal;
		this.catAsentamientosDTO = catAsentamientosDTO;
		this.catMunicipiosDTO = catMunicipiosDTO;
		this.catEstadosDTO = catEstadosDTO;
		this.catTiposAsentamientoDTO = catTiposAsentamientoDTO;
	}
	
	public CatCodigosPostalesDTO(int idCodigoPostal, String codigoPostal, Integer idAsentamiento , String descripcion,
			Integer idMunicipio, String municipio, int idEstado, String estado) {
		this.idCodigoPostal = idCodigoPostal;
		this.codigoPostal = codigoPostal;
		this.catAsentamientosDTO = new CatAsentamientosDTO(idAsentamiento, descripcion);
		this.catMunicipiosDTO = new CatMunicipiosDTO(idMunicipio, municipio, idEstado, estado);
	}
	
	

	/**
	 * @return the idCodigoPostal
	 */
	public Integer getIdCodigoPostal() {
		return idCodigoPostal;
	}

	/**
	 * @param idCodigoPostal the idCodigoPostal to set
	 */
	public void setIdCodigoPostal(Integer idCodigoPostal) {
		this.idCodigoPostal = idCodigoPostal;
	}

	/**
	 * @return the catAsentamientosDTO
	 */
	public CatAsentamientosDTO getCatAsentamientosDTO() {
		return catAsentamientosDTO;
	}

	/**
	 * @param catAsentamientosDTO the catAsentamientosDTO to set
	 */
	public void setCatAsentamientosDTO(CatAsentamientosDTO catAsentamientosDTO) {
		this.catAsentamientosDTO = catAsentamientosDTO;
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
	 * @return the catTiposAsentamientoDTO
	 */
	public CatTiposAsentamientoDTO getCatTiposAsentamientoDTO() {
		return catTiposAsentamientoDTO;
	}

	/**
	 * @param catTiposAsentamientoDTO the catTiposAsentamientoDTO to set
	 */
	public void setCatTiposAsentamientoDTO(CatTiposAsentamientoDTO catTiposAsentamientoDTO) {
		this.catTiposAsentamientoDTO = catTiposAsentamientoDTO;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
}
