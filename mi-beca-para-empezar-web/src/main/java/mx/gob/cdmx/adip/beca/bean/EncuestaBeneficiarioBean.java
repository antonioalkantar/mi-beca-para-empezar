package mx.gob.cdmx.adip.beca.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mx.gob.cdmx.adip.beca.dao.CatGrupoPerteneceDAO;
import mx.gob.cdmx.adip.beca.dao.CatIngresosFamiliaDAO;
import mx.gob.cdmx.adip.beca.dao.CatMaterialesDomicilioDAO;
import mx.gob.cdmx.adip.beca.dao.CatTipoDomicilioDAO;
import mx.gob.cdmx.adip.beca.dao.EncuestaDAO;
import mx.gob.cdmx.adip.beca.facade.BeneficiarioFacade;
import mx.gob.cdmx.adip.beca.dao.CatCicloEscolarDAO;
import mx.gob.cdmx.adip.beca.commons.dto.CatGrupoPerteneceDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatIngresosFamiliaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatMaterialesDomicilioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatTipoDomicilioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.EncuestaDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

@Named
@SessionScoped
public class EncuestaBeneficiarioBean implements Serializable {

	private static final long serialVersionUID = 4245265798123283958L;

	@Inject private BeneficiarioFacade beneficiarioFacade;
	@Inject private EncuestaDAO encuestaDAO;
	@Inject private CatTipoDomicilioDAO catTipoDomicilioDAO;
	@Inject private CatMaterialesDomicilioDAO catMaterialesDomicilioDAO;
	@Inject private CatGrupoPerteneceDAO catGrupoPerteneceDAO;
	@Inject private CatIngresosFamiliaDAO catIngresosFamiliaDAO;
	@Inject private RegistroBeneficiarioBean registroBeneficiarioBean;
	@Inject private RegistroCompletadoBean registroCompletadoBean;
	@Inject	private CatCicloEscolarDAO catCicloEscolarDAO;

	private List<CatTipoDomicilioDTO> lstCatTipoDomicilio;
	private List<CatMaterialesDomicilioDTO> lstCatMaterialesDomicilio;
	private List<CatGrupoPerteneceDTO> lstCatGrupoPertenece;
	private List<CatIngresosFamiliaDTO> lstCatIngresoFamiliar;
	private String[] lstDatos;
	private List<String> lstDatosFront;
	private EncuestaDTO encuestaDTO;
	private Boolean habilitaOtro;
	private Boolean habilitaOtroGpo;
	private boolean soloLectura;
	private String msgNumeroP;
	private String msgNumeroT;
	
	public void init() {
		encuestaDTO = new EncuestaDTO();
		encuestaDTO = encuestaDAO.buscarPorIdSolicitud(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO().getSolicitudDTO().getIdSolicitud());
		lstCatTipoDomicilio = new ArrayList<CatTipoDomicilioDTO>();
		lstCatMaterialesDomicilio = new ArrayList<CatMaterialesDomicilioDTO>();
		lstCatGrupoPertenece = new ArrayList<CatGrupoPerteneceDTO>();
		lstCatIngresoFamiliar = new ArrayList<CatIngresosFamiliaDTO>();
		lstCatTipoDomicilio = catTipoDomicilioDAO.buscarTodos();
		lstCatMaterialesDomicilio = catMaterialesDomicilioDAO.buscarTodos();
		lstCatGrupoPertenece = catGrupoPerteneceDAO.buscarTodos();
		lstCatIngresoFamiliar = catIngresosFamiliaDAO.buscarTodos();
		llenaOpciones();
		habilitaOtro = false;
		habilitaOtroGpo = false;
		msgNumeroP = null;
		msgNumeroP = null;
		lstDatos = new String[6];
		if (encuestaDTO == null) {
			encuestaDTO = new EncuestaDTO();
			encuestaDTO.getSolicitudDTO().setIdSolicitud(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO().getSolicitudDTO().getIdSolicitud());
			soloLectura=false;
		} else { 
			encuestaDTO.getSolicitudDTO().setIdSolicitud(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO().getSolicitudDTO().getIdSolicitud());
			llenaOpcionesRetorno();
			seleccionaOtroGpo();
			soloLectura=true;
		}
	}
	
