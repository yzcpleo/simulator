package com.shhxzq.fin.simulator.mapper;

import com.shhxzq.fin.simulator.model.vo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends MyMapper<Role> {

    /**
     * 根据用户名查找角色
     *
     * @param username
     * @return
     */
    List<Role> selectRolesByUsername(String username);

    /**
     * 删除所有用户角色
     *
     * @param username
     */
    void deleteAllRolesByUsername(String username);

    /**
     * 删除角色菜单
     *
     * @param code
     */
    void deleteRoleMenus(@Param("code") String code);

    /**
     * 插入角色菜单
     *
     * @param code
     * @param menuCodes
     */
    void insertRoleMenus(@Param("code") String code, @Param("menuCodes") List<String> menuCodes);

}