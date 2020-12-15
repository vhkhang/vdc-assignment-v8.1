package vn.vdc.nab.alice.common.entity;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import vn.vdc.nab.alice.util.DateTimeUtils;

public class AuditListener {

	@PrePersist
	public void setCreatedOn(Auditable auditable) {
		Audit audit = auditable.getAudit();

		if (audit == null) {
			audit = new Audit();
			auditable.setAudit(audit);
		}

		Date curDate = new Date();
		try {
			curDate = DateTimeUtils.getUTCTodayWithPattern(DateTimeUtils.DEFAULT_FULL_DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// do nothing
		}

		audit.setCreationTimestamp(curDate);
		audit.setUpdateTimestamp(curDate);
	}

	@PreUpdate
	public void setUpdatedOn(Auditable auditable) {
		Audit audit = auditable.getAudit();
		Date curDate = new Date();
		try {
			curDate = DateTimeUtils.getUTCTodayWithPattern(DateTimeUtils.DEFAULT_FULL_DATE_TIME_FORMAT);
		} catch (ParseException e) {
			// do nothing
		}
		audit.setUpdateTimestamp(curDate);
	}
}