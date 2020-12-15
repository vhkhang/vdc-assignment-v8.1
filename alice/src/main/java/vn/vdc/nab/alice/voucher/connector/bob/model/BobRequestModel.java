package vn.vdc.nab.alice.voucher.connector.bob.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BobRequestModel {

	private String id;
	private String mobile;

}
