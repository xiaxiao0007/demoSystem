package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxf
 * @since 23-03-29
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name);


    void setStudentCourse(Integer courseId, Integer studentId);
}
