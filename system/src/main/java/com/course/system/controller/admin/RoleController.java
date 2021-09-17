package com.course.system.controller.admin;

import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.RoleDto;
import com.course.server.service.RoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Jessica
 * @Version v
 * @Date 2021/9/7
 */
@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    public static final String BUSINESS_NAME = "角色";

    @Resource
    private RoleService roleService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        roleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleDto roleDto) {

        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        roleService.delete(id);
        return responseDto;
    }

    /**
     * 保存资源
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto){
        LOG.info("保存角色资源关联开始");
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 加载已关联的资源
     */
    @GetMapping("/list-resource/{roleId}")
    public ResponseDto listResource(@PathVariable String roleId){
        LOG.info("加载资源开始");
        ResponseDto responseDto = new ResponseDto();
        List<String> resourceIdList = roleService.listResource(roleId);
        responseDto.setContent(resourceIdList);
        return responseDto;
    }

    /**
     * 保存用户
     */
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RoleDto roleDto){
        LOG.info("保存用户关联角色开始");
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.saveUser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }
}