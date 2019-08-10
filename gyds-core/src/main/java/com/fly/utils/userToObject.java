package com.fly.utils;

import java.util.Date;

import com.fly.domain.UserDO;
import com.fly.verifyName.domain.NameDO;

public class userToObject {
	public static NameDO  userToverify(UserDO user,Integer id) {
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
		name.setStreet("");
		name.setTeamId(id);
		name.setType(1);
		name.setUserId(user.getUserId());
		return name;
	}
}
