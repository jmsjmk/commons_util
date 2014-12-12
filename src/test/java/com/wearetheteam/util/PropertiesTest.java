/**
 * Project Name:common_util
 * File Name:PropertiesTest.java
 * Package Name:com.wearetheteam.util
 * Date:2014年12月9日下午4:32:31
 *
 */

package com.wearetheteam.util;

import org.junit.Test;

/**
 * ClassName:PropertiesTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年12月9日 下午4:32:31 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class PropertiesTest {

	@Test
	public void test() {
		PropertiesLoader.getInstance();
		MonitorJvm.getInstance();
	}

}
