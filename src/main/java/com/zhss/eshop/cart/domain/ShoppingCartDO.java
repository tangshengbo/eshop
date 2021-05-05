package com.zhss.eshop.cart.domain;

import com.zhss.eshop.common.util.AbstractObject;

import java.util.Date;
import java.util.Objects;

/**
 * 购物车DO类
 * @author zhonghuashishan
 *
 */
public class ShoppingCartDO extends AbstractObject {
	
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户账号id
	 */
	private Long userAccountId;
	/**
	 * 购物车的创建时间
	 */
	private Date gmtCreate;
	/**
	 * 购物车的修改时间
	 */
	private Date gmtModified;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ShoppingCartDO that = (ShoppingCartDO) o;
		return Objects.equals(id, that.id) && Objects.equals(userAccountId, that.userAccountId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userAccountId);
	}

	@Override
	public String toString() {
		return "ShoppingCartDO [id=" + id + ", userAccountId=" + userAccountId + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + "]";
	}
	
}
