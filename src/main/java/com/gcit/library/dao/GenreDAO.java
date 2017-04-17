package com.gcit.library.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.library.entity.Genre;

public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{

	@Override
	public void create(Genre be) throws SQLException {
		template.update("insert into tbl_genre(genre_name) values(?)", new Object[]{be.getGenreName()});
		
	}

	@Override
	public void update(Genre be) throws SQLException {
		template.update("update tbl_genre set genre_name=? where genre_id=?", 
				new Object[]{be.getGenreName(), be.getGenreId()});
		
	}

	@Override
	public Genre read(Integer[] pk) throws SQLException {
		List<Genre> list= template.query("select * from tbl_genre where genre_id=?", pk, this);
		if (list!=null) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void delete(Genre be) throws SQLException {
		template.update("delete from tbl_genre where genre_id=?", new Object[]{be.getGenreId()});
		
	}

	public List<Genre> readAll() throws SQLException {
		return template.query("select * from tbl_genre", new Object[]{}, this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> list=new ArrayList<Genre>();
		while (rs.next()) {
			Genre g=new Genre(rs.getString("genreName"));
			g.setGenreId(rs.getInt("genreId"));
			list.add(g);
		}
		return list;
	}

}
