package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.blog.domain.entity.BlogBarrage;
import com.koi.blog.domain.vo.request.BlogBarrageReqVO;
import com.koi.blog.domain.vo.response.BlogBarrageRespVO;
import com.koi.blog.mapper.mysql.BlogBarrageMapper;
import com.koi.blog.service.BlogBarrageService;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.http.IpUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.koi.common.enums.CommonStatusEnum.DISABLE;
import static com.koi.common.enums.CommonStatusEnum.ENABLE;

/**
 * @author zjl
 * @description
 * @createDate 2024-10-13 18:32:52
 */
@Service
public class BlogBarrageServiceImpl implements BlogBarrageService {

    @Resource
    private BlogBarrageMapper blogBarrageMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public List<BlogBarrageRespVO> queryMessages() {
        List<BlogBarrage> blogBarrageList = blogBarrageMapper.selectList(new LambdaQueryWrapper<BlogBarrage>()
                .select(BlogBarrage::getId, BlogBarrage::getNickname, BlogBarrage::getAvatar, BlogBarrage::getMessageContent, BlogBarrage::getTime)
                .eq(BlogBarrage::getIsReview, ENABLE.getStatus()));
        return BeanCopyUtils.copyList(blogBarrageList, BlogBarrageRespVO.class);
    }

    @Override
    public void addMessage(BlogBarrageReqVO messageVO) {
        // TODO 判断是否留言审核
        Integer isMessageReview = DISABLE.getStatus();
        // 获取用户信息
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        BlogBarrage blogBarrage = BeanCopyUtils.copyObject(messageVO, BlogBarrage.class);
        blogBarrage.setIpAddress(ipAddress);
        blogBarrage.setIpSource(ipSource);
        blogBarrage.setIsReview(isMessageReview == ENABLE.getStatus() ? DISABLE.getStatus() : ENABLE.getStatus());

        blogBarrageMapper.insert(blogBarrage);
    }
}




