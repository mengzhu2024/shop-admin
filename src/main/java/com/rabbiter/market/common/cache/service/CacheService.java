package com.rabbiter.market.common.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CacheService {
    @Autowired
    public Map<String, Object> cache;

    public <T> void setCacheObject(final String key, final T value) {
        cache.put(key, value);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return cache.containsKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        return (T) cache.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public void deleteObject(final String key) {
        cache.remove(key);
    }
}
