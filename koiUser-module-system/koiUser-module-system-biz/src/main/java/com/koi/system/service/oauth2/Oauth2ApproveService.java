package com.koi.system.service.oauth2;

import com.baomidou.mybatisplus.extension.service.IService;
import com.koi.system.domain.oauth2.entity.Oauth2Approve;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 针对表【system_oauth2_approve(OAuth2 批准表)】的数据库操作Service
 * @createDate 2023-08-01 17:03:08
 */
public interface Oauth2ApproveService extends IService<Oauth2Approve> {

    /**
     * 获得指定用户，针对指定客户端的指定授权，是否通过
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param requestedScopes 授权范围
     * @return 是否授权通过
     */
    boolean checkForPreApproval(Long userId, Integer userType, String clientId, Collection<String> requestedScopes);

    /**
     * 获得用户的批准列表，排除已过期的
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @return 是否授权通过
     */
    List<Oauth2Approve> getApproveList(Long userId, Integer userType, String clientId);

    /**
     * 在用户同意授权时，基于 scopes 的选项，计算最终是否通过
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param requestedScopes 授权范围
     * @return 是否授权通过
     */
    boolean updateAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes);
}
