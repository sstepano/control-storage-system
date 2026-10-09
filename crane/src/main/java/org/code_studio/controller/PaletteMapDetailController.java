package org.code_studio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class PaletteMapDetailController {

    @GetMapping("/paletteMapDetail/{warehouseRowId}")
    public String paletteMapDetail(@PathVariable("warehouseRowId") Integer warehouseRowId, Model model) {
    	model.addAttribute("warehouseRowId", warehouseRowId);
        return "palette_map_detail";
    }
    
}
