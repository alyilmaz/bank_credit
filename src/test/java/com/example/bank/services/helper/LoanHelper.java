package com.example.bank.services.helper;

import com.example.bank.domain.Customer;
import com.example.bank.domain.Loan;
import com.example.bank.domain.LoanInstallment;
import com.example.bank.validation.CreateLoanRequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoanHelper {

    public List<Loan> getLoanList(){
        Customer customer = getCustomer(1L);
        Loan l1 = new Loan();
        l1.setId(1L);
        l1.setLoanAmount(100.0);
        l1.setIsPaid(false);
        l1.setCustomer(customer);
        Loan l2 = new Loan();
        l2.setId(2L);
        l2.setLoanAmount(50.0);
        l2.setIsPaid(false);
        List<Loan> loans = Arrays.asList(l1, l2);
        return loans;
    }

    public Loan getLoanOne(CreateLoanRequestBody req){
        Customer customer = getCustomer(1L);
        List<LoanInstallment> loanInstallments = new ArrayList<>();
        double totalAmount = req.amount() * (req.interestRate() + 1);
        double mountOfIns = totalAmount / req.numberOfInstallments();
        for(int i=0; i< req.numberOfInstallments(); i++){
            LoanInstallment inst1 = new LoanInstallment();
            inst1.setId((long) i);
            inst1.setAmount(mountOfIns);
            inst1.setDueDate(LocalDate.now().plusMonths(i+1).withDayOfMonth(1));
            inst1.setIsPaid(false);
            loanInstallments.add(inst1);
        }

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setLoanAmount(50.0);
        loan.setNumberOfInstallment(6L);
        loan.setIsPaid(false);
        loan.setCustomer(customer);
        loan.setInstallments(loanInstallments);
        return loan;
    }

    public Loan getLoanById(Long loanId, Double amount){
        CreateLoanRequestBody req = new CreateLoanRequestBody(loanId, amount, 1.2, 6L);
        Loan loan = getLoanOne(req);
        return loan;
    }

    public Customer getCustomer(Long id){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Ali");
        customer.setSurname("YILMAZ");
        customer.setUsedCreditLimit(0.0);
        customer.setCreditLimit(100.0);
        return customer;
    }

    /*

    {
  "customerId": 2,
  "amount": 50,
  "interestRate": 0.5,
  "numberOfInstallments": 6
}
    {
  "id": 1,
  "loanAmount": 50,
  "numberOfInstallment": 6,
  "createDate": "2025-05-10",
  "isPaid": false,
  "installments": [
    {
      "id": 1,
      "amount": 12.5,
      "paidAmount": null,
      "dueDate": "2025-06-01",
      "paymentDate": null,
      "isPaid": false
    },
    {
      "id": 2,
      "amount": 12.5,
      "paidAmount": null,
      "dueDate": "2025-07-01",
      "paymentDate": null,
      "isPaid": false
    },
    {
      "id": 3,
      "amount": 12.5,
      "paidAmount": null,
      "dueDate": "2025-08-01",
      "paymentDate": null,
      "isPaid": false
    },
    {
      "id": 4,
      "amount": 12.5,
      "paidAmount": null,
      "dueDate": "2025-09-01",
      "paymentDate": null,
      "isPaid": false
    },
    {
      "id": 5,
      "amount": 12.5,
      "paidAmount": null,
      "dueDate": "2025-10-01",
      "paymentDate": null,
      "isPaid": false
    },
    {
      "id": 6,
      "amount": 12.5,
      "paidAmount": null,
      "dueDate": "2025-11-01",
      "paymentDate": null,
      "isPaid": false
    }
  ]
}
     */
}
