package org.code_studio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PaletteMapController {

    @GetMapping("/paletteMap")
    public String paletteMap() {
         return "palette_map";
    }

}
