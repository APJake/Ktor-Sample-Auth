package com.apjake.utils.base

abstract class BaseValidator<T> {
    abstract fun check(data: T): Boolean
    operator fun invoke(data: T): Boolean{
        return check(data)
    }
}