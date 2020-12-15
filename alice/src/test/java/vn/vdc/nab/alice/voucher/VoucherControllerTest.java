package vn.vdc.nab.alice.voucher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import vn.vdc.nab.alice.AliceApplicationTests;
import vn.vdc.nab.alice.util.StringUtils;
import vn.vdc.nab.alice.voucher.connector.IVoucherGeneratorConnector;
import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.entity.VoucherEntity;
import vn.vdc.nab.alice.voucher.entity.VoucherState;
import vn.vdc.nab.alice.voucher.exception.VoucherNeedsMoreTimeException;
import vn.vdc.nab.alice.voucher.model.VoucherRequestModel;
import vn.vdc.nab.alice.voucher.model.VoucherResponseModel;
import vn.vdc.nab.alice.voucher.repository.VoucherDao;

public class VoucherControllerTest extends AliceApplicationTests {

	@MockBean
	private IVoucherGeneratorConnector voucherGeneratorConnector;
	
	@Autowired
	private VoucherDao voucherDao;
	
	@Before
	public void setup() {
		this.voucherDao.deleteAll();
	}
	
	@Test
	public void return_error_for_invalid_phone() throws IOException, Exception {
		ResultActions register = generateVoucher(TestDataFactory.generateVoucherRequestModel(invalidPhone()));
		register.andDo(print()).andExpect(status().is4xxClientError());
	}

	@Test
	public void register_voucher() throws IOException, Exception {
		Optional<BobResponseModel> data = Optional.of(TestDataFactory.fakeResponse(validPhone()));
		when(voucherGeneratorConnector.generateVoucher(any(),any()))
				.thenReturn(data);

		VoucherRequestModel generateVoucherRequestModel = TestDataFactory.generateVoucherRequestModel(validPhone());
		ResultActions register = generateVoucher(generateVoucherRequestModel);
		register.andDo(print()).andExpect(status().isOk());
		
		VoucherResponseModel voucherResponseModel = this.extractContentAs(register, VoucherResponseModel.class);
		assertEquals(generateVoucherRequestModel.getMobile(), voucherResponseModel.getMobile());
		assertNotNull(voucherResponseModel.getVoucher());
		assertNotNull(voucherResponseModel.getExpiry());
		assertNotNull(voucherResponseModel.getExpiry().getDate());
		assertNotNull(voucherResponseModel.getExpiry().getTimeZone());
		
		VoucherEntity savedEntity = this.voucherDao.findAll().get(0);
		assertNotNull(savedEntity);
		assertEquals(generateVoucherRequestModel.getMobile(), savedEntity.getMobile());
		assertEquals(voucherResponseModel.getVoucher(), savedEntity.getVoucherCode());
		assertEquals(voucherResponseModel.getExpiry().getDate(), savedEntity.getExpiryDate());
		assertEquals(voucherResponseModel.getExpiry().getTimeZone(), savedEntity.getTimeZone());
		assertEquals(VoucherState.DONE_IN_TIME.name(), savedEntity.getState());
	}
	
	@Test
	public void register_voucher_with_slow_response_issue() throws IOException, Exception {
		VoucherNeedsMoreTimeException socketTimeoutException = new VoucherNeedsMoreTimeException(10);
		when(voucherGeneratorConnector.generateVoucher(any(),any()))
				.thenThrow(socketTimeoutException);
		VoucherRequestModel generateVoucherRequestModel = TestDataFactory.generateVoucherRequestModel(validPhone());
		ResultActions register = generateVoucher(generateVoucherRequestModel);
		register.andDo(print()).andExpect(status().isAccepted());
		
		VoucherEntity savedEntity = this.voucherDao.findAll().get(0);
		assertNotNull(savedEntity);
		assertEquals(generateVoucherRequestModel.getMobile(), savedEntity.getMobile());
		assertTrue(StringUtils.isBlank(savedEntity.getVoucherCode()));
		assertTrue(StringUtils.isBlank(savedEntity.getExpiryDate()));
		assertTrue(StringUtils.isBlank(savedEntity.getTimeZone()));
		assertEquals(VoucherState.PROCESSING.name(), savedEntity.getState());
	}

	private String validPhone() {
		return "090";
	}
	
	private String invalidPhone() {
		return "090a";
	}

	private ResultActions generateVoucher(VoucherRequestModel voucherRequestModel) throws Exception {
		return this.mockMvc
				.perform(post("/vouchers").content(this.json(voucherRequestModel)).contentType(CONTENT_TYPE));
	}

}
