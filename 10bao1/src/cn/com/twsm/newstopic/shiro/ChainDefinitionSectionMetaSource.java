package cn.com.twsm.newstopic.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;

import cn.com.twsm.newstopic.dao.PermissionMapper;
import cn.com.twsm.newstopic.model.Permission;

public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{
	@Resource
	private PermissionMapper permissionMapper;
	/*
	 * 静态资源访问权限
	 */
	private String filterChainDefinitions = null;
	
	/*
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"%s\"]";
	
	public static final String PREMISSION_STRING_PREFIX = "perms[\"%s\"\\**]";
	
	@Override
	public Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<Permission> pList = permissionMapper.getByWhere(whereMap);
		for (Permission permission : pList) {
			// 构成permission字符串
			if (StringUtils.isNotEmpty(permission.getPermissionUrl()) && StringUtils.isNotEmpty(permission.getPermissionCode())) {
				// 如果不为空值添加到section中
				section.put(permission.getPermissionUrl(),String.format(PREMISSION_STRING,permission.getPermissionCode()));
				// 不对角色进行权限验证,如需要则 permission = "roles[" + resources.getResKey() + "]";
			}
		}
		// 所有资源的访问权限，必须放在最后
		section.put("/**", "authc");
		return section;
	}
	
	

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	
}
