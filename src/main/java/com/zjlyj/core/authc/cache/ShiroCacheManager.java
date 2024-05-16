package com.zjlyj.core.authc.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class ShiroCacheManager implements CacheManager
{
    //TODO
    public static LRUCache cache = new LRUCache();

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException
    {
        return new ShiroCache<K, V>(cache);
    }
}
