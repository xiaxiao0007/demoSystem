package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxf
 * @since 23-03-29
 */
@Getter
@Setter
@TableName("sys_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 学分
     */
    private Integer score;

    /**
     * 上课时间
     */
    private String times;

    /**
     * 是否开课
     */
    private Boolean state;

    /**
     * 授课老师id
     */
    private Integer teacherId;

    @TableField(exist = false)
    private String teacher;


}
