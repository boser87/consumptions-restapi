package com.stefano.learning.rest.example;

import com.stefano.learning.domain.Consumption;
import com.stefano.learning.utils.filereader.ConsumptionsFileReaderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
public class ConsumptionsFileReaderTest {

    @Autowired
    private JacksonTester<List<Consumption>> json;

    @Test
    public void readConsumptionsFileShouldGenerateCorrectListOfConsumptions() throws IOException {
        ConsumptionsFileReaderImpl consumptionsFileReader = new ConsumptionsFileReaderImpl();
        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", this.getClass().getResourceAsStream("/consumptions.json"));

        List<Consumption> consumptions = json.readObject(this.getClass().getResourceAsStream("/consumptions.json"));

        assertEquals("bill", consumptions.get(0).getDriverId());
    }
}
