package com.example.sec03kotlinspringcrudwebapi

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Customer 作成エンドポイント、Customer 更新エンドポイントのリクエストボディ
 *
 * @property firstName
 * @property lastName
 */
data class CustomerRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
)

/**
 * Customer 一覧取得エンドポイントのレスポンス
 *
 * @property customers
 */
data class CustomerResponse(
    val customers: List<Customer>,
)

/**
 * Customer テーブルの CRUD をおこなう Web API のエンドポイントを定義するクラス
 *
 * @property customerService
 */
@RestController
class CustomerController(val customerService: CustomerService) {
    /**
     * Customer 作成エンドポイント
     *
     * @param request
     * @return
     */
    @PostMapping("/customers")
    fun insert(@RequestBody request: CustomerRequest): String{
        customerService.insertCustomer(request.firstName, request.lastName)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    /**
     * Customer 一覧取得エンドポイント
     *
     * @return
     */
    @GetMapping("/customers")
    fun read(): CustomerResponse{
        return CustomerResponse(customers = customerService.selectCustomer())
    }

    /**
     * Customer 更新エンドポイント
     *
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/customers/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody request: CustomerRequest):String{
        customerService.updateCustomer(id, request.firstName, request.lastName)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    /**
     * Customer 削除エンドポイント
     *
     * @param id
     * @return
     */
    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable("id") id:Int): String{
        customerService.deleteCustomer(id)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }
}