	public String guardar() {
//		LOGGER.info("Guardar encuesta ");
		if (encuestaDTO.getNumeroHabitantes() == 0) {
			msgNumeroP = Constantes.MENSAJE_ENCUESTA_ERROR_VALIDACION;
			return null;
		} else {
			msgNumeroP = null;
		}

		if (encuestaDTO.getNumeroHabitantes() < encuestaDTO.getHabitantesTrabajadores()) {
			msgNumeroT = Constantes.MENSAJE_ENCUESTA_ERROR_VALIDACION_PERS_TRABAJAN;
			return null;
		} else {
			msgNumeroT = null;
		}
		
		if (lstDatos != null && lstDatos.length > 0) {
			for (int i = 0; i < lstDatos.length; i++) {
				if (lstDatos[i].compareTo(Constantes.UTILES_ESCOLARES) == 0)
					encuestaDTO.setUtilesEscolares(true);
				if (lstDatos[i].compareTo(Constantes.ROPA) == 0)
					encuestaDTO.setRopa(true);
				if (lstDatos[i].compareTo(Constantes.ZAPATOS) == 0)
					encuestaDTO.setZapatos(true);
				if (lstDatos[i].compareTo(Constantes.COMIDA) == 0)
					encuestaDTO.setComida(true);
				if (lstDatos[i].compareTo(Constantes.JUGUETES) == 0)
					encuestaDTO.setJuguetes(true);
				if (lstDatos[i].compareTo(Constantes.OTRO) == 0)
					encuestaDTO.setOtro(true);
			}
		}
		encuestaDTO.getCatCicloEscolarDTO().setIdCicloEscolar(catCicloEscolarDAO.buscarCicloVigente().getIdCicloEscolar());
		registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO().setEncuestaDTO(encuestaDTO);
		if (registroBeneficiarioBean.isValidador()) {
			beneficiarioFacade.registrBeneficiarioCompleto(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO());
		}else {
			beneficiarioFacade.registraEncuesta(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO());	
		}			
		registroCompletadoBean.setSolicitud(registroBeneficiarioBean.getCrcBeneficiarioSolicitudDTO());
		return registroCompletadoBean.inicializar();
	}

	public void llenaOpciones() {
		lstDatosFront = new ArrayList<>();
		lstDatosFront.add(Constantes.UTILES_ESCOLARES);
		lstDatosFront.add(Constantes.ROPA);
		lstDatosFront.add(Constantes.ZAPATOS);
		lstDatosFront.add(Constantes.COMIDA);
		lstDatosFront.add(Constantes.JUGUETES);
		lstDatosFront.add(Constantes.OTRO);
	}

	public void llenaOpcionesRetorno() {
		lstDatos = new String[6];
		if (encuestaDTO.getUtilesEscolares() != null && encuestaDTO.getUtilesEscolares())
			lstDatos[0] = Constantes.UTILES_ESCOLARES;
		if (encuestaDTO.getRopa() != null && encuestaDTO.getRopa())
			lstDatos[1] = Constantes.ROPA;
		if (encuestaDTO.getZapatos() != null && encuestaDTO.getZapatos())
			lstDatos[2] = Constantes.ZAPATOS;
		if (encuestaDTO.getComida() != null && encuestaDTO.getComida())
			lstDatos[3] = Constantes.COMIDA;
		if (encuestaDTO.getJuguetes() != null && encuestaDTO.getJuguetes())
			lstDatos[4] = Constantes.JUGUETES;
		if (encuestaDTO.getOtro() != null && encuestaDTO.getOtro()) {
			lstDatos[5] = Constantes.OTRO;
			habilitaOtro = true;
		}
	}

