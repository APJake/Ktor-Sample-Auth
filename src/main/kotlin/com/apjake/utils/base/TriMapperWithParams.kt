package com.apjake.utils.base

interface TriMapperWithParams<Req, Model, Res, P : Params> {
    fun getModel(request: Req, param: P): Model
    fun getResponse(model: Model): Res
}

interface TriMapper<Req, Model, Res> {
    fun getModel(request: Req): Model
    fun getResponse(model: Model): Res
}

interface Params
object EmptyParam : Params