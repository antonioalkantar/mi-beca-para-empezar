package mx.gob.cdmx.adip.widget.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.widget.indicadoresDTO.ServicioSemaforoEpDTO;
import mx.gob.cdmx.adip.widget.application.WidgetBean;
import mx.gob.cdmx.adip.widget.client.ServicioEpidemiologicoClient;

public class SemaforoCovidRunnable implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(SemaforoCovidRunnable.class);
	
	private final WidgetBean widgetBean;
	
	public SemaforoCovidRunnable(final WidgetBean widgetBean) {
		this.widgetBean = widgetBean; 
	}
	
	@Override
	public void run() {
		try {
			ServicioEpidemiologicoClient servicioEpidemiologicoClient = new ServicioEpidemiologicoClient();
			ServicioSemaforoEpDTO servicioSemaforoEpDTO = servicioEpidemiologicoClient.consultaSemaforoEpidemiologico();
			if (servicioSemaforoEpDTO == null) {
				servicioSemaforoEpDTO = new ServicioSemaforoEpDTO();
				servicioSemaforoEpDTO.setNombre("------");
				servicioSemaforoEpDTO.setColor("#CCCCCC");
			}
			widgetBean.getSeccionIndicadores().setServicioSemaforoEpDTO(servicioSemaforoEpDTO);
			
			LOGGER.info("------------------> WIDGET: Se actualizó correctamente en el portal el semáforo epidemiológico");			
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en SemaforoCovidRunnable:", e);
		}
	}
}
