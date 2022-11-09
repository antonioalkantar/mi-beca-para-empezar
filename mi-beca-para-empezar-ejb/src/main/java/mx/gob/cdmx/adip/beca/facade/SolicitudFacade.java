package mx.gob.cdmx.adip.beca.facade;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambiosTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.PadronBeneficiariosContinuidadDTO;
import mx.gob.cdmx.adip.beca.commons.dto.SolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.dao.BitacoraCambiosTutorDAO;
import mx.gob.cdmx.adip.beca.dao.PadronBeneficiariosContinuidadDAO;
import mx.gob.cdmx.adip.beca.dao.SolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.BitacoraTutorDAO;
import mx.gob.cdmx.adip.beca.dao.DetCuentaBeneficiarioDAO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;

@Stateless
@LocalBean
public class SolicitudFacade {
	
	@Inject
	private SolicitudDAO solicitudDAO;
	
	@Inject
	private BitacoraCambiosTutorDAO bitacoraCambiosTutorDAO;
	
	@Inject
	private PadronBeneficiariosContinuidadDAO padronBeneficiariosContinuidadDAO;	
	
	@Inject
	private DetCuentaBeneficiarioDAO detCuentaBeneficiarioDAO;
	
	@Inject 
	private BitacoraTutorDAO bitacoraTutorDAO;

	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarCambioTutor(BitacoraCambiosTutorDTO bitacoraCambiosTutorDTO, TutorDTO tutorNuevo, SolicitudDTO solicitud, Long idUsuario) throws IOException {
		
		
		
		// 1.1 Se guarda documento 
		if (bitacoraCambiosTutorDTO.getContentFileSoporteDoc() != null) {
			FileUtils.writeByteArrayToFile(new File(bitacoraCambiosTutorDTO.getRutaDocto()), bitacoraCambiosTutorDTO.getContentFileSoporteDoc());
		}

		TutorDTO tutorAnterior = solicitud.getTutorDTO();
		bitacoraCambiosTutorDTO.setTutorByIdTutorAnteriorDTO(tutorAnterior);
		bitacoraCambiosTutorDTO.setTutorByIdTutorNuevoDTO(tutorNuevo);
		bitacoraCambiosTutorDTO.setFecha(new Date());
		bitacoraCambiosTutorDTO.setSolicitudDTO(solicitud);
		bitacoraCambiosTutorDTO.setIdUsuario(idUsuario);
		
		//1.2 Se guarda Bitacora del Cambio del Tutor
		bitacoraCambiosTutorDAO.guardarBitacora(bitacoraCambiosTutorDTO);
		
		solicitud.getTutorDTO().setIdUsuarioLlaveCdmx(tutorNuevo.getIdUsuarioLlaveCdmx());
		
		//1.3 Se Actualizan en la solicitud al nuevo tutor
		solicitudDAO.actualizarTutor(solicitud);	
		
		//1.4 validar si existe el registro en padron_continuidad para hacer el cambio del beneficiario al tutor nuevo
		 //1.4.1 Consulta por curp del beneficiafio
		PadronBeneficiariosContinuidadDTO padronBeneficiariosDTO = new PadronBeneficiariosContinuidadDTO();	
		padronBeneficiariosDTO = padronBeneficiariosContinuidadDAO.buscarPorCurp(solicitud.getBeneficiarioDTO().getCurpBeneficiario());
		
		if (padronBeneficiariosDTO != null){
			//Cambio de tutor en tabla padron_continuidad
			padronBeneficiariosDTO.setCurpTutor(tutorNuevo.getCurp());	
			padronBeneficiariosContinuidadDAO.actualizarCurpTutor(padronBeneficiariosDTO);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizaEnvioPagatodo(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		//Actualiza Solicitud cuando fue un envio exitoso y codigo 0 que devolvio una cuenta bancaria
		solicitudDAO.actualizaSolicitudPagatodo(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		detCuentaBeneficiarioDAO.actualizar(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO());
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizaEnvioFallidoPagatodo(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		//Actualiza solicitud con codigo resupuesta pagatodo fallido 
		solicitudDAO.actualizaSolicitudPagatodo(crcBeneficiarioSolicitudDTO.getSolicitudDTO());
		//Si el codigo devuelve 11 o 7 se notifica al tutor que debe actualizar su informacion de contacto y se añade a bitacoraTutor
		if (crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO().getIdCodigoPagatodo() == Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA 
				|| crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
						.getIdCodigoPagatodo() == Constantes.RESPUESTA_CORREO_ASOCIADO_A_CUENTA ) {
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setInformacionGralCorrecto(false);
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setCatEstatusDTO(new CatEstatusDTO(Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO));
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().setInformacionGeneralObservaciones(
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.getIdCodigoPagatodo() == Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA ? Constantes.MENSAJE_BENEFICIARIO_TELEFONO_TUTOR : Constantes.MENSAJE_BENEFICIARIO_CORREO_TUTOR);
			//se añade movimiento a bitacoraTutor
			BitacoraTutorDTO bitacoraTutorDTO = new BitacoraTutorDTO();
			bitacoraTutorDTO.setTutorDTO(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO());
			bitacoraTutorDTO.setFechaCaptura(new Date());
			bitacoraTutorDTO.setIdUsuarioFidegar((long) 0);
			bitacoraTutorDTO.setCatEstatusDTO(crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCatEstatusDTO());
			bitacoraTutorDAO.guardar(bitacoraTutorDTO);

		}
		
	}
}
