package com.bondsbis.trade.model;

import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.bondsbiz.trade.business.model.TradeMessage;
import com.bondsbiz.trade.business.services.ModelValidator;
import com.bondsbiz.trade.business.storage.MyMath;

/**
 * This is ongoing with more tests dued.
 *
 */
public class TradeMessageTest {

	@Test
	public void valid_object_should_not_have_validation_violations() {

		String userID = "" + ThreadLocalRandom.current().nextInt(10, 255);
		String amountSell = "" + ThreadLocalRandom.current().nextInt(1, 99_000);

		String amountBuy = "" + ThreadLocalRandom.current().nextInt(1, 99_000);

		TradeMessage msg = new TradeMessage(userID, "EUR", "GBP", new BigDecimal(amountSell, MyMath.MC),
				new BigDecimal(amountBuy, MyMath.MC), new BigDecimal("0.7471", MyMath.MC), "24-MAR-22 16:59:44", "FR");

		assertFalse(ModelValidator.hasConstraintViolations(msg));
	}

}
