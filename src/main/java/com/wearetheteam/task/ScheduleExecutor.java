/**
 * Project Name:common_util
 * File Name:AbstratcSchedulJob.java
 * Package Name:com.wearetheteam.task
 * Date:2014年11月28日下午2:20:47
 *
 */

package com.wearetheteam.task;

import org.apache.log4j.Logger;

/**
 *
 * ClassName: ScheduleExecutor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月28日 下午3:08:42 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 */
public class ScheduleExecutor {

	private final static Logger logger = Logger.getLogger(ScheduleExecutor.class);

	public ScheduleExecutor(final ScheduleJob task) {
		logger.info("Init ScheduleJob " + task);
		start(task);
	}

	public void start(final ScheduleJob task) {

		new Thread(new Runnable() {
			public void run() {
				final long intervalTime = task.getExecuteInterval();
				logger.info("AbstratcSchedulJob heartbeat interval:" + intervalTime);
				while (true) {
					try {
						task.doJob();
					} catch (final Exception e) {
						logger.error(task.getClass() + " doJob error msg:" + e.getMessage());
					}
					try {
						Thread.sleep(intervalTime);
					} catch (final InterruptedException e) {
						logger.error(ScheduleExecutor.class + " sleep error! msg:" + e.getMessage());
					}
				}
			}
		}).start();
	}
}
