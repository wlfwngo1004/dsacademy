package com.ds.academy.client.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.academy.client.member.dao.MemberDao;
import com.ds.academy.client.member.vo.AthoVO;
import com.ds.academy.client.member.vo.MemberVO;

import lombok.Setter;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Setter(onMethod_ = @Autowired)
	private MemberDao memberDao;
	
	@Override
	public AthoVO checkAtho(AthoVO avo) {
		AthoVO checkAtho = null;
		checkAtho = memberDao.checkAtho(avo);
		return checkAtho;
	}
	
	@Override
	public MemberVO checkMember(AthoVO avo) {
		MemberVO checkMember = null;
		checkMember = memberDao.checkMember(avo);
		return checkMember;
	}

	@Override
	public int joinMember(MemberVO mvo) {
		int joinMember = 0;
		joinMember = memberDao.joinMember(mvo);
		return joinMember;
	}

	
}
