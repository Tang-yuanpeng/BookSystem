package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.bookDao;
import pojo.PageBean;
import pojo.book;
import utils.myDB;

public class bookDaoImpl implements bookDao{

	@Override
	public ArrayList<book> searchAllBook(PageBean<book> pb) {
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<book> books=new ArrayList<>();
		int totalCount=pb.getTotalCount();
		pb.setTotalCount(totalCount);
		if(pb.getTotalCount()<=0) {
			pb.setCurrentPage(1);
		}
		else if (pb.getCurrentPage()>pb.getTotalPage()) {
			pb.setCurrentPage(pb.getTotalPage());
		}
		//获取当前页面
		int currentPage=pb.getCurrentPage();
		//查询起始行
		int index=(currentPage-1)*pb.getPageCount();
		//每页的行数
		int count=pb.getPageCount();
		//开始分页查询
		try {
			//获取连接
			conn=myDB.getConnection();
			//编写SQL语句
			String adminLoginSql="select * from book limit "+index+","+count;
			//创建语句执行者
			st=conn.prepareStatement(adminLoginSql);
			//获取结果
			rs=st.executeQuery();
			while(rs.next()) {
				book newbook=new book();
				newbook.setBk_img(rs.getString("bk_img"));
				newbook.setBk_borrowed(rs.getInt("bk_borrowed"));
				newbook.setBk_name(rs.getString("bk_name"));
				newbook.setBk_author(rs.getString("bk_author"));
				newbook.setBk_category(rs.getString("bk_category"));
				newbook.setBk_intro(rs.getString("bk_intro"));
				books.add(newbook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			myDB.closeResource(conn, st, rs);
			
		}
		
		// TODO Auto-generated method stub
		return books;
	}

	@Override
	public int getTotalCount() {
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int count=0;
		try {
			//获取连接
			conn=myDB.getConnection();
			//编写SQL语句
			String adminLoginSql="select * from book";
			//创建语句执行者
			st=conn.prepareStatement(adminLoginSql);
			//获取结果
			rs=st.executeQuery();
			while(rs.next())
				count++;
			System.out.println(count+"-------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			myDB.closeResource(conn, st, rs);
			
		}
		return count;
	}
}


