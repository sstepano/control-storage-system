package org.code_studio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WarehouseController {

    @GetMapping("/warehouse")
    public String warehouse() {
         return "warehouse";
    }

}
