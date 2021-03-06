package com.tvl.midl.cacheclient;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tvl.midl.cacheclient.config.CacheConfig;
import com.tvl.midl.cacheclient.exception.TvlCacheException;
import com.tvl.midl.cacheclient.provider.redis.IRedisCache;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class RedisCacheClient extends CacheBuilder implements IRedisCache {

	private static final String CACHE_REDIS = "redis";

	IRedisCache redisCache;

	public RedisCacheClient(CacheConfig cacheConfig) {
		super(CACHE_REDIS, cacheConfig);
	}

	public void init() {
		ICache cache = createCache();
		if (cache instanceof IRedisCache) {
			this.redisCache = (IRedisCache) cache;
		} else {
			throw new TvlCacheException("----缓存配置类型无效！");
		}
	}

	public void put(String key, String value) throws TvlCacheException {
		this.redisCache.put(key, value);
	}

	public void put(String key, int expire, String value) throws TvlCacheException {
		this.redisCache.put(key, expire, value);
	}

	public String get(String key) throws TvlCacheException {
		return this.redisCache.get(key);
	}

	public void remove(String key) throws TvlCacheException {
		this.redisCache.remove(key);
	}

	public void flushAll() throws TvlCacheException {
		this.redisCache.flushAll();
	}

	public void hput(String key, String field, String value) throws TvlCacheException {
		this.redisCache.hput(key, field, value);
	}

	public String hget(String key, String field) throws TvlCacheException {
		return this.redisCache.hget(key, field);
	}

	public List<String> hmget(String key, String... fields) {
		return this.redisCache.hmget(key, fields);
	}

	public Map<String, String> hgetAll(String key) {
		return this.redisCache.hgetAll(key);
	}

	public void hremove(String key, String[] fields) {
		this.redisCache.hremove(key, fields);
	}

	public Long incr(String key) {
		return this.redisCache.incr(key);
	}

	public Long incr(String key, int expire) {
		return this.redisCache.incr(key, expire);
	}

	public Long incrBy(String key, long integer) {
		return this.redisCache.incrBy(key, integer);
	}

	public Long incrBy(String key, long integer, int expire) {
		return this.redisCache.incrBy(key, integer, expire);
	}

	public Double incrByFloat(String key, double value) {
		return this.redisCache.incrByFloat(key, value);
	}

	public Double incrByFloat(String key, double value, int expire) {
		return this.redisCache.incrByFloat(key, value, expire);
	}

	public Long expire(String key, int seconds) {
		return this.redisCache.expire(key, seconds);
	}

	public String set(String key, String value) throws TvlCacheException {
		return this.redisCache.set(key, value);
	}

	public String set(String key, String value, String nxxx, String expx, long time) throws TvlCacheException {
		return this.redisCache.set(key, value, nxxx, expx, time);
	}

	public Boolean exists(String key) throws TvlCacheException {
		return this.redisCache.exists(key);
	}

	public Long persist(String key) throws TvlCacheException {
		return this.redisCache.persist(key);
	}

	public String type(String key) throws TvlCacheException {
		return this.redisCache.type(key);
	}

	public Long pexpire(String key, long milliseconds) throws TvlCacheException {
		return this.redisCache.pexpire(key, milliseconds);
	}

	public Long expireAt(String key, long unixTime) throws TvlCacheException {
		return this.redisCache.expireAt(key, unixTime);
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) throws TvlCacheException {
		return this.redisCache.pexpireAt(key, millisecondsTimestamp);
	}

	public Long ttl(String key) throws TvlCacheException {
		return this.redisCache.ttl(key);
	}

	public Boolean setbit(String key, long offset, boolean value) throws TvlCacheException {
		return this.redisCache.setbit(key, offset, value);
	}

	public Boolean setbit(String key, long offset, String value) throws TvlCacheException {
		return this.redisCache.setbit(key, offset, value);
	}

	public Boolean getbit(String key, long offset) throws TvlCacheException {
		return this.redisCache.getbit(key, offset);
	}

	public Long setrange(String key, long offset, String value) throws TvlCacheException {
		return this.redisCache.setrange(key, offset, value);
	}

	public String getrange(String key, long startOffset, long endOffset) throws TvlCacheException {
		return this.redisCache.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) throws TvlCacheException {
		return this.redisCache.getSet(key, value);
	}

	public Long setnx(String key, String value) throws TvlCacheException {
		return this.redisCache.setnx(key, value);
	}

	public String setex(String key, int seconds, String value) throws TvlCacheException {
		return this.redisCache.setex(key, seconds, value);
	}

	public Long decrBy(String key, long integer) throws TvlCacheException {
		return this.redisCache.decrBy(key, integer);
	}

	public Long decr(String key) throws TvlCacheException {
		return this.redisCache.decr(key);
	}

	public Long append(String key, String value) throws TvlCacheException {
		return this.redisCache.append(key, value);
	}

	public String substr(String key, int start, int end) throws TvlCacheException {
		return this.redisCache.substr(key, start, end);
	}

	public Long hset(String key, String field, String value) throws TvlCacheException {
		return this.redisCache.hset(key, field, value);
	}

	public Long hsetnx(String key, String field, String value) throws TvlCacheException {
		return this.redisCache.hsetnx(key, field, value);
	}

	public String hmset(String key, Map<String, String> hash) throws TvlCacheException {
		return this.redisCache.hmset(key, hash);
	}

	public Long hincrBy(String key, String field, long value) throws TvlCacheException {
		return this.redisCache.hincrBy(key, field, value);
	}

	public Boolean hexists(String key, String field) throws TvlCacheException {
		return this.redisCache.hexists(key, field);
	}

	public Long hdel(String key, String... field) throws TvlCacheException {
		return this.redisCache.hdel(key, field);
	}

	public Long hlen(String key) throws TvlCacheException {
		return this.redisCache.hlen(key);
	}

	public Set<String> hkeys(String key) throws TvlCacheException {
		return this.redisCache.hkeys(key);
	}

	public List<String> hvals(String key) throws TvlCacheException {
		return this.redisCache.hvals(key);
	}

	public Long rpush(String key, String... string) throws TvlCacheException {
		return this.redisCache.rpush(key, string);
	}

	public Long lpush(String key, String... string) throws TvlCacheException {
		return this.redisCache.lpush(key, string);
	}

	public Long llen(String key) throws TvlCacheException {
		return this.redisCache.llen(key);
	}

	public List<String> lrange(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.lrange(key, start, end);
	}

	public String ltrim(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.ltrim(key, start, end);
	}

	public String lindex(String key, long index) throws TvlCacheException {
		return this.redisCache.lindex(key, index);
	}

	public String lset(String key, long index, String value) throws TvlCacheException {
		return this.redisCache.lset(key, index, value);
	}

	public Long lrem(String key, long count, String value) throws TvlCacheException {
		return this.redisCache.lrem(key, count, value);
	}

	public String lpop(String key) throws TvlCacheException {
		return this.redisCache.lpop(key);
	}

	public String rpop(String key) throws TvlCacheException {
		return this.redisCache.rpop(key);
	}

	public Long sadd(String key, String... member) throws TvlCacheException {
		return this.redisCache.sadd(key, member);
	}

	public Set<String> smembers(String key) throws TvlCacheException {
		return this.redisCache.smembers(key);
	}

	public Long srem(String key, String... member) throws TvlCacheException {
		return this.redisCache.srem(key, member);
	}

	public String spop(String key) throws TvlCacheException {
		return this.redisCache.spop(key);
	}

	public Set<String> spop(String key, long count) throws TvlCacheException {
		return this.redisCache.spop(key, count);
	}

	public Long scard(String key) throws TvlCacheException {
		return this.redisCache.scard(key);
	}

	public Boolean sismember(String key, String member) throws TvlCacheException {
		return this.redisCache.sismember(key, member);
	}

	public String srandmember(String key) throws TvlCacheException {
		return this.redisCache.srandmember(key);
	}

	public List<String> srandmember(String key, int count) throws TvlCacheException {
		return this.redisCache.srandmember(key, count);
	}

	public Long strlen(String key) throws TvlCacheException {
		return this.redisCache.strlen(key);
	}

	public Long zadd(String key, double score, String member) throws TvlCacheException {
		return this.redisCache.zadd(key, score, member);
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) throws TvlCacheException {
		return this.redisCache.zadd(key, scoreMembers);
	}

	public Set<String> zrange(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.zrange(key, start, end);
	}

	public Long zrem(String key, String... member) throws TvlCacheException {
		return this.redisCache.zrem(key, member);
	}

	public Double zincrby(String key, double score, String member) throws TvlCacheException {
		return this.redisCache.zincrby(key, score, member);
	}

	public Long zrank(String key, String member) throws TvlCacheException {
		return this.redisCache.zrank(key, member);
	}

	public Long zrevrank(String key, String member) throws TvlCacheException {
		return this.redisCache.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) throws TvlCacheException {
		return this.redisCache.zcard(key);
	}

	public Double zscore(String key, String member) throws TvlCacheException {
		return this.redisCache.zscore(key, member);
	}

	public List<String> sort(String key) throws TvlCacheException {
		return this.redisCache.sort(key);
	}

	public List<String> sort(String key, SortingParams sortingParameters) throws TvlCacheException {
		return this.redisCache.sort(key, sortingParameters);
	}

	public Long zcount(String key, double min, double max) throws TvlCacheException {
		return this.redisCache.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) throws TvlCacheException {
		return this.redisCache.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) throws TvlCacheException {
		return this.redisCache.zrangeByScore(key, min, max);
	}

	public Set<String> zrangeByScore(String key, String min, String max) throws TvlCacheException {
		return this.redisCache.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) throws TvlCacheException {
		return this.redisCache.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) throws TvlCacheException {
		return this.redisCache.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws TvlCacheException {
		return this.redisCache.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) throws TvlCacheException {
		return this.redisCache.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) throws TvlCacheException {
		return this.redisCache.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) throws TvlCacheException {
		return this.redisCache.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(String key, double start, double end) throws TvlCacheException {
		return this.redisCache.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(String key, String start, String end) throws TvlCacheException {
		return this.redisCache.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(String key, String min, String max) throws TvlCacheException {
		return this.redisCache.zlexcount(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max) throws TvlCacheException {
		return this.redisCache.zrangeByLex(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) throws TvlCacheException {
		return this.redisCache.zrangeByLex(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) throws TvlCacheException {
		return this.redisCache.zrevrangeByLex(key, max, min);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.redisCache.zrevrangeByLex(key, max, min, offset, count);
	}

	public Long zremrangeByLex(String key, String min, String max) throws TvlCacheException {
		return this.redisCache.zremrangeByLex(key, min, max);
	}

	public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value)
			throws TvlCacheException {
		return this.redisCache.linsert(key, where, pivot, value);
	}

	public Long lpushx(String key, String... string) throws TvlCacheException {
		return this.redisCache.lpushx(key, string);
	}

	public Long rpushx(String key, String... string) throws TvlCacheException {
		return this.redisCache.rpushx(key, string);
	}

	public List<String> blpop(String arg) throws TvlCacheException {
		return this.redisCache.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) throws TvlCacheException {
		return this.redisCache.blpop(timeout, key);
	}

	public List<String> brpop(String arg) throws TvlCacheException {
		return this.redisCache.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) throws TvlCacheException {
		return this.redisCache.brpop(timeout, key);
	}

	public Long del(String key) throws TvlCacheException {
		return this.redisCache.del(key);
	}

	public String echo(String string) throws TvlCacheException {
		return this.redisCache.echo(string);
	}

	public Long move(String key, int dbIndex) throws TvlCacheException {
		return this.redisCache.move(key, dbIndex);
	}

	public Long bitcount(String key) throws TvlCacheException {
		return this.redisCache.bitcount(key);
	}

	public Long bitcount(String key, long start, long end) throws TvlCacheException {
		return this.redisCache.bitcount(key, start, end);
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) throws TvlCacheException {
		return this.redisCache.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, int cursor) throws TvlCacheException {
		return this.redisCache.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, int cursor) throws TvlCacheException {
		return this.redisCache.zscan(key, cursor);
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) throws TvlCacheException {
		return this.redisCache.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, String cursor) throws TvlCacheException {
		return this.redisCache.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, String cursor) throws TvlCacheException {
		return this.redisCache.zscan(key, cursor);
	}

	public Long pfadd(String key, String... elements) throws TvlCacheException {
		return this.redisCache.pfadd(key, elements);
	}

	public long pfcount(String key) throws TvlCacheException {
		return this.redisCache.pfcount(key);
	}

	public Set<String> keys(String pattern) {
		return this.redisCache.keys(pattern);
	}

	public List<String> mget(String... keys) {
		return this.redisCache.mget(keys);
	}

	public String mset(String... keysvalues) {
		return this.redisCache.mset(keysvalues);
	}

	public Long msetnx(String... keysvalues) {
		return this.redisCache.msetnx(keysvalues);
	}
}
