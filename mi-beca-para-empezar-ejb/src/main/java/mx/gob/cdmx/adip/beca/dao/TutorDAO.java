package mx.gob.cdmx.adip.beca.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.cdmx.adip.beca.commons.dao.IBaseDAO;
import mx.gob.cdmx.adip.beca.commons.dto.TutorDTO;
import mx.gob.cdmx.adip.beca.model.CatAsentamientos;
import mx.gob.cdmx.adip.beca.model.CatComprobanteDomicilio;
import mx.gob.cdmx.adip.beca.model.CatEstatus;
import mx.gob.cdmx.adip.beca.model.CatIdentificacionOficial;
import mx.gob.cdmx.adip.beca.model.CatMunicipios;
import mx.gob.cdmx.adip.beca.model.CatTipoIne;
import mx.gob.cdmx.adip.beca.model.Tutor;

@Stateless
@LocalBean
public class TutorDAO extends IBaseDAO<TutorDTO, Long> {

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public TutorDTO buscarPorId(Long id) {
		List<TutorDTO> listado = em.createNamedQuery("Tutor.findByIdLlave", TutorDTO.class)
				.setParameter("idUsuarioLlave", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	public List<TutorDTO> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TutorDTO> buscarPorCriterios(TutorDTO e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(TutorDTO e) {
		// TODO Auto-generated method stub

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void guardar(TutorDTO t) {
		Tutor tutor = new Tutor();
		tutor.setIdUsuarioLlaveCdmx(t.getIdUsuarioLlaveCdmx());
		tutor.setNombre(t.getNombre());
		tutor.setPrimerApellido(t.getPrimerApellido());
		tutor.setSegundoApellido(t.getSegundoApellido());
		tutor.setCurp(t.getCurp());
		tutor.setTelefono(
				t.getTelefono() != null && !t.getTelefono().isEmpty() ? t.getTelefono().replaceAll(" ", "") : null);
		tutor.setCorreo(t.getCorreo());
		tutor.setCatIdentificacionOficial(em.getReference(CatIdentificacionOficial.class,
				t.getCatIdentificacionOficialDTO().getIdIdentificacion()));
		tutor.setArchivoIdentificacion(t.getArchivoIdentificacion());
		tutor.setOcr(t.getOcr());
		tutor.setCalle(t.getCalle());
		tutor.setNumInt(t.getNumInt());
		tutor.setNumExt(t.getNumExt());
		tutor.setCodigoPostal(t.getCodigoPostal());
		tutor.setCatComprobanteDomicilio(em.getReference(CatComprobanteDomicilio.class,
				t.getCatComprobanteDomicilioDTO().getIdComprobanteDomicilio()));
		tutor.setArchivoComprobanteDomicilio(t.getArchivoComprobanteDomicilio());
		tutor.setCatMunicipios(em.getReference(CatMunicipios.class, t.getCatMunicipiosDTO().getIdMunicipio()));
		tutor.setCatAsentamientos(
				em.getReference(CatAsentamientos.class, t.getCatAsentamientosDTO().getIdAsentamiento()));
		tutor.setCic(t.getCic());
		tutor.setClaveElector(t.getClaveElector());
		tutor.setNumeroEmision(t.getNumeroEmision());
		tutor.setNacionalidad(t.getNacionalidad());
		tutor.setCatEstatus(em.getReference(CatEstatus.class, t.getCatEstatusDTO().getIdEstatus()));
		tutor.setCatTipoIne(
				t.getCatTipoIneDTO() != null ? em.getReference(CatTipoIne.class, t.getCatTipoIneDTO().getIdTipoIne())
						: null);
		tutor.setEsExtranjero(t.isEsExtranjero());
		tutor.setDocumentoLlave(t.isDocumentoLlave());
		tutor.setIdDocumentoLlave(t.getIdDocumentoLlave());
		tutor.setLada(t.getLada());
		tutor.setEnviadoAPagatodo(t.isEnviadoAPagatodo());
		tutor.setSexo(t.getSexo());
		tutor.setFechaNacimiento(t.getFechaNacimiento());
		tutor.setFechaEnvioAPagatodo(t.getFechaEnvioAPagatodo());
		em.persist(tutor);
		em.flush();
	}

	public TutorDTO buscarPorCurp(String curp) {
		List<TutorDTO> listado = em.createNamedQuery("Tutor.findByCurp", TutorDTO.class).setParameter("curp", curp)
				.getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	public List<TutorDTO> buscarPorNombreCompleto(String nombreCompleto, String apellidoPaterno, String apellidoMaterno) {
		if (apellidoMaterno != null && apellidoMaterno.trim().length() > 0) {
			return em.createNamedQuery("Tutor.findByNombreAMaterno", TutorDTO.class)
					.setParameter("nombrecompleto", nombreCompleto.toUpperCase()+"%")
					.setParameter("apellidoPaterno", apellidoPaterno.toUpperCase()+"%")
					.setParameter("apellidoMaterno", apellidoMaterno.toUpperCase()+"%")
					.getResultList();			
		} else {			
			return em.createNamedQuery("Tutor.findByNombreAPaterno", TutorDTO.class)
					.setParameter("nombrecompleto", nombreCompleto.toUpperCase()+"%")
					.setParameter("apellidoPaterno", apellidoPaterno.toUpperCase()+"%")
					.getResultList();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaTutor(TutorDTO t) {
		Tutor tutor = new Tutor();
		tutor.setIdUsuarioLlaveCdmx(t.getIdUsuarioLlaveCdmx());
		tutor.setNombre(t.getNombre());
		tutor.setPrimerApellido(t.getPrimerApellido());
		tutor.setSegundoApellido(t.getSegundoApellido());
		tutor.setCurp(t.getCurp());
		tutor.setTelefono(
				t.getTelefono() != null && !t.getTelefono().isEmpty() ? t.getTelefono().replaceAll(" ", "") : null);
		tutor.setCorreo(t.getCorreo());
		tutor.setCatIdentificacionOficial(em.getReference(CatIdentificacionOficial.class,
				t.getCatIdentificacionOficialDTO().getIdIdentificacion()));
		tutor.setArchivoIdentificacion(t.getArchivoIdentificacion());
		tutor.setOcr(t.getOcr());
		tutor.setCalle(t.getCalle());
		tutor.setNumInt(t.getNumInt());
		tutor.setNumExt(t.getNumExt());
		tutor.setCodigoPostal(t.getCodigoPostal());
		tutor.setCatComprobanteDomicilio(em.getReference(CatComprobanteDomicilio.class,
				t.getCatComprobanteDomicilioDTO().getIdComprobanteDomicilio()));
		tutor.setArchivoComprobanteDomicilio(t.getArchivoComprobanteDomicilio());
		tutor.setCatMunicipios(em.getReference(CatMunicipios.class, t.getCatMunicipiosDTO().getIdMunicipio()));
		tutor.setCatAsentamientos(
				em.getReference(CatAsentamientos.class, t.getCatAsentamientosDTO().getIdAsentamiento()));
		tutor.setCic(t.getCic());
		tutor.setClaveElector(t.getClaveElector());
		tutor.setNumeroEmision(t.getNumeroEmision());
		tutor.setNacionalidad(t.getNacionalidad());
		tutor.setCatEstatus(em.getReference(CatEstatus.class, t.getCatEstatusDTO().getIdEstatus()));
		tutor.setCatTipoIne(
				t.getCatTipoIneDTO() != null ? em.getReference(CatTipoIne.class, t.getCatTipoIneDTO().getIdTipoIne())
						: null);
		tutor.setInformacionGralCorrecto(t.getInformacionGralCorrecto());
		tutor.setInformacionGeneralObservaciones(t.getInformacionGeneralObservaciones());
		tutor.setDomicilioCorrecto(t.getDomicilioCorrecto());
		tutor.setDomicilioObservaciones(t.getDomicilioObservaciones());
		tutor.setEsExtranjero(t.isEsExtranjero());
		tutor.setDocumentoLlave(t.isDocumentoLlave());
		tutor.setIdDocumentoLlave(t.getIdDocumentoLlave());
		tutor.setEsMayorTresBeneficiarios(t.isEsMayorTresBeneficiarios());
		tutor.setEsCasaHogar(t.isEsCasaHogar());
		tutor.setNombreCasaHogar(t.getNombreCasaHogar());
		tutor.setMotivoMayorTresBeneficiarios(t.getMotivoMayorTresBeneficiarios());
		tutor.setLada(t.getLada());
		em.merge(tutor);
		em.flush();
	}
	
	//Metodo para actualizar si el tutor desea registrar mas de tres beneficiarios
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean actualizaMasBeneficiarios(TutorDTO tutor) {
		em.createNamedQuery("Tutor.updateMasBeneficiarios")
			.setParameter("esMayorTresBeneficiarios", tutor.isEsMayorTresBeneficiarios())
			.setParameter("esCasaHogar", tutor.isEsCasaHogar())
			.setParameter("nombreCasaHogar", tutor.getNombreCasaHogar())
			.setParameter("motivoMayorTresBeneficiarios", tutor.getMotivoMayorTresBeneficiarios())
			.setParameter("idUsuarioLlaveCdmx", tutor.getIdUsuarioLlaveCdmx())
			.executeUpdate();
		return true;
	}
	
	/*
	 * Método para consultar CURP para generación automática
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<String> consultaHomonimia(String curp){
		List<String> listado = new ArrayList<>();
		List<String> lstHomonimias = em.createNamedQuery("Tutor.consultaHomonimias")
				.setParameter("curp", curp+"%")
				.getResultList();
		List<String> lstHomonimiasExtranjero = em.createNamedQuery("TutorExtranjero.consultaHomonimias")
				.setParameter("curp", curp+"%")
				.getResultList();
		listado.addAll(lstHomonimias);
		listado.addAll(lstHomonimiasExtranjero);
		return listado;
	}
	
	//Metodo para Actualizar el Estatus del tutor en la pantalla de Consultas
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaEstatusTutor(TutorDTO t) {
		final Tutor tutor = em.getReference(Tutor.class, t.getIdUsuarioLlaveCdmx());
		tutor.setCatEstatus(em.getReference(CatEstatus.class, t.getCatEstatusDTO().getIdEstatus()));
		em.merge(tutor);
		em.flush();
		
	}
	
	/*
	 * Método para actualizar correo y teléfono
	 */
	public void actualizaCorreoTelefono(TutorDTO tutor) {
		em.createNamedQuery("Tutor.updateCorreoTelefono")
		.setParameter("correo", tutor.getCorreo())
		.setParameter("telefono", tutor.getTelefono())
		.setParameter("fecha", new Date())
		.setParameter("idUsuarioLlaveCdmx", tutor.getIdUsuarioLlaveCdmx())
		.executeUpdate();
	}
	
	/*
	 * Metodo para consultar tutores no enviados a pagatodo
	 */
	public List<TutorDTO> consultaTutoresNoEnviados() {
		List<TutorDTO> listado = em.createNamedQuery("Tutor.findNoEnviados", TutorDTO.class).getResultList();
		return listado;
	}

	/*
	 * Metodo para Actualizar el campo enviado_a_pagatodo de tutor
	 * 
	 * @Parameter TutorDTO
	 * 
	 * @return true = exitoso
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaEnviadoAPagatodo(TutorDTO tutor) {
		em.createNamedQuery("Tutor.actualizaEnviadoAPagatodo")
		.setParameter("fecha", tutor.getFechaEnvioAPagatodo())
				.setParameter("idUsuarioLlaveCdmx", tutor.getIdUsuarioLlaveCdmx()).executeUpdate();
	}
	
}
