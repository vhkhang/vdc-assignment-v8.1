package vn.vdc.nab.alice.voucher.connector.bob.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BobResponseModel {
	private String id;
	private String voucher;
	private String expiryDate;
	private String timeZone;
}
