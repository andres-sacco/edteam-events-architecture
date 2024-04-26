package com.edteam.reservations.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;

@Hidden
@Controller
@RequestMapping("documentation")
public class DocumentationController {

    @ResponseBody
    @GetMapping("/sync")
    public void redirectToOpenAPIDocumentation(HttpServletResponse response) {
        try {
            response.sendRedirect("../swagger-ui.html");
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("UNEXPECTED ERROR: ");
            if (e.getMessage() != null) {
                sb.append(e.getMessage());
            }
        }
    }

    @ResponseBody
    @GetMapping("/async")
    public void redirectToAsyncAPIDocumentation(HttpServletResponse response) {
        try {
            response.sendRedirect("../springwolf/asyncapi-ui.html");
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("UNEXPECTED ERROR: ");
            if (e.getMessage() != null) {
                sb.append(e.getMessage());
            }
        }
    }
}