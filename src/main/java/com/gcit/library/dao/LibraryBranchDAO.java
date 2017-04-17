package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.library.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements ResultSetExtractor<List<LibraryBranch>>{

	@Override
	public void create(LibraryBranch be) throws SQLException {
		template.update("insert into tbl_library_branch(branchName, branchAddress) values(?,?)", 
				new Object[]{be.getBranchName(), be.getBranchAddress()});

	}

	@Override
	public void update(LibraryBranch be) throws SQLException {
		template.update("update tbl_library_branch set branchName=?, branchAddress=? where branchId=?", 
				new Object[]{be.getBranchName(), be.getBranchAddress(), be.getBranchId()});

	}

	@Override
	public void delete(LibraryBranch be) throws SQLException {
		template.update("delete from tbl_library_branch where branchId=?", new Object[]{be.getBranchId()});

	}

	@Override
	public LibraryBranch read(Integer[] pk) throws SQLException {
		List<LibraryBranch> list= template.query("select * from tbl_library_branch where branchId=?", pk, this);
		if (list!=null) {
			return list.get(0);
		}
		return null;
	}

	public List<LibraryBranch> readAll() throws SQLException {
		return template.query("select * from tbl_library_branch", new Object[]{}, this);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> list=new ArrayList<LibraryBranch>();
		while (rs.next()) {
			LibraryBranch lb=new LibraryBranch(rs.getString("branchName"), rs.getString("branchAddress"));
			lb.setBranchId(rs.getInt("branchId"));
			list.add(lb);
		}
		return list;
	}

}
