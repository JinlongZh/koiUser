package com.koi.blog.mapper.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.blog.domain.dto.ReplyCountDTO;
import com.koi.blog.domain.entity.Comment;
import com.koi.blog.domain.vo.request.CommentQueryReqVO;
import com.koi.blog.domain.vo.response.CommentRespVO;
import com.koi.blog.domain.vo.response.ReplyRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentRespVO> pageComment(@Param("req") CommentQueryReqVO req, @Param("current") Integer current, @Param("size") Integer size);

    default Long countComment(CommentQueryReqVO req) {
        return selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getType, req.getCommentType())
                .eq(Comment::getTopicId, req.getTopicId())
                .isNull(Comment::getParentId));
    }

    List<ReplyRespVO> listReplies(List<Long> commentIdList);

    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Long> commentIdList);

    List<ReplyRespVO> pageCommentReply(@Param("commentId") Long commentId, @Param("current") Integer current, @Param("pageSize") Integer pageSize);
}




