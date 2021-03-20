package tk.mihou.amatsuki.impl.cache.entities;

import tk.mihou.amatsuki.impl.cache.CacheManager;

public class CacheEntity<T> {

    private T entity;
    private final long expected;
    private final String key;

    public CacheEntity(T entity, String key){
        this.entity = entity;
        this.key = key;
        this.expected = System.currentTimeMillis() + CacheManager.unit.toMillis(CacheManager.lifespan.get());
    }

    /**
     * Retrieves the entity hiding inside the cache.
     * This only works when cache is enabled.
     * @return an entity.
     */
     public T get(){
         return entity;
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
