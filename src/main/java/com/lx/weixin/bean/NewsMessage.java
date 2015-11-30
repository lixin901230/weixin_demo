package com.lx.weixin.bean;

import java.util.List;

/**
 * 图文消息实体
 * 
 * @author lixin
 *
 */
public class NewsMessage extends BaseMessage {

	private String ArticleCount;
	private List<NewsMessageBody> Articles;
	
	public String getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}
	public List<NewsMessageBody> getArticles() {
		return Articles;
	}
	public void setArticles(List<NewsMessageBody> articles) {
		Articles = articles;
	}
	
}
