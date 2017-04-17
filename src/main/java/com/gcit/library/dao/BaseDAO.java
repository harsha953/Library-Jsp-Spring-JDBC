package com.gcit.library.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gcit.library.entity.BaseEntity;

@SuppressWarnings("hiding")
public abstract class BaseDAO<BaseEntity> {

	@Autowired
	JdbcTemplate template;

	private Integer pageNo;
	private Integer pageSize=10;

	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public abstract void create(BaseEntity be) throws SQLException;
	public abstract void update(BaseEntity be) throws SQLException; 
	public abstract void delete(BaseEntity be) throws SQLException;
	public abstract BaseEntity read(Integer[] pk) throws SQLException;

	public String appendLimit(){
		if(getPageNo()>0){
			int index=(getPageNo()-1)*getPageSize();
			return " limit "+index+" , "+getPageSize();
		}
		return "";
	}

}
