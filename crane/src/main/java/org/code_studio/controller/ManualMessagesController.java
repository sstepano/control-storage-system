package org.code_studio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ManualMessagesController {

    @GetMapping("/manualMessages")
    public String manualMessages() {
         return "manual_messages";
    }
    
    @GetMapping("/")
   public String redirectToIndex() {
        return "redirect:/manualMessages";
   }

   @GetMapping("/index")
   public String index() {
	   return "redirect:/manualMessages";
   }

}
