package com.example.demo.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxf
 * @since 2023-03-04
 */
@Getter
@Setter
@TableName("sys_user")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Alias("用户名")
    private String username;

    @Alias("密码")
    private String password;

    @Alias("昵称")
    private String nickname;

    @Alias("电话")
    private String phone;

    @Alias("邮箱")
    private String email;

    @Alias("地址")
    private String address;

    @Alias("创建时间")
    private LocalDateTime createTime;
    @Alias("头像地址")
    private String avatarUrl;

}
