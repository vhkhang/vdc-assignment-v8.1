package vn.vdc.nab.alice.voucher.connector;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.vdc.nab.alice.config.ApplicationConfig;
import vn.vdc.nab.alice.util.JsonMapper;
import vn.vdc.nab.alice.util.LoggingUtil;
import vn.vdc.nab.alice.util.Request;
import vn.vdc.nab.alice.voucher.connector.bob.model.BobRequestModel;
import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.exception.VoucherNeedsMoreTimeException;

@Component
public class VoucherGeneratorConnector implements IVoucherGeneratorConnector{

	@Autowired
	Request request;

	@Autowired
	LoggingUtil loggingUtil;

	@Autowired
	ApplicationConfig appConfig;

	@Autowired
	JsonMapper jsonMapper;

	@Override
	public Optional<BobResponseModel> generateVoucher(String id, String mobile) throws VoucherNeedsMoreTimeException, IOException {
		int expectedTimeout = this.appConfig.getVoucherTimeoutInSeconds();
		String url = this.appConfig.getVoucherUrl();

		loggingUtil.info("Calling BOB to generate voucher: ");
		loggingUtil.info("Url: " + url);
		loggingUtil.info("Expected timeout: " + expectedTimeout);
		String data = jsonMapper.toJson(BobRequestModel.builder().id(id).mobile(mobile).build());
		loggingUtil.info("Data: " + data);
		try {
			HttpResponse response = request.postAsJson(url, data, expectedTimeout);
			int status = response.getStatusLine().getStatusCode();
			BobResponseModel jsonResponse = convert(response.getEntity()).orElse(new BobResponseModel());
			if (status > 200 && status < 300) {
				return Optional.of(jsonResponse);
			}
			this.handleError(response);
			return Optional.empty();
		}catch (SocketTimeoutException ex) {
			loggingUtil.warn("Reach timeout while generate voucher: " + expectedTimeout);
			throw new VoucherNeedsMoreTimeException(expectedTimeout);
		} catch (IOException e) {
			loggingUtil.error(e.getMessage());
			throw e;
		}
	}

	private void handleError(HttpResponse response) {
		loggingUtil.error("BOB connector has error: " + this.jsonMapper.toJson(response));
	}

	private Optional<BobResponseModel> convert(HttpEntity entity) {
		try {
			return jsonMapper.toObj(entity.getContent(), BobResponseModel.class);
		} catch (UnsupportedOperationException | IOException e) {
			return Optional.empty();
		}
	}

}
