package com.stefano.learning.consumptions.utils.filereader;

import com.stefano.learning.core.domain.Consumption;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConsumptionsFileReader {
    List<Consumption> getConsumptionsList(MultipartFile file) throws ConsumptionsFileReadingException;
}
