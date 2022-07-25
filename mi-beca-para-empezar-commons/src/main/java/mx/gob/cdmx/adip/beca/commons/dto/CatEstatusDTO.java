package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

public class CatEstatusDTO implements Serializable {

	private static final long serialVersionUID = -1459190810434138736L;
	private Integer idEstatus;
	private String descripcion;
	private Boolean vigente;
	private Boolean tutor;
	private Boolean beneficiario;

	public CatEstatusDTO() {
	}
	public CatEstatusDTO(Integer idEstatus) {
		this.idEstatus = idEstatus;
	}

	public CatEstatusDTO(Integer idEstatus, String descripcion, Boolean vigente, Boolean tutor, Boolean beneficiario) {
		this.idEstatus = idEstatus;
		this.descripcion = descripcion;
		this.vigente = vigente;
		this.tutor = tutor;
		this.beneficiario = beneficiario;
	}
	
	//DTO utilizado para la Bitacora del Funcionario
	public CatEstatusDTO(Integer idEstatus, String descripcion) {
		this.idEstatus = idEstatus;
		this.descripcion = descripcion;		
	}

	public Integer getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(Integer idEstatus) {
		this.idEstatus = idEstatus;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Boolean getTutor() {
		return tutor;
	}

	public void setTutor(Boolean tutor) {
		this.tutor = tutor;
	}

	public Boolean getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Boolean beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	public static String getDescripcionEstatusById(Integer id) {
		String label = Constantes.EMPTY_STRING;
		switch(id) {
			case 1 :
				label  = "En proceso";
			      break;
			case 2 :
				label  = "Pendiente de validación";
			      break;
			case 3 :
				label  = "Corregida por el ciudadano";
			      break;
			case 4 :
				label  = "Aclaración por circunstancia";
			      break;
			case 5 :
				label  = "Suspendida";
			      break;
			case 6 :
				label  = "Aprobada";
			      break;
			case 7 :
				label  = "Corrección por parte del ciudadano";
			      break;
			default:
				throw new IllegalArgumentException("El ID Estatus: "+id+" no existe. Revise la lógica de programación");
		}
		return label;
	}

}
