package com.isiweekloan.service;

import com.isiweekloan.entity.CustomerEntity;
import com.isiweekloan.exception.BadRequestException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerEntity> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomerEntity findCustomerById(Long id) throws ResourceNotFoundException {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer with ID " + id + " not found.");
        }
        return customer.get();
    }

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customer) throws BadRequestException {
        validateRequiredFields(customer);
        return customerRepository.save(customer);
    }

    @Transactional
    public CustomerEntity updateCustomer(Long id, CustomerEntity customer) throws BadRequestException, ResourceNotFoundException {

        if (!customer.getId().equals(id)) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }

        validateRequiredFields(customer);
        Optional<CustomerEntity> existingCustomer = customerRepository.findById(id);

        if (!existingCustomer.isPresent()) {
            throw new ResourceNotFoundException("Customer with ID " + id + " not found.");
        }

        existingCustomer.get().setCreditScore(customer.getCreditScore());
        existingCustomer.get().setIdPerson(customer.getIdPerson());
        existingCustomer.get().setMaxLoanAmount(customer.getMaxLoanAmount());
        existingCustomer.get().setObservations(customer.getObservations());

        return customerRepository.save(existingCustomer.get());
    }

    @Transactional
    public void deleteCustomer(Long id) throws ResourceNotFoundException {

        Optional<CustomerEntity> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer with ID " + id + " not found.");
        }

        customerRepository.delete(customer.get());
    }

    private void validateRequiredFields(CustomerEntity customer) throws BadRequestException {

        if (customer.getCreditScore() == null) {
            throw new BadRequestException("The credit score field is required.");
        }

        if (customer.getIdPerson() == null) {
            throw new BadRequestException("The id person field is required.");
        }

        if (customer.getMaxLoanAmount() == null) {
            throw new BadRequestException("The max loan amount field is required.");
        }

        if (customer.getObservations() == null) {
            throw new BadRequestException("The observations field is required.");
        }
    }
}
