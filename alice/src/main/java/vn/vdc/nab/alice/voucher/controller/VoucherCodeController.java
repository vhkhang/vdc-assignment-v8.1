package vn.vdc.nab.alice.voucher.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import vn.vdc.nab.alice.util.LoggingUtil;
import vn.vdc.nab.alice.voucher.exception.ExternalIsUnAvailableException;
import vn.vdc.nab.alice.voucher.exception.VoucherNeedsMoreTimeException;
import vn.vdc.nab.alice.voucher.model.VoucherRequestModel;
import vn.vdc.nab.alice.voucher.model.VoucherResponseModel;
import vn.vdc.nab.alice.voucher.service.VoucherService;

@RestController
public class VoucherCodeController {

	@Autowired
	LoggingUtil loggingUtil;

	@Autowired
	VoucherService voucherService;

	@PostMapping(path = "/vouchers")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<VoucherResponseModel> generateVoucher(
			@Valid @RequestBody @NotNull VoucherRequestModel voucherRequestModel) throws VoucherNeedsMoreTimeException, ExternalIsUnAvailableException {
		VoucherResponseModel model = this.voucherService.genereate(voucherRequestModel);
		return ResponseEntity.ok(model);
	}
}
