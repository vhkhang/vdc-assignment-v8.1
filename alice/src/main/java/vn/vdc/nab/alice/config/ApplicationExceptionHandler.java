package vn.vdc.nab.alice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import vn.vdc.nab.alice.common.model.ApiErrorModel;
import vn.vdc.nab.alice.util.LoggingUtil;
import vn.vdc.nab.alice.voucher.exception.ExternalIsUnAvailableException;
import vn.vdc.nab.alice.voucher.exception.VoucherNeedsMoreTimeException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	LoggingUtil loggingUtil;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorModel> defaultException(Exception exception, WebRequest request) {
		loggingUtil.warn("Default exception: " + exception.getMessage());
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(VoucherNeedsMoreTimeException.class)
	public ResponseEntity<ApiErrorModel> voucherTimeoutException(VoucherNeedsMoreTimeException exception,
			WebRequest request) {
		loggingUtil.warn("Having timeout while calling BOB: " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(ApiErrorModel.builder().message("The request is being processed within 30 seconds").build());
	}

	@ExceptionHandler(ExternalIsUnAvailableException.class)
	public ResponseEntity<ApiErrorModel> externalIsUnAvailableException(ExternalIsUnAvailableException exception,
			WebRequest request) {
		loggingUtil.warn("Having problem when calling external service");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiErrorModel.builder().message("Server has problem while calling the external system").build());
	}

}
