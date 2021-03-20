package tk.mihou.amatsuki.impl.cache;

import tk.mihou.amatsuki.impl.cache.entities.CacheEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheManager {

    /**
     * This is a generic CacheManager
     * which is why all the errors here can be ignored.
     */

    // The time unit to use for calculations.
    public static TimeUnit unit = TimeUnit.HOURS;

    // The lifespan of the objects.
    public static AtomicInteger lifespan = new AtomicInteger(1);

    // The status of the CacheManager.
    public static AtomicBoolean enabled = new AtomicBoolean(true);

    static final Map<String, CacheEntity> genericCache = new HashMap<>();

    public static <T> T getCache(Class expected, String key){
        if(genericCache.containsKey(key)){
            if(genericCache.get(key).sameType(expected)){
                return (T) genericCache.get(key).get();
            }
        }

        // We can't use optionals, so we use nulls instead.
        return null;
    }

    public static boolean isCached(String key){
        return genericCache.containsKey(key) && !genericCache.get(key).isInvalid();
    }

    public static <T> T addCache(T entity, String key){
        genericCache.put(key, new CacheEntity<>(entity, key));
        return entity;
    }

    public static <T> T replace(T entity, String key){
        if(genericCache.containsKey(key)){
            genericCache.get(key).replace(entity);
        } else {
            genericCache.put(key, new CacheEntity<>(entity, key));
        }

        return entity;
    }

    public static void invalidate(String key){
        genericCache.remove(key);
    }

    public static void setLifespan(int span, TimeUnit time){
        lifespan.set(span); unit = time;
    }

    public static void switchStatus(){
        enabled.set(!enabled.get());
    }

    public static void switchStatus(boolean enable){
        enabled.set(enable);
    }

}
