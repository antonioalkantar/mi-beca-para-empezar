package mx.gob.cdmx.adip.beca.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que sirve para guardar el resultado de un query paginado y utilizarlo en una tabla (p:dataTable)
 * @author Raúl SG
 * @param <T> El tipo de objeto que alojará la lista
 */
@SuppressWarnings("serial")
public class ListPaginador<T> extends ArrayList<T> implements Serializable {
	
    private final List<T> rows;
    private final int total;
    private int pageSize;

	/**
     * Constructor de la Clase
     * @param rows La lista de objetos, es decir, los renglones a iterar en una tabla que contenga un paginador
     * @param total El total de registros que debe mostrar la tabla. Por ejemplo, si el total es 50 y el pageSize es 10, entonces el paginador de la tabla mostrará 5 hojas.
     * @param pageSize El tamaño de cada hoja del paginador, es decir, el múmero de renglones
     */
    public ListPaginador(List<T> rows, int total, int pageSize){
        this.pageSize = pageSize;
        this.rows = rows;
        this.total = total;
    }

	/**
     * Método sobreescrito del ArrayList que será llamado por un p:dataTable y que obtendrá el renglón de datos solicitado.
     */
    @Override
    public T get(int index) {
        index = index%pageSize;
        /**
         * En las versiones de primefaces anteriores (Como primefaces 7), el datatable funcionaba bien regresando el "index" con el cálculo  index = index%pageSize;
         * Sin embargo, por alguna razón en la versión de primefaces 11, cuando se da clic en alguna opción de la tabla para ejecutar alguna acción, la datatable vuelve a pedir los index del 0 al 8 (como si pidiera el listado de la primer página)
         * no importando si está en la página 2, 3 o N del paginado de la tabla. Entonces, si se está en la última página con menos registros que en una página llena, marca IndexOutOfBoundsException dado que la lista actual no tiene tantos elementos.
         * 
         * Por esa razón, se tuvo que hacer este parche para que si en el evento de dar clic a alguna opción sobre la tabla itera una lista pensando que viene completa no marque dicho error.
         */
        T t = null;
        try {
        	t = rows.get(index);
        }catch(java.lang.IndexOutOfBoundsException e) {
        	t = rows.get(0);
        	//Catch sigiloso, no se requiere enviar al log la excepción.
        }
        return t;
    }

    /**
     *  Método sobreescrito del ArrayList que nos da el tamaño ficticio de la lista, es decir, el tamaño total de resultados de una consulta sin paginar.
     *  Esto sirve para decirle al usuario por ejemplo que se encontraron 1000 registros pero en sí la lista solo tiene 10 registros
     *   Si se desea obtener el tamaño real de esta lista ver el método sizeReal();
     */
    @Override
    public int size() {
        return total;
    }
    
    @Override
    public boolean isEmpty(){
    	return total == 0;
    }
    
    /**
     * Método que obtiene el tamaño real de la lista
     * @return
     */
    public int sizeReal(){
    	return rows.size();
    }

    /**
     * Método que devuelve la lista genérica que se pasó en el constructor.
     * @return
     */
    public List<T> getRows(){
    	return rows;
    }
}
