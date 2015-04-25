package nz.co.scuff.server.util;

import java.util.List;

/**
 * Created by callumw on 9/2/2014.
 */
public interface AbstractFacadeInterface<T> {

    public void create(T entity);

    public void edit(T entity);

    public void remove(T entity);

    public T find(Object id);

    public List<T> findAll();

    public List<T> findRange(int[] range);

    public int count();

}