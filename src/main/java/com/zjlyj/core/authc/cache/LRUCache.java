package com.zjlyj.core.authc.cache;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LRUCache
{
    //TODO over write
    //TODO capacity setting
    Cache<Object, Object> lruCache = CacheUtil.newLRUCache(30);

    /**
     * get cache
     *
     * @param key
     * @return
     */
    public Object get(Object key)
    {
        if (lruCache.containsKey(key)) {
            return lruCache.get(key);
        }
        return null;
    }

    /**
     * put cache
     *
     * @param key
     * @param value
     */
    public void put(Object key, Object value)
    {
        lruCache.put(key, value);
    }

    /**
     * remove cache
     *
     * @param key
     */
    public void remove(Object key)
    {
        if (lruCache.containsKey(key)) {
            lruCache.remove(key);
        }
    }

    /**
     * size
     * @return
     */
    public int size()
    {
        return lruCache.size();
    }

    /**
     * clear cache
     */
    public void clear()
    {
        lruCache.clear();
    }

    /**
     * get values
     *
     * @return
     */
    public Collection<Object> values()
    {
        List<Object> values = new ArrayList<>(lruCache.size());

        for(Object value : lruCache){
            values.add(value);
        }

        return values;
    }

    public static void main(String[] args)
    {
        LRUCache lruCache = new LRUCache();

        lruCache.put("a", "1");
        lruCache.put("b", "2");
        lruCache.put("c", "3");

        System.out.println(lruCache.get("a"));
        System.out.println(lruCache.get("b"));
        System.out.println(lruCache.get("c"));

        lruCache.remove("b");

        System.out.println(lruCache.get("a"));
        System.out.println(lruCache.get("b"));
        System.out.println(lruCache.get("c"));

        lruCache.values().forEach(System.out::println);
    }
}