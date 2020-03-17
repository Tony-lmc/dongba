package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo{
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long parentId; //父级Id
	private String name;
	private Integer status;
	private Integer sortOrder;
	private Boolean isParent;//是否为父级
	
}
