package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 为了减少用户的操作
 * @author lmc
 *
 */
//系统返回值对象
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
	private Integer status;//200表示成功 201表示失败  在JS代码里规定
	private String msg;
	private Object data;//后台返回的数据

	public static SysResult ok() {
		return new SysResult(200,null,null);
	}
	public static SysResult ok(Object data) {
		return new SysResult(200,null,data);
	}
	public static SysResult ok(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	public static SysResult fail() {
		return new SysResult(201,null,null);
	}
	public static SysResult fail(String msg) {
		return new SysResult(201,msg,null);
	}
}
