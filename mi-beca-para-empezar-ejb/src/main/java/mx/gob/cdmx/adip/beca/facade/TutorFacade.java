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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.client.ObtenMasRegistrarTutor;
import mx.gob.cdmx.adip.beca.client.ObtenMasToken;
import mx.gob.cdmx.adip.beca.common.infra.Environment;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraCambioContactoDTO;
import mx.gob.cdmx.adip.beca.commons.dto.BitacoraTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;
import mx.gob.cdmx.adip.beca.commons.dto.RespuestaDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.UserDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BitacoraCambioContactoDAO;
import mx.gob.cdmx.adip.beca.dao.BitacoraTutorDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Stateless
@LocalBean
public class TutorFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TutorFacade.class);
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject 
	private BitacoraTutorDAO bitacoraTutorDAO;
	
	@Inject 
	private BitacoraCambioContactoDAO bitacoraCambioContactoDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int registroNuevoTutor(TutorDTO tutor) throws IOException {
		int estatusRespuesta = 0;

		StringBuilder pathFinalArchivoIdentificacion = new StringBuilder();
		pathFinalArchivoIdentificacion.append(Environment.getPathDocumentos());
		pathFinalArchivoIdentificacion.append(tutor.getIdUsuarioLlaveCdmx());
		pathFinalArchivoIdentificacion.append(Constantes.SEPARADOR_RUTA);
		pathFinalArchivoIdentificacion.append(Constantes.RUTA_IDENTIFICACION_OFICIAL);
		pathFinalArchivoIdentificacion.append(Constantes.SEPARADOR_RUTA);
		pathFinalArchivoIdentificacion.append(tutor.getNombreArchivoIdentificaCorto());

		// Se copia el documento identificación oficial de temporal a destino
		FileUtils.copyFile(new File(tutor.getArchivoIdentificacion()), new File(pathFinalArchivoIdentificacion.toString()));

		StringBuilder pathFinalArchivoDomicilio = new StringBuilder();
		pathFinalArchivoDomicilio.delete(0, pathFinalArchivoDomicilio.toString().length());
		pathFinalArchivoDomicilio.append(Environment.getPathDocumentos());
		pathFinalArchivoDomicilio.append(tutor.getIdUsuarioLlaveCdmx());
		pathFinalArchivoDomicilio.append(Constantes.SEPARADOR_RUTA);
		pathFinalArchivoDomicilio.append(Constantes.RUTA_COMPROBANTE_DOMICILIO);
		pathFinalArchivoDomicilio.append(Constantes.SEPARADOR_RUTA);
		pathFinalArchivoDomicilio.append(tutor.getNombreArchivoComprobanteDomCorto());
		// Se copia el documento Comprobante de domicilio de temporal a destino
		FileUtils.copyFile(new File(tutor.getArchivoComprobanteDomicilio()), new File(pathFinalArchivoDomicilio.toString()));
		
		try {
			RespuestaDTO respuesta = new RespuestaDTO();
			respuesta = envioPagatodo(tutor);
			estatusRespuesta = respuesta.getStatus();
			if (respuesta.getCode() != 0 ) {
				if(respuesta.getStatus() != Constantes.RESPUESTA_TELEFONO_ASOCIADO_A_CUENTA && respuesta.getStatus() != Constantes.RESPUESTA_CORREO_ASOCIADO_A_CUENTA){
					// Esto no debería de pasar, pero si ocurre hay que imprimir en el log los detalles de la petición
					LOGGER.error("El servicio de pagatodo arrojo un status: "+respuesta.getStatus()+", con el siguiente tutor:"+tutor.toString());
				}
				return respuesta.getStatus();
			}else {
				tutor.setEnviadoAPagatodo(true);
				tutor.setFechaEnvioAPagatodo(new Date());
			}

		} catch (Exception ex) {
			LOGGER.error("No es posible enviar el registro a pagatodo: " + tutor.toString(), ex);
		}
		
		/* 2. Se guarda Tutor en BD. 
		 * El borrado de archivos temporales y las rutas finales de los archivos se setean hasta este momento porque si se asignan antes y el Ws de pagatodo regresa un error 
		 * ya perdimos la ruta temporal y si vuelven a dar clic en guardar ya no va a poder guardar de origen a destino, sino tendría los valores de destino a destino marcando un error.
		 */
		FileUtils.forceDelete(new File(tutor.getArchivoIdentificacion().substring(0, tutor.getArchivoIdentificacion().lastIndexOf(Constantes.SEPARADOR_RUTA))));
		FileUtils.forceDelete(new File(tutor.getArchivoComprobanteDomicilio().substring(0, tutor.getArchivoComprobanteDomicilio().lastIndexOf(Constantes.SEPARADOR_RUTA))));
		
		tutor.setArchivoIdentificacion(pathFinalArchivoIdentificacion.toString());
		tutor.setArchivoComprobanteDomicilio(pathFinalArchivoDomicilio.toString());	
		tutorDAO.guardar(tutor);
		
		// 3. Se guarda en bitácora
		BitacoraTutorDTO bitacoraTutorDTO = new BitacoraTutorDTO();
		bitacoraTutorDTO.setTutorDTO(tutor);
		bitacoraTutorDTO.setFechaCaptura(new Date());
		bitacoraTutorDTO.setCatEstatusDTO(tutor.getCatEstatusDTO());
		bitacoraTutorDAO.guardar(bitacoraTutorDTO);
		return estatusRespuesta;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean validaTutor(TutorDTO tutor, Long idUsuarioFidegar) {
		if (tutor.getInformacionGralCorrecto() == null 
				|| tutor.getInformacionGralCorrecto() == false
				|| tutor.getDomicilioCorrecto() == null 
				|| tutor.getDomicilioCorrecto() == false ) {				
			tutor.setCatEstatusDTO(new CatEstatusDTO(Constantes.ID_ESTATUS_CORRECCION_POR_PARTE_CIUDADANO));				
		}else {
			tutor.setCatEstatusDTO(new CatEstatusDTO(Constantes.ID_ESTATUS_APROBADA));
		}			
		tutorDAO.actualizaTutor(tutor);			
		BitacoraTutorDTO bitacoraTutorDTO = new BitacoraTutorDTO();
		bitacoraTutorDTO.setTutorDTO(tutor);
		bitacoraTutorDTO.setFechaCaptura(new Date());
		bitacoraTutorDTO.setIdUsuarioFidegar(idUsuarioFidegar);
		bitacoraTutorDTO.setCatEstatusDTO(tutor.getCatEstatusDTO());
		bitacoraTutorDAO.guardar(bitacoraTutorDTO);

		return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean envioCorreccionTutor(TutorDTO tutor , Long idUsuarioFidegar) throws IOException {
		
		StringBuilder path = new StringBuilder();

		path.append(Environment.getPathDocumentosTemporales());
		path.append(tutor.getIdUsuarioLlaveCdmx());
		path.append(Constantes.SEPARADOR_RUTA);
		path.append(Constantes.RUTA_IDENTIFICACION_OFICIAL);
		path.append(Constantes.SEPARADOR_RUTA);
		path.append(tutor.getArchivoIdentificacion().substring(1+ tutor.getArchivoIdentificacion().lastIndexOf(Constantes.SEPARADOR_RUTA)));

		File fileComp = new File(path.toString());
		if (fileComp.exists()) {
			path.delete(0, path.toString().length());
			path.append(Environment.getPathDocumentos());
			path.append(tutor.getIdUsuarioLlaveCdmx());
			path.append(Constantes.SEPARADOR_RUTA);
			path.append(Constantes.RUTA_IDENTIFICACION_OFICIAL);
			path.append(Constantes.SEPARADOR_RUTA);
			path.append(tutor.getNombreArchivoIdentificaCorto());
			
			FileUtils.forceDelete(new File(path.toString().substring(0,
					tutor.getArchivoIdentificacion().lastIndexOf(Constantes.SEPARADOR_RUTA))));
			
			// Se copia el documento identificación oficial de temporal a destino
			FileUtils.copyFile(new File(tutor.getArchivoIdentificacion()), new File(path.toString()));
			// Se elimina el directorio de documento de Identificación oficial
			FileUtils.forceDelete(new File(tutor.getArchivoIdentificacion().substring(0,
					tutor.getArchivoIdentificacion().lastIndexOf(Constantes.SEPARADOR_RUTA))));
			tutor.setArchivoIdentificacion(path.toString());

			fileComp = null;
		}
		path.delete(0, path.toString().length());
		path.append(Environment.getPathDocumentosTemporales());
		path.append(tutor.getIdUsuarioLlaveCdmx());
		path.append(Constantes.SEPARADOR_RUTA);
		path.append(Constantes.RUTA_COMPROBANTE_DOMICILIO);
		path.append(Constantes.SEPARADOR_RUTA);
		path.append(tutor.getArchivoComprobanteDomicilio().substring(1+ tutor.getArchivoComprobanteDomicilio().lastIndexOf(Constantes.SEPARADOR_RUTA)));

		fileComp = new File(path.toString());
		if (fileComp.exists()) {
			path.delete(0, path.toString().length());
			path.append(Environment.getPathDocumentos());
			path.append(tutor.getIdUsuarioLlaveCdmx());
			path.append(Constantes.SEPARADOR_RUTA);
			path.append(Constantes.RUTA_COMPROBANTE_DOMICILIO);
			path.append(Constantes.SEPARADOR_RUTA);
			path.append(tutor.getNombreArchivoComprobanteDomCorto());
			
			FileUtils.forceDelete(new File(path.toString().substring(0,
					tutor.getArchivoComprobanteDomicilio().lastIndexOf(Constantes.SEPARADOR_RUTA))));

			// Se copia el documento Comprobante de domicilio de temporal a destino
			FileUtils.copyFile(new File(tutor.getArchivoComprobanteDomicilio()), new File(path.toString()));
			// Se elimina el directorio de documento de Comprobante de domicilio
			FileUtils.forceDelete(new File(tutor.getArchivoComprobanteDomicilio().substring(0,
					tutor.getArchivoComprobanteDomicilio().lastIndexOf(Constantes.SEPARADOR_RUTA))));

			tutor.setArchivoComprobanteDomicilio(path.toString());
		}
		// 2. Se actualiza al tutor en la BD
		tutorDAO.actualizaTutor(tutor);

		BitacoraTutorDTO bitacoraTutorDTO = new BitacoraTutorDTO();
		bitacoraTutorDTO.setTutorDTO(tutor);
		bitacoraTutorDTO.setIdUsuarioFidegar(idUsuarioFidegar);
		bitacoraTutorDTO.setFechaCaptura(new Date());
		bitacoraTutorDTO.setCatEstatusDTO(tutor.getCatEstatusDTO());
		
		bitacoraTutorDAO.guardar(bitacoraTutorDTO);
		
		return true;
	}
	
	public RespuestaDTO envioPagatodo(TutorDTO tutor) {
		ObtenMasToken obtenMasToken = new ObtenMasToken();
		ObtenMasRegistrarTutor obtenMasRegistro = new ObtenMasRegistrarTutor();
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta = obtenMasRegistro.registrarSoloTutor(obtenMasToken.obtenerToken(), tutor);
		return respuesta;

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean actualizaContacto(TutorDTO tutor, UserDTO user, Long idUsuarioLlave) {
		boolean exito = true;
		try {
			BitacoraCambioContactoDTO bitacora = new BitacoraCambioContactoDTO();			
			bitacora.setIdUsuarioLlaveCdmx(tutor.getIdUsuarioLlaveCdmx());
			bitacora.setIdUsuarioFidegar(idUsuarioLlave);
			bitacora.setCorreoAnterior(tutor.getCorreo());
			bitacora.setCorreoNuevo(user.getCorreo());
			bitacora.setTelefonoAnterior(tutor.getTelefono());
			bitacora.setTelefonoNuevo(user.getTelefono());
			bitacora.setFechaModificacion(new Date());
			bitacora.setEnviadoAPagatodo(false);
			bitacoraCambioContactoDAO.guardarBitacora(bitacora);
			
			tutor.setTelefono(user.getTelefono());
			tutor.setCorreo(user.getCorreo());
			tutorDAO.actualizaCorreoTelefono(tutor);

		} catch (Exception ex) {
			exito = false;
			LOGGER.error("Error al actualizar datos de contacto : " + tutor.toString(), ex);
		}
		return exito;
	}
}
