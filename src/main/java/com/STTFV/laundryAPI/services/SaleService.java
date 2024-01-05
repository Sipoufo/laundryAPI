package com.STTFV.laundryAPI.services;

import com.STTFV.laundryAPI.dto.requests.SaleRequest;
import com.STTFV.laundryAPI.entities.Product;
import com.STTFV.laundryAPI.entities.Sale;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.ProductRepository;
import com.STTFV.laundryAPI.repositories.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService {


    @Autowired

    private SaleRepository saleRepository;

    public Sale saveSale(SaleRequest saleRequest){
        Sale sale = Sale.builder().num(saleRequest.getNum()).build();
        return saleRepository.save(sale);
    }

    public Optional<Sale> getSale(Long saleId) {
        return saleRepository.findById(saleId);
    }

    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }

    public  Sale updateSale(SaleRequest saleRequest, Long saleId) {
        Optional<Sale> sale = saleRepository.findById(saleId);

        if (sale.isEmpty()) {
            throw new ResourceNotFoundException("sale not found.");
        }

        sale.get().setNum(saleRequest.getNum());

        return saleRepository.save(sale.get());
    }


    public void deleteSale(Long saleId) {
        Optional<Sale> sale = saleRepository.findById(saleId);

        if (sale.isEmpty()) {
            throw new ResourceNotFoundException("Sale not found.");
        }

        saleRepository.deleteById(saleId);
    }



}
