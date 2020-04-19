package com.tvl.midl.cacheclient.provider.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvl.midl.cacheclient.exception.TvlCacheException;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class JedisClusterCache implements IRedisCache {

	private static final Logger logger = LoggerFactory.getLogger(JedisClusterCache.class);

	private JedisCluster jedisCluster = null;

	public JedisClusterCache() {
	}

	public JedisClusterCache(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public String get(String key) throws TvlCacheException {
		return this.jedisCluster.get(key);
	}

	public void put(String key, String value) throws TvlCacheException {
		this.jedisCluster.set(key, String.valueOf(value));
	}

	public void put(String key, int expire, String value) throws TvlCacheException {
		this.jedisCluster.set(key, String.valueOf(value));
		if (expire > 0) {
			this.jedisCluster.expire(key, expire);
		}
	}

	public void remove(String key) throws TvlCacheException {
		this.jedisCluster.del(key);
	}

	public void hput(String key, String field, String value) throws TvlCacheException {
		this.jedisCluster.hset(key, field, value);
	}

	public String hget(String key, String field) throws TvlCacheException {
		return this.jedisCluster.hget(key, field);
	}

	public List<String> hmget(String key, String... fields) {
		return this.jedisCluster.hmget(key, fields);
	}

	public Map<String, String> hgetAll(String key) {
		return this.jedisCluster.hgetAll(key);
	}

	public void hremove(String key, String[] fields) {
		this.jedisCluster.hdel(key, fields);
	}

	public Long incr(String key) {
		return this.jedisCluster.incr(key);
	}

	public Long incr(String key, int expire) {
		Long v = this.jedisCluster.incr(key);
		this.jedisCluster.expire(key, expire);
		return v;
	}

	public Long incrBy(String key, long integer) {
		return this.jedisCluster.incrBy(key, integer);
	}

	public Long incrBy(String key, long integer, int expire) {
		Long v = this.jedisCluster.incrBy(key, integer);
		this.jedisCluster.expire(key, expire);
		return v;
	}

	public Double incrByFloat(String key, double value) {
		return this.jedisCluster.incrByFloat(key, value);
	}

	public Double incrByFloat(String key, double value, int expire) {
		Double v = this.jedisCluster.incrByFloat(key, value);
		this.jedisCluster.expire(key, expire);
		return v;
	}

	public Long expire(String key, int seconds) {
		return this.jedisCluster.expire(key, seconds);
	}

	public void flushAll() throws TvlCacheException {
		throw new TvlCacheException("----cluster模式下不支持flushAll操作");
	}

	public String set(String key, String value) throws TvlCacheException {
		return this.jedisCluster.set(key, value);
	}

	public String set(String key, String value, String nxxx, String expx, long time) throws TvlCacheException {
		return this.jedisCluster.set(key, value, nxxx, expx, time);
	}

	public Boolean exists(String key) throws TvlCacheException {
		return this.jedisCluster.exists(key);
	}

	public Long persist(String key) throws TvlCacheException {
		return this.jedisCluster.persist(key);
	}

	public String type(String key) throws TvlCacheException {
		return this.jedisCluster.type(key);
	}

	public Long pexpire(String key, long milliseconds) throws TvlCacheException {
		return this.jedisCluster.pexpire(key, milliseconds);
	}

	public Long expireAt(String key, long unixTime) throws TvlCacheException {
		return this.jedisCluster.expireAt(key, unixTime);
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) throws TvlCacheException {
		return this.jedisCluster.pexpireAt(key, millisecondsTimestamp);
	}

	public Long ttl(String key) throws TvlCacheException {
		return this.jedisCluster.ttl(key);
	}

	public Boolean setbit(String key, long offset, boolean value) throws TvlCacheException {
		return this.jedisCluster.setbit(key, offset, value);
	}

	public Boolean setbit(String key, long offset, String value) throws TvlCacheException {
		return this.jedisCluster.setbit(key, offset, value);
	}

	public Boolean getbit(String key, long offset) throws TvlCacheException {
		return this.jedisCluster.getbit(key, offset);
	}

	public Long setrange(String key, long offset, String value) throws TvlCacheException {
		return this.jedisCluster.setrange(key, offset, value);
	}

	public String getrange(String key, long startOffset, long endOffset) throws TvlCacheException {
		return this.jedisCluster.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) throws TvlCacheException {
		return this.jedisCluster.getSet(key, value);
	}

	public Long setnx(String key, String value) throws TvlCacheException {
		return this.jedisCluster.setnx(key, value);
	}

	public String setex(String key, int seconds, String value) throws TvlCacheException {
		return this.jedisCluster.setex(key, seconds, value);
	}

	public Long decrBy(String key, long integer) throws TvlCacheException {
		return this.jedisCluster.decrBy(key, integer);
	}

	public Long decr(String key) throws TvlCacheException {
		return this.jedisCluster.decr(key);
	}

	public Long append(String key, String value) throws TvlCacheException {
		return this.jedisCluster.append(key, value);
	}

	public String substr(String key, int start, int end) throws TvlCacheException {
		return this.jedisCluster.substr(key, start, end);
	}

	public Long hset(String key, String field, String value) throws TvlCacheException {
		return this.jedisCluster.hset(key, field, value);
	}

	public Long hsetnx(String key, String field, String value) throws TvlCacheException {
		return this.jedisCluster.hsetnx(key, field, value);
	}

	public String hmset(String key, Map<String, String> hash) throws TvlCacheException {
		return this.jedisCluster.hmset(key, hash);
	}

	public Long hincrBy(String key, String field, long value) throws TvlCacheException {
		return this.jedisCluster.hincrBy(key, field, value);
	}

	public Boolean hexists(String key, String field) throws TvlCacheException {
		return this.jedisCluster.hexists(key, field);
	}

	public Long hdel(String key, String... field) throws TvlCacheException {
		return this.jedisCluster.hdel(key, field);
	}

	public Long hlen(String key) throws TvlCacheException {
		return this.jedisCluster.hlen(key);
	}

	public Set<String> hkeys(String key) throws TvlCacheException {
		return this.jedisCluster.hkeys(key);
	}

	public List<String> hvals(String key) throws TvlCacheException {
		return this.jedisCluster.hvals(key);
	}

	public Long rpush(String key, String... string) throws TvlCacheException {
		return this.jedisCluster.rpush(key, string);
	}

	public Long lpush(String key, String... string) throws TvlCacheException {
		return this.jedisCluster.lpush(key, string);
	}

	public Long llen(String key) throws TvlCacheException {
		return this.jedisCluster.llen(key);
	}

	public List<String> lrange(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.lrange(key, start, end);
	}

	public String ltrim(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.ltrim(key, start, end);
	}

	public String lindex(String key, long index) throws TvlCacheException {
		return this.jedisCluster.lindex(key, index);
	}

	public String lset(String key, long index, String value) throws TvlCacheException {
		return this.jedisCluster.lset(key, index, value);
	}

	public Long lrem(String key, long count, String value) throws TvlCacheException {
		return this.jedisCluster.lrem(key, count, value);
	}

	public String lpop(String key) throws TvlCacheException {
		return this.jedisCluster.lpop(key);
	}

	public String rpop(String key) throws TvlCacheException {
		return this.jedisCluster.rpop(key);
	}

	public Long sadd(String key, String... member) throws TvlCacheException {
		return this.jedisCluster.sadd(key, member);
	}

	public Set<String> smembers(String key) throws TvlCacheException {
		return this.jedisCluster.smembers(key);
	}

	public Long srem(String key, String... member) throws TvlCacheException {
		return this.jedisCluster.srem(key, member);
	}

	public String spop(String key) throws TvlCacheException {
		return this.jedisCluster.spop(key);
	}

	public Set<String> spop(String key, long count) throws TvlCacheException {
		return this.jedisCluster.spop(key, count);
	}

	public Long scard(String key) throws TvlCacheException {
		return this.jedisCluster.scard(key);
	}

	public Boolean sismember(String key, String member) throws TvlCacheException {
		return this.jedisCluster.sismember(key, member);
	}

	public String srandmember(String key) throws TvlCacheException {
		return this.jedisCluster.srandmember(key);
	}

	public List<String> srandmember(String key, int count) throws TvlCacheException {
		return this.jedisCluster.srandmember(key, count);
	}

	public Long strlen(String key) throws TvlCacheException {
		return this.jedisCluster.strlen(key);
	}

	public Long zadd(String key, double score, String member) throws TvlCacheException {
		return this.jedisCluster.zadd(key, score, member);
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) throws TvlCacheException {
		return this.jedisCluster.zadd(key, scoreMembers);
	}

	public Set<String> zrange(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.zrange(key, start, end);
	}

	public Long zrem(String key, String... member) throws TvlCacheException {
		return this.jedisCluster.zrem(key, member);
	}

	public Double zincrby(String key, double score, String member) throws TvlCacheException {
		return this.jedisCluster.zincrby(key, score, member);
	}

	public Long zrank(String key, String member) throws TvlCacheException {
		return this.jedisCluster.zrank(key, member);
	}

	public Long zrevrank(String key, String member) throws TvlCacheException {
		return this.jedisCluster.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) throws TvlCacheException {
		return this.jedisCluster.zcard(key);
	}

	public Double zscore(String key, String member) throws TvlCacheException {
		return this.jedisCluster.zscore(key, member);
	}

	public List<String> sort(String key) throws TvlCacheException {
		return this.jedisCluster.sort(key);
	}

	public List<String> sort(String key, SortingParams sortingParameters) throws TvlCacheException {
		return this.jedisCluster.sort(key, sortingParameters);
	}

	public Long zcount(String key, double min, double max) throws TvlCacheException {
		return this.jedisCluster.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) throws TvlCacheException {
		return this.jedisCluster.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) throws TvlCacheException {
		return this.jedisCluster.zrangeByScore(key, min, max);
	}

	public Set<String> zrangeByScore(String key, String min, String max) throws TvlCacheException {
		return this.jedisCluster.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws TvlCacheException {
		return this.jedisCluster.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) throws TvlCacheException {
		return this.jedisCluster.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(String key, double start, double end) throws TvlCacheException {
		return this.jedisCluster.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(String key, String start, String end) throws TvlCacheException {
		return this.jedisCluster.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(String key, String min, String max) throws TvlCacheException {
		return this.jedisCluster.zlexcount(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max) throws TvlCacheException {
		return this.jedisCluster.zrangeByLex(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) throws TvlCacheException {
		return this.jedisCluster.zrangeByLex(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) throws TvlCacheException {
		return this.jedisCluster.zrevrangeByLex(key, max, min);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.jedisCluster.zrevrangeByLex(key, max, min, offset, count);
	}

	public Long zremrangeByLex(String key, String min, String max) throws TvlCacheException {
		return this.jedisCluster.zremrangeByLex(key, min, max);
	}

	public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value)
			throws TvlCacheException {
		return this.jedisCluster.linsert(key, where, pivot, value);
	}

	public Long lpushx(String key, String... string) throws TvlCacheException {
		return this.jedisCluster.lpushx(key, string);
	}

	public Long rpushx(String key, String... string) throws TvlCacheException {
		return this.jedisCluster.rpushx(key, string);
	}

	public List<String> blpop(String arg) throws TvlCacheException {
		return this.jedisCluster.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) throws TvlCacheException {
		return this.jedisCluster.blpop(timeout, key);
	}

	public List<String> brpop(String arg) throws TvlCacheException {
		return this.jedisCluster.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) throws TvlCacheException {
		return this.jedisCluster.brpop(timeout, key);
	}

	public Long del(String key) throws TvlCacheException {
		return this.jedisCluster.del(key);
	}

	public String echo(String string) throws TvlCacheException {
		return this.jedisCluster.echo(string);
	}

	public Long move(String key, int dbIndex) throws TvlCacheException {
		return this.jedisCluster.move(key, dbIndex);
	}

	public Long bitcount(String key) throws TvlCacheException {
		return this.jedisCluster.bitcount(key);
	}

	public Long bitcount(String key, long start, long end) throws TvlCacheException {
		return this.jedisCluster.bitcount(key, start, end);
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) throws TvlCacheException {
		return this.jedisCluster.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, int cursor) throws TvlCacheException {
		return this.jedisCluster.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, int cursor) throws TvlCacheException {
		return this.jedisCluster.zscan(key, cursor);
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) throws TvlCacheException {
		return this.jedisCluster.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, String cursor) throws TvlCacheException {
		return this.jedisCluster.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, String cursor) throws TvlCacheException {
		return this.jedisCluster.zscan(key, cursor);
	}

	public Long pfadd(String key, String... elements) throws TvlCacheException {
		return this.jedisCluster.pfadd(key, elements);
	}

	public long pfcount(String key) throws TvlCacheException {
		return this.jedisCluster.pfcount(key);
	}

	public Set<String> keys(String pattern) {
		throwExceptionIfNotAccess();
		return null;
	}

	public List<String> mget(String... keys) {
		throwExceptionIfNotAccess();
		return null;
	}

	public String mset(String... keysvalues) {
		throwExceptionIfNotAccess();
		return null;
	}

	public Long msetnx(String... keysvalues) {
		throwExceptionIfNotAccess();
		return null;
	}

	void throwExceptionIfNotAccess() {
		throw new IllegalArgumentException("----Jedis cluster不支持该操作");
	}
}
