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

import mx.gob.cdmx.adip.widget.indicadoresDTO.SeccionIndicadoresDTO;
import mx.gob.cdmx.adip.widget.runnable.ClimaRunnable;
import mx.gob.cdmx.adip.widget.runnable.FechaActualRunnable;
import mx.gob.cdmx.adip.widget.runnable.HoyNoCirculaRunnable;
import mx.gob.cdmx.adip.widget.runnable.SemaforoCovidRunnable;
import mx.gob.cdmx.adip.widget.runnable.TemperaturaAireIndiceUVRunnable;

import org.omnifaces.cdi.Eager;

@Eager // Que se construya al iniciar el Wildfly
@Named("widgetBean")
@ApplicationScoped
public class WidgetBean implements Serializable{

	private static final long serialVersionUID = 582670487161852523L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(WidgetBean.class);

	private ScheduledExecutorService schedulerFecha;
	
	private ScheduledExecutorService schedulerTemperaturaAireIndiceUV;
	
	private ScheduledExecutorService schedulerClima;
	
	private ScheduledExecutorService schedulerSemaforoCovid;
	
	private ScheduledExecutorService schedulerHoyNoCircula;
	
	private SeccionIndicadoresDTO seccionIndicadores = new SeccionIndicadoresDTO();
	
	public WidgetBean() {
	}
	
	@PostConstruct
	public void init() {
		LOGGER.info("------------------> WIDGET: Inicializando información en común para todos los usuarios que usen el Widget de clima, temperatura, Hoy No Circula");
		
		// 1. Se revisa la fecha a mostrar en el Widget cada minuto, si hay cambio de día se actualiza el string con la fecha
		FechaActualRunnable fechaActualRunnable = new FechaActualRunnable(this);
	    schedulerFecha = Executors.newSingleThreadScheduledExecutor();
	    schedulerFecha.scheduleAtFixedRate(fechaActualRunnable, 0, 1, TimeUnit.MINUTES);
	    
	    // 2. Se actualiza la temperatura, calidad del aire e índice UV cada hora ya que esta información no cambia seguido
	    TemperaturaAireIndiceUVRunnable temperaturaAirteIndiceUVRunnable = new TemperaturaAireIndiceUVRunnable(this);
	    schedulerTemperaturaAireIndiceUV = Executors.newSingleThreadScheduledExecutor();
	    schedulerTemperaturaAireIndiceUV.scheduleAtFixedRate(temperaturaAirteIndiceUVRunnable, 0, 1, TimeUnit.HOURS);
	    
	    /* 3. Se actualiza el clima con el WS de OpenWeather. 
	     * Recordar que este servicio online restringe el num de peticiones por min y por día
	     * por eso la importancia de hacer pocas consultas contemplando que se usa el mismo APPID para todos los sistemas de la ADIP.
	     * Por esta razón solo se realizará la consulta del clima cada 2 hrs
	     */
	    ClimaRunnable climaRunnable = new ClimaRunnable(this);
	    schedulerClima = Executors.newSingleThreadScheduledExecutor();
	    schedulerClima.scheduleAtFixedRate(climaRunnable, 0, 2, TimeUnit.HOURS);
	    
	    SemaforoCovidRunnable semaforoRunnable = new SemaforoCovidRunnable(this);
	    schedulerSemaforoCovid = Executors.newSingleThreadScheduledExecutor();
	    schedulerSemaforoCovid.scheduleAtFixedRate(semaforoRunnable, 0, 1, TimeUnit.HOURS);
	 
	    HoyNoCirculaRunnable hoyNoCirculaRunnable = new HoyNoCirculaRunnable(this); 
	    schedulerHoyNoCircula = Executors.newSingleThreadScheduledExecutor();
	    schedulerHoyNoCircula.scheduleAtFixedRate(hoyNoCirculaRunnable, 0, 1, TimeUnit.MINUTES);
	    
	}

	@PreDestroy
	public void destroy() {
		try { schedulerFecha.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerFecha"); /*Catch silencioso */ }
		try { schedulerTemperaturaAireIndiceUV.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerTemperaturaAireIndiceUV"); /*Catch silencioso */ }
		try { schedulerClima.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerClima"); /*Catch silencioso */ }
		try { schedulerSemaforoCovid.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerSemaforoCovid"); /*Catch silencioso */ }
		try { schedulerHoyNoCircula.shutdownNow(); } catch (Exception e) {  LOGGER.warn("No se pudo apagar el schedulerHoyNoCircula"); /*Catch silencioso */ }
	    
	}
	
	public SeccionIndicadoresDTO getSeccionIndicadores() {
		return seccionIndicadores;
	}
}