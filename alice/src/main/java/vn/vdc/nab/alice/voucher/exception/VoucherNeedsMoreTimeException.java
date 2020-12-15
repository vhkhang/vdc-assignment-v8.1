package vn.vdc.nab.alice.voucher.exception;

import lombok.Getter;

@Getter
public class VoucherNeedsMoreTimeException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private final int voucherTimeoutInSeconds;

	public VoucherNeedsMoreTimeException(int voucherTimeoutInSeconds) {
		this.voucherTimeoutInSeconds = voucherTimeoutInSeconds;
	}

}
