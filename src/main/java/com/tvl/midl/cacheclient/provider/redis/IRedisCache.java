package com.tvl.midl.cacheclient.provider.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tvl.midl.cacheclient.ICache;
import com.tvl.midl.cacheclient.exception.TvlCacheException;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public interface IRedisCache extends ICache {

	void hput(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	void hremove(String paramString, String[] paramArrayOfString) throws TvlCacheException;

	Long incr(String paramString, int paramInt) throws TvlCacheException;

	Long incrBy(String paramString, long paramLong, int paramInt) throws TvlCacheException;

	Double incrByFloat(String paramString, double paramDouble, int paramInt) throws TvlCacheException;

	String set(String paramString1, String paramString2) throws TvlCacheException;

	String set(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
			throws TvlCacheException;

	String get(String paramString) throws TvlCacheException;

	Boolean exists(String paramString) throws TvlCacheException;

	Long persist(String paramString) throws TvlCacheException;

	String type(String paramString) throws TvlCacheException;

	Long expire(String paramString, int paramInt) throws TvlCacheException;

	Long pexpire(String paramString, long paramLong) throws TvlCacheException;

	Long expireAt(String paramString, long paramLong) throws TvlCacheException;

	Long pexpireAt(String paramString, long paramLong) throws TvlCacheException;

	Long ttl(String paramString) throws TvlCacheException;

	Boolean setbit(String paramString, long paramLong, boolean paramBoolean) throws TvlCacheException;

	Boolean setbit(String paramString1, long paramLong, String paramString2) throws TvlCacheException;

	Boolean getbit(String paramString, long paramLong) throws TvlCacheException;

	Long setrange(String paramString1, long paramLong, String paramString2) throws TvlCacheException;

	String getrange(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	String getSet(String paramString1, String paramString2) throws TvlCacheException;

	Long setnx(String paramString1, String paramString2) throws TvlCacheException;

	String setex(String paramString1, int paramInt, String paramString2) throws TvlCacheException;

	Long decrBy(String paramString, long paramLong) throws TvlCacheException;

	Long decr(String paramString) throws TvlCacheException;

	Long incrBy(String paramString, long paramLong) throws TvlCacheException;

	Double incrByFloat(String paramString, double paramDouble) throws TvlCacheException;

	Long incr(String paramString) throws TvlCacheException;

	Long append(String paramString1, String paramString2) throws TvlCacheException;

	String substr(String paramString, int paramInt1, int paramInt2) throws TvlCacheException;

	Long hset(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	String hget(String paramString1, String paramString2) throws TvlCacheException;

	Long hsetnx(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	String hmset(String paramString, Map<String, String> paramMap) throws TvlCacheException;

	List<String> hmget(String paramString, String... paramVarArgs) throws TvlCacheException;

	Long hincrBy(String paramString1, String paramString2, long paramLong) throws TvlCacheException;

	Boolean hexists(String paramString1, String paramString2) throws TvlCacheException;

	Long hdel(String paramString, String... paramVarArgs) throws TvlCacheException;

	Long hlen(String paramString) throws TvlCacheException;

	Set<String> hkeys(String paramString) throws TvlCacheException;

	List<String> hvals(String paramString) throws TvlCacheException;

	Map<String, String> hgetAll(String paramString) throws TvlCacheException;

	Long rpush(String paramString, String... paramVarArgs) throws TvlCacheException;

	Long lpush(String paramString, String... paramVarArgs) throws TvlCacheException;

	Long llen(String paramString) throws TvlCacheException;

	List<String> lrange(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	String ltrim(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	String lindex(String paramString, long paramLong) throws TvlCacheException;

	String lset(String paramString1, long paramLong, String paramString2) throws TvlCacheException;

	Long lrem(String paramString1, long paramLong, String paramString2) throws TvlCacheException;

	String lpop(String paramString) throws TvlCacheException;

	String rpop(String paramString) throws TvlCacheException;

	Long sadd(String paramString, String... paramVarArgs) throws TvlCacheException;

	Set<String> smembers(String paramString) throws TvlCacheException;

	Long srem(String paramString, String... paramVarArgs) throws TvlCacheException;

	String spop(String paramString) throws TvlCacheException;

	Set<String> spop(String paramString, long paramLong) throws TvlCacheException;

	Long scard(String paramString) throws TvlCacheException;

	Boolean sismember(String paramString1, String paramString2) throws TvlCacheException;

	String srandmember(String paramString) throws TvlCacheException;

	List<String> srandmember(String paramString, int paramInt) throws TvlCacheException;

	Long strlen(String paramString) throws TvlCacheException;

	Long zadd(String paramString1, double paramDouble, String paramString2) throws TvlCacheException;

	Long zadd(String paramString, Map<String, Double> paramMap) throws TvlCacheException;

	Set<String> zrange(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	Long zrem(String paramString, String... paramVarArgs) throws TvlCacheException;

	Double zincrby(String paramString1, double paramDouble, String paramString2) throws TvlCacheException;

	Long zrank(String paramString1, String paramString2) throws TvlCacheException;

	Long zrevrank(String paramString1, String paramString2) throws TvlCacheException;

	Set<String> zrevrange(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	Set<Tuple> zrangeWithScores(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	Set<Tuple> zrevrangeWithScores(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	Long zcard(String paramString) throws TvlCacheException;

	Double zscore(String paramString1, String paramString2) throws TvlCacheException;

	List<String> sort(String paramString) throws TvlCacheException;

	List<String> sort(String paramString, SortingParams paramSortingParams) throws TvlCacheException;

	Long zcount(String paramString, double paramDouble1, double paramDouble2) throws TvlCacheException;

	Long zcount(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2) throws TvlCacheException;

	Set<String> zrangeByScore(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2) throws TvlCacheException;

	Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<String> zrevrangeByScore(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Set<String> zrangeByScore(String paramString1, String paramString2, String paramString3, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<Tuple> zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2)
			throws TvlCacheException;

	Set<Tuple> zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2)
			throws TvlCacheException;

	Set<Tuple> zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<String> zrevrangeByScore(String paramString1, String paramString2, String paramString3, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<Tuple> zrangeByScoreWithScores(String paramString1, String paramString2, String paramString3)
			throws TvlCacheException;

	Set<Tuple> zrevrangeByScoreWithScores(String paramString1, String paramString2, String paramString3)
			throws TvlCacheException;

	Set<Tuple> zrangeByScoreWithScores(String paramString1, String paramString2, String paramString3, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<Tuple> zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Set<Tuple> zrevrangeByScoreWithScores(String paramString1, String paramString2, String paramString3, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Long zremrangeByRank(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	Long zremrangeByScore(String paramString, double paramDouble1, double paramDouble2) throws TvlCacheException;

	Long zremrangeByScore(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Long zlexcount(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Set<String> zrangeByLex(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Set<String> zrangeByLex(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
			throws TvlCacheException;

	Set<String> zrevrangeByLex(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Set<String> zrevrangeByLex(String paramString1, String paramString2, String paramString3, int paramInt1,
			int paramInt2) throws TvlCacheException;

	Long zremrangeByLex(String paramString1, String paramString2, String paramString3) throws TvlCacheException;

	Long linsert(String paramString1, BinaryClient.LIST_POSITION paramLIST_POSITION, String paramString2,
			String paramString3) throws TvlCacheException;

	Long lpushx(String paramString, String... paramVarArgs) throws TvlCacheException;

	Long rpushx(String paramString, String... paramVarArgs) throws TvlCacheException;

	@Deprecated
	List<String> blpop(String paramString) throws TvlCacheException;

	List<String> blpop(int paramInt, String paramString) throws TvlCacheException;

	@Deprecated
	List<String> brpop(String paramString) throws TvlCacheException;

	List<String> brpop(int paramInt, String paramString) throws TvlCacheException;

	Long del(String paramString) throws TvlCacheException;

	String echo(String paramString) throws TvlCacheException;

	Long move(String paramString, int paramInt) throws TvlCacheException;

	Long bitcount(String paramString) throws TvlCacheException;

	Long bitcount(String paramString, long paramLong1, long paramLong2) throws TvlCacheException;

	@Deprecated
	ScanResult<Map.Entry<String, String>> hscan(String paramString, int paramInt) throws TvlCacheException;

	@Deprecated
	ScanResult<String> sscan(String paramString, int paramInt) throws TvlCacheException;

	@Deprecated
	ScanResult<Tuple> zscan(String paramString, int paramInt) throws TvlCacheException;

	ScanResult<Map.Entry<String, String>> hscan(String paramString1, String paramString2) throws TvlCacheException;

	ScanResult<String> sscan(String paramString1, String paramString2) throws TvlCacheException;

	ScanResult<Tuple> zscan(String paramString1, String paramString2) throws TvlCacheException;

	Long pfadd(String paramString, String... paramVarArgs) throws TvlCacheException;

	long pfcount(String paramString) throws TvlCacheException;

	Set<String> keys(String paramString);

	List<String> mget(String... paramVarArgs);

	String mset(String... paramVarArgs);

	Long msetnx(String... paramVarArgs);
}
