package com.springapp.mvc.repository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhangchen
 * Date: 2/24/14
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class RedisDatasource implements InitializingBean {
    private static JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws RuntimeException {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(-1);
        config.setMaxIdle(1000);
        config.setMaxWait(1000);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    public void put(String key, Map<String, String> map) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.hmset(key, map);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void put(String key, List<String> values) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.sadd(key, values.toArray(new String[]{}));
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public String getMapValue(String mapKey, String field) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.hget(mapKey, field);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public List<String> getList(String key) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.lrange(key, 0, -1);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public String getLast(String key) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            List<String> lrange = resource.lrange(key, -1, -1);
            return lrange.isEmpty() ? null : lrange.get(0);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void set(String key, String value) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.set(key, value);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public Set<String> keys(String key) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.keys(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public String getString(String key) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.get(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }


    public int incr(String key) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.incr(key).intValue();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void incrby(String key, long value) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.incrBy(key, value);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public int getInt(String key) throws RuntimeException {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            String value = resource.get(key);
            if (StringUtils.hasText(value)) {
                return Integer.valueOf(value);
            }
            return 0;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void rpush(String key, String value) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.rpush(key, value);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public int llen(String key) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            Long length = resource.llen(key);
            if (length != null) {
                return length.intValue();
            }
            return 0;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public boolean exist(String key) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.exists(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void put(String key, String field, String value) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.hset(key, field, value);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public List<String> getList(String key, int startIndex, int endIndex) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.lrange(key, Long.valueOf(startIndex), Long.valueOf(endIndex));
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void lpush(String key, String value) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.lpush(key, value);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public Map<String, String> getAll(String key) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.hgetAll(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void del(String key) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.del(key);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public int lrem(String key, String value) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.lrem(key, 0L, value).intValue();
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public void zincrby(String key, String guid, int incr) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            resource.zincrby(key, Double.valueOf(incr), guid);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }

    public Set<String> zreverange(String key, int start, int end) {
        Jedis resource = null;
        try {
            resource = jedisPool.getResource();
            return resource.zrevrange(key, Long.valueOf(start), Long.valueOf(end));
        } catch (RuntimeException e) {
            throw e;
        } finally {
            if (resource != null) {
                jedisPool.returnResource(resource);
            }
        }
    }
}