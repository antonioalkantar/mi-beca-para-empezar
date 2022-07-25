package mx.gob.cdmx.adip.beca.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Carlos
 */
public class Base64Util {
	
	private Base64Util() {
		/**Constructor privado para que no instanciar la clase */
	}

    public static String cifrarBase64(String a){
        return Base64.getEncoder().encodeToString(a.getBytes(StandardCharsets.UTF_8) );        
    }
    
    public static String descifrarBase64(String a){
    	return new String( Base64.getDecoder().decode(a) );
    }
}
