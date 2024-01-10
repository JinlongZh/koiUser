package com.koi.system.service.permission.impl;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.constants.MenuTypeEnum;
import com.koi.system.domain.permission.entity.Menu;
import com.koi.system.domain.permission.vo.response.MenuListRespVO;
import com.koi.system.mapper.mysql.permission.MenuMapper;
import com.koi.system.service.permission.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单接口实现类
 *
 * @Author zjl
 * @Date 2024/1/6 17:59
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuListRespVO> listUserMenus() {
        // TODO 根据角色获取
        List<Menu> menuList = menuMapper.selectList(null);
        // 获取目录
        List<Menu> directoryList = listDirectory(menuList);
        // 根据目录id获取菜单map
        Map<Long, List<Menu>> menuMap = convertMenuMap(menuList);
        // 转化为前端需要的结构
        List<MenuListRespVO> menuListRespVOList = directoryList.stream().map(directory -> {
                    MenuListRespVO menuListRespVO = BeanCopyUtils.copyObject(directory, MenuListRespVO.class);
                    List<MenuListRespVO> childrenMenuListRespVO = BeanCopyUtils.copyList(menuMap.get(directory.getId()), MenuListRespVO.class).stream()
                            .sorted(Comparator.comparingInt(MenuListRespVO::getOrderNum))
                            .collect(Collectors.toList());
                    menuListRespVO.setChildren(childrenMenuListRespVO);
                    return menuListRespVO;
                }).collect(Collectors.toList());
        // 组装没有目录的菜单
        List<Menu> singleMenu = menuList.stream()
                .filter(menu -> Objects.equals(menu.getType(), MenuTypeEnum.MENU.getType()) && menu.getParentId() == null)
                .collect(Collectors.toList());
        menuListRespVOList.addAll(BeanCopyUtils.copyList(singleMenu, MenuListRespVO.class));
        // 排序
        return menuListRespVOList.stream().sorted(Comparator.comparingInt(MenuListRespVO::getOrderNum))
                .collect(Collectors.toList());
    }

    private List<Menu> listDirectory(List<Menu> menuList) {
        return menuList.stream()
                .filter(menu -> Objects.equals(menu.getType(), MenuTypeEnum.DIR.getType()))
                .collect(Collectors.toList());
    }

    private Map<Long, List<Menu>> convertMenuMap(List<Menu> menuList) {
        return menuList.stream()
                .filter(menu -> Objects.nonNull(menu.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }

}
