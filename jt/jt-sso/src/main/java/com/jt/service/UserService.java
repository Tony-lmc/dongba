package com.jt.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.vo.SysResult;

public interface UserService {

	Boolean checkUser(String params, Integer type);

	void saveUser(User user);


}
