package com.shhxzq.fin.simulator.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.fin.simulator.biz.annotation.CacheDelete;
import com.shhxzq.fin.simulator.biz.annotation.CacheDeleteAll;
import com.shhxzq.fin.simulator.biz.annotation.CacheGetOrSave;
import com.shhxzq.fin.simulator.biz.annotation.LogTime;
import com.shhxzq.fin.simulator.biz.service.UserService;
import com.shhxzq.fin.simulator.common.util.Digests;
import com.shhxzq.fin.simulator.common.util.Encodes;
import com.shhxzq.fin.simulator.mapper.RoleMapper;
import com.shhxzq.fin.simulator.mapper.UserMapper;
import com.shhxzq.fin.simulator.model.constants.AppConstants;
import com.shhxzq.fin.simulator.model.vo.ShiroUser;
import com.shhxzq.fin.simulator.model.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/22
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @LogTime
    @CacheGetOrSave("user:username:{0}")
    public User findUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return super.selectOne(user);
    }

    @Override
    @LogTime
    public ShiroUser getShiroUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    @LogTime
    public List<User> searchUsers(int pageNum, String fullname) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(fullname)) {
            criteria.andEqualTo("fullname", fullname);
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    @LogTime
    @CacheDeleteAll("user:all")
    public void saveUserWithDefaultRole(User user) {
        entryptPassword(user);
        super.insertSelective(user);

        saveUserRoles(user.getUsername(), AppConstants.DEFAULT_ROLE_CODE);
    }

    @Override
    @LogTime
    @CacheGetOrSave("user:id:{0}")
    public User findUserById(Long id) {
        User user =  super.selectByPrimaryKey(id);

        if (user != null) {
            user.setPassword(null);
            user.setSalt(null);
        }
        return user;
    }

    @Override
    @LogTime
    @CacheDelete("user:id:{0:id}||user:username:{0:username}||user:all")
    public void updateUser(User user) {
        super.updateByPrimaryKeySelective(user);
    }

    @Override
    @LogTime
    @CacheDelete("user:id:{0:id}||user:username:{0:username}")
    public void updateUserPassword(User user) {
        entryptPassword(user);
        super.updateByPrimaryKeySelective(user);
    }

    @Override
    @LogTime
    @CacheDelete("menu:username:{0}||role:username:{0}")
    public void updateUserRoles(String username, String roleCodes) {
        roleMapper.deleteAllRolesByUsername(username);

        if (StringUtils.isNotEmpty(roleCodes)) {
            saveUserRoles(username, roleCodes);
        }
    }

    @Override
    @LogTime
    public boolean existsUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectCount(user) == 1;
    }

    /**
     * 批量保存用户角色
     *
     * @param username
     * @param roleCodes
     */
    private void saveUserRoles(String username, String roleCodes) {
        userMapper.insertUserRoles(username, Arrays.asList(roleCodes.split(",")));
    }

    /**
     * 设定安全的密码，生成随机的salt并经过N次 sha-1 hash
     *
     * @param user
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(AppConstants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, AppConstants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }
}
