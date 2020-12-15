package vn.vdc.nab.alice.common.entity;

public interface Auditable {
 
    Audit getAudit();
 
    void setAudit(Audit audit);
}