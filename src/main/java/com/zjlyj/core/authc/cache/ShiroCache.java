package com.zjlyj.core.authc.cache;

import com.zjlyj.core.authc.ShiroAuthContants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;



public class ShiroCache<K, V> implements Cache<K, V> {


  private LRUCache cache;

  public ShiroCache(LRUCache cache) {
    this.cache = cache;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object get(Object k) throws CacheException {
    return cache.get(genKey(k));
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object put(Object k, Object v) throws CacheException {
    cache.put(genKey(k), v);
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object remove(Object k) throws CacheException {
    cache.remove(genKey(k));
    return null;
  }

  @Override
  public void clear() throws CacheException {
    cache.clear();
  }

  @Override
  public int size() {
    return cache.size();
  }

  @Override
  public Set<K> keys() {
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<V> values() {
    List<V> values = new ArrayList<>(cache.size());
    cache.values().forEach(value -> values.add((V) value));
    return values;
  }

  private String genKey(Object key) {
    return ShiroAuthContants.PREFIX_SHIRO_CACHE + key.toString();
  }
}