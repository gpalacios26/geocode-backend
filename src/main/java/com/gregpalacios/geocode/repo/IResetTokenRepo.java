package com.gregpalacios.geocode.repo;

import com.gregpalacios.geocode.model.ResetToken;

public interface IResetTokenRepo extends IGenericRepo<ResetToken, Integer> {

	ResetToken findByToken(String token);
}
