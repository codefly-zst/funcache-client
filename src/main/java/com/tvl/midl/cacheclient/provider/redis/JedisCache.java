package com.tvl.midl.cacheclient.provider.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tvl.midl.cacheclient.exception.TvlCacheException;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public class JedisCache implements IRedisCache {

	private Jedis jedis;

	public JedisCache() {
	}

	public JedisCache(Jedis redis) {
		this.jedis = redis;
	}

	public void put(String key, String value) throws TvlCacheException {
		this.jedis.set(key, value);
	}

	public void put(String key, int expire, String value) throws TvlCacheException {
		this.jedis.set(key, value);
		if (expire > 0) {
			this.jedis.expire(key, expire);
		}
	}

	public String get(String key) throws TvlCacheException {
		return this.jedis.get(key);
	}

	public void remove(String key) throws TvlCacheException {
		this.jedis.del(key);
	}

	public void hput(String key, String field, String value) throws TvlCacheException {
		this.jedis.hset(key, field, value);
	}

	public String hget(String key, String field) throws TvlCacheException {
		return this.jedis.hget(key, field);
	}

	public List<String> hmget(String key, String... fields) {
		return this.jedis.hmget(key, fields);
	}

	public Map<String, String> hgetAll(String key) {
		return this.jedis.hgetAll(key);
	}

	public void hremove(String key, String[] fields) {
		this.jedis.hdel(key, fields);
	}

	public Long incr(String key) {
		return this.jedis.incr(key);
	}

	public Long incr(String key, int expire) {
		Long v = this.jedis.incr(key);
		this.jedis.expire(key, expire);
		return v;
	}

	public Long incrBy(String key, long integer) {
		return this.jedis.incrBy(key, integer);
	}

	public Long incrBy(String key, long integer, int expire) {
		Long v = this.jedis.incrBy(key, integer);
		this.jedis.expire(key, expire);
		return v;
	}

	public Double incrByFloat(String key, double value) {
		return this.jedis.incrByFloat(key, value);
	}

	public Double incrByFloat(String key, double value, int expire) {
		Double v = this.jedis.incrByFloat(key, value);
		this.jedis.expire(key, expire);
		return v;
	}

	public Long expire(String key, int seconds) {
		return this.jedis.expire(key, seconds);
	}

	public void flushAll() throws TvlCacheException {
		this.jedis.flushAll();
	}

	public String set(String key, String value) throws TvlCacheException {
		return this.jedis.set(key, value);
	}

	public String set(String key, String value, String nxxx, String expx, long time) throws TvlCacheException {
		return this.jedis.set(key, value, nxxx, expx, time);
	}

	public Boolean exists(String key) throws TvlCacheException {
		return this.jedis.exists(key);
	}

	public Long persist(String key) throws TvlCacheException {
		return this.jedis.persist(key);
	}

	public String type(String key) throws TvlCacheException {
		return this.jedis.type(key);
	}

	public Long pexpire(String key, long milliseconds) throws TvlCacheException {
		return this.jedis.pexpire(key, milliseconds);
	}

	public Long expireAt(String key, long unixTime) throws TvlCacheException {
		return this.jedis.expireAt(key, unixTime);
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) throws TvlCacheException {
		return this.jedis.pexpireAt(key, millisecondsTimestamp);
	}

	public Long ttl(String key) throws TvlCacheException {
		return this.jedis.ttl(key);
	}

	public Boolean setbit(String key, long offset, boolean value) throws TvlCacheException {
		return this.jedis.setbit(key, offset, value);
	}

	public Boolean setbit(String key, long offset, String value) throws TvlCacheException {
		return this.jedis.setbit(key, offset, value);
	}

	public Boolean getbit(String key, long offset) throws TvlCacheException {
		return this.jedis.getbit(key, offset);
	}

	public Long setrange(String key, long offset, String value) throws TvlCacheException {
		return this.jedis.setrange(key, offset, value);
	}

	public String getrange(String key, long startOffset, long endOffset) throws TvlCacheException {
		return this.jedis.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) throws TvlCacheException {
		return this.jedis.getSet(key, value);
	}

	public Long setnx(String key, String value) throws TvlCacheException {
		return this.jedis.setnx(key, value);
	}

	public String setex(String key, int seconds, String value) throws TvlCacheException {
		return this.jedis.setex(key, seconds, value);
	}

	public Long decrBy(String key, long integer) throws TvlCacheException {
		return this.jedis.decrBy(key, integer);
	}

	public Long decr(String key) throws TvlCacheException {
		return this.jedis.decr(key);
	}

	public Long append(String key, String value) throws TvlCacheException {
		return this.jedis.append(key, value);
	}

	public String substr(String key, int start, int end) throws TvlCacheException {
		return this.jedis.substr(key, start, end);
	}

	public Long hset(String key, String field, String value) throws TvlCacheException {
		return this.jedis.hset(key, field, value);
	}

	public Long hsetnx(String key, String field, String value) throws TvlCacheException {
		return this.jedis.hsetnx(key, field, value);
	}

	public String hmset(String key, Map<String, String> hash) throws TvlCacheException {
		return this.jedis.hmset(key, hash);
	}

	public Long hincrBy(String key, String field, long value) throws TvlCacheException {
		return this.jedis.hincrBy(key, field, value);
	}

	public Boolean hexists(String key, String field) throws TvlCacheException {
		return this.jedis.hexists(key, field);
	}

	public Long hdel(String key, String... field) throws TvlCacheException {
		return this.jedis.hdel(key, field);
	}

	public Long hlen(String key) throws TvlCacheException {
		return this.jedis.hlen(key);
	}

	public Set<String> hkeys(String key) throws TvlCacheException {
		return this.jedis.hkeys(key);
	}

	public List<String> hvals(String key) throws TvlCacheException {
		return this.jedis.hvals(key);
	}

	public Long rpush(String key, String... string) throws TvlCacheException {
		return this.jedis.rpush(key, string);
	}

	public Long lpush(String key, String... string) throws TvlCacheException {
		return this.jedis.lpush(key, string);
	}

	public Long llen(String key) throws TvlCacheException {
		return this.jedis.llen(key);
	}

	public List<String> lrange(String key, long start, long end) throws TvlCacheException {
		return this.jedis.lrange(key, start, end);
	}

	public String ltrim(String key, long start, long end) throws TvlCacheException {
		return this.jedis.ltrim(key, start, end);
	}

	public String lindex(String key, long index) throws TvlCacheException {
		return this.jedis.lindex(key, index);
	}

	public String lset(String key, long index, String value) throws TvlCacheException {
		return this.jedis.lset(key, index, value);
	}

	public Long lrem(String key, long count, String value) throws TvlCacheException {
		return this.jedis.lrem(key, count, value);
	}

	public String lpop(String key) throws TvlCacheException {
		return this.jedis.lpop(key);
	}

	public String rpop(String key) throws TvlCacheException {
		return this.jedis.rpop(key);
	}

	public Long sadd(String key, String... member) throws TvlCacheException {
		return this.jedis.sadd(key, member);
	}

	public Set<String> smembers(String key) throws TvlCacheException {
		return this.jedis.smembers(key);
	}

	public Long srem(String key, String... member) throws TvlCacheException {
		return this.jedis.srem(key, member);
	}

	public String spop(String key) throws TvlCacheException {
		return this.jedis.spop(key);
	}

	public Set<String> spop(String key, long count) throws TvlCacheException {
		return this.jedis.spop(key, count);
	}

	public Long scard(String key) throws TvlCacheException {
		return this.jedis.scard(key);
	}

	public Boolean sismember(String key, String member) throws TvlCacheException {
		return this.jedis.sismember(key, member);
	}

	public String srandmember(String key) throws TvlCacheException {
		return this.jedis.srandmember(key);
	}

	public List<String> srandmember(String key, int count) throws TvlCacheException {
		return this.jedis.srandmember(key, count);
	}

	public Long strlen(String key) throws TvlCacheException {
		return this.jedis.strlen(key);
	}

	public Long zadd(String key, double score, String member) throws TvlCacheException {
		return this.jedis.zadd(key, score, member);
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) throws TvlCacheException {
		return this.jedis.zadd(key, scoreMembers);
	}

	public Set<String> zrange(String key, long start, long end) throws TvlCacheException {
		return this.jedis.zrange(key, start, end);
	}

	public Long zrem(String key, String... member) throws TvlCacheException {
		return this.jedis.zrem(key, member);
	}

	public Double zincrby(String key, double score, String member) throws TvlCacheException {
		return this.jedis.zincrby(key, score, member);
	}

	public Long zrank(String key, String member) throws TvlCacheException {
		return this.jedis.zrank(key, member);
	}

	public Long zrevrank(String key, String member) throws TvlCacheException {
		return this.jedis.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) throws TvlCacheException {
		return this.jedis.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) throws TvlCacheException {
		return this.jedis.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) throws TvlCacheException {
		return this.jedis.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) throws TvlCacheException {
		return this.jedis.zcard(key);
	}

	public Double zscore(String key, String member) throws TvlCacheException {
		return this.jedis.zscore(key, member);
	}

	public List<String> sort(String key) throws TvlCacheException {
		return this.jedis.sort(key);
	}

	public List<String> sort(String key, SortingParams sortingParameters) throws TvlCacheException {
		return this.jedis.sort(key, sortingParameters);
	}

	public Long zcount(String key, double min, double max) throws TvlCacheException {
		return this.jedis.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) throws TvlCacheException {
		return this.jedis.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) throws TvlCacheException {
		return this.jedis.zrangeByScore(key, min, max);
	}

	public Set<String> zrangeByScore(String key, String min, String max) throws TvlCacheException {
		return this.jedis.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) throws TvlCacheException {
		return this.jedis.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) throws TvlCacheException {
		return this.jedis.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) throws TvlCacheException {
		return this.jedis.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) throws TvlCacheException {
		return this.jedis.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		return this.jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws TvlCacheException {
		return this.jedis.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) throws TvlCacheException {
		return this.jedis.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count)
			throws TvlCacheException {
		return this.jedis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) throws TvlCacheException {
		return this.jedis.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) throws TvlCacheException {
		return this.jedis.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count)
			throws TvlCacheException {
		return this.jedis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count)
			throws TvlCacheException {
		return this.jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count)
			throws TvlCacheException {
		return this.jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(String key, long start, long end) throws TvlCacheException {
		return this.jedis.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(String key, double start, double end) throws TvlCacheException {
		return this.jedis.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(String key, String start, String end) throws TvlCacheException {
		return this.jedis.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(String key, String min, String max) throws TvlCacheException {
		return this.jedis.zlexcount(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max) throws TvlCacheException {
		return this.jedis.zrangeByLex(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) throws TvlCacheException {
		return this.jedis.zrangeByLex(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) throws TvlCacheException {
		return this.jedis.zrevrangeByLex(key, max, min);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) throws TvlCacheException {
		return this.jedis.zrevrangeByLex(key, max, min, offset, count);
	}

	public Long zremrangeByLex(String key, String min, String max) throws TvlCacheException {
		return this.jedis.zremrangeByLex(key, min, max);
	}

	public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value)
			throws TvlCacheException {
		return this.jedis.linsert(key, where, pivot, value);
	}

	public Long lpushx(String key, String... string) throws TvlCacheException {
		return this.jedis.lpushx(key, string);
	}

	public Long rpushx(String key, String... string) throws TvlCacheException {
		return this.jedis.rpushx(key, string);
	}

	public List<String> blpop(String arg) throws TvlCacheException {
		return this.jedis.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) throws TvlCacheException {
		return this.jedis.blpop(timeout, key);
	}

	public List<String> brpop(String arg) throws TvlCacheException {
		return this.jedis.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) throws TvlCacheException {
		return this.jedis.brpop(timeout, key);
	}

	public Long del(String key) throws TvlCacheException {
		return this.jedis.del(key);
	}

	public String echo(String string) throws TvlCacheException {
		return this.jedis.echo(string);
	}

	public Long move(String key, int dbIndex) throws TvlCacheException {
		return this.jedis.move(key, dbIndex);
	}

	public Long bitcount(String key) throws TvlCacheException {
		return this.jedis.bitcount(key);
	}

	public Long bitcount(String key, long start, long end) throws TvlCacheException {
		return this.jedis.bitcount(key, start, end);
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) throws TvlCacheException {
		return this.jedis.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, int cursor) throws TvlCacheException {
		return this.jedis.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, int cursor) throws TvlCacheException {
		return this.jedis.zscan(key, cursor);
	}

	public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) throws TvlCacheException {
		return this.jedis.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, String cursor) throws TvlCacheException {
		return this.jedis.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, String cursor) throws TvlCacheException {
		return this.jedis.zscan(key, cursor);
	}

	public Long pfadd(String key, String... elements) throws TvlCacheException {
		return this.jedis.pfadd(key, elements);
	}

	public long pfcount(String key) throws TvlCacheException {
		return this.jedis.pfcount(key);
	}

	public Set<String> keys(String pattern) {
		return this.jedis.keys(pattern);
	}

	public List<String> mget(String... keys) {
		return this.jedis.mget(keys);
	}

	public String mset(String... keysvalues) {
		return this.jedis.mset(keysvalues);
	}

	public Long msetnx(String... keysvalues) {
		return this.jedis.msetnx(keysvalues);
	}
}
