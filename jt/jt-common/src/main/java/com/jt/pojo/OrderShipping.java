package com.jt.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@TableName("tb_order_shipping")
@Data
@Accessors(chain=true)
public class OrderShipping extends BasePojo{
	
	@TableId
    private String orderId;

    private String receiverName;//js

    private String receiverPhone;//js

    private String receiverMobile;//js

    private String receiverState;//js

    private String receiverCity;//js

    private String receiverDistrict;//js

    private String receiverAddress;//js

    private String receiverZip;
    
}