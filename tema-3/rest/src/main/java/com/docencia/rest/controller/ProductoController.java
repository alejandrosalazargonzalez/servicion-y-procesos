package com.docencia.rest.controller;

import com.docencia.rest.exeption.ResourceNotFoundException;
import com.docencia.rest.model.Producto;
import com.docencia.rest.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/producto")

public class ProductoController {
    ProductoService productoService;

    @Autowired
    public void setProductoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Get all products")
    @GetMapping("/")
    public List<Producto> getAllProductos(){
        return productoService.findAll();
    }
}