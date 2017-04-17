package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.library.entity.Author;

public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>> {

	@Override
	public void create(Author be) throws SQLException {
		template.update("insert into tbl_author(authorName) values(?)", new Object[] { be.getAuthorName() });
	}

	public Integer saveAndGetId(Author be) throws SQLException {
		final String query = "insert into tbl_author(authorName) values(?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(query, new String[] { "authorId" });
				ps.setString(1, be.getAuthorName());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public List<Author> getAuthorsListForBook(int bookId) throws SQLException {
		List<Author> list = template.query(
				"select a.* from tbl_book_authors tb join tbl_author a on a.authorId=tb.authorId where bookId=?",
				new Object[] { bookId }, this);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	@Override
	public void update(Author be) throws SQLException {
		template.update("update tbl_author set authorName=? where authorId=?",
				new Object[] { be.getAuthorName(), be.getAuthorId() });
	}

	@Override
	public void delete(Author be) throws SQLException {
		template.update("delete from tbl_author where authorId=?", new Object[] { be.getAuthorId() });
	}

	@Override
	public Author read(Integer[] pk) throws SQLException {
		List<Author> list = template.query("select * from tbl_author where authorId=?", pk, this);
		if (list != null) {
			return list.get(0);
		}
		return null;

	}

	public List<Author> readAll() throws SQLException {
		return template.query("select * from tbl_author", this);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> list = new ArrayList<Author>();
		while (rs.next()) {
			Author a = new Author(rs.getString("authorname"));
			a.setAuthorId(rs.getInt("authorId"));
			list.add(a);
		}
		return list;
	}

	public Integer getCount() throws SQLException {
		return template.queryForObject("select count(*) from tbl_author", Integer.class);
	}

	public Integer getCountByName(String searchString) throws SQLException {
		searchString = "%" + searchString + "%";
		return template.queryForObject("select count(*) from tbl_author where authorName like ?",
				new Object[] { searchString }, Integer.class);
	}

	public List<Author> readAuthorsByName(Integer pageNo, String searchString) throws SQLException {
		setPageNo(pageNo);
		return template.query("select * from tbl_author where authorName like ?" + appendLimit(),
				new Object[] { searchString }, this);
	}

}
