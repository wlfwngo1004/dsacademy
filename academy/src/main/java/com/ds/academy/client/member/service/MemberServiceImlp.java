package com.ds.academy.client.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.academy.client.member.dao.MemberDao;
import com.ds.academy.client.member.vo.Member;

import lombok.Setter;

@Service
public class MemberServiceImlp implements MemberService{
	
	@Setter(onMethod_ = @Autowired)
	private MemberDao memberDao;

	@Override
	public List<Member> memberList(Member mvo) {
		List<Member> list = null;
		list = memberDao.memberList(mvo);
		return list;
	}
	
	
	

}
