package mx.gob.cdmx.adip.beca.commons.dto;

import java.util.Date;

public class DispersionDTO {

	private Long idDispersion;
	private CatCicloEscolarDTO catCicloEscolar;
	private CatPeriodoEscolarDTO catPeriodoEscolar;
	private CatTipoDispersionDTO catTipoDispersion;
	private Long numBeneficiarios;
	private Date fechaEjecucion;
	private Long idUsuarioEjecucion;
	private Date fechaConclusion;
	private CatEstatusDispersionDTO catEstatusDispersion;
	private Double aplicaDispersionPorcentaje;
	private Long aplicaDispersionNumero;
	private Double noAplicaDispersionPorcentaje;
	private Long noAplicaDispersionNumero;
	private Date fechaDescarga;

	public DispersionDTO() {
		catCicloEscolar = new CatCicloEscolarDTO();
		catPeriodoEscolar = new CatPeriodoEscolarDTO();
		catTipoDispersion = new CatTipoDispersionDTO();
		catEstatusDispersion = new CatEstatusDispersionDTO();
	}

	public DispersionDTO(Long idDispersion, Long idCicloEscolar, String descripcionCicloEscolar, Long idPeriodoEscolar,
			String descripcionPeriodo, Long idTipoDispersion, String descripcionTipoDispersion, Long numBeneficiarios,
			Date fechaEjecucion, Long idUsuarioEjecucion, Date fechaConclusion, Long idEstatusDispersion,
			String descripcionEstatusDispersion, Double aplicaDispersionPorcentaje, Long aplicaDispersionNumero,
			Double noAplicaDispersionPorcentaje, Long noAplicaDispersionNumero, Date fechaDescarga) {
		this.idDispersion = idDispersion;
		this.catPeriodoEscolar = new CatPeriodoEscolarDTO(idPeriodoEscolar, descripcionPeriodo);
		this.catCicloEscolar = new CatCicloEscolarDTO(idCicloEscolar, descripcionCicloEscolar);
		this.catTipoDispersion = new CatTipoDispersionDTO(idTipoDispersion, descripcionTipoDispersion);
		this.numBeneficiarios = numBeneficiarios;
		this.fechaEjecucion = fechaEjecucion;
		this.idUsuarioEjecucion = idUsuarioEjecucion;
		this.fechaConclusion = fechaConclusion;
		this.catEstatusDispersion = new CatEstatusDispersionDTO(idEstatusDispersion, descripcionEstatusDispersion);
		this.aplicaDispersionPorcentaje = aplicaDispersionPorcentaje;
		this.aplicaDispersionNumero = aplicaDispersionNumero;
		this.noAplicaDispersionPorcentaje = noAplicaDispersionPorcentaje;
		this.noAplicaDispersionNumero = noAplicaDispersionNumero;
		this.fechaDescarga = fechaDescarga;
	}

	public Long getIdDispersion() {
		return idDispersion;
	}

	public void setIdDispersion(Long idDispersion) {
		this.idDispersion = idDispersion;
	}

	public CatCicloEscolarDTO getCatCicloEscolar() {
		return catCicloEscolar;
	}

	public void setCatCicloEscolar(CatCicloEscolarDTO catCicloEscolar) {
		this.catCicloEscolar = catCicloEscolar;
	}

	public CatPeriodoEscolarDTO getCatPeriodoEscolar() {
		return catPeriodoEscolar;
	}

	public void setCatPeriodoEscolar(CatPeriodoEscolarDTO catPeriodoEscolar) {
		this.catPeriodoEscolar = catPeriodoEscolar;
	}

	public CatTipoDispersionDTO getCatTipoDispersion() {
		return catTipoDispersion;
	}

	public void setCatTipoDispersion(CatTipoDispersionDTO catTipoDispersion) {
		this.catTipoDispersion = catTipoDispersion;
	}

	public Long getNumBeneficiarios() {
		return numBeneficiarios;
	}

	public void setNumBeneficiarios(Long numBeneficiarios) {
		this.numBeneficiarios = numBeneficiarios;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Long getIdUsuarioEjecucion() {
		return idUsuarioEjecucion;
	}

	public void setIdUsuarioEjecucion(Long idUsuarioEjecucion) {
		this.idUsuarioEjecucion = idUsuarioEjecucion;
	}

	public Date getFechaConclusion() {
		return fechaConclusion;
	}

	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}

	public CatEstatusDispersionDTO getCatEstatusDispersion() {
		return catEstatusDispersion;
	}

	public void setCatEstatusDispersion(CatEstatusDispersionDTO catEstatusDispersion) {
		this.catEstatusDispersion = catEstatusDispersion;
	}

	public Double getAplicaDispersionPorcentaje() {
		return aplicaDispersionPorcentaje;
	}

	public void setAplicaDispersionPorcentaje(Double aplicaDispersionPorcentaje) {
		this.aplicaDispersionPorcentaje = aplicaDispersionPorcentaje;
	}

	public Long getAplicaDispersionNumero() {
		return aplicaDispersionNumero;
	}

	public void setAplicaDispersionNumero(Long aplicaDispersionNumero) {
		this.aplicaDispersionNumero = aplicaDispersionNumero;
	}

	public Double getNoAplicaDispersionPorcentaje() {
		return noAplicaDispersionPorcentaje;
	}

	public void setNoAplicaDispersionPorcentaje(Double noAplicaDispersionPorcentaje) {
		this.noAplicaDispersionPorcentaje = noAplicaDispersionPorcentaje;
	}

	public Long getNoAplicaDispersionNumero() {
		return noAplicaDispersionNumero;
	}

	public void setNoAplicaDispersionNumero(Long noAplicaDispersionNumero) {
		this.noAplicaDispersionNumero = noAplicaDispersionNumero;
	}

	public Date getFechaDescarga() {
		return fechaDescarga;
	}

	public void setFechaDescarga(Date fechaDescarga) {
		this.fechaDescarga = fechaDescarga;
	}
}
