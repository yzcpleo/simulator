package com.shhxzq.fin.simulator.biz.service.impl;

import com.shhxzq.fin.simulator.biz.annotation.CacheDeleteAll;
import com.shhxzq.fin.simulator.biz.annotation.CacheGetOrSave;
import com.shhxzq.fin.simulator.biz.annotation.LogTime;
import com.shhxzq.fin.simulator.biz.service.MenuService;
import com.shhxzq.fin.simulator.mapper.MenuMapper;
import com.shhxzq.fin.simulator.model.vo.Menu;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/22
 */
@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @LogTime
    @CacheGetOrSave("menu:username:{0}")
    public List<Menu> findMenusByUsername(String username) {
        List<Menu> menus = menuMapper.selectMenusByUsername(username);
        List<Menu> wrapList = new ArrayList();

        return recursionList(menus, wrapList, "", 0L);
    }

    @Override
    @LogTime
    public boolean existsMenuCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);

        return menuMapper.selectCount(menu) == 1;
    }

    @Override
    @LogTime
    @CacheGetOrSave("menu:code:{0}")
    public Menu findMenuByCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);

        return super.selectOne(menu);
    }

    @Override
    @LogTime
    @CacheDeleteAll("menu")
    public void saveMenu(Menu menu) {
        super.insertSelective(menu);
    }

    @Override
    @LogTime
    @CacheGetOrSave("menu:id:{0}")
    public Menu findMenuById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    @CacheDeleteAll("menu")
    public void updateMenu(Menu menu) {
        super.updateByPrimaryKeySelective(menu);
    }

    @Override
    @LogTime
    @CacheDeleteAll("menu")
    public void deleteMenu(Menu menu) {
        super.deleteByPrimaryKey(menu);
    }

    @Override
    @CacheGetOrSave("menu:role:{0}")
    public List<Menu> findMenus4Role(String code) {
        return menuMapper.selectMenus4Role(code);
    }

    @Override
    @CacheGetOrSave("menu:all")
    public List<Menu> findAllMenus() {
        Example example = new Example(Menu.class);
        example.setOrderByClause("sort asc");

        List<Menu> menus = super.selectByExample(example);
        List<Menu> wrapList = new ArrayList();

        return recursionTreeList(menus, wrapList, "", 0L);
    }

    /**
     * 递归找出 parentCode 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param pcode
     * @param pid
     * @return
     */
    private List<Menu> recursionList(List<Menu> from, List<Menu> toList, String pcode, Long pid) {

        if (CollectionUtils.isEmpty(from)) {
            return null;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (pcode.equals(menu.getPcode())) {
                List<Menu> leaf = new ArrayList();
                menu.setLeaf(leaf);
                menu.setPid(pid);
                toList.add(menu);
                recursionList(from, leaf, menu.getCode(), menu.getId());
            }
        }
        return toList;
    }

    /**
     * 递归找出 parentCode 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param pcode
     * @param pid
     * @return
     */
    private List<Menu> recursionTreeList(List<Menu> from, List<Menu> toList, String pcode, Long pid) {

        if (CollectionUtils.isEmpty(from)) {
            return null;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (pcode.equals(menu.getPcode())) {
                menu.setPid(pid);
                toList.add(menu);
                recursionTreeList(from, toList, menu.getCode(), menu.getId());
            }
        }
        return toList;
    }
}
