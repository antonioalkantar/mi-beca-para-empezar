package mx.gob.cdmx.adip.widget.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.widget.indicadoresDTO.ServicioGralClimaDTO;
import mx.gob.cdmx.adip.widget.application.WidgetBean;
import mx.gob.cdmx.adip.widget.client.ServicioClimaCliente;

public class TemperaturaAireIndiceUVRunnable implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(TemperaturaAireIndiceUVRunnable.class);
	
	private final WidgetBean widgetBean;
	
	public TemperaturaAireIndiceUVRunnable(final WidgetBean widgetBean) {
		this.widgetBean = widgetBean; 
	}
	
	@Override
	public void run() {
		try {
			LOGGER.info("------------------> WIDGET: Intentando actualizar en el portal la temperatura, calidad del aire e indice UV... ");
			ServicioClimaCliente servicioConsultaClima = new ServicioClimaCliente();
			ServicioGralClimaDTO climaconsultaDTO = servicioConsultaClima.consultarClima();
			if(climaconsultaDTO.getServClimaDTO().getTemperaturaProm() != null && !climaconsultaDTO.getServClimaDTO().getTemperaturaProm().isEmpty()) {
				LOGGER.info("------------------> WIDGET: Se actualizó correctamente en el portal la temperatura, calidad del aire e indice UV ");
				climaconsultaDTO.getServClimaDTO().setTemperaturaFarenheit(convetirToFarenheit(Long.valueOf(climaconsultaDTO.getServClimaDTO().getTemperaturaProm())));
				widgetBean.getSeccionIndicadores().setServicioClima(climaconsultaDTO);	
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en TemperaturaAirteIndiceUVRunnable:", e);
		}
	}
	
	private String convetirToFarenheit(long centigrados) {
		return String.valueOf((centigrados * (9/5) + 32));
	}
}
