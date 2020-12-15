package vn.vdc.nab.alice.voucher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.model.VoucherRequestModel;

public class TestDataFactory {
	private static final String DEFAULT_TIME_ZONE = "UTC";

	public static VoucherRequestModel generateVoucherRequestModel(String validPhone) {
		VoucherRequestModel requestModel = new VoucherRequestModel();
		requestModel.setMobile(validPhone);
		return requestModel;
	}
	
	public static BobResponseModel fakeResponse(String mobile) {
		BobResponseModel responseModel = new BobResponseModel();
		responseModel.setVoucher(UUID.randomUUID().toString());
		responseModel.setExpiryDate(currentTime());
		responseModel.setTimeZone(DEFAULT_TIME_ZONE);
		return responseModel;
	}

	private static String currentTime() {
		LocalDateTime localDate = LocalDateTime.now(ZoneId.of(DEFAULT_TIME_ZONE));
		return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

}
