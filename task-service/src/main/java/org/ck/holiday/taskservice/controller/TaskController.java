package org.ck.holiday.taskservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TaskController {

    @GetMapping("tasks")
    public Map<String, Object> task() {
        return Map.of("test", "Bonjour");
    }
}
