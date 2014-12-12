/**
 * Project Name:common_util
 * File Name:SimulateLoad.java
 * Package Name:com.wearetheteam.util
 * Date:2014年12月1日上午11:40:26
 *
 */

package com.wearetheteam.util;

/**
 * 示例save action配置 <br/>
 * 1、方法的排序优先级为static > 方法名字母顺序<br/>
 * 2、private方法或参数如果没有使用会被自动删除<br/>
 * 3、static变量和方法排在public变量和方法之前<br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年12月1日 上午11:40:26 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class SaveActionSample {

	public static String c;

	private static void bsv() {
		System.out.println("");
	}

	private final static String bsv1(int a) {
		System.out.println("");
		return "";
	}

	public final static String bsv2() {
		System.out.println("");
		return "";
	}

	private static void psv() {
		System.out.println("");
	}

	public String a;

	private void asva() {
		System.out.println("");
	}

	public void doJob() {
		String str = "";
		for (int i = 0; i < 10000; i++) {
			str += "" + i;
		}
		if (str.equals("----")) {
			System.out.println("");
		}
		psv();
		bsv();
		asva();
		bsv1(1);
	}

	public int getExecuteInterval() {
		// TODO Auto-generated method stub
		return 10;
	}

}
