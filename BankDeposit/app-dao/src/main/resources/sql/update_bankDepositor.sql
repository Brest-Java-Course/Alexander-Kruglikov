update BANKDEPOSITOR set depositorName=:depositorName,
			depositorIdDeposit=:depositorIdDeposit,
			depositorDateDeposit=:depositorDateDeposit,
			depositorAmountDeposit=:depositorAmountDeposit,
			depositorAmountPlusDeposit=:depositorAmountPlusDeposit,
			depositorAmountMinusDeposit=:depositorAmountMinusDeposit,
			depositorDateReturnDeposit=:depositorDateReturnDeposit,
			depositorMarkReturnDeposit=:depositorMarkReturnDeposit
where depositorId=:depositorId
