package com.douglas.APIHosital.controller;

import com.douglas.APIHosital.service.FichaAtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ficha")
public class FichaAtendimentoController {

    @Autowired
    private FichaAtendimentoService fichaAtendimentoService;
}
