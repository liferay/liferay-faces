package com.liferay.faces.demos.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.demos.dto.LiferayBenefit;
import com.liferay.faces.demos.service.LiferayBenefitService;

@ManagedBean
@RequestScoped
public class RatingModelBean {
	
	private Boolean like;
	
	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	private String favorite;
	
	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	
	private Long favoriteId;

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	@ManagedProperty(name = "liferayBenefitService", value = "#{liferayBenefitService}")
	private LiferayBenefitService liferayBenefitService;

	public List<LiferayBenefit> getLiferayBenefits() {
		return liferayBenefitService.getLiferayBenefits();
	}

	public void setLiferayBenefitService(LiferayBenefitService liferayBenefitService) {
		this.liferayBenefitService = liferayBenefitService;
	}
}
