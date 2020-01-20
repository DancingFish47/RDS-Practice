package com.rychkov.rds.controllers;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.dtos.ResponseDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final DataService dataService;

    @GetMapping({"/", "/{dataTypeName}"})
    public String mainPage(@PathVariable(required = false) String dataTypeName,
                           Model model) {
        if (dataTypeName!=null) {
            model.addAttribute("dataObjects", dataService.getAllDataObjectsByDataType(dataTypeName));
        }
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
