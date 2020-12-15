package vn.vdc.nab.alice.voucher.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import vn.vdc.nab.alice.common.entity.Audit;
import vn.vdc.nab.alice.common.entity.AuditListener;
import vn.vdc.nab.alice.common.entity.Auditable;

@Entity
@Table(name = "voucher")
@EntityListeners(AuditListener.class)
@Getter
@Setter
public class VoucherEntity implements Auditable {

	@Embedded
	private Audit audit;

	/**
	 * Auto generated id attribute
	 */
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "voucherCode")
	private String voucherCode;

	@Column(name = "expiryDate")
	private String expiryDate;

	@Column(name = "timeZone")
	private String timeZone;

	@Column(name = "state")
	private String state;

	@Override
	public Audit getAudit() {
		return audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

}
