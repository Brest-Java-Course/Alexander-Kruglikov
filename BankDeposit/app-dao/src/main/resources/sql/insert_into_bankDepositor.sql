insert into BANKDEPOSITOR (depositorId,
                        depositorName,
                        depositorIdDeposit,
						depositorDateDeposit,
						depositorAmountDeposit,
						depositorAmountPlusDeposit,
						depositorAmountMinusDeposit,
						depositorDateReturnDeposit,
						depositorMarkReturnDeposit)
values (:depositorId,
        :depositorName,
        :depositorIdDeposit,
        :depositorDateDeposit,
        :depositorAmountDeposit,
        :depositorAmountPlusDeposit,
        :depositorAmountMinusDeposit,
		:depositorDateReturnDeposit,
		:depositorMarkReturnDeposit)