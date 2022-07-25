package mx.gob.cdmx.adip.widget.application;

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

import mx.gob.cdmx.adip.widget.indicadoresDTO.NotificacionDTO;
import mx.gob.cdmx.adip.widget.runnable.NotificacionesRunnable;

import org.omnifaces.cdi.Eager;

@Eager // Que se construya al iniciar el Wildfly
@Named("notificacionesBean")
@ApplicationScoped
public class NotificacionesBean implements Serializable{

	private static final long serialVersionUID = 582670487161852523L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(NotificacionesBean.class);

	private ScheduledExecutorService schedulerNotificaciones;
	
	private NotificacionDTO notificacionDTO;
	
	public NotificacionesBean() {
	}
	
	@PostConstruct
	public void init() {
		LOGGER.info("------------------> Notificaciones: Inicializando información en común para todos los usuarios de Notificaciones en el Header");
		NotificacionesRunnable notificacionesRunnable = new NotificacionesRunnable(this);
		schedulerNotificaciones = Executors.newSingleThreadScheduledExecutor();
		schedulerNotificaciones.scheduleAtFixedRate(notificacionesRunnable, 0, 15, TimeUnit.MINUTES);
	}

	@PreDestroy
	public void destroy() {
		try { schedulerNotificaciones.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerNotificaciones"); /*Catch silencioso */ }
	    
	}

	public NotificacionDTO getNotificacionDTO() {
		return notificacionDTO;
	}

	public void setNotificacionDTO(NotificacionDTO notificacionDTO) {
		this.notificacionDTO = notificacionDTO;
	}
}