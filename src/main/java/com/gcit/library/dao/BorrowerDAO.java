package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.library.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>> {

	@Override
	public void create(Borrower be) throws SQLException {
		template.update("insert into tbl_borrower(name, address, phone) values(?,?,?)", new Object[] { be.getName(), be.getAddress(), be.getPhone() });

	}

	@Override
	public void update(Borrower be) throws SQLException {
		template.update("update tbl_borrower set name=?, address=?, phone=? where cardNo=?",
				new Object[] { be.getName(), be.getAddress(), be.getPhone(), be.getCardNo() });

	}

	@Override
	public Borrower read(Integer[] pk) throws SQLException {
		List<Borrower> list= template.query("select * from tbl_borrower where cardNo=?", pk, this);
		if (list!=null) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void delete(Borrower be) throws SQLException {
		template.update("delete from tbl_borrower where cardNo=?", new Object[] { be.getCardNo() });
		
	}

	public List<Borrower> readAll() throws SQLException {
		return template.query("select * from tbl_borrower", new Object[] {}, this);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> list = new ArrayList<Borrower>();
		while (rs.next()) {
			Borrower b = new Borrower(rs.getString("name"), rs.getString("address"), rs.getString("phone"));
			b.setCardNo(rs.getInt("cardNo"));
			list.add(b);
		}
		return list;
	}



}
