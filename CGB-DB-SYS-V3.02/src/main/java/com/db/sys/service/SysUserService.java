package com.db.sys.service;

import java.util.Map;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserService 
       extends PageService<SysUserDeptVo>{
	
	 
	 Map<String,Object> findObjectById(
			 Integer id);
	
	 /**
	  * 更新用户以及用户对应的角色信息
	  * @param entity
	  * @param roleIds
	  * @return
	  */
	 int updateObject(SysUser entity,
			 Integer[] roleIds);
	/**
	   * 保存用户以及用户对应的角色信息
	   * @param entity
	   * @param roleIds
	   * @return
	   */
	  int saveObject(SysUser entity,
			         Integer[] roleIds);

	  int validById(Integer id,Integer valid,String modifiedUser);
}
