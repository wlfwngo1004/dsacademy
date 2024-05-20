package com.ds.academy.client.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	private int mNum;
	private String mAthoNum;
	private String email;
	private String mPwd;
	private String name;
	private String gender;
	private String mobile;
}
