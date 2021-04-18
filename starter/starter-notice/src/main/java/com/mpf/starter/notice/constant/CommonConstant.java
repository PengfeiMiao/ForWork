package com.mpf.starter.notice.constant;

/**
 * 通用业务常量信息
 *
 * @author egoo
 * @date 2020/3/2
 */
public class CommonConstant {
	/**
	 * 业务状态[1:正常]
	 */
	int DB_STATUS_NORMAL = 1;

	/**
	 * 删除状态[0:正常,1:删除]
	 */
	int DB_NOT_DELETED = 0;
	int DB_IS_DELETED = 1;

	/**
	 * 用户锁定状态
	 */
	int DB_ADMIN_NON_LOCKED = 0;
	int DB_ADMIN_LOCKED = 1;

	/**
	 * 默认为空消息
	 */
	public static String DEFAULT_NULL_MESSAGE = "暂无承载数据";

	/**
	 * 默认成功消息
	 */
	public static String DEFAULT_SUCCESS_MESSAGE = "操作成功";

	/**
	 * 默认失败消息
	 */
	public static String DEFAULT_FAILURE_MESSAGE = "操作失败";

	/**
	 * 默认未授权消息
	 */
	public static String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";

}
