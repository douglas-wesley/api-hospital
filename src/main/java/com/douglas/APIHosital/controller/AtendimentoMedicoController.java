package com.douglas.APIHosital.controller;

import com.douglas.APIHosital.service.AtendimentoMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/atendimento")
public class AtendimentoMedicoController {

    @Autowired
    private AtendimentoMedicoService atendimentoMedicoService;
}
