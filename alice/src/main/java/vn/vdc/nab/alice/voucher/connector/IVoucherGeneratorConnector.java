package vn.vdc.nab.alice.voucher.connector;

import java.io.IOException;
import java.util.Optional;

import vn.vdc.nab.alice.voucher.connector.bob.model.BobResponseModel;
import vn.vdc.nab.alice.voucher.exception.VoucherNeedsMoreTimeException;

public interface IVoucherGeneratorConnector {

	Optional<BobResponseModel> generateVoucher(String id, String mobile) throws VoucherNeedsMoreTimeException, IOException;

}
