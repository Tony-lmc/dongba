package com.db.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;

/**
 * 借助realm实现授权和认证
 * @author lmc
 *
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {//授权信息
		//1.获取登录用户id
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		//2.基于用户id获取用户对应的角色id并判定
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if(roleIds==null||roleIds.isEmpty())
			throw new AuthorizationException();
		//3.基于角色id获取对应的菜单id并判定
		Integer[] array = {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));//将roleID 利用toarray泛型方法转化为数组
		if(menuIds==null||menuIds.isEmpty())
			throw new AuthorizationException();
		//4.基于菜单id获取权限表示
		List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
		if(permissions==null||permissions.isEmpty())
			throw new AuthorizationException();
		//5.封装数据并返回
		Set<String> stringPermissions = new HashSet<String>();//需要set集合 转化为set set集合可以去重
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for(String per:permissions) {
			if(!StringUtils.isEmpty(per)) {
				stringPermissions.add(per);//如果per不为空 则往set集合里面放数据
			}
		}
		info.setStringPermissions(stringPermissions);
		return info;
	}
	
	/**
	 * 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {//认证信息
		//1 从token获取用户输入信息
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		//2 基于用户输入信息查询数据库中用户信息
		SysUser user = sysUserDao.findUserByUserName(username);
		//3 判断用户是否存在
		if(user==null)
			throw new UnknownAccountException();//shiro提供的异常
		//4 判断用户是否被禁用
		if(user.getValid()==0)
			throw new LockedAccountException();
		//5 封装并返回信息
		ByteSource credentialssalt=ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, //表示身份
																	user.getPassword(),//表示已加密的密码
																	credentialssalt,// salt
																	"shiroUserName");//
		return info;//此对象返回给认证管理器
	}
	
	/**
	 * 设置凭证匹配器 告诉认证管理器采用的加密方式
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		//1.构建setCredentialsMatcher对象
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		//2.设置加密算法
		hcm.setHashAlgorithmName("MD5");
		//3.设置加密次数
		hcm.setHashIterations(1);
		super.setCredentialsMatcher(hcm);
	}

}
