package com.koi.system.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.constants.MenuTypeEnum;
import com.koi.system.domain.permission.entity.Menu;
import com.koi.system.domain.permission.vo.request.MenuAddReqVO;
import com.koi.system.domain.permission.vo.request.MenuQueryReqVO;
import com.koi.system.domain.permission.vo.request.MenuStatusReqVO;
import com.koi.system.domain.permission.vo.request.MenuUpdateReqVO;
import com.koi.system.domain.permission.vo.response.MenuListRespVO;
import com.koi.system.domain.permission.vo.response.MenuRespVO;
import com.koi.system.mapper.mysql.permission.MenuMapper;
import com.koi.system.service.permission.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

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
        List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getStatus, CommonStatusEnum.ENABLE.getStatus()));
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

    @Override
    public List<MenuRespVO> listMenus(MenuQueryReqVO req) {
        List<Menu> menuList = menuMapper.listMenus(req);
        // 获取目录列表
        List<Menu> directoryList = this.listDirectory(menuList);
        // 根据目录id获取菜单map
        Map<Long, List<Menu>> menuMap = convertMenuMap(menuList);
        // 组装
        List<MenuRespVO> menuRespVOList = directoryList.stream().map(directory -> {
            MenuRespVO menuRespVO = BeanCopyUtils.copyObject(directory, MenuRespVO.class);
            List<MenuRespVO> childrenMenuListRespVO = BeanCopyUtils.copyList(menuMap.get(directory.getId()), MenuRespVO.class).stream()
                    .sorted(Comparator.comparingInt(MenuRespVO::getOrderNum))
                    .collect(Collectors.toList());
            menuRespVO.setChildren(childrenMenuListRespVO);
            return menuRespVO;
        }).collect(Collectors.toList());
        // 组装没有目录的菜单
        List<Menu> singleMenu = menuList.stream()
                .filter(menu -> Objects.equals(menu.getType(), MenuTypeEnum.MENU.getType()) && menu.getParentId() == null)
                .collect(Collectors.toList());
        menuRespVOList.addAll(BeanCopyUtils.copyList(singleMenu, MenuRespVO.class));
        // 排序
        return menuRespVOList.stream().sorted(Comparator.comparingInt(MenuRespVO::getOrderNum))
                .collect(Collectors.toList());
    }

    @Override
    public void updateMenu(MenuUpdateReqVO req) {
        Menu menuUpdate = Menu.builder()
                .id(req.getId())
                .name(req.getName())
                .permission(req.getPermission())
                .type(req.getType())
                .orderNum(req.getOrderNum())
                .parentId(req.getParentId())
                .path(req.getPath())
                .component(req.getComponent())
                .icon(req.getIcon())
                .build();

        checkParentMenu(menuUpdate);

        menuMapper.updateById(menuUpdate);
    }

    @Override
    public void addMenu(MenuAddReqVO req) {
        Menu menuAdd = Menu.builder()
                .name(req.getName())
                .permission(req.getPermission())
                .type(req.getType())
                .orderNum(req.getOrderNum())
                .parentId(req.getParentId())
                .path(req.getPath())
                .component(req.getComponent())
                .icon(req.getIcon())
                .build();

        checkParentMenu(menuAdd);

        menuMapper.insert(menuAdd);
    }

    @Override
    public void deleteMenu(Long menuId) {
        // TODO 查询是否有角色关联

        // 删除目录及其菜单
        List<Long> meanIdList = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                        .select(Menu::getId)
                        .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        meanIdList.add(menuId);
        menuMapper.deleteBatchIds(meanIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMenuStatus(MenuStatusReqVO req) {
        Menu menuUpdate = Menu.builder()
                .id(req.getId())
                .status(req.getStatus())
                .build();

        menuMapper.updateById(menuUpdate);

        // 如果禁用，子元素也需要禁用
        if (Objects.equals(req.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            menuMapper.update(null, new LambdaUpdateWrapper<Menu>()
                    .set(Menu::getStatus, CommonStatusEnum.DISABLE.getStatus())
                    .eq(Menu::getParentId, req.getId())
            );
        }
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

    private void checkParentMenu(Menu menu) {
        if (menu != null && menu.getParentId() != null) {
            if (menu.getType().equals(MenuTypeEnum.DIR.getType())) {
                throw new ServiceException(BAD_REQUEST.getCode(), "目录只能作为一级菜单");
            }
            Menu parentMenu = menuMapper.selectById(menu.getParentId());
            if (parentMenu.getType().equals(MenuTypeEnum.MENU.getType())) {
                throw new ServiceException(BAD_REQUEST.getCode(), "父菜单类型不能为菜单");
            }
        }
    }

}
