package com.isiweek.customer.service;

import com.isiweek.customer.domain.Customer;
import com.isiweek.customer.model.CustomerDTO;
import com.isiweek.customer.repos.CustomerRepository;
import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.person.domain.Person;
import com.isiweek.person.repos.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final LoanContractRepository loanContractRepository;

    public CustomerService(final CustomerRepository customerRepository,
            final PersonRepository personRepository,
            final LoanContractRepository loanContractRepository) {
        this.customerRepository = customerRepository;
        this.personRepository = personRepository;
        this.loanContractRepository = loanContractRepository;
    }

    public List<CustomerDTO> findAll() {
        final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
        return customers.stream()
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .toList();
    }

    public CustomerDTO get(final Long id) {
        return customerRepository.findById(id)
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        mapToEntity(customerDTO, customer);
        return customerRepository.save(customer).getId();
    }

    public void update(final Long id, final CustomerDTO customerDTO) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(customerDTO, customer);
        customerRepository.save(customer);
    }

    public void delete(final Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(final Customer customer, final CustomerDTO customerDTO) {
        customerDTO.setId(customer.getId());
        customerDTO.setCreditScore(customer.getCreditScore());
        customerDTO.setMaxLoanAmount(customer.getMaxLoanAmount());
        customerDTO.setObservations(customer.getObservations());
        customerDTO.setPerson(customer.getPerson() == null ? null : customer.getPerson().getId());
        customerDTO.setPerson(customer.getPerson() == null ? null : customer.getPerson().getId());
        return customerDTO;
    }

    private Customer mapToEntity(final CustomerDTO customerDTO, final Customer customer) {
        customer.setCreditScore(customerDTO.getCreditScore());
        customer.setMaxLoanAmount(customerDTO.getMaxLoanAmount());
        customer.setObservations(customerDTO.getObservations());
        final Person person = customerDTO.getPerson() == null ? null : personRepository.findById(customerDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        customer.setPerson(person);
        return customer;
    }

    public String getReferencedWarning(final Long id) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LoanContract customerLoanContract = loanContractRepository.findFirstByCustomer(customer);
        if (customerLoanContract != null) {
            return WebUtils.getMessage("customer.loanContract.customer.referenced", customerLoanContract.getId());
        }
        return null;
    }

}
