package com.stefano.learning.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.stefano.learning.controller.exception.ConsumptionNotFoundException;
import com.stefano.learning.controller.exception.DataFormatException;
import com.stefano.learning.domain.Consumption;
import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.dto.ConsumptionsBatchDTO;
import com.stefano.learning.service.ConsumptionService;
import com.stefano.learning.utils.filereader.ConsumptionsFileReader;
import com.stefano.learning.utils.filereader.ConsumptionsFileReadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("${resource.consumption.name}")
public class ConsumptionResource extends AbstractResource {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private ConsumptionsFileReader consumptionsFileReader;

    @PostMapping
    public ResponseEntity<Object> createConsumption(@Valid @RequestBody Consumption consumption, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new DataFormatException("Create consumption request contains invalid data", bindingResult.getFieldErrors());
        }

        Consumption savedConsumption = consumptionService.save(consumption);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedConsumption.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PostMapping("batch")
    public ConsumptionsBatchDTO createConsumptionsFromFile(@RequestParam("file") MultipartFile file) throws ConsumptionsFileReadingException {

        List<Consumption> consumptionsList = consumptionsFileReader.getConsumptionsList(file);
        return consumptionService.saveBatch(consumptionsList);
    }

    @GetMapping("/{id}")
    public Consumption getConsumption(@PathVariable("id") Long id) throws ConsumptionNotFoundException {
        Optional<Consumption> consumption = consumptionService.findById(id);

        if(!consumption.isPresent()) {
            throw new ConsumptionNotFoundException(String.format("Consumption with id [%s] does not exist", String.valueOf(id)));
        }

        return consumption.get();
    }

    @GetMapping(params = "month")
    public List<ConsumptionByMonthDTO> getConsumptionsByMonth(@RequestParam("month") int month) {
        return consumptionService.getConsumptionsByMonth(month);
    }

    @GetMapping(params = {"month", "driverId"})
    public List<ConsumptionByMonthDTO> getConsumptionsByMonthAndByDriverId(@RequestParam("month") int month, @RequestParam("driverId") String driverId) {
        return consumptionService.getConsumptionsByMonth(driverId, month);
    }
}
