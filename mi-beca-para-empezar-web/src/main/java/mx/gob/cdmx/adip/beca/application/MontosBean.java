package mx.gob.cdmx.adip.beca.application;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.dto.CatMontoApoyoDTO;
import mx.gob.cdmx.adip.beca.runnable.MontosRunnable;
import mx.gob.cdmx.adip.widget.runnable.NotificacionesRunnable;

import org.omnifaces.cdi.Eager;

@Eager // Que se construya al iniciar el Wildfly
@Named("monstosBean")
@ApplicationScoped

public class MontosBean implements Serializable{
	
	private static final long serialVersionUID = 7319700161989223734L;

	private static Logger LOGGER = LoggerFactory.getLogger(MontosBean.class);

	private ScheduledExecutorService schedulerNotificaciones;
	
	private CatMontoApoyoDTO preescolar;
	private CatMontoApoyoDTO primaria;
	private CatMontoApoyoDTO CentroAtencionMultiple;
	private String cicloEscolar;
	
	public MontosBean() {
	}
	
	@PostConstruct
	public void init() {
		LOGGER.info("------------------> Montos: Inicializando información en común para todos los usuarios de Monto Apoyos");
		MontosRunnable montosRunnable = new MontosRunnable(this);
		schedulerNotificaciones = Executors.newSingleThreadScheduledExecutor();
		schedulerNotificaciones.scheduleAtFixedRate(montosRunnable, 0, 24, TimeUnit.HOURS);
	}

	@PreDestroy
	public void destroy() {
		try { schedulerNotificaciones.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerMontos"); /*Catch silencioso */ }
	    
	}

	public CatMontoApoyoDTO getPreescolar() {
		return preescolar;
	}

	public void setPreescolar(CatMontoApoyoDTO preescolar) {
		this.preescolar = preescolar;
	}

	public CatMontoApoyoDTO getPrimaria() {
		return primaria;
	}

	public void setPrimaria(CatMontoApoyoDTO primaria) {
		this.primaria = primaria;
	}

	public CatMontoApoyoDTO getCentroAtencionMultiple() {
		return CentroAtencionMultiple;
	}

	public void setCentroAtencionMultiple(CatMontoApoyoDTO centroAtencionMultiple) {
		CentroAtencionMultiple = centroAtencionMultiple;
	}

	public String getCicloEscolar() {
		return cicloEscolar;
	}

	public void setCicloEscolar(String cicloEscolar) {
		this.cicloEscolar = cicloEscolar;
	}
	
	
}
