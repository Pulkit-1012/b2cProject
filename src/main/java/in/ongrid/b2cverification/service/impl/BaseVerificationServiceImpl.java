package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.BaseVerificationRepository;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.service.BaseVerificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseVerificationServiceImpl implements BaseVerificationService {

    private final BaseVerificationRepository baseVerificationRepository;

    public BaseVerificationServiceImpl(BaseVerificationRepository baseVerificationRepository) {
        this.baseVerificationRepository = baseVerificationRepository;
    }


    @Override
    public List<BaseVerification> findAll() {
        return baseVerificationRepository.findAll();
    }

    @Override
    public BaseVerification save(BaseVerification baseVerification) {
        return baseVerificationRepository.save(baseVerification);
    }

    @Override
    public BaseVerification findById(long id) {
        return baseVerificationRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        baseVerificationRepository.deleteById(id);
    }
}
