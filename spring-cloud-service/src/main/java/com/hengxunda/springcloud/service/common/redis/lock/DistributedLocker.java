package com.hengxunda.springcloud.service.common.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public interface DistributedLocker {

    /**
     * 加锁
     *
     * @param lockKey
     * @return
     */
    RLock lock(final String lockKey);

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout
     * @return
     */
    RLock lock(final String lockKey, final int timeout);

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout
     * @param unit
     * @return
     */
    RLock lock(final String lockKey, final int timeout, final TimeUnit unit);

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     */
    boolean tryLock(final String lockKey, final int waitTime, final int leaseTime, final TimeUnit unit);

    /**
     * 释放锁
     *
     * @param lockKey
     */
    void unlock(final String lockKey);

    /**
     * 释放锁
     *
     * @param lock
     */
    void unlock(final RLock lock);

    void setRedissonClient(final RedissonClient redissonClient);
}