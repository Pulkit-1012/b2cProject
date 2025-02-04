package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.service.DataFormatterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateFormatterServiceImpl implements DataFormatterService {

    @Override
    public String localDateToString(LocalDate date) {
        if(date == null) throw new ResourceNotFoundException("Date Not Found");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

}
