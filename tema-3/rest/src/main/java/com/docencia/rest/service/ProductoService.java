package com.docencia.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docencia.rest.model.Producto;
import com.docencia.rest.repository.ProductoRepository;
import com.docencia.rest.service.interfaces.ProductoServiceInterface;

@Service
public class ProductoService implements ProductoServiceInterface{
    
    private ProductoRepository productoRepository;
    
    @Autowired
    public void setProductoRepository(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    @Override
    public Optional<Producto> find(Producto producto) {
        return productoRepository.findById(producto.getId());
    }

    @Override
    public Optional<Producto> findById(int id) {
        Producto productoFind = new Producto(id);
        return  find(productoFind);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto save(Producto producto) {
        if(producto != null){
            return productoRepository.save(producto);
        }
        return producto;
    }

    @Override
    public boolean delete(Producto producto) {
        productoRepository.deleteById(producto.getId());
        return  true;
    }

    @Override
    public boolean deleteById(int id) {
        Producto productoDelete = new Producto(id);
        return delete(productoDelete);
    }


}
