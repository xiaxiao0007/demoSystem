package com.example.demo.mapper;

import com.example.demo.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxf
 * @since 23-03-21
 */
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select id from sys_menu")
    List<Integer> findIds();
}
