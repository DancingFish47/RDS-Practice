package com.rychkov.rds.controllers;

import com.rychkov.rds.dtos.DataObjectDto;
import com.rychkov.rds.dtos.ResponseDto;
import com.rychkov.rds.entities.DataObject;
import com.rychkov.rds.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final DataService dataService;

    @GetMapping({"/", "/{dataTypeName}"})
    public String mainPage(@PathVariable(required = false) String dataTypeName,
                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           Model model) {
        if (dataTypeName != null) {
            Page<DataObject> dataObjectPage = dataService.getPageDataObjectsByDataTypeName(dataTypeName, page);

            if (dataObjectPage.hasNext()) model.addAttribute("nextPage", page + 1);
            if (dataObjectPage.hasPrevious()) model.addAttribute("prevPage", page - 1);

            model.addAttribute("page", page);
            model.addAttribute("dataObjects", dataObjectPage);
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
/* TODO 1) YML PROPS
        2) VALIDATION?
        3) EDITING
        4) EXCEPTION HANDLING
 */
