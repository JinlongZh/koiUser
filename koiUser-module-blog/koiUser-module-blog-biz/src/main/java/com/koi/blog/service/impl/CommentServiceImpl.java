package com.koi.blog.service.impl;

import com.koi.blog.domain.dto.ReplyCountDTO;
import com.koi.blog.domain.entity.Comment;
import com.koi.blog.domain.vo.request.CommentAddReqVO;
import com.koi.blog.domain.vo.request.CommentQueryReqVO;
import com.koi.blog.domain.vo.request.ReplyQueryReqVO;
import com.koi.blog.domain.vo.response.CommentRespVO;
import com.koi.blog.domain.vo.response.ReplyRespVO;
import com.koi.blog.mapper.mysql.CommentMapper;
import com.koi.blog.service.CommentService;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.framework.mybatis.utils.PageUtils.getStart;

/**
 * @Author zjl
 * @Date 2023/12/25 17:11
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public PageResult<CommentRespVO> pageComment(CommentQueryReqVO req) {
        // 查询评论数量
        Long count = commentMapper.countComment(req);
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询评论
        List<CommentRespVO> commentRespVOList = commentMapper.pageComment(req, getStart(req), req.getPageSize());
        // 查询所有父评论的ID
        List<Long> commentIdList = commentRespVOList.stream()
                .map(CommentRespVO::getId)
                .collect(Collectors.toList());
        // 查询子评论
        List<ReplyRespVO> replyRespList = commentMapper.listReplies(commentIdList);
        // 根据评论id分组子评论
        Map<Long, List<ReplyRespVO>> replyMap = replyRespList.stream()
                .collect(Collectors.groupingBy(ReplyRespVO::getParentId));
        // 查询评论的子评论数量
        Map<Long, Long> replyCountMap = commentMapper.listReplyCountByCommentId(commentIdList).stream()
                .collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装返回数据
        for (CommentRespVO commentRespVO : commentRespVOList) {
            commentRespVO.setReplyCount(replyCountMap.get(commentRespVO.getId()));
            commentRespVO.setReplyList(replyMap.get(commentRespVO.getId()));
        }
        return new PageResult<>(commentRespVOList, count);
    }

    @Override
    public void addComment(CommentAddReqVO req, Long userId) {
        if (req.getParentId() == null && (req.getType() == null || req.getTopicId() == null)){
            throw new ServiceException(BAD_REQUEST.getCode(), "父评论id和帖子id不能同时为空");
        }
        Comment comment = Comment.builder()
                .userId(userId)
                .commentContent(req.getCommentContent())
                .parentId(req.getParentId())
                .replyUserId(req.getReplyUserId())
                .topicId(req.getTopicId())
                .type(req.getType())
                .build();
        commentMapper.insert(comment);
    }

    @Override
    public List<ReplyRespVO> pageCommentReply(ReplyQueryReqVO req) {
        return commentMapper.pageCommentReply(req.getCommentId(), getStart(req), req.getPageSize());
    }
}
