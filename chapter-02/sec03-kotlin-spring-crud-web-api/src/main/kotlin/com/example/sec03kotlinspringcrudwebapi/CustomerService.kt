package com.example.sec03kotlinspringcrudwebapi

import org.springframework.stereotype.Service

interface CustomerService {
    /**
     * Insert Customer
     *
     * @param firstName
     * @param lastName
     */
    fun insertCustomer(firstName: String, lastName:String)

    /**
     * Select customer
     *
     * @return
     */
    fun selectCustomer(): List<Customer>

    /**
     * Update customer
     *
     * @param id
     * @param firstName
     * @param lastName
     */
    fun updateCustomer(id: Int, firstName: String,lastName: String)

    /**
     * Delete customer
     *
     * @param id
     */
    fun deleteCustomer(id: Int)
}

/**
 * Customer service impl
 *
 * @property customerRepository
 * 補足:
 *  - CustomerRepositoryImpl をインスタンス化する
 *  - @ServiceもDIするためのアノテーションで、インターフェースをDIする場合は@Componentか@Repository アノテーションを実装する
 */
@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository): CustomerService{
    override fun insertCustomer(firstName: String, lastName: String) {
        customerRepository.add(firstName,lastName)
        return
    }

    override fun selectCustomer(): List<Customer> {
        return customerRepository.find()
    }

    override fun updateCustomer(id: Int, firstName: String, lastName: String) {
        customerRepository.update(id, firstName, lastName)
        return
    }

    override fun deleteCustomer(id: Int) {
        customerRepository.delete(id)
        return
    }
}
