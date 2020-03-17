package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 商品信息秒速表对象
 * @author lmc
 *
 */
@Data
@Accessors(chain = true)
@TableName("tb_item_desc")
@NoArgsConstructor
@AllArgsConstructor
public class ItemDesc extends BasePojo{
	@TableId
	private Long itemId;
	private String itemDesc;
}
