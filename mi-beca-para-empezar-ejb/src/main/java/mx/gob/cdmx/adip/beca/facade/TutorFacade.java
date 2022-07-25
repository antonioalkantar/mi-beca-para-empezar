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

import mx.gob.cdmx.adip.beca.commons.dto.BitacoraTutorDTO;
import mx.gob.cdmx.adip.beca.commons.dto.CatEstatusDTO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.commons.utils.Constantes;
import mx.gob.cdmx.adip.beca.dao.BitacoraTutorDAO;
import mx.gob.cdmx.adip.beca.dao.TutorDAO;

@Stateless
@LocalBean
public class TutorFacade {
	
	@Inject
	private TutorDAO tutorDAO;
	
	@Inject 
	private BitacoraTutorDAO bitacoraTutorDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registroNuevoTutor(TutorDTO tutor) throws IOException {
		// 1. Primero se guardan los documentos en fileSystem, así si falla su guarado se evita que quede un registro inconsistente en  la BD, es decir, que exista en la BD el tutor pero hayamos perdido el documento

		// 1.1 Se guarda documento de Identificación Oficial
		if (tutor.getFileIdentifica() != null) {
			FileUtils.writeByteArrayToFile(new File(tutor.getArchivoIdentificacion()), tutor.getContentFileIdentifica());
		}
		// 1.2 Se guarda documento Comprobante de Domicilio
		if (tutor.getFileComprobanteDom() != null) {
			FileUtils.writeByteArrayToFile(new File(tutor.getArchivoComprobanteDomicilio()), tutor.getContentFileComprobanteDom());
		}
		
		// 2. Se guarda Tutor en BD
		tutorDAO.guardar(tutor);
		
		// 3. Se guarda en bitácora
		BitacoraTutorDTO bitacoraTutorDTO = new BitacoraTutorDTO();
		bitacoraTutorDTO.setTutorDTO(tutor);
		bitacoraTutorDTO.setFechaCaptura(new Date());
		bitacoraTutorDTO.setCatEstatusDTO(tutor.getCatEstatusDTO());
		bitacoraTutorDAO.guardar(bitacoraTutorDTO);
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

		// Se guardan los documentos en fileSystem
//			// Se guarda documento de Identificación Oficial
//			if (tutor.getFileIdentifica() != null) {
//				FileUtils.writeByteArrayToFile(new File(tutor.getArchivoIdentificacion()),
//						tutor.getContentFileIdentifica());
//			}
//			// Se guarda documento Comprobante de Domicilio
//			if (tutor.getFileComprobanteDom() != null) {
//				FileUtils.writeByteArrayToFile(new File(tutor.getArchivoComprobanteDomicilio()),
//						tutor.getContentFileComprobanteDom());
//			}
		return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean envioCorreccionTutor(TutorDTO tutor , Long idUsuarioFidegar) throws IOException {
		// 1. Se guardan los documentos en fileSystem, así si falla su guarado se evita que quede un registro inconsistente en  la BD, es decir, que exista en la BD el tutor pero hayamos perdido el documento

		// 1.1 Se verifica si el documento de Identificación se ha cargado nuevamente
		if (tutor.getFileIdentifica() != null) {

			// Se elimina el documento de identificación oficial
			FileUtils.forceDelete(new File(tutor.getArchivoIdentificacion().substring(0,
					tutor.getArchivoIdentificacion().lastIndexOf(Constantes.SEPARADOR_RUTA))));

			// Se guarda documento de Identificación Oficial
			FileUtils.writeByteArrayToFile(new File(tutor.getArchivoIdentificacion()),
					tutor.getContentFileIdentifica());
		}
		// 1.2 Se verifica si el documento Comprobante Domicilio se ha cargado nuevamente
		if (tutor.getFileComprobanteDom() != null) {

			// Se elimina el directorio de comrpobante de domicilio
			FileUtils.forceDelete(new File(tutor.getArchivoComprobanteDomicilio().substring(0,
					tutor.getArchivoComprobanteDomicilio().lastIndexOf(Constantes.SEPARADOR_RUTA))));

			// Se guarda documento Comprobante de Domicilio
			FileUtils.writeByteArrayToFile(new File(tutor.getArchivoComprobanteDomicilio()),
					tutor.getContentFileComprobanteDom());
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
}
