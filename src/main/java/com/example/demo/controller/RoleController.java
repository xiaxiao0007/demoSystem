package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

import com.example.demo.service.IRoleService;
import com.example.demo.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxf
 * @since 23-03-20
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    // 保存或者更新用户数据
    @PostMapping
    public Boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        roleService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    @GetMapping("/{id}")
    public List<Role> findOne(@PathVariable Integer id) {
        return roleService.list();
    }

    @GetMapping("/page")
    public Page<Role> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!"".equals(name),"name",name);
        return roleService.page(new Page<>(pageNum, pageSize),queryWrapper);
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId 角色id
     * @param menuIds 菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return Result.success( roleService.getRoleMenu(roleId));
    }

}

