package com.koi.system.service.permission;

import com.koi.system.domain.permission.vo.request.MenuAddReqVO;
import com.koi.system.domain.permission.vo.request.MenuQueryReqVO;
import com.koi.system.domain.permission.vo.request.MenuStatusReqVO;
import com.koi.system.domain.permission.vo.request.MenuUpdateReqVO;
import com.koi.system.domain.permission.vo.response.MenuListRespVO;
import com.koi.system.domain.permission.vo.response.MenuRespVO;

import java.util.List;

/**
 * 菜单接口
 *
 * @Author zjl
 * @Date 2024/1/6 17:58
 */
public interface MenuService {

    List<MenuListRespVO> listUserMenus();

    List<MenuRespVO> listMenus(MenuQueryReqVO req);

    void updateMenu(MenuUpdateReqVO req);

    void addMenu(MenuAddReqVO req);

    void deleteMenu(Long menuId);

    void updateMenuStatus(MenuStatusReqVO req);
}
