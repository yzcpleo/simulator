package com.shhxzq.fin.simulator.biz.service;


import com.shhxzq.fin.simulator.model.vo.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/22
 */
public interface RoleService {

    /**
     * 根据用户名查找角色
     *
     * @param username
     * @return
     */
    List<Role> findRolesByUsername(String username);

    /**
     * 查找所有角色
     *
     * @return
     */
    List<Role> findAllRoles();

    /**
     * 校验角色代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsRoleCode(String code);

    /**
     * 搜索角色
     *
     * @param pageNum
     * @param code
     * @param name
     * @return
     */
    List<Role> searchRoles(int pageNum, String code, String name);

    /**
     * 保存角色
     *
     * @param role
     */
    void saveRole(Role role);

    /**
     * 根据id查找角色
     *
     * @param id
     * @return
     */
    Role findRoleById(Long id);

    /**
     * 更新角色
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 更新角色菜单
     *
     * @param code
     * @param menuCodes
     */
    void updateRoleMenus(String code, String menuCodes);
}
