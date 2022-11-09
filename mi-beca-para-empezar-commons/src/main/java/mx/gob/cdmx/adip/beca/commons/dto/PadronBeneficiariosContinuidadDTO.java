package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class PadronBeneficiariosContinuidadDTO implements Serializable {

	private static final long serialVersionUID = -265330398093994042L;
	
	private Long idBeneficiarioContinuidad;
	private String curp;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String cct;
	private String nivel;
	private String grado;
	private String alcaldia;
	private Double monto;
	private String genero;
	private String cuenta;
	private String folio;
	private String dispersion;
	private String curpTutor;
	
	
	public PadronBeneficiariosContinuidadDTO() {
	}
	
	public PadronBeneficiariosContinuidadDTO(String curp, String curpTutor, Long idBeneficiarioContinuidad) {
		this.curp = curp;
		this.curpTutor = curpTutor;
		this.idBeneficiarioContinuidad =idBeneficiarioContinuidad;
	}
	
	public PadronBeneficiariosContinuidadDTO (Long idBeneficiarioContinuidad, String curp, String nombre,
			String primerApellido, String segundoApellido, String cct, String nivel, String grado, String alcaldia,
			Double monto, String genero, String cuenta, String folio, String dispersion) {
		this.idBeneficiarioContinuidad = idBeneficiarioContinuidad;
		this.curp = curp;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.cct = cct;
		this.nivel = nivel;
		this.grado = grado;
		this.alcaldia = alcaldia;
		this.monto = monto;
		this.genero = genero;
		this.cuenta = cuenta;
		this.folio = folio;
		this.dispersion = dispersion;
	}

	public Long getIdBeneficiarioContinuidad() {
		return idBeneficiarioContinuidad;
	}

	public void setIdBeneficiarioContinuidad(Long idBeneficiarioContinuidad) {
		this.idBeneficiarioContinuidad = idBeneficiarioContinuidad;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCct() {
		return cct;
	}

	public void setCct(String cct) {
		this.cct = cct;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getAlcaldia() {
		return alcaldia;
	}

	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getDispersion() {
		return dispersion;
	}

	public void setDispersion(String dispersion) {
		this.dispersion = dispersion;
	}

	public String getCurpTutor() {
		return curpTutor;
	}

	public void setCurpTutor(String curpTutor) {
		this.curpTutor = curpTutor;
	}
	
	
	

}
