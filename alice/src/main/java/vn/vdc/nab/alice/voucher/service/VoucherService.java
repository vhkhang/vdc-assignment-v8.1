package vn.vdc.nab.alice.voucher.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.vdc.nab.alice.util.LoggingUtil;
import vn.vdc.nab.alice.voucher.connector.IVoucherGeneratorConnector;
import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.converter.VoucherEntityConverter;
import vn.vdc.nab.alice.voucher.converter.VoucherResponseConverter;
import vn.vdc.nab.alice.voucher.entity.VoucherEntity;
import vn.vdc.nab.alice.voucher.entity.VoucherState;
import vn.vdc.nab.alice.voucher.exception.VoucherNeedsMoreTimeException;
import vn.vdc.nab.alice.voucher.model.VoucherRequestModel;
import vn.vdc.nab.alice.voucher.model.VoucherResponseModel;
import vn.vdc.nab.alice.voucher.repository.VoucherDao;

@Component
public class VoucherService {

	@Autowired
	private IVoucherGeneratorConnector voucherGeneratorConnector;

	@Autowired
	LoggingUtil loggingUtil;

	@Autowired
	VoucherResponseConverter voucherResponseConverter;
	
	@Autowired
	VoucherEntityConverter voucherEntityConverter;

	@Autowired
	private VoucherDao voucherDao;

	public VoucherResponseModel genereate(VoucherRequestModel voucherRequestModel)
			throws VoucherNeedsMoreTimeException {
		VoucherEntity entity = voucherDao.saveAndFlush(preProcessingVoucher(voucherRequestModel.getMobile()));
		
		Optional<BobResponseModel> canBeReseponse = this.voucherGeneratorConnector
				.generateVoucher(entity.getId(), voucherRequestModel.getMobile());
		if (!canBeReseponse.isPresent()) {
			// FIXME: handle error here
			return null;
		}

		BobResponseModel bobResponseModel = canBeReseponse.get();
		entity.setState(VoucherState.DONE_IN_TIME.name());
		voucherEntityConverter.from(entity, bobResponseModel);
		this.voucherDao.saveAndFlush(entity);
		return voucherResponseConverter.toResponseModel(bobResponseModel).toBuilder()
				.mobile(voucherRequestModel.getMobile()).build();
	}

	private VoucherEntity preProcessingVoucher(String mobile) {
		VoucherEntity entity = new VoucherEntity();
		entity.setMobile(mobile);
		entity.setState(VoucherState.PROCESSING.name());
		return entity;
	}

}
