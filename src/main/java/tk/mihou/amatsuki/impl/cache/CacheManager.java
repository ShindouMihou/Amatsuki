package tk.mihou.amatsuki.impl.cache;

import tk.mihou.amatsuki.impl.cache.entities.CacheEntity;
import tk.mihou.amatsuki.impl.cache.enums.CacheTypes;

import java.util.*;
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
    public static AtomicInteger ranking = new AtomicInteger(6);

    // The status of the CacheManager (DEFAULT)
    public static AtomicBoolean enabled = new AtomicBoolean(true);
    public static AtomicBoolean search = new AtomicBoolean(true);
    public static AtomicBoolean rankings = new AtomicBoolean(true);

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

    public static List<?> getList(String key){
        if(genericCache.containsKey(key)){
            if(genericCache.get(key).isList()){
                return (List<?>) genericCache.get(key).getList();
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

    public static void addCache(List<?> entity, String key, CacheTypes type){
        genericCache.put(key, new CacheEntity<>(entity, key, type.equals(CacheTypes.RANKINGS) ? ranking.get() : lifespan.get()));
    }

    public static <T> T replace(T entity, String key){
        if(genericCache.containsKey(key)){
            genericCache.get(key).replace(entity);
        } else {
            genericCache.put(key, new CacheEntity<>(entity, key));
        }

        return entity;
    }

    public static void replace(Collection<?> entity, String key){
        if(genericCache.containsKey(key)){
            genericCache.get(key).replace(entity);
        } else {
            genericCache.put(key, new CacheEntity<>(entity, key));
        }
    }

    public static void invalidate(String key){
        genericCache.remove(key);
    }

    public static void setLifespan(int span, TimeUnit time){
        lifespan.set(span); unit = time;
    }

    /**
     * Sets the lifespan of all other lists like rankings and search finder.
     * @param span the amount of time to cache, to set the timeunit, please use setLifespan(...)
     */
    public static void setRankings(int span){
        ranking.set(span);
    }

    public static void switchStatus(boolean enable){
        if(enable){
            enable(CacheTypes.DEFAULT, CacheTypes.RANKINGS, CacheTypes.SEARCH);
        } else {
            disable(CacheTypes.DEFAULT, CacheTypes.RANKINGS, CacheTypes.SEARCH);
        }
    }

    public static void enable(CacheTypes... cacheTypes){
        Arrays.stream(cacheTypes).forEach(ty -> {
            if(ty.equals(CacheTypes.DEFAULT) && !enabled.get())
                enabled.set(true);

            if(ty.equals(CacheTypes.RANKINGS) && !rankings.get())
                rankings.set(true);

            if(ty.equals(CacheTypes.SEARCH) && !search.get())
                search.set(true);
        });
    }

    public static void disable(CacheTypes... cacheTypes){
        Arrays.stream(cacheTypes).forEach(ty -> {
            if(ty.equals(CacheTypes.DEFAULT) && enabled.get())
                enabled.set(false);

            if(ty.equals(CacheTypes.RANKINGS) && rankings.get())
                rankings.set(false);

            if(ty.equals(CacheTypes.SEARCH) && search.get())
                search.set(false);
        });
    }

}
