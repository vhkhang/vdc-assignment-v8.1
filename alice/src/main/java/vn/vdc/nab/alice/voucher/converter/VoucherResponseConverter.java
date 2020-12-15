package vn.vdc.nab.alice.voucher.converter;

import org.springframework.stereotype.Component;

import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.model.ExpiryResponseModel;
import vn.vdc.nab.alice.voucher.model.VoucherResponseModel;

@Component
public class VoucherResponseConverter {

	public VoucherResponseModel toResponseModel(BobResponseModel bobResponseModel) {
		return VoucherResponseModel
				.builder()
				.voucher(bobResponseModel.getVoucher())
				.expiry(toeExpiry(bobResponseModel))
				.build();
	}

	private ExpiryResponseModel toeExpiry(BobResponseModel bobResponseModel) {
		return ExpiryResponseModel.builder()
				.date(bobResponseModel.getExpiryDate()).timeZone(bobResponseModel.getTimeZone()).build();
	}

}
