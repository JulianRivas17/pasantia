package com.example.api.resouce;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/personas")
public class PersonasCrontroller {

    @GetMapping("/")
    public List<String> personas() {
        List<String> data = new ArrayList<>();
        data.add("Jorge");
        data.add("Raul");
        data.add("Alejandra");
        data.add("Juli");
        return data;
    }
}
