package vn.vdc.nab.alice.voucher.converter;

import org.springframework.stereotype.Component;

import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.entity.VoucherEntity;

@Component
public class VoucherEntityConverter {

	public VoucherEntity from(VoucherEntity entity, BobResponseModel bobResponseModel) {
		entity.setVoucherCode(bobResponseModel.getVoucher());
		entity.setExpiryDate(bobResponseModel.getExpiryDate());
		entity.setTimeZone(bobResponseModel.getTimeZone());
		return entity;
	}

}
