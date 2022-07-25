package mx.gob.cdmx.adip.widget.runnable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.utils.StringUtils;
import mx.gob.cdmx.adip.widget.application.WidgetBean;

public class FechaActualRunnable implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(FechaActualRunnable.class);
	
	private final WidgetBean widgetBean;
	private LocalDate fechaWidget;
	
	public FechaActualRunnable(final WidgetBean widgetBean) {
		this.widgetBean = widgetBean; 
	}
	
	@Override
	public void run() {
		try {
			LocalDate currentdate = LocalDate.now();
			// Se checa si ya es el día siguiente, entonces se debe actualizar la fecha que se muestra en el widget
			if(fechaWidget == null || fechaWidget.isBefore(currentdate) ) {
				fechaWidget = currentdate;
				asignaFechaActualWidget();
				LOGGER.info("------------------> WIDGET: Se actualizó la fecha ");
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en FechaActualRunnable:", e);
		}
	}
	
	private void asignaFechaActualWidget() {
		 LocalDate currentdate = LocalDate.now();
		 Locale spanishLocale = new Locale("es", "ES");		
		 String day = currentdate.format(DateTimeFormatter.ofPattern("dd", spanishLocale));
		 String month = currentdate.format(DateTimeFormatter.ofPattern("MMMM", spanishLocale));
		 String year = currentdate.format(DateTimeFormatter.ofPattern("yyyy", spanishLocale));
		 
		 widgetBean.getSeccionIndicadores().setFecha("Hoy "+day+" de "+StringUtils.capitalizeFirstLetter(month)+" del "+year );	 
	}

}
