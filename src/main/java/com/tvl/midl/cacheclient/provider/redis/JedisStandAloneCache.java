package com.tvl.midl.cacheclient.provider.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.exception.TvlCacheException;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class JedisStandAloneCache implements IRedisCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(JedisStandAloneCache.class);

	private JedisPool jedisPool = null;

	public JedisStandAloneCache() {
	}

	public JedisStandAloneCache(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public void put(String key, String value) throws TvlCacheException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {
			returnResource(this.jedisPool, jedis);
		}
	}

	public void put(String key, int expire, String value) throws TvlCacheException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			jedis.set(key, value);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
	}

	public String get(String key) throws TvlCacheException {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return value;
	}

	public void remove(String key) throws TvlCacheException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {
			returnResource(this.jedisPool, jedis);
		}
	}

	public void hput(String key, String field, String value) throws TvlCacheException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {
			returnResource(this.jedisPool, jedis);
		}
	}

	public String hget(String key, String field) throws TvlCacheException {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			value = jedis.hget(key, field);
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {
			returnResource(this.jedisPool, jedis);
		}
		return value;
	}

	public List<String> hmget(String key, String... fields) {
		List<String> list = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			list = jedis.hmget(key, fields);
		} catch (Exception e) {
			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return list;
	}

	public Map<String, String> hgetAll(String key) {
		Map<String, String> map = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			map = jedis.hgetAll(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return map;
	}

	public void hremove(String key, String[] fields) {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			jedis.hdel(key, fields);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
	}

	public Long incr(String key) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			value = jedis.incr(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return value;
	}

	public Long incr(String key, int expire) {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.incr(key);
			jedis.expire(key, expire);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long incrBy(String key, long integer) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			value = jedis.incrBy(key, integer);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return value;
	}

	public Long incrBy(String key, long integer, int expire) {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.incrBy(key, integer);
			jedis.expire(key, expire);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Double incrByFloat(String key, double value) {
		Double d = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			d = jedis.incrByFloat(key, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return d;
	}

	public Double incrByFloat(String key, double value, int expire) {
		Double d = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			d = jedis.incrByFloat(key, value);
			jedis.expire(key, expire);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return d;
	}

	public Long expire(String key, int seconds) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			value = jedis.expire(key, seconds);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return value;
	}

	public void flushAll() throws TvlCacheException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			jedis.flushAll();
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
	}

	public String set(String key, String value) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.set(key, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String set(String key, String value, String nxxx, String expx, long time) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.set(key, value, nxxx, expx, time);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Boolean exists(String key) throws TvlCacheException {
		Boolean v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.exists(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long persist(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.persist(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String type(String key) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.type(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long pexpire(String key, long milliseconds) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.pexpire(key, milliseconds);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long expireAt(String key, long unixTime) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.expireAt(key, unixTime);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.pexpireAt(key, millisecondsTimestamp);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long ttl(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.ttl(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Boolean setbit(String key, long offset, boolean value) throws TvlCacheException {
		Boolean v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.setbit(key, offset, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Boolean setbit(String key, long offset, String value) throws TvlCacheException {
		Boolean v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.setbit(key, offset, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Boolean getbit(String key, long offset) throws TvlCacheException {
		Boolean v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.getbit(key, offset);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long setrange(String key, long offset, String value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.setrange(key, offset, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String getrange(String key, long startOffset, long endOffset) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String getSet(String key, String value) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.getSet(key, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long setnx(String key, String value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.setnx(key, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String setex(String key, int seconds, String value) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.setex(key, seconds, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long decrBy(String key, long integer) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.decrBy(key, integer);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long decr(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.decr(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long append(String key, String value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.append(key, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String substr(String key, int start, int end) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.substr(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long hset(String key, String field, String value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hset(key, field, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long hsetnx(String key, String field, String value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hsetnx(key, field, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String hmset(String key, Map<String, String> hash) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hmset(key, hash);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long hincrBy(String key, String field, long value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hincrBy(key, field, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Boolean hexists(String key, String field) throws TvlCacheException {
		Boolean v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hexists(key, field);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long hdel(String key, String... field) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hdel(key, field);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long hlen(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hlen(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> hkeys(String key) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hkeys(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> hvals(String key) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hvals(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long rpush(String key, String... string) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.rpush(key, string);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long lpush(String key, String... string) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lpush(key, string);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long llen(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.llen(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> lrange(String key, long start, long end) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lrange(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String ltrim(String key, long start, long end) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.ltrim(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String lindex(String key, long index) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lindex(key, index);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String lset(String key, long index, String value) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lset(key, index, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long lrem(String key, long count, String value) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lrem(key, count, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String lpop(String key) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lpop(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String rpop(String key) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.rpop(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long sadd(String key, String... member) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.sadd(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> smembers(String key) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.smembers(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long srem(String key, String... member) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.srem(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String spop(String key) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.spop(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> spop(String key, long count) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.spop(key, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long scard(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.scard(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Boolean sismember(String key, String member) throws TvlCacheException {
		Boolean v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.sismember(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String srandmember(String key) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.srandmember(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> srandmember(String key, int count) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.srandmember(key, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long strlen(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.strlen(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zadd(String key, double score, String member) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zadd(key, score, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zadd(key, scoreMembers);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrange(String key, long start, long end) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrange(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zrem(String key, String... member) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrem(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Double zincrby(String key, double score, String member) throws TvlCacheException {
		Double v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zincrby(key, score, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zrank(String key, String member) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrank(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zrevrank(String key, String member) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrank(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrange(String key, long start, long end) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrange(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeWithScores(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zcard(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zcard(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Double zscore(String key, String member) throws TvlCacheException {
		Double v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zscore(key, member);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> sort(String key) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.sort(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> sort(String key, SortingParams sortingParameters) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.sort(key, sortingParameters);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zcount(String key, double min, double max) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zcount(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zcount(String key, String min, String max) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zcount(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrangeByScore(String key, double min, double max) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrangeByScore(String key, String min, String max) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScore(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		Set<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zremrangeByRank(String key, long start, long end) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zremrangeByRank(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zremrangeByScore(String key, double start, double end) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zremrangeByScore(String key, String start, String end) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zlexcount(String key, String min, String max) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zlexcount(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrangeByLex(String key, String min, String max) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByLex(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrangeByLex(key, min, max, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByLex(key, max, min);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zrevrangeByLex(key, max, min, offset, count);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long zremrangeByLex(String key, String min, String max) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zremrangeByLex(key, min, max);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value)
			throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.linsert(key, where, pivot, value);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long lpushx(String key, String... string) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.lpushx(key, string);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long rpushx(String key, String... string) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.rpushx(key, string);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> blpop(String arg) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.blpop(arg);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> blpop(int timeout, String key) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.blpop(timeout, key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> brpop(String arg) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.brpop(arg);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> brpop(int timeout, String key) throws TvlCacheException {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.brpop(timeout, key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long del(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.del(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String echo(String string) throws TvlCacheException {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.echo(string);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long move(String key, int dbIndex) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.move(key, dbIndex);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long bitcount(String key) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.bitcount(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long bitcount(String key, long start, long end) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.bitcount(key, start, end);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) throws TvlCacheException {
		ScanResult<Map.Entry<String, String>> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hscan(key, cursor);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public ScanResult<String> sscan(String key, int cursor) throws TvlCacheException {
		ScanResult<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.sscan(key, cursor);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public ScanResult<Tuple> zscan(String key, int cursor) throws TvlCacheException {
		ScanResult<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zscan(key, cursor);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) throws TvlCacheException {
		ScanResult<Map.Entry<String, String>> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.hscan(key, cursor);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public ScanResult<String> sscan(String key, String cursor) throws TvlCacheException {
		ScanResult<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.sscan(key, cursor);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public ScanResult<Tuple> zscan(String key, String cursor) throws TvlCacheException {
		ScanResult<Tuple> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.zscan(key, cursor);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long pfadd(String key, String... elements) throws TvlCacheException {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.pfadd(key, elements);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public long pfcount(String key) throws TvlCacheException {
		long v = 0L;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.pfcount(key);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Set<String> keys(String pattern) {
		Set<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.keys(pattern);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public List<String> mget(String... keys) {
		List<String> v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.mget(keys);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public String mset(String... keysvalues) {
		String v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.mset(keysvalues);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public Long msetnx(String... keysvalues) {
		Long v = null;
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			v = jedis.msetnx(keysvalues);
		} catch (Exception e) {

			this.jedisPool.returnBrokenResource(jedis);
			LOGGER.error("----释放连接异常:{}", e);
		} finally {

			returnResource(this.jedisPool, jedis);
		}
		return v;
	}

	public void returnResource(JedisPool pool, Jedis redis) {
		if (redis != null)
			pool.returnResource(redis);
	}
}
