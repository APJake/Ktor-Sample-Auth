package com.apjake.utils.base

abstract class UniMapper<From, To> {
    protected abstract fun map(data: From): To
    operator fun invoke(data: From) = map(data)
}