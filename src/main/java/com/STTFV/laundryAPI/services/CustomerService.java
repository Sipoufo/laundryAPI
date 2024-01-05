package com.STTFV.laundryAPI.services;



import com.STTFV.laundryAPI.dto.requests.CustomerRequest;
import com.STTFV.laundryAPI.entities.Customer;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired

        private CustomerRepository customerRepository;

        public  Customer  saveCustomer(CustomerRequest customerRequest){
        Customer customer = Customer.builder()
                .phone(customerRequest.getPhone())
                .num(customerRequest.getNum())
                .address(customerRequest.getAddress())
                .firstName(customerRequest.getFirstname())
                .lastName(customerRequest.getLastname())
                .remains(customerRequest.getRemains())
                .transaction(customerRequest.getTransaction())
                .paid(customerRequest.getPaid())
                .build();
        return customerRepository.save(customer);
        }


        public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
        }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public  Customer updateCustomer(CustomerRequest customerRequest, Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found.");
        }

        customer.get().setPhone(customerRequest.getPhone());

        return customerRepository.save(customer.get());
    }


    public void deleteCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found.");
        }

        customerRepository.deleteById(customerId);
    }
}
