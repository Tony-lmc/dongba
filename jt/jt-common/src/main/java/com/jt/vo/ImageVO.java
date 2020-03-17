package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
/**
 * 上传图片vo对象
 * @author lmc
 *
 */
public class ImageVO implements Serializable{
	private Integer error;
	private String url;
	private Integer width;
	private Integer height;
}
