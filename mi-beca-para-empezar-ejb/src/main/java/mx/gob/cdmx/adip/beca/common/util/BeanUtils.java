package mx.gob.cdmx.adip.beca.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
        if (date == Constantes.OBJETO_NULO) {
            return null;
        } else {
            return new Date(date.getTime());
        }
    }

    public static <T> T[] copyArray(T[] array) {
        if (array == Constantes.OBJETO_NULO) {
            return null;
        } else {
            return Arrays.copyOf(array, array.length);
        }
    }

    public static boolean isDiferent(Integer number, int value) {
        if (number == Constantes.OBJETO_NULO) {
            return false;
        }
        return number.intValue() != value;
    }

    public static boolean isDiferent(Long number, int value) {
        if (number == Constantes.OBJETO_NULO) {
            return false;
        }
        return number.longValue() != value;
    }

    public static boolean isEquals(Integer number, int value) {
        if (number == Constantes.OBJETO_NULO) {
            return false;
        }
        return number.intValue() == value;
    }

    public static Integer getInteger(Object number) {
        if (number == Constantes.OBJETO_NULO || !(number instanceof Number)) {
            return null;
        }
        return ((Number)number).intValue();
    }
    
    public static Long getLong(Object number) {
        if (number == Constantes.OBJETO_NULO || !(number instanceof Number)) {
            return null;
        }
        return ((Number)number).longValue();
    }
    
    /**
     * Función que convierte un numero double a int
     */
    public static int toInt(double numero){
        return (int)numero;
    }
    
    /**
     * Función que evalua una condición ternaria 
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
     * Función que convierte un Date a String con formato dd/MM/yyyy
     *
     */
    
    public static String convertirDateString(final Date fecha) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fecha);
    }
    
    /**
     * Función que convierte un Date a String con formato yyyy-MM-dd
     *
     */
    
    public static String convertirDateStringAnioMesDia(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(fecha);
    }

    public static String convertirDateStringAAMMDD(final Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        return sdf.format(fecha);
    }

    public static String agregarCerosTotalOcho(String numero) {
    	int totalDigitos = 8;
    	String ceros = "";
    	for(int i = numero.length(); i < totalDigitos; i++ ) {
    		ceros += "" + 0;
    	}
    	return ceros + numero;
    }
    
    public static String agregarConsecutivoX() {
    	int totalDigitos = 8;
    	String temporalFlo = "";
    	for(int i = 0; i < totalDigitos; i++ ) {
    		temporalFlo += "X";
    	}
    	return temporalFlo;
    }

    public static String agregarCerosTotalDiez(String numero) {
    	int totalDigitos = 10;
    	String ceros = "";
    	for(int i = numero.length(); i < totalDigitos; i++ ) {
    		ceros += "" + 0;
    	}
    	return ceros + numero;
    }

    public static String agregarCerosTotalNueve(String numero) {
    	int totalDigitos = 9;
    	String ceros = "";
    	for(int i = numero.length(); i < totalDigitos; i++ ) {
    		ceros += "" + 0;
    	}
    	return ceros + numero;
    }
    
    public static String cambiarXporConsecutivo(String folioConX, String consecutivo) {
    	return folioConX.replace("XXXXXXXX", consecutivo);
    }
    
    public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
	}

    public static String generaFolioBeneficiario(Long idSolicitud) {
		String folio = "";
		folio =  Constantes.ID_CLAVE_FOLIO_SOLICITUD + Constantes.SEPARADOR_FOLIO + agregarCerosTotalNueve(idSolicitud.toString());
		return folio;
	}

//    public static void main(String args[]) {
//    	String x = "ABCD-878-969-XXXXXXXX";
//    	String ceros = "00000008";
//    	
//    	x.replace(ceros,"XXXXXXXX");
//    	System.out.println(x.replace("XXXXXXXX", ceros));
//    }
}
