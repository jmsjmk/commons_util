package com.wearetheteam.util;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;

import com.wearetheteam.task.ScheduleExecutor;
import com.wearetheteam.task.ScheduleJob;

public class MonitorJvm implements ScheduleJob {

	private static class LazyHolder {
		private static final MonitorJvm INSTANCE = new MonitorJvm();
	}

	private static Logger logger = Logger.getLogger(MonitorJvm.class);

	private static final String MONITOR_JVM_OPEN = "monitorJvmOpen";

	private static final String MONITOR_JVM_PERIOD = "monitorJvmPeriod";

	private static final int MONITOR_JVM_PERIOD_DEFAULT_VALUE = 5000 * 1000;

	public static MonitorJvm getInstance() {
		return LazyHolder.INSTANCE;
	}

	public static String printAvgSystemLoad() {
		final OperatingSystemMXBean oBean = ManagementFactory.getOperatingSystemMXBean();
		final StringBuilder sb = new StringBuilder();
		sb.append(",SystemLoadAverage=" + oBean.getSystemLoadAverage());
		sb.append(",AvailableProcessors=" + oBean.getAvailableProcessors());
		return sb.toString();
	}

	// 垃圾回收 次数 执行时间
	private static String printGarbageCollectorMXBean() {
		final StringBuilder sb = new StringBuilder();
		// 获得单一实例
		final List<GarbageCollectorMXBean> instances = ManagementFactory.getGarbageCollectorMXBeans();
		// 遍历每个实例
		for (final GarbageCollectorMXBean instance : instances) {
			// 返回垃圾收集器的名字

			sb.append("GC_").append(instance.getName().replaceAll(" ", "-")).append("=");
			// 返回已发生的回收的总次数
			sb.append("colletion_count=").append(instance.getCollectionCount());
			// 返回近似的累积回收时间
			sb.append(",collection_time=").append(instance.getCollectionTime()).append(" ");
		}
		return sb.toString();
	}

	// 内存，最大，最小，当前已分配
	private static String printMemoryMXBean() {
		final StringBuilder sb = new StringBuilder();
		// 获得单一实例
		final MemoryMXBean instance = ManagementFactory.getMemoryMXBean();
		// 返回用于对象分配的堆的当前内存使用量
		sb.append("heap_memory_usage=").append(printMemoryUsageInfo(instance.getHeapMemoryUsage()));
		// 返回Java虚拟机使用的非堆内存的当前使用量
		sb.append(" non_heap_memory_usage=").append(printMemoryUsageInfo(instance.getNonHeapMemoryUsage()));
		return sb.toString();
	}

	/**
	 * Returns a descriptive representation of this memory usage.
	 */
	private static String printMemoryUsageInfo(final MemoryUsage usage) {
		final StringBuilder buf = new StringBuilder();
		buf.append("init=").append(usage.getInit() >> 20).append("M,");
		buf.append("used=").append(usage.getUsed() >> 20).append("M,");
		buf.append("committed=").append(usage.getCommitted() >> 20).append("M,");
		buf.append("max=").append(usage.getMax() >> 20).append("M");
		return buf.toString();
	}

	public static String printRuntimeMXBean() {
		final StringBuilder buf = new StringBuilder();
		final RuntimeMXBean rBean = ManagementFactory.getRuntimeMXBean();
		buf.append(",ClassPath=" + rBean.getClassPath());
		buf.append(",BootClassPath=" + rBean.getBootClassPath());
		buf.append(",LibraryPath=" + rBean.getLibraryPath());
		buf.append(",ManagementSpecVersion=" + rBean.getManagementSpecVersion());
		buf.append(",Name=" + rBean.getName());
		buf.append(",SpecName=" + rBean.getSpecName());
		buf.append(",SpecVendor=" + rBean.getSpecVendor());
		buf.append(",getSpecVersion=" + rBean.getSpecVersion());
		buf.append(",getStartTime=" + rBean.getStartTime());
		buf.append(",getUptime=" + rBean.getUptime());
		buf.append(",getVmName=" + rBean.getVmName());
		buf.append(",getVmVendor=" + rBean.getVmVendor());
		buf.append(",getVmVersion=" + rBean.getVmVersion());
		return buf.toString();
	}

	// 线程数目，状态，汇总
	private static String printThreadMXBean() {
		final StringBuilder sb = new StringBuilder();
		// 获得单一实例
		final ThreadMXBean instance = ManagementFactory.getThreadMXBean();
		// 返回活动线程ID
		final ThreadInfo[] threadInfos = instance.getThreadInfo(instance.getAllThreadIds());
		int resinCount = 0;
		int runnableCount = 0;
		int blockedCount = 0;
		int waitingCount = 0;
		for (final ThreadInfo threadInfo : threadInfos) {
			final String name = threadInfo.getThreadName();
			final Thread.State state = threadInfo.getThreadState();
			if (name.contains("resin")) {
				resinCount++;
				if (state.equals(Thread.State.RUNNABLE)) {
					runnableCount++;
				} else if (state.equals(Thread.State.BLOCKED)) {
					blockedCount++;
				} else if (state.equals(Thread.State.WAITING) || state.equals(Thread.State.TIMED_WAITING)) {
					waitingCount++;
				}
			}
		}
		sb.append(",thread=");
		sb.append(",resin_count=").append(resinCount);
		sb.append(",runnable_count=").append(runnableCount);
		sb.append(",block_count=").append(blockedCount);
		sb.append(",waiting_count=").append(waitingCount);
		return sb.toString();
	}

	public static String toString(Object obj) {
		final StringBuilder sb = new StringBuilder();
		try {
			Class class1 = obj.getClass();
			Field afield[] = class1.getDeclaredFields();
			for (Field element : afield) {
				sb.append(element.getName() + "=" + element.get(obj) + "\r\n");
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.out);
		}
		return sb.toString();
	}

	private MonitorJvm() {
		if (PropertiesLoader.getInstance().getBoolean(MONITOR_JVM_OPEN, false)) {
			new ScheduleExecutor(this);
		}
	}

	public void doJob() {
		final StringBuilder sb = new StringBuilder();
		sb.append(printMemoryMXBean()).append(" ");
		sb.append(printThreadMXBean()).append(" ");
		sb.append(printGarbageCollectorMXBean()).append(" ");
		// sb.append(printRuntimeMXBean()).append(" ");
		sb.append(printAvgSystemLoad()).append(" ");
		logger.info(sb.toString());
	}

	public int getExecuteInterval() {
		return PropertiesLoader.getInstance().getInteger(MONITOR_JVM_PERIOD, MONITOR_JVM_PERIOD_DEFAULT_VALUE) * 1000;
	}

}
