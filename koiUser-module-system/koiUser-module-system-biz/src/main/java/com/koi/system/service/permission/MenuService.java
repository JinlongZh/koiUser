package com.koi.system.service.permission;

import com.koi.system.domain.permission.vo.response.MenuListRespVO;

import java.util.List;

/**
 * 菜单接口
 *
 * @Author zjl
 * @Date 2024/1/6 17:58
 */
public interface MenuService {

    List<MenuListRespVO> listUserMenus();
}
