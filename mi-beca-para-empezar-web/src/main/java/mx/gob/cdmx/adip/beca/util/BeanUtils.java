package mx.gob.cdmx.adip.beca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

public final class BeanUtils {

	private BeanUtils() {
	}

	public static boolean isNull(final Object object) {
		return object == null;
	}

	public static boolean isNotNull(final Object object) {
		return !isNull(object);
	}

	public static boolean isFalse(boolean expresion) {
		return !expresion;
	}

	public static boolean isEmpty(String string) {
		return isNull(string) || string.isEmpty();
	}

	public static boolean isNotEmpty(String string) {
		return isNotNull(string) && !string.isEmpty();
	}

	public static boolean isEmpty(List<?> list) {
		boolean output = true;
		if (isNotNull(list)) {
			output = list.isEmpty();
		}
		return output;
	}

	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	public static boolean isEmpty(Set<?> set) {
		boolean output = true;
		if (isNotNull(set)) {
			output = set.isEmpty();
		}
		return output;
	}

	public static boolean isEmptyOtro(List<?> list) {
		boolean output = false;
		if (isNotNull(list)) {
			output = list.isEmpty();
		}
		return output;
	}

	public static boolean isNotEmpty(Set<?> set) {
		return !isEmpty(set);
	}

	public static Date copyDate(Date date) {
		if (date == Constantes.NULL) {
			return null;
		} else {
			return new Date(date.getTime());
		}
	}

	public static <T> T[] copyArray(T[] array) {
		if (array == Constantes.NULL) {
			return null;
		} else {
			return Arrays.copyOf(array, array.length);
		}
	}

	public static boolean isDiferent(Integer number, int value) {
		if (number == Constantes.NULL) {
			return false;
		}
		return number.intValue() != value;
	}

	public static boolean isDiferent(Long number, int value) {
		if (number == Constantes.NULL) {
			return false;
		}
		return number.longValue() != value;
	}

	public static boolean isEquals(Integer number, int value) {
		if (number == Constantes.NULL) {
			return false;
		}
		return number.intValue() == value;
	}

	public static Integer getInteger(Object number) {
		if (number == Constantes.NULL || !(number instanceof Number)) {
			return null;
		}
		return ((Number) number).intValue();
	}

	public static Long getLong(Object number) {
		if (number == Constantes.NULL || !(number instanceof Number)) {
			return null;
		}
		return ((Number) number).longValue();
	}

	/**
	 * Funcion que convierte un numero double a int
	 */
	public static int toInt(double numero) {
		return (int) numero;
	}

	/**
	 * Funcion que evalua una condicion ternaria
	 *
	 */

	public static <T> T evaluacionTernaria(boolean expresion, T valueTrue, T valueFalse) {
		if (expresion) {
			return valueTrue;
		} else {
			return valueFalse;
		}
	}

	/**
	 * Funci\u00f3n que convierte un Date a String con formato dd/MM/yyyy
	 *
	 */

	public static String convertirDateString(final Date fecha) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		return sdf.format(fecha);
	}

	/**
	 * Funci\u00f3n que convierte un Date a String con formato yyyy-MM-dd
	 *
	 */

	public static String convertirDateStringAnioMesDia(final Date fecha) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		return sdf.format(fecha);
	}
	
	 /**
     * Funcion para convertir un String en fecha (Date).
     * @param fecha Cadena de fecha yyyy-MM-dd HH:mm:ss.SSS
     * @return Objeto Date
     */
    public static Date convertirStringDate(String fecha){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex){
            System.out.println(ex);
        }
        return fechaDate;
    }
    
    /**
     * Funcion para convertir una fecha (Date).
     * @param fecha Cadena de fecha EEEE d 'de' MMMM 'de' yyyy
     * @return Objeto Date
     */
    public static String convertirStringDateMx(final Date fecha){
        final SimpleDateFormat formatoEsMX = new SimpleDateFormat(
              "EEEE d 'de' MMMM 'de' yyyy", new Locale("ES", "MX"));       
        return formatoEsMX.format(fecha);
    }
    

    /**
     * Funcion para convertir un Date a Calendar 
     * @param date
     * @return
     */
  	public static Calendar convertirDateCalendar(Date date) {
  		Calendar calendar = Calendar.getInstance();
  		calendar.setTime(date);
  		return calendar;
  	}
  	
  	
	/**
	 * Funci\u00f3n que convierte un Date a String con formato dd-MM-yyyy
	 *
	 */
	public static String convertirDateStringDiaMesAnio(final Date fecha) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		return sdf.format(fecha);
	}
	
	public static Date sumarAniosFecha(Date fecha, int anios) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.YEAR, anios);
		return calendar.getTime();
	}
	
	

}