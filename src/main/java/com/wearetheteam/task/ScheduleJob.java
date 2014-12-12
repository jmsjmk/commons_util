/**
 * Project Name:common_util
 * File Name:ScheduleJob.java
 * Package Name:com.wearetheteam.task
 * Date:2014年11月28日下午2:15:48
 *
 */

package com.wearetheteam.task;

/**
 * ClassName:ScheduleJob <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年11月28日 下午2:15:48 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public interface ScheduleJob {

	public void doJob();

	public int getExecuteInterval();

}
