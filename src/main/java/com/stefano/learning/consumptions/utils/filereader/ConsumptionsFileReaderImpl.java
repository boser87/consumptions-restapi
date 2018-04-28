package com.stefano.learning.consumptions.utils.filereader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefano.learning.core.domain.Consumption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ConsumptionsFileReaderImpl implements ConsumptionsFileReader {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public List<Consumption> getConsumptionsList(MultipartFile file) throws ConsumptionsFileReadingException {
        if(file.isEmpty()) {
            throw new ConsumptionsFileReadingException("File is empty");
        }

        List<Consumption> consumptions;

        try {
            consumptions = jacksonObjectMapper.readValue(file.getInputStream(), jacksonObjectMapper.getTypeFactory().constructCollectionType(List.class, Consumption.class));
        } catch (JsonParseException | JsonMappingException exception) {
            log.info("Error while reading consumptions list file", exception);
            throw new ConsumptionsFileReadingException("Error while parsing or mapping the input to a list of consumptions");
        } catch (IOException exception) {
            log.info("Error while reading consumptions list file", exception);
            throw new ConsumptionsFileReadingException("Generic I/O exception occurred");
        }

        return consumptions;
    }
}
