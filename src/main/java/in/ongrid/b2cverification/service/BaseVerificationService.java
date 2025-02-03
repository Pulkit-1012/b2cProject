package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.entities.BaseVerification;

import java.util.List;

public interface BaseVerificationService {
    List<BaseVerification>findAll();
    BaseVerification save(BaseVerification baseVerification);
    BaseVerification findById(long id);
    void delete(long id);
}
