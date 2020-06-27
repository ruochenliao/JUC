package com.example.juc;

/**
 * HashTable 锁全表, 效率非常低
 *
 * ConcurrentHashMap 采用锁分段机制，默认分16段，每个段有默认最多有16 个元素，每个段都有一个独立的锁
 *
 * JDK 8 以后，如果 Hash 上的值是 null, 则使用 CAS 插入
 *            如果 Hash 冲突了，则使用 synchronized 对链表操作加锁
 */
public class ConcurrentHashMapTest {
}
