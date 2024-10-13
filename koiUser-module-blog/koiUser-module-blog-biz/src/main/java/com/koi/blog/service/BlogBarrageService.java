package com.koi.blog.service;

import com.koi.blog.domain.vo.request.BlogBarrageReqVO;
import com.koi.blog.domain.vo.response.BlogBarrageRespVO;

import java.util.List;

/**
* @author zjl
* @description
* @createDate 2024-10-13 18:32:52
*/
public interface BlogBarrageService {

    /***
     * 查询留言
     *
     * @param
     * @Return List<MessageRespVO>
     */
    List<BlogBarrageRespVO> queryMessages();

    /***
     * 添加留言
     *
     * @param messageVO
     * @Return void
     */
    void addMessage(BlogBarrageReqVO messageVO);
}
