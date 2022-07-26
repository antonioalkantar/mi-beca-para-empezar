package mx.gob.cdmx.adip.beca.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author raul
 */
public class DateUtils {
	
	private static final DateFormat DATE_FORMAT_YMD_WITH_DASH = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat DATE_FORMAT_DMY_WITH_SLASH = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DATE_FORMAT_YMD_HMSS_WITH_DASH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final DateFormat DATE_FORMAT_LARGE_MX = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("ES", "MX"));
	
	private DateUtils() {
		/*Constructor privado para que no se pueda instanciar la clase*/
	}
	
	public static LocalDate convertDateToLocalDate(Date dateToConvert) {
	    //return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static int getEdadByFechaNacimiento(final Date fechaNacimiento) {
		if(fechaNacimiento == null || fechaNacimiento.after(new Date())){
			throw new IllegalArgumentException("No se puede calcular la edad si la fecha de nacimiento es nula o es una fecha a futuro: "+ (fechaNacimiento == null ? "null" : fechaNacimiento.toString())) ;
		}
		return Period.between(convertDateToLocalDate(fechaNacimiento), LocalDate.now()).getYears();
	}
	
	public static String convertDateToString(final Date date, final DateFormatStyle dateFormatStyle) {
		switch (dateFormatStyle) {
			case YMD_WITH_DASH:
				return DATE_FORMAT_YMD_WITH_DASH.format(date); 
			case DMY_WITH_SLASH:
				return DATE_FORMAT_DMY_WITH_SLASH.format(date);
			case YMD_HMSS_WITH_DASH:
				return DATE_FORMAT_YMD_HMSS_WITH_DASH.format(date);
			case LARGE_MX:
				return DATE_FORMAT_LARGE_MX.format(date);
			default:
				throw new IllegalArgumentException("DateFormatStyle no implementado para su conversión");
		}
	}
	
	public static Date convertStringToDate(final String date, final DateFormatStyle dateFormatStyle) throws ParseException {
		switch (dateFormatStyle) {
			case YMD_WITH_DASH:
				return DATE_FORMAT_YMD_WITH_DASH.parse(date); 
			case DMY_WITH_SLASH:
				return DATE_FORMAT_DMY_WITH_SLASH.parse(date);
			case YMD_HMSS_WITH_DASH:
				return DATE_FORMAT_YMD_HMSS_WITH_DASH.parse(date);
			case LARGE_MX:
				return DATE_FORMAT_LARGE_MX.parse(date);
			default:
				throw new IllegalArgumentException("DateFormatStyle no implementado para su conversión");
		}
	}
	
  	public static Calendar convertirDateCalendar(final Date date) {
  		Calendar calendar = Calendar.getInstance();
  		calendar.setTime(date);
  		return calendar;
  	}
  	
	public static Date sumarAniosFecha(final Date fecha, final int anios) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.YEAR, anios);
		return calendar.getTime();
	}
	
	public enum DateFormatStyle {
		YMD_WITH_DASH, DMY_WITH_SLASH, YMD_HMSS_WITH_DASH, LARGE_MX
	}
	
	public static void main(String[] args) throws ParseException {
//		System.out.println("Edad:"+ getEdadByFechaNacimiento( new Date(85, 6, 21) ) ); //Ejemplo: Hoy es 20/Jul/22, si nació el 21/jul/85 = 36años
//		System.out.println("Edad:"+ getEdadByFechaNacimiento( new Date(130, 6, 21) ) ); //Ejemplo: Hoy es 20/Jul/22, si nació el 21/jul/30 = 36años
//		System.out.println("Edad:"+ getEdadByFechaNacimiento( null ) );
//		System.out.println("Fecha:"+ convertDateToString( new Date(85, 6, 21), DateFormatStyle.DMY_WITH_SLASH ) );
//		System.out.println("Fecha:"+ convertDateToString( new Date(85, 6, 21), DateFormatStyle.YMD_WITH_DASH ) );
//		System.out.println("Fecha:"+ convertDateToString( new Date(85, 6, 21), DateFormatStyle.YMD_HMSS_WITH_DASH ) );
//		System.out.println("Fecha:"+ convertDateToString( new Date(85, 6, 21), DateFormatStyle.LARGE_MX ) );
//		System.out.println("Fecha:"+ convertStringToDate( "2022-07-21 18:10:20.1111", DateFormatStyle.YMD_HMSS_WITH_DASH ) );
//		System.out.println("Fecha:"+ convertStringToDate( "domingo 21 de julio de 1985", DateFormatStyle.LARGE_MX ) );
	}
}
