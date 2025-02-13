package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.GDCVerificationRepository;
import in.ongrid.b2cverification.model.entities.GDCVerification;
import in.ongrid.b2cverification.service.GDCVerificationService;
import org.springframework.stereotype.Service;

@Service
public class GDCVerificationServiceImpl implements GDCVerificationService {

    private final GDCVerificationRepository gdcVerificationRepository;

    public GDCVerificationServiceImpl(GDCVerificationRepository gdcVerificationRepository) {
        this.gdcVerificationRepository = gdcVerificationRepository;
    }


    @Override
    public void save(GDCVerification gdcVerification) {
        gdcVerificationRepository.save(gdcVerification);
    }
}
