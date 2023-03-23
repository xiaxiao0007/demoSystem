package com.example.demo.mapper;

import com.example.demo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxf
 * @since 23-03-20
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from sys_role_menu where flag = #{flag}")
    Integer findIdByFlag(@Param("flag") String flag);

}
