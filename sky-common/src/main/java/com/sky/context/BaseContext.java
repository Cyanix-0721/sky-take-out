/**
 * BaseContext 类用于管理当前线程的上下文信息。
 * 它提供了一种在同一个线程内共享数据的方法，尤其适用于那些需要跨方法调用的场景，
 * 保证了数据在线程内的封闭性和安全性。
 */
package com.sky.context;

public class BaseContext {

	/**
	 * 使用 ThreadLocal 存储当前线程的上下文ID。
	 * ThreadLocal 为每个线程提供了一个独立的变量副本，确保了变量在不同线程之间的隔离性。
	 */
	public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

	/**
	 * 获取当前线程的上下文ID。
	 *
	 * @return 当前线程的上下文ID，如果未设置则返回null。
	 */
	public static Long getCurrentId() {
		return threadLocal.get();
	}

	/**
	 * 设置当前线程的上下文ID。
	 *
	 * @param id 要设置的上下文ID。
	 */
	public static void setCurrentId(Long id) {
		threadLocal.set(id);
	}

	/**
	 * 移除当前线程的上下文ID。
	 * 这个方法通常在不再需要上下文信息或者线程即将结束时调用，以避免内存泄漏。
	 */
	public static void removeCurrentId() {
		threadLocal.remove();
	}

}
