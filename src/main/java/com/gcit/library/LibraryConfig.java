package com.gcit.library;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.dao.BookCopiesDAO;
import com.gcit.library.dao.BookDAO;
import com.gcit.library.dao.BookLoansDAO;
import com.gcit.library.dao.BorrowerDAO;
import com.gcit.library.dao.LibraryBranchDAO;
import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.service.Adminstrator;
import com.gcit.library.service.BorrowerService;
import com.gcit.library.service.Librarian;

@EnableTransactionManagement
@Configuration
public class LibraryConfig {
	private static final String driver = "com.mysql.jdbc.Driver";
	// Local db connection
	private static final String url = "jdbc:mysql://127.0.0.1:3306/library";
	private static final String user = "root";
	private static final String pwd = "mysql";
	// Online DB Server connection
/*	private static final String url = "jdbc:mysql://sql9.freemysqlhosting.net/sql9173390";
	private static final String user = "sql9173390";
	private static final String pwd = "vFXIBg7cVf";
*/
	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(pwd);
		return dataSource;
	}

	@Bean
	public JdbcTemplate template() {
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSource());
		return tx;
	}
	
	@Bean
	public Adminstrator admin(){
		return new Adminstrator();
	}
	
	@Bean
	public BorrowerService borService(){
		return new BorrowerService();
	}
	
	@Bean
	public Librarian lib(){
		return new Librarian();
	}
	
	@Bean
	public AuthorDAO aDAO(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bDAO(){
		return new BookDAO();
	}
	
	@Bean
	public LibraryBranchDAO lbDAO(){
		return new LibraryBranchDAO();
	}
	
	@Bean
	public BorrowerDAO brDAO(){
		return new BorrowerDAO();
	}
	
	@Bean
	public PublisherDAO pDAO(){
		return new PublisherDAO();
	}
	
	@Bean
	public BookLoansDAO blDAO(){
		return new BookLoansDAO();
	}
	
	@Bean
	public BookCopiesDAO bcDAO(){
		return new BookCopiesDAO();
	}
}
