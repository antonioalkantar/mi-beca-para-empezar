package mx.gob.cdmx.adip.beca.commons.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class IBaseDAO<E, ID> {
	
	@Inject
	protected EntityManager em;
	
	public abstract E buscarPorId(ID id);
	
	public abstract List<E> buscarTodos();
	
	public abstract List<E> buscarPorCriterios(E e);
	
	public abstract void actualizar(E e);
	
	public abstract void guardar(E e);
}