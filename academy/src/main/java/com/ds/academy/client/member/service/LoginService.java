package com.ds.academy.client.member.service;

import com.ds.academy.client.member.vo.AthoVO;
import com.ds.academy.client.member.vo.MemberVO;

public interface LoginService {
	public AthoVO checkAtho(AthoVO avo);
	public MemberVO checkMember(AthoVO avo);
	public int joinMember(MemberVO mvo);
	
	public MemberVO checkUser(String email);
	
	public MemberVO checkId(MemberVO mvo);
	public int joinNormalMember(MemberVO mvo);
}
