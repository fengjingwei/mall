package com.hengxunda.springcloud.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class Collections3Utils {

    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    public static String convertToString(final Collection collection, final String prefix, final String postfix) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(prefix).append(o).append(postfix);
        }
        return builder.toString();
    }

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    /**
     * 获取Collection的最后一个元素,如果collection为空返回null
     *
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        // 当类型为List时，直接取得最后一个元素
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }

        // 其他类型通过iterator滚动到最后一个元素
        Iterator<T> iterator = collection.iterator();
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * 返回a + b的新List
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回a - b的新List
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<>(a);
        for (T element : b) {
            list.remove(element);
        }
        return list;
    }

    /**
     * 返回a与b的交集的新List
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<>();
        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    public static <R> List<R> copy(List<?> source, Class<R> classz) {
        return copyProperties(source, classz);
    }

    private static <R> List<R> copyProperties(List<?> sources, Class<R> classz) {
        List<R> target = Lists.newCopyOnWriteArrayList();
        if (isNotEmpty(sources)) {
            sources.forEach(source -> {
                try {
                    final R r = classz.newInstance();
                    BeanUtils.copyProperties(source, r);
                    target.add(r);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        return target;
    }
}
