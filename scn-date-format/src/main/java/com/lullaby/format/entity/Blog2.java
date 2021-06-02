package com.lullaby.format.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Blog2 {

    private Long[] value; // 存放的是博客对应的标签列表
    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 赞赏状态
     */
    private boolean appreciation;

    /**
     * 评论状态
     */
    private boolean commentEnabled;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 标记
     */
    private String flag;

    /**
     * 点赞数
     */
    private Integer thumbs;

    /**
     * 发布状态
     */
    private boolean published;

    /**
     * 推荐状态
     */
    private boolean recommend;

    /**
     * 版权状态
     */
    private String shareStatement;

    /**
     * 标题
     */
    private String title;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 浏览次数
     */
    private Integer views;

    private Long typeId;

    private Long uid;

    /**
     * 博客摘要
     */
    private String description;

}
