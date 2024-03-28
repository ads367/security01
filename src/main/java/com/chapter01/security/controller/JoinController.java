package com.chapter01.security.controller;

import com.chapter01.security.dto.JoinDTO;
import com.chapter01.security.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    JoinService joinService;

    @GetMapping("/join")
    public String joinP() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcP(JoinDTO joinDTO) {

        System.out.println("username : " + joinDTO.getUsername());

        joinService.joinPorc(joinDTO);

        return "redirect:/login";
    }

}
