package vn.vdc.nab.alice.voucher.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.vdc.nab.alice.voucher.entity.VoucherEntity;

public interface VoucherDao extends JpaRepository<VoucherEntity, String> {

}
