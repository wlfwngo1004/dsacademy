package com.ds.academy.client.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ds.academy.client.member.vo.AthoVO;
import com.ds.academy.client.member.vo.MemberVO;

@Mapper
public interface MemberDao {
	
	public AthoVO checkAtho(AthoVO avo);
	public MemberVO checkMember(AthoVO avo);
	public int joinMember(MemberVO mvo);
	
	public MemberVO checkUser(String email);
	
	public MemberVO checkId(MemberVO mvo);
	public int joinNormalMember(MemberVO mvo);
	
	public MemberVO findId(MemberVO mvo);
	public MemberVO findPassword(MemberVO mvo);
	public int updatePwd(MemberVO mvo);
	public MemberVO loginMember(MemberVO mvo);
}
