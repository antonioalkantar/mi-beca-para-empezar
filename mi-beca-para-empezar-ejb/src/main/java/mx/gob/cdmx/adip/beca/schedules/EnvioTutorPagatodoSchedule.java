package mx.gob.cdmx.adip.beca.schedules;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.TransactionTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.client.ObtenMasRegistrarTutor;
import mx.gob.cdmx.adip.beca.client.ObtenMasToken;
import mx.gob.cdmx.adip.beca.commons.dto.BeneficiarioDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatCodigosRespuestaPagatodoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CrcBeneficiarioSolicitudDTO;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.CrcBeneficiarioSolicitudDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;
import mx.gob.cdmx.adip.beca.facade.SolicitudFacade;
import mx.gob.cdmx.adip.beca.facade.TutorFacade;

@Singleton
@Startup
public class EnvioTutorPagatodoSchedule {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvioTutorPagatodoSchedule.class);

	@Resource
	TimerService timerService;

	@Inject
	private TutorDAO tutorDAO;
	
	@Inject
	private TutorFacade tutorFacade;
	
	@Inject
	private CrcBeneficiarioSolicitudDAO crcSolicitudDAO;
	@Inject 
	private SolicitudFacade solicitudFacade;

	@TransactionTimeout(value = 4, unit = TimeUnit.HOURS)
//	@Schedules({ @Schedule(month = "*", dayOfMonth = "*", hour = "23", minute = "0", second = "0", persistent = false) })
//	@Schedules({ @Schedule(hour = "0-23", minute = "*/5", second = "1", persistent = false) })
	public void actualizaEnviadoPagatodo() {

		LOGGER.info("> Ejecución schedule - actualizacion de enviado_a_pagatodo Tutor  "
				+ new SimpleDateFormat("dd-MMMM-yyyy").format(new Date()));

		List<TutorDTO> listado = tutorDAO.consultaTutoresNoEnviados();

		listado.forEach(t -> {
			try {
				RespuestaDTO respuesta = new RespuestaDTO();
				respuesta = tutorFacade.envioPagatodo(t);
				if (respuesta.getCode() == 0) {
					t.setFechaEnvioAPagatodo(new Date());
					tutorDAO.actualizaEnviadoAPagatodo(t);
				}
				//TODO: revisar si es posible marcar al tutor en estatus diferente en caso de que sea regitro nuevo
//				else if(respuesta.getStatus() == Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA || respuesta.getStatus() == Constantes.RESPUESTA_CORREO_ASOCIADO_A_CUENTA) {
//					t.setInformacionGeneralObservaciones(respuesta.getStatus() == Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA 
//							? Constantes.MENSAJE_BENEFICIARIO_TELEFONO_TUTOR
//							: Constantes.MENSAJE_BENEFICIARIO_CORREO_TUTOR);
//					t.setInformacionGralCorrecto(false);
//					t.setCatEstatusDTO(new CatEstatusDTO(Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO));
//				}
			} catch (Exception e) {
				LOGGER.error("No es posible realizar la actualizacion de tutor", e);
			}
		});

		LOGGER.info("> Fin de ejecución schedule - actualizacion de campo enviado_a_pagatodo Tutor : " + new Date() + " total : "
				+ (listado != null ? listado.size() : Constantes.INT_VALOR_CERO));
		
		ReenvioSolicitudes();
		
	}
	public void ReenvioSolicitudes() {		
		LOGGER.info("> INICIO de ejecución schedule - actualizacion de campo PagatodoEnvioExitoso Solicitudes : " + new Date()			);
		List<CrcBeneficiarioSolicitudDTO> lstSolicitudes = crcSolicitudDAO.buscarNoEnviadoPagatodo();
		
		for (CrcBeneficiarioSolicitudDTO solicitud : lstSolicitudes) {
			solicitud =	enviaDatosPagatodo(solicitud);
			if (solicitud.getSolicitudDTO().isPagatodoEnvioExitoso()) {
				solicitudFacade.actualizaEnvioPagatodo(solicitud);	
			}
			else {
				solicitudFacade.actualizaEnvioFallidoPagatodo(solicitud);
			}			
		}		
		LOGGER.info("> Fin de ejecución schedule - actualizacion de campo PagatodoEnvioExitoso Solicitudes : " + new Date()			);
	}
	
	public CrcBeneficiarioSolicitudDTO enviaDatosPagatodo(CrcBeneficiarioSolicitudDTO crcBeneficiarioSolicitudDTO) {
		List<BeneficiarioDTO> lstBeneficiarios = new ArrayList<>();
		lstBeneficiarios.add(crcBeneficiarioSolicitudDTO.getBeneficiarioDTO());
		RespuestaDTO respuestaPagatodo = new RespuestaDTO();
		ObtenMasRegistrarTutor obtenMasRegistrarTutor = new ObtenMasRegistrarTutor();
		ObtenMasToken obtenMasToken = new ObtenMasToken();
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoFechaEnvio(new Date());
		crcBeneficiarioSolicitudDTO.getSolicitudDTO().setCatCodigosRespuestaPagatodoDTO(new CatCodigosRespuestaPagatodoDTO());
		try {
			respuestaPagatodo = obtenMasRegistrarTutor.registrarTutor(obtenMasToken.obtenerToken(),
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO(), lstBeneficiarios);
			switch (respuestaPagatodo.getCode()) {
				case 0:
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(true);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());					
					crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getDetCuentaBeneficiarioDTO()
							.setNumeroCuenta(respuestaPagatodo.getCuentaBeneficiario().get(0));
					break;
				case 7:					
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					break;
				case 11:
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					break;
				case 10:
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					break;
				default:
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
					crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
							.setIdCodigoPagatodo((long) respuestaPagatodo.getStatus());
					LOGGER.error("Error Servicio obtenMas (PAGATODO), retornó un codigo: "+respuestaPagatodo.getStatus()+" "+
							respuestaPagatodo.getMensaje()+" Tutor:"+ crcBeneficiarioSolicitudDTO.getSolicitudDTO().getTutorDTO().getCurp());
					break;
			}
		} catch (IOException e) {
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().setPagatodoEnvioExitoso(false);
			crcBeneficiarioSolicitudDTO.getSolicitudDTO().getCatCodigosRespuestaPagatodoDTO()
					.setIdCodigoPagatodo((long) Constantes.CODIGO_PAGATODO_NO_ENVIADO);			
			LOGGER.error("Error servicio pagatodo Beneficiario: "
					+ crcBeneficiarioSolicitudDTO.getBeneficiarioDTO().getCurpBeneficiario() + " --> IOException", e);
		}
		return crcBeneficiarioSolicitudDTO;

	}

}