	public void seleccionaOtro() {
		if (lstDatos != null && lstDatos.length > 0) 
			for (int i = 0; i < lstDatos.length; i++) {
//				LOGGER.info("Datos en la lista " + lstDatos[i]);
				if (lstDatos[i].compareTo(Constantes.OTRO) == 0) {
					habilitaOtro = true;
					break;
				}
				else {
					encuestaDTO.setEspecificaOtro("");
					habilitaOtro = false;
				}
			}
	}

	public void seleccionaOtroGpo() {
		if (encuestaDTO.getCatGrupoPerteneceDTO().getIdGrupoPertenece() == 6) { 
			habilitaOtroGpo = true;
		} else {
			habilitaOtroGpo = false;
		}
	}

	public void consulta(Long idSolicitud) {
		encuestaDTO = new EncuestaDTO();
		encuestaDTO = encuestaDAO.buscarPorIdSolicitud(idSolicitud);
		soloLectura=true;
	}
	
	public List<CatTipoDomicilioDTO> getLstCatTipoDomicilio() {
		return lstCatTipoDomicilio;
	}

	public void setLstCatTipoDomicilio(List<CatTipoDomicilioDTO> lstCatTipoDomicilio) {
		this.lstCatTipoDomicilio = lstCatTipoDomicilio;
	}

	public List<CatMaterialesDomicilioDTO> getLstCatMaterialesDomicilio() {
		return lstCatMaterialesDomicilio;
	}

	public void setLstCatMaterialesDomicilio(List<CatMaterialesDomicilioDTO> lstCatMaterialesDomicilio) {
		this.lstCatMaterialesDomicilio = lstCatMaterialesDomicilio;
	}

	public List<CatGrupoPerteneceDTO> getLstCatGrupoPertenece() {
		return lstCatGrupoPertenece;
	}

	public void setLstCatGrupoPertenece(List<CatGrupoPerteneceDTO> lstCatGrupoPertenece) {
		this.lstCatGrupoPertenece = lstCatGrupoPertenece;
	}

	public List<CatIngresosFamiliaDTO> getLstCatIngresoFamiliar() {
		return lstCatIngresoFamiliar;
	}

	public void setLstCatIngresoFamiliar(List<CatIngresosFamiliaDTO> lstCatIngresoFamiliar) {
		this.lstCatIngresoFamiliar = lstCatIngresoFamiliar;
	}

	public EncuestaDTO getEncuestaDTO() {
		return encuestaDTO;
	}

	public void setEncuestaDTO(EncuestaDTO encuestaDTO) {
		this.encuestaDTO = encuestaDTO;
	}

	public String[] getLstDatos() {
		return lstDatos;
	}

	public void setLstDatos(String[] lstDatos) {
		this.lstDatos = lstDatos;
	}

	public List<String> getLstDatosFront() {
		return lstDatosFront;
	}

	public void setLstDatosFront(List<String> lstDatosFront) {
		this.lstDatosFront = lstDatosFront;
	}

	public Boolean getHabilitaOtro() {
		return habilitaOtro;
	}

	public void setHabilitaOtro(Boolean habilitaOtro) {
		this.habilitaOtro = habilitaOtro;
	}

	public boolean isSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public Boolean getHabilitaOtroGpo() {
		return habilitaOtroGpo;
	}

	public void setHabilitaOtroGpo(Boolean habilitaOtroGpo) {
		this.habilitaOtroGpo = habilitaOtroGpo;
	}

	public String getMsgNumeroP() {
		return msgNumeroP;
	}

	public void setMsgNumeroP(String msgNumeroP) {
		this.msgNumeroP = msgNumeroP;
	}

	public String getMsgNumeroT() {
		return msgNumeroT;
	}

	public void setMsgNumeroT(String msgNumeroT) {
		this.msgNumeroT = msgNumeroT;
	}
}