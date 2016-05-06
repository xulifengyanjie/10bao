package cn.com.twsm.newstopic.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import cn.com.twsm.newstopic.model.Permission;
import cn.com.twsm.newstopic.model.Role;
import cn.com.twsm.newstopic.model.User;
import cn.com.twsm.newstopic.service.PermissionService;
import cn.com.twsm.newstopic.service.RoleService;
import cn.com.twsm.newstopic.service.UserService;

public class MyRealm extends AuthorizingRealm {
	@Resource
	private UserService userService;
	
	@Resource
	private PermissionService permissionService;
	
	@Resource
	private RoleService roleService;
	
	
	public MyRealm() {
		super();
		setCachingEnabled(false);
	}

	/**
	 * 为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
	 * @see 个人感觉若使用了Spring3
	 *      .1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
	 * @see 比如说这里从数据库获取权限信息时 ,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		if (loginName != null) {
			User user = userService.getByLoginName(loginName);
			List<Role> roles = user.getRoles();
			//List<ResourceEntity> resourceList = permissionMapper.findResourcesByUserId(Integer.valueOf(userId));
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色集合
			// info.addRole("default");
			// 用户的角色集合
			// info.setRoles(user.getRolesName());
			// 用户的角色对应的所有权限，如果只使用角色定义访问权限
			List<Permission> permissions = null;
			List<Permission> menus = new ArrayList<Permission>();
			Role r = null;
			if(roles != null){
				for (Role role : roles) {
					r = roleService.getPermissionByRoleId(role.getId());
					permissions = r.getPermissions();
					if(permissions != null){
						for(Permission permission : permissions){
							info.addStringPermission(permission.getPermissionCode());
							menus.add(permission);
						}
					}
				}
			}
			this.setSession("cur_user", user);
			this.setSession("menus", menus);
			return info;
		}
		return null;
	}

	/**
	 * 验证当前登录的Subject
	 * 
	 * @see 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		// 两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		System.out.println("验证当前Subject时获取到token为"
				+ ReflectionToStringBuilder.toString(token,
						ToStringStyle.MULTI_LINE_STYLE));
		User user = userService.getByLoginName(token.getUsername());
		
		if(null != user){
			AuthenticationInfo authcInfo = new
			SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(),
			user.getUserName());
			
			//用户所有菜单
			List<Role> roles = user.getRoles();
			List<Permission> permissions = null;
			List<Permission> menus = new ArrayList<Permission>();
			Role r = null;
			if(roles != null){
				for (Role role : roles) {
					r = roleService.getPermissionByRoleId(role.getId());
					permissions = r.getPermissions();
					if(permissions != null){
						for(Permission permission : permissions){
							if("menu".equals(permission.getPermissionType())){
								menus.add(permission);
							}
						}
					}
				}
			}
			this.setSession("cur_user", user);
			this.setSession("menus", menus);
			return authcInfo;
		}else{
			return null;
		}
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}
}
