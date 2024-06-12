package com.rabbiter.market.controller.system.role;

import com.rabbiter.market.common.exception.BusinessException;
import com.rabbiter.market.common.sercurity.annotation.HasPermisson;
import com.rabbiter.market.common.web.response.JsonResult;
import com.rabbiter.market.domain.system.role.Role;
import com.rabbiter.market.qo.system.RoleQuery;
import com.rabbiter.market.service.system.role.IRoleService;
import com.rabbiter.market.vo.role.RolePermissonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 查询角色所有的信息
     *
     * @return
     */
    @HasPermisson("system:role:list")
    @PostMapping("/list")
    public JsonResult list(RoleQuery qo) {
        List<Role> roles = roleService.listByQo(qo);
        return JsonResult.success(roles);
    }

    /**
     * 停用角色
     *
     * @param rid
     * @return
     */
    @HasPermisson("system:role:forbiddenRole")
    @PostMapping("/forbiddenRole")
    public JsonResult forbiddenRole(Long rid) {
        roleService.forbiddenRole(rid);
        return JsonResult.success();
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     */
    @HasPermisson("system:role:edit_role")
    @PostMapping("/edit_role")
    public JsonResult edit_role(Role role) {
        if (Role.SYS_ID == role.getId() || 2L == role.getId()) {
            throw new BusinessException("不能停用系统拥有者");
        }
        if (role != null && StringUtils.hasText(role.getInfo())) {
            roleService.updateById(role);
        }
        return JsonResult.success();
    }

    /**
     * 保存角色信息
     *
     * @param role
     * @return
     */
    @HasPermisson("system:role:save")
    @PostMapping("/save")
    public JsonResult save(Role role) {
        roleService.saveRole(role);
        return JsonResult.success();
    }

    /**
     * 查询角色拥有的权限
     *
     * @param rid
     * @return
     */
    @HasPermisson("system:role:saveRolePermissons")
    @GetMapping("/checkPermissons")
    public JsonResult checkPermissons(@NotNull(message = "角色不能为空") Long rid) {
        RolePermissonVo vo = roleService.checkPermissons(rid);
        return JsonResult.success(vo);
    }

    /**
     * 保存角色-菜单的关系
     *
     * @param rid
     * @param menuIds
     * @return
     */
    @HasPermisson("system:role:saveRolePermissons")
    @PostMapping("/saveRolePermissons")
    public JsonResult saveRolePermissons(@NotNull(message = "角色不能为空") @RequestParam("rid") Long rid, @RequestParam("menuIds") Long[] menuIds) {
        roleService.saveRolePermissons(rid, menuIds);

        return JsonResult.success();

    }

    @GetMapping("/all")
    public JsonResult getRoleAll() {
        List<Map<String, Object>> list = roleService.getRoleAll();
        return JsonResult.success(list);
    }

    @HasPermisson("personnel_management:employee:queryRoleIdsByEid")
    @GetMapping("/queryRoleIdsByEid")
    public JsonResult queryRoleIdsByEid(Long eid) {

        List<Long> list = roleService.queryRoleIdsByEid(eid);
        return JsonResult.success(list);
    }
    @HasPermisson("personnel_management:employee:queryRoleIdsByEid")
    @PostMapping("/saveRoleEmp")
    public JsonResult saveRoleEmp(Long eid, Long[] empRoleIds, HttpServletRequest request) {
        String token = request.getHeader("token");
        roleService.saveRoleEmp(eid, empRoleIds, token);
        return JsonResult.success();

    }
}
