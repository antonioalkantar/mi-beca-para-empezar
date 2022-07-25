package mx.gob.cdmx.adip.beca.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

public class JPQL implements Serializable {

	private static final long serialVersionUID = 961703738988388954L;
	
	private final  StringBuilder jpqlQuery;
    private final  HashMap<String, Object> params;

	public JPQL() {
        this.jpqlQuery = new StringBuilder();
        this.params = new HashMap<>();
    }

    public JPQL(String jpql) {
        this.jpqlQuery = new StringBuilder(jpql);
        this.params = new HashMap<>();
    }
    
    public JPQL(String jpql, HashMap<String, Object> params) {
        this.jpqlQuery = new StringBuilder(jpql);
        this.params = params;
    }

    public JPQL append(boolean expression, String jpqlFragment, String paramName, Object paramValue) {
        if (expression) {
            append(jpqlFragment, paramName, paramValue);
        }
        return this;
    }

    public JPQL append(String jpqlFragment, String paramName, Object paramValue) {
        this.jpqlQuery.append(Constantes.ESPACIO + jpqlFragment + Constantes.ESPACIO);
        this.params.put(paramName, paramValue);
        return this;
    }

    public JPQL append(String string) {
        this.jpqlQuery.append(string);
        return this;
    }

    public void setParameters(Query query) {
    	for (Map.Entry<String,Object> entry : params.entrySet()) {
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
    }    

    public static <T> T getSingleResult(List<T> result, T defaultValue) {
        if (result == Constantes.OBJETO_NULO || result.isEmpty()) {
            return defaultValue;
        }
        return result.get(Constantes.FIRST_INDEX_LIST);
    }

    public static <T> T getSingleResult(List<T> result) {
        return getSingleResult(result, null);
    }
    
    public HashMap<String, Object> getParams() {
		return params;
	}
    
    @Override
    public String toString() {
        return jpqlQuery.toString();
    }
}