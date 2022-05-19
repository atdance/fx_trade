package com.bondsbiz.trade.business.services;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bondsbiz.trade.business.model.CurrencyPair;
import com.bondsbiz.trade.business.model.Exchange;
import com.bondsbiz.trade.business.model.TradeMessage;

public class ModelValidator {
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	private static Validator validator = factory.getValidator();

	/**
	 * Private constructor as this is a utiility class
	 */
	private ModelValidator() {

	}

	public static boolean hasConstraintViolations(TradeMessage pMsg) {
		Set<ConstraintViolation<TradeMessage>> constraintViolationsForTradeMessage = validator.validate(pMsg);

		if (constraintViolationsForTradeMessage.size() > 0) {

			return true;
		}

		return false;
	}

	public static boolean hasConstraintViolations(CurrencyPair pCurrencyPair, Exchange pExchange) {
		Set<ConstraintViolation<CurrencyPair>> constraintViolationsForCurrencyPair = validator.validate(pCurrencyPair);

		Set<ConstraintViolation<Exchange>> constraintViolationsForExchange = validator.validate(pExchange);

		if (constraintViolationsForCurrencyPair.size() > 0 || constraintViolationsForExchange.size() > 0) {

			return true;
		}

		return false;
	}

}
