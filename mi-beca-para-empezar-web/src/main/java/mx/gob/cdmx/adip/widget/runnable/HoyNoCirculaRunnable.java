package mx.gob.cdmx.adip.widget.runnable;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.widget.indicadoresDTO.HoyNoCirculaDTO;
import mx.gob.cdmx.adip.widget.application.WidgetBean;
import mx.gob.cdmx.adip.widget.client.HoyNoCirculaClient;

public class HoyNoCirculaRunnable implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(HoyNoCirculaRunnable.class);
	
	private final WidgetBean widgetBean;
	private LocalDate fechaWidget;
	
	public HoyNoCirculaRunnable(final WidgetBean widgetBean) {
		this.widgetBean = widgetBean; 
	}
	
	@Override
	public void run() {
		try {
			LocalDate currentdate = LocalDate.now();
			/*
			 *  Se checa si ya es el día siguiente, entonces se debe actualizar el programa HoyNoCircula. 
			 *  Es decir no tiene sentido estar checando a cada rato dado que es el mismo valor hasta el día siguiente
			 */
			if(fechaWidget == null || fechaWidget.isBefore(currentdate) ) {
				fechaWidget = currentdate;
				HoyNoCirculaClient hoyNoCirculaClient = new HoyNoCirculaClient();
				HoyNoCirculaDTO hoyNoCirculaDTO = hoyNoCirculaClient.consulatHoyNoCircula();
				widgetBean.getSeccionIndicadores().setHoyNoCircula(hoyNoCirculaDTO);
				LOGGER.info("------------------> WIDGET: Se actualizó correctamente en el portal el hoy no circula");
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en HoyNoCirculaRunnable:", e);
		}
	}
}
