package com.fly.utils;

import java.util.Date;

import com.fly.domain.UserDO;
import com.fly.utils.xss.PublicUtils;
import com.fly.verifyName.domain.NameDO;

public class userToObject {
	public static NameDO  userToverify(UserDO user,Long id) {
		NameDO name = new NameDO();
		name.setAddress(user.getLiveAddress());
		name.setBirth(user.getBirth());
		name.setCardBackImg(user.getCardBackImg());
		name.setCardFrontImg(user.getCardFrontImg());
		name.setCardNo(user.getCardNo());
		name.setCity(user.getCity());
		name.setCreateTime(new Date());
		name.setDistrict(user.getDistrict());
		name.setEmail(user.getEmail());
		name.setMobile(user.getMobile());
		name.setName(user.getName());
		name.setNation(user.getNation());
		name.setPoliticalStatus(user.getPoliticalStatus());
		name.setProvince(user.getProvince());
		name.setRegionCode(user.getRegionCode());
		name.setSex(user.getSex());
		name.setStatus(1);
		name.setStreet(user.getStreet());
		name.setTeamId(id);
		name.setUserId(user.getUserId());
		name.setPost(user.getPost());
		name.setEducation(user.getEducation());
		return name;
	}
	/***
	 *	 实名认证资料补充
	 * @param name
	 * @param user
	 * @return
	 */
	public static UserDO  isIdentification(UserDO user,NameDO name) {
		user.setBirth(name.getBirth());
		user.setLiveAddress(name.getAddress());
		user.setHeadImg(name.getCardBackImg());
		user.setCardFrontImg(name.getCardFrontImg());
		user.setCardNo(name.getCardNo());
		user.setAge(PublicUtils.IdNOToAge(user.getCardNo()));
		user.setCity(name.getCity());
		user.setDistrict(name.getDistrict());
		user.setEmail(name.getEmail());
		user.setMobile(name.getMobile());
		user.setName(name.getName());
		user.setNation(name.getNation());
		user.setPoliticalStatus(name.getPoliticalStatus());
		user.setProvince(name.getProvince());
		user.setRegionCode(name.getRegionCode());
		user.setSex(name.getSex());
		user.setPost(name.getPost());
		user.setEducation(name.getEducation());
		user.setIsIdentification(1);
		user.setStreet(name.getStreet());
		return user;
	}
}
