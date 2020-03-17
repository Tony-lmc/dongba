/**
 * 
 */
package com.db.sys.service.impl;

import com.db.sys.service.MessageService;

/**
 * @author lmc
 *
 */
public class MessageServiceImpl implements MessageService{

	@Override
	public void sendMessage(String msg) {
		System.out.println(msg);
	}

}
