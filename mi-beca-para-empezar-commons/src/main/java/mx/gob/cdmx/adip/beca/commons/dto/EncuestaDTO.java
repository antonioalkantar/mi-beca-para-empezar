package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class EncuestaDTO implements Serializable {

	private static final long serialVersionUID = 2207564698412579092L;

	private int idEncuesta;
	private CatGrupoPerteneceDTO catGrupoPerteneceDTO;
	private CatIngresosFamiliaDTO catIngresosFamiliaDTO;
	private CatMaterialesDomicilioDTO catMaterialesDomicilioDTO;
	private CatTipoDomicilioDTO catTipoDomicilioDTO;
	private SolicitudDTO solicitudDTO;
	private int numeroHabitantes;
	private int habitantesTrabajadores;
	private Boolean utilesEscolares;
	private Boolean ropa;
	private Boolean zapatos;
	private Boolean comida;
	private Boolean juguetes;
	private Boolean otro;
	private String especificaOtro;
	private String otroGrupo;
	private CatCicloEscolarDTO catCicloEscolarDTO;

	public EncuestaDTO() {
		catGrupoPerteneceDTO = new CatGrupoPerteneceDTO();
		catIngresosFamiliaDTO = new CatIngresosFamiliaDTO();
		catMaterialesDomicilioDTO = new CatMaterialesDomicilioDTO();
		catTipoDomicilioDTO = new CatTipoDomicilioDTO();
		solicitudDTO = new SolicitudDTO();
		catCicloEscolarDTO = new CatCicloEscolarDTO();
	}

	public EncuestaDTO(int idEncuesta) {
		this.idEncuesta = idEncuesta;
	}
	
	public EncuestaDTO(int idEncuesta, Integer idCicloEscolar, String descripcionCicloEscolar) {
		this.idEncuesta = idEncuesta;
		this.catCicloEscolarDTO = new CatCicloEscolarDTO(idCicloEscolar, descripcionCicloEscolar);
	}
	// constructor para Encuesta.findByIdSolicitud
	public EncuestaDTO(int idEncuesta,
	int cgpIdGrupoPertenece, Boolean cgpEstatus, String cgpDescripcion, 
	int cifIdIngresosFamilia,Boolean cifEstatus, String cifDescripcion, 
	int cmdIdMaterialesDomicilio, Boolean cmdEstatus, String cmdDescripcion, 
	int ctdIdTipoDomicilio,Boolean ctdEstatus, String ctdDescripcion, 
	Long idSolicitud,
	int numeroHabitantes,
	int habitantesTrabajadores,
	Boolean utilesEscolares,
	Boolean ropa,
	Boolean zapatos,
	Boolean comida,
	Boolean juguetes,
	Boolean otro,
	String especificaOtro,
	String otroGrupo
	) {
		this.idEncuesta = idEncuesta;
		catGrupoPerteneceDTO = new CatGrupoPerteneceDTO(cgpIdGrupoPertenece, cgpDescripcion, cgpEstatus);
		catIngresosFamiliaDTO = new CatIngresosFamiliaDTO(cifIdIngresosFamilia, cifDescripcion, cifEstatus);
		catMaterialesDomicilioDTO = new CatMaterialesDomicilioDTO(cmdIdMaterialesDomicilio, cmdDescripcion, cmdEstatus);
		catTipoDomicilioDTO = new CatTipoDomicilioDTO(ctdIdTipoDomicilio, ctdDescripcion, ctdEstatus);
		solicitudDTO = new SolicitudDTO(idSolicitud);
		this.numeroHabitantes = numeroHabitantes;
		this.habitantesTrabajadores = habitantesTrabajadores;
		this.utilesEscolares = utilesEscolares;
		this.ropa = ropa;
		this.zapatos = zapatos;
		this.comida = comida;
		this.juguetes = juguetes;
		this.otro = otro;
		this.especificaOtro = especificaOtro;
		this.otroGrupo = otroGrupo;
	}

	// constructor para Encuesta.allByIdSolicitud
	public EncuestaDTO(int idEncuesta,
	int cgpIdGrupoPertenece, Boolean cgpEstatus, String cgpDescripcion, 
	int cifIdIngresosFamilia,Boolean cifEstatus, String cifDescripcion, 
	int cmdIdMaterialesDomicilio, Boolean cmdEstatus, String cmdDescripcion, 
	int ctdIdTipoDomicilio,Boolean ctdEstatus, String ctdDescripcion, 
	Long idSolicitud,
	int numeroHabitantes,
	int habitantesTrabajadores,
	Boolean utilesEscolares,
	Boolean ropa,
	Boolean zapatos,
	Boolean comida,
	Boolean juguetes,
	Boolean otro,
	String especificaOtro,
	String otroGrupo,
	Integer idCicloEscolar,
	String descCicloEscolar
	) {
		this.idEncuesta = idEncuesta;
		catGrupoPerteneceDTO = new CatGrupoPerteneceDTO(cgpIdGrupoPertenece, cgpDescripcion, cgpEstatus);
		catIngresosFamiliaDTO = new CatIngresosFamiliaDTO(cifIdIngresosFamilia, cifDescripcion, cifEstatus);
		catMaterialesDomicilioDTO = new CatMaterialesDomicilioDTO(cmdIdMaterialesDomicilio, cmdDescripcion, cmdEstatus);
		catTipoDomicilioDTO = new CatTipoDomicilioDTO(ctdIdTipoDomicilio, ctdDescripcion, ctdEstatus);
		solicitudDTO = new SolicitudDTO(idSolicitud);
		this.numeroHabitantes = numeroHabitantes;
		this.habitantesTrabajadores = habitantesTrabajadores;
		this.utilesEscolares = utilesEscolares;
		this.ropa = ropa;
		this.zapatos = zapatos;
		this.comida = comida;
		this.juguetes = juguetes;
		this.otro = otro;
		this.especificaOtro = especificaOtro;
		this.otroGrupo = otroGrupo;
		this.catCicloEscolarDTO = new CatCicloEscolarDTO(idCicloEscolar, descCicloEscolar);
	}

	public int getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(int idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public CatGrupoPerteneceDTO getCatGrupoPerteneceDTO() {
		return catGrupoPerteneceDTO;
	}

	public void setCatGrupoPerteneceDTO(CatGrupoPerteneceDTO catGrupoPerteneceDTO) {
		this.catGrupoPerteneceDTO = catGrupoPerteneceDTO;
	}

	public CatIngresosFamiliaDTO getCatIngresosFamiliaDTO() {
		return catIngresosFamiliaDTO;
	}

	public void setCatIngresosFamiliaDTO(CatIngresosFamiliaDTO catIngresosFamiliaDTO) {
		this.catIngresosFamiliaDTO = catIngresosFamiliaDTO;
	}

	public CatMaterialesDomicilioDTO getCatMaterialesDomicilioDTO() {
		return catMaterialesDomicilioDTO;
	}

	public void setCatMaterialesDomicilioDTO(CatMaterialesDomicilioDTO catMaterialesDomicilioDTO) {
		this.catMaterialesDomicilioDTO = catMaterialesDomicilioDTO;
	}

	public CatTipoDomicilioDTO getCatTipoDomicilioDTO() {
		return catTipoDomicilioDTO;
	}

	public void setCatTipoDomicilioDTO(CatTipoDomicilioDTO catTipoDomicilioDTO) {
		this.catTipoDomicilioDTO = catTipoDomicilioDTO;
	}

	public SolicitudDTO getSolicitudDTO() {
		return solicitudDTO;
	}

	public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
		this.solicitudDTO = solicitudDTO;
	}

	public int getNumeroHabitantes() {
		return numeroHabitantes;
	}

	public void setNumeroHabitantes(int numeroHabitantes) {
		this.numeroHabitantes = numeroHabitantes;
	}

	public int getHabitantesTrabajadores() {
		return habitantesTrabajadores;
	}

	public void setHabitantesTrabajadores(int habitantesTrabajadores) {
		this.habitantesTrabajadores = habitantesTrabajadores;
	}

	public Boolean getUtilesEscolares() {
		return utilesEscolares;
	}

	public void setUtilesEscolares(Boolean utilesEscolares) {
		this.utilesEscolares = utilesEscolares;
	}

	public Boolean getRopa() {
		return ropa;
	}

	public void setRopa(Boolean ropa) {
		this.ropa = ropa;
	}

	public Boolean getZapatos() {
		return zapatos;
	}

	public void setZapatos(Boolean zapatos) {
		this.zapatos = zapatos;
	}

	public Boolean getComida() {
		return comida;
	}

	public void setComida(Boolean comida) {
		this.comida = comida;
	}

	public Boolean getJuguetes() {
		return juguetes;
	}

	public void setJuguetes(Boolean juguetes) {
		this.juguetes = juguetes;
	}

	public Boolean getOtro() {
		return otro;
	}

	public void setOtro(Boolean otro) {
		this.otro = otro;
	}

	public String getEspecificaOtro() {
		return especificaOtro;
	}

	public void setEspecificaOtro(String especificaOtro) {
		this.especificaOtro = especificaOtro;
	}
	public String getOtroGrupo() {
		return otroGrupo;
	}
	public void setOtroGrupo(String otroGrupo) {
		this.otroGrupo = otroGrupo;
	}

	public CatCicloEscolarDTO getCatCicloEscolarDTO() {
		return catCicloEscolarDTO;
	}

	public void setCatCicloEscolarDTO(CatCicloEscolarDTO catCicloEscolarDTO) {
		this.catCicloEscolarDTO = catCicloEscolarDTO;
	}

}
