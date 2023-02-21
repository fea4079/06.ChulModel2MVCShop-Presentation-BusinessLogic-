package com.model2.mvc.service.user.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserDao;


//==> ȸ������ DAO CRUD ����
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public UserDaoImpl() {
		System.out.println("UserDaoImpl.java.UserDaoImpl() ���̾ "+this.getClass());
	}

	///Method
	public void addUser(User user) throws Exception {
		System.out.println("UserDaoImpl.java addUser �����");
		sqlSession.insert("UserMapper.addUser", user);
	}

	public User getUser(String userId) throws Exception {
		System.out.println("UserDaoImpl.java getUser �����");
		return sqlSession.selectOne("UserMapper.getUser", userId);
	}
	
	public void updateUser(User user) throws Exception {
		System.out.println("UserDaoImpl.java updateUser �����");
		sqlSession.update("UserMapper.updateUser", user);
	}

	public List<User> getUserList(Search search) throws Exception {
		System.out.println("UserDaoImpl.java getUserList �����");
		return sqlSession.selectList("UserMapper.getUserList", search);
	}

	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception {
		System.out.println("UserDaoImpl.java getTotalCount �����");
		return sqlSession.selectOne("UserMapper.getTotalCount", search);
	}
}