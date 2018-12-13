package com.bridgelabz.note.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bridgelabz.note.model.Login;
import com.bridgelabz.note.model.User;
import com.bridgelabz.note.utility.MyBatisUtil;

@Repository
public class UserDaoImpl implements UserDao{

	public void register(User user) {
		System.out.println("UserDaoImpl- register()");
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			userDao.register(user);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	public User loginUser(Login login) {
		System.out.println("UserDaoImpl- register()");
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			return userDao.loginUser(login);
		} finally {
			sqlSession.close();
		}
	}

	public int changePass(User user) {
		System.out.println("daoImpl - changePass()");
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			int flag = userDao.changePass(user);
			System.out.println("rows affected:" + flag);
			sqlSession.commit();
			return flag;
		} finally {
			sqlSession.close();
		}
	}

	public User getUserByEmailID(String emailId) {
		System.out.println("daoImpl  - getUserByEmailID()");
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			return userDao.getUserByEmailID(emailId);
		} finally {
			sqlSession.close();
		}
	}
}
