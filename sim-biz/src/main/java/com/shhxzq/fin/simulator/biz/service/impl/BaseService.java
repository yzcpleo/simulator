package com.shhxzq.fin.simulator.biz.service.impl;

import com.shhxzq.fin.simulator.mapper.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 16/5/23
 */
@Service
public abstract class BaseService<T> {

    @Autowired
    protected MyMapper<T> mapper;

    /**
     * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
     *
     * @param entity
     * @return List<T>
     */
    public List<T> select(T entity) {
        return mapper.select(entity);
    }

    /**
     * 根据 Example 对象查询，Example 可灵活定义
     *
     * @param example
     * @return
     */
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    /**
     * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
     *
     * @param entity
     * @return int
     */
    public int count(T entity) {
        return mapper.selectCount(entity);
    }

    /**
     * 根据主键进行查询,必须保证结果唯一
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     *
     * @param key
     * @return T
     */
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 根据任意属性返回单条记录
     *
     * @param entity
     * @return T
     */
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    /**
     * 插入一条数据
     * 优先使用传入的参数值，参数值空时，才会使用序列、UUID，自动增长
     *
     * @param entity
     * @return int
     */
    public int save(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
     * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     *
     * @param entity
     * @return int
     */
    public int insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     *
     * @param entity
     * @return int
     */
    public int delete(T entity) {
        return mapper.delete(entity);
    }

    /**
     * 通过主键进行删除,这里最多只会删除一条数据
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     *
     * @param key
     * @return int
     */
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据主键进行更新,这里最多只会更新一条数据
     *
     * @param entity
     * @return int
     */
    public int updateByPrimaryKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键进行更新
     * 只会更新不是null的数据
     *
     * @param entity
     * @return int
     */
    public int updateByPrimaryKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

}

