package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.library.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{

	@Override
	public void create(Publisher be) throws SQLException {
		template.update("insert into tbl_publisher(publisherName, publisherAddress, publisherPhone) values(?,?,?)", 
				new Object[]{be.getPublisherName(), be.getPublisherAddress(), be.getPublisherPhone()});
	}

	@Override
	public void update(Publisher be) throws SQLException {
		template.update("update tbl_publisher set publisherName=?, publisherAddress=?, publisherPhone=? where publisherId=?", 
				new Object[]{be.getPublisherName(), be.getPublisherAddress(), be.getPublisherPhone(), be.getPublisherId()});
	}

	@Override
	public Publisher read(Integer[] pk) throws SQLException {
		List<Publisher> list=template.query("select * from tbl_publisher where publisherId=?", pk, this);
		if (list!=null) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void delete(Publisher be) throws SQLException {
		template.update("delete from tbl_publisher where publisherId=?", new Object[]{be.getPublisherId()});

	}
	
	public List<Publisher> readAll() throws SQLException {
		return template.query("select * from tbl_publisher", new Object[]{}, this);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> list=new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher p=new Publisher(rs.getString("publisherName"), rs.getString("publisherAddress"), rs.getString("publisherPhone"));
			p.setPublisherId(rs.getInt("publisherId"));
			list.add(p);
		}

		return list;
	}
}
