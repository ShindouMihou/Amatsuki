package tk.mihou.amatsuki.impl.cache.entities;

import tk.mihou.amatsuki.impl.cache.CacheManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CacheEntity<T> {

    private T entity;
    private List<T> list;
    private final long expected;
    private final String key;

    public CacheEntity(T entity, String key){
        this.entity = entity;
        this.key = key;
        this.expected = System.currentTimeMillis() + CacheManager.unit.toMillis(CacheManager.lifespan.get());
    }

    public CacheEntity(T entity, String key, int lifespan, TimeUnit unit){
        this.entity = entity;
        this.key = key;
        this.expected = System.currentTimeMillis() + unit.toMillis(lifespan);
    }

    public CacheEntity(List<T> entity, String key){
        this.list = entity;
        this.key = key;
        this.expected = System.currentTimeMillis() + CacheManager.unit.toMillis(CacheManager.lifespan.get());
    }

    public CacheEntity(List<T> entity, String key, int lifespan){
        this.list = entity;
        this.key = key;
        this.expected = System.currentTimeMillis() + CacheManager.unit.toMillis(lifespan);
    }

    public CacheEntity(List<T> entity, String key, int lifespan, TimeUnit unit){
        this.list = entity;
        this.key = key;
        this.expected = System.currentTimeMillis() + unit.toMillis(lifespan);
    }

    /**
     * Retrieves the entity hiding inside the cache.
     * This only works when cache is enabled.
     * @return an entity.
     */
     public T get(){
         return entity;
     }

     public List<T> getList(){
         return list;
     }

    /**
     * Checks the validity of the cache.
     * This only works when cache is enabled.
     * @return an entity.
     */
    public boolean isInvalid(){
        if((expected - System.currentTimeMillis()) <= 0){
            CacheManager.invalidate(key);
        }

        return (expected - System.currentTimeMillis()) <= 0;
    }

    /**
     * Replaces the entity inside the cache.
     * This only works when cache is enabled.
     */
    public void replace(T entity){
        this.entity = entity;
    }

    public boolean isList(){
        // We can only rely on this for now.
        // If you have a better solution, please do a PR.
        return list != null;
    }

    /**
     * This is used to validate whether the two types
     * are valid, to keep complete control over which types
     * we are returning.
     * @param type the type expected to return.
     * @return are they the same type?
     */
    public boolean sameType(Class type){
        return type.equals(entity.getClass());
    }

}
