package com.jackyli.salestax.products

import com.jackyli.salestax.domain.product.model.Product
import com.jackyli.salestax.domain.product.usecase.ProductsUseCase
import com.jackyli.salestax.BasePresenter
import com.jackyli.salestax.domain.product.model.BaseTaxCalculator
import com.jackyli.salestax.domain.product.model.ImportedTaxCalculator
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ProductsPresenter @Inject constructor(private val productsUseCase: ProductsUseCase) : BasePresenter<ProductsActivity>() {
  fun loadProducts() {
    val disposable = productsUseCase.getProductList()
            .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
              override fun onSuccess(products: List<Product>) {
                getView()?.setProducts(products)
              }

              override fun onError(e: Throwable) {
                e.printStackTrace()
              }
            })
    disposables.add(disposable)
  }

  fun checkout(products: List<Product>) {
    getView()?.showReceipt(products.map { it.id })
  }
}