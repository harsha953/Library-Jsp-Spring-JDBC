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
import com.gcit.library.entity.Book;
import com.gcit.library.entity.Publisher;

public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	public void createInBookAuthorsTbl(Book be) throws SQLException{
		if (be.getAuthorsList()!=null) {
			for (Author a : be.getAuthorsList()) {
				template.update("insert into tbl_book_authors(bookId, authorId) values(?,?)", new Object[]{be.getBookId(), a.getAuthorId()});
			}	
		}
	}

	public int saveAndGetId(Book be) throws SQLException{
		final String query = "insert into tbl_book(title, pubId) values(?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(query, new String[] { "bookId" });
				ps.setString(1, be.getTitle());
				if (be.getPublisher()==null) {
					ps.setNull(2, java.sql.Types.INTEGER);
				}else{
				ps.setInt(2, be.getPublisher().getPublisherId());
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void create(Book be) throws SQLException {
		if (be.getPublisher()==null) {
			template.update("insert into tbl_book(title) values(?)", new Object[]{be.getTitle()});
		}
		template.update("insert into tbl_book(title, pubId) values(?,?)", 
				new Object[]{be.getTitle(), be.getPublisher().getPublisherId()});	
	}

	@Override
	public void update(Book be) throws SQLException {
		if (be.getPublisher()==null) {
			template.update("update tbl_book set title=?, pubId=null where bookId=?",
					new Object[] { be.getTitle(), be.getBookId() });
		}else{

			template.update("update tbl_book set title=?, pubId=? where bookId=?",
					new Object[] { be.getTitle(), be.getPublisher().getPublisherId(), be.getBookId() });
		}
	}

	@Override
	public void delete(Book be) throws SQLException {
		template.update("delete from tbl_book where bookId=?", new Object[]{be.getBookId()});

	}

	public List<Book> readAll() throws SQLException {
		return template.query("select * from tbl_book", new Object[]{}, this);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> list=new ArrayList<Book>();
		while (rs.next()) {
			Book b;
			if (rs.getInt("pubId")==0) {
				b=new Book(rs.getString("title"), null);
				b.setBookId(rs.getInt("bookId"));
				list.add(b);
			}else{
				b=new Book(rs.getString("title"), new Publisher(rs.getInt("pubId")));
				b.setBookId(rs.getInt("bookId"));
				list.add(b);
			}
		}
		return list;
	}

	public List<Book> readAll(int pageNo, String searchString) throws SQLException{
		setPageNo(pageNo);
		return template.query("select * from tbl_book where title like ?"+appendLimit(), new Object[]{searchString}, this);
	}

	public Integer getCount() throws SQLException{
		return template.queryForObject("select count(*) from tbl_book", new Object[]{}, Integer.class);
	}

	public Integer getCountByName(String searchString) throws SQLException {
		searchString="%"+searchString+"%";
		return template.queryForObject("select count(*) from tbl_book where title like ?", new Object[]{searchString}, Integer.class);
	}
	
	@Override
	public Book read(Integer[] pk) throws SQLException {
		List<Book> list = template.query("select * from tbl_book where bookId=?", pk, this);
		if (list!=null) {
			return list.get(0);
		}
		return null;
	}
	
	public void deleteBookAuthors(Book b) throws SQLException {
		template.update("delete from tbl_book_authors where bookId=?", new Object[]{b.getBookId()});

	}
	
	public void updateBookAuthors(Book b) throws SQLException{
		for (Author a : b.getAuthorsList()) {
			template.update("update tbl_book_authors set authorId=? where bookId=?", new Object[]{a.getAuthorId(), b.getBookId()});
		}	
	}
}
