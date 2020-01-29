package com.rychkov.rds.controllers;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.dtos.ResponseDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.sql.Date.valueOf;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final DataService dataService;

    @GetMapping({"/"})
    public String homePage() {
        return "redirect:/getDataObjects";
    }

    @GetMapping({"/getDataObjects"})
    public String getDataObjects(@RequestParam(value = "dataType", required = false) String dataTypeName,
                                 @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                 Model model) {

        if (date == null) {
            date = valueOf(LocalDate.now());
        }
        List<DataObject> dataObjectList = new ArrayList<>();
        if (dataTypeName != null) {
            Optional<DataObject> dataObject = dataService.getTopDataObjectsByDataTypeName(dataTypeName, date);
            dataObject.ifPresent(dataObjectList::add);
        } else {
            dataObjectList.addAll(dataService.getTopDataForEachDataType(date));
        }

        model.addAttribute("dataObjects", dataObjectList);
        model.addAttribute("dataTypes", dataService.getAllDataTypes());
        model.addAttribute("lifeCycles", dataService.getAllLifeCycles());
        model.addAttribute("lifeCyclesCount", dataService.countLifeCycles());
        return "index";
    }


    @RequestMapping(value = "/saveNewDataObject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveNewDataObject(@RequestBody DataObjectDto dataObjectDto) {
        dataService.saveNewDataObject(dataObjectDto);
        return ResponseDto.builder().error(false).build();
    }
}

