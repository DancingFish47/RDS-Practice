package com.rychkov.rds.controllers;

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

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("dataTypes", dataService.getAllDataTypes());
        model.addAttribute("lifeCycles", dataService.getAllLifeCycles());
        model.addAttribute("lifeCyclesCount", dataService.countLifeCycles());
        return "index";
    }

    @RequestMapping(value = "/getDataObjects", method = RequestMethod.POST)
    @ResponseBody
    public List<DataObject> getDataObjects(@RequestBody Integer dataTypeId, Integer page) {
        return dataService.getDataObjects(dataTypeId, page);
    }

    @RequestMapping(value = "/saveNewDataObject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto saveNewDataObject(@RequestBody DataObject dataObject) {
        dataService.saveNewDataObject(dataObject);
        return ResponseDto.builder().error(false).build();
    }
}
