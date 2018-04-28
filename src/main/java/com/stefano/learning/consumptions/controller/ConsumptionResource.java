package com.stefano.learning.consumptions.controller;

import com.stefano.learning.ConsumptionsConfigurations;
import com.stefano.learning.consumptions.dto.ConsumptionBatchDTO;
import com.stefano.learning.consumptions.dto.ConsumptionByMonthDTO;
import com.stefano.learning.consumptions.service.ConsumptionService;
import com.stefano.learning.consumptions.utils.filereader.ConsumptionsFileReader;
import com.stefano.learning.consumptions.utils.filereader.ConsumptionsFileReadingException;
import com.stefano.learning.core.controller.AbstractResource;
import com.stefano.learning.core.controller.exception.ConsumptionNotFoundException;
import com.stefano.learning.core.controller.exception.DataFormatException;
import com.stefano.learning.core.domain.Consumption;
import com.stefano.learning.core.dto.ConsumptionDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("consumptions")
public class ConsumptionResource extends AbstractResource {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private ConsumptionsFileReader consumptionsFileReader;

    @PostMapping
    public ResponseEntity<Object> createConsumption(@Valid @RequestBody ConsumptionDTO consumptionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataFormatException("Create consumption request contains invalid data", bindingResult.getFieldErrors());
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ConsumptionDTO, Consumption>() {
            @Override
            protected void configure() {
                this.skip().setId(null);
            }
        });
        Consumption consumption = modelMapper.map(consumptionDTO, Consumption.class);

        ConsumptionDTO savedConsumption = consumptionService.save(consumption);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedConsumption.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PostMapping("batch")
    public List<ConsumptionBatchDTO> createConsumptionsFromFile(@RequestParam("file") MultipartFile file) throws ConsumptionsFileReadingException {
        List<Consumption> consumptionsList = consumptionsFileReader.getConsumptionsList(file);
        return consumptionService.saveBatch(consumptionsList);
    }

    @GetMapping("/{id}")
    public ConsumptionDTO getConsumption(@PathVariable("id") Long id) throws ConsumptionNotFoundException {
        Optional<ConsumptionDTO> consumption = consumptionService.findById(id);

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
        return consumptionService.getConsumptionsByDriverIdAndByMonth(driverId, month);
    }
}
