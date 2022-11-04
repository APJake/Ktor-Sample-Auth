package com.apjake.utils.helper

import com.apjake.plugins.pipelines.Role

infix fun Role.gt(another: Role): Boolean {
    return this.priority > another.priority
}

infix fun Role.ge(another: Role): Boolean {
    return this.priority >= another.priority
}

infix fun Role.lt(another: Role): Boolean {
    return this.priority < another.priority
}

infix fun Role.le(another: Role): Boolean {
    return this.priority <= another.priority
}