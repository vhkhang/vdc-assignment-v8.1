package vn.vdc.nab.alice.voucher.model;

import lombok.Getter;
import lombok.Setter;
import vn.vdc.nab.alice.voucher.constraint.PhoneNumberConstraint;

@Getter
@Setter
public class VoucherRequestModel {
	
	@PhoneNumberConstraint
	private String mobile;
}
