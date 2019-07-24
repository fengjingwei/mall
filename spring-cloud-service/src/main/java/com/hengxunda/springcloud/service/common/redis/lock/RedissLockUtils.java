package com.hengxunda.springcloud.service.common.redis.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class RedissLockUtils {

    private static DistributedLocker redissLock;

    static void setLocker(DistributedLocker locker) {
        redissLock = locker;
    }

    public static RLock lock(String lockKey) {
        return redissLock.lock(lockKey);
    }

    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    public static void unlock(RLock lock) {
        redissLock.unlock(lock);
    }

    public static RLock lock(String lockKey, int timeout) {
        return redissLock.lock(lockKey, timeout);
    }

    public static RLock lock(String lockKey, int timeout, TimeUnit unit) {
        return redissLock.lock(lockKey, timeout, unit);
    }

    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, waitTime, leaseTime, TimeUnit.SECONDS);
    }

    public static boolean tryLock(String lockKey, int waitTime, int leaseTime, TimeUnit unit) {
        return redissLock.tryLock(lockKey, waitTime, leaseTime, unit);
    }
}