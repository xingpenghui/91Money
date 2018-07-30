package com.qfedu.service.admin.impl;

import com.qfedu.mapper.admin.SysMenuMapper;
import com.qfedu.mapper.admin.SysUserMapper;
import com.qfedu.domain.admin.SysMenu;
import com.qfedu.service.admin.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:36*/
@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;
	/*@Autowired
	private SysUserService sysUserService;*/
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysMenu> queryListAll() {
        return sysMenuMapper.queryListAll();
    }

//    @Override
//	public DataGridResult getPageList(Query query) {
//
//		List<SysMenu> rows = sysMenuMapper.queryList(query);
//		int total = sysMenuMapper.queryTotal(query);
//
//		//创建DataGridResult对象
//		DataGridResult dataGridResult = new DataGridResult(rows, total);
//		return dataGridResult;
//	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void deleteBatch(Long[] menuIds) {
		sysMenuMapper.deleteBatch(menuIds);
	}

    @Override
    public List<SysMenu> queryNotButtonList() {
        return sysMenuMapper.queryNotButtonList();
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void save(SysMenu menu) {
		sysMenuMapper.insertSelective(menu);
	}

    @Override
    public SysMenu queryObject(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(SysMenu menu) {
		sysMenuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public Set<String> getUserPermissions(long userId) {
		List<String> permsList = null;
		//超级用户（id=1的用户），拥有最高权限
		if(userId == 1){
			List<SysMenu> menuList = queryListAll();
			permsList = new ArrayList<String>(menuList.size());
			for(SysMenu menu : menuList){
				permsList.add(menu.getPerms());
			}
		}else{
			permsList = sysMenuMapper.queryAllPerms(userId);
		}

		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList){
			if(StringUtils.isEmpty(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		return permsSet;
	}

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId ==1){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysMenuMapper.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 根据父菜单，查询当前用户可见的子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID列表
     */
    private List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        //根据副菜单id获取子菜单列表
        List<SysMenu> menuList = sysMenuMapper.queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        //过滤出当前用户有权查看的子菜单列表
        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 填充当前级别菜单列表的下一层菜单列表：递归形成树形结构
     * @param menuList 当前用户可访问的菜单（当前级别）
     * @param menuIdList 当前用户可访问的菜单id列表
     * @return
     */
    private void getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
        for(SysMenu entity : menuList){
            if(entity.getType() == 0){//目录
                //根据菜单id获取当前用户可见的子菜单列表
                List<SysMenu> subMenuList = queryListParentId(entity.getMenuId(), menuIdList);
                getMenuTreeList(subMenuList, menuIdList);
                entity.setList(subMenuList);
            }
        }
    }
    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

}
