package com.douglas.APIHosital.service;

import com.douglas.APIHosital.repository.FichaAtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaAtendimentoService {

    @Autowired
    private FichaAtendimentoRepository fichaAtendimentoRepository;

}
