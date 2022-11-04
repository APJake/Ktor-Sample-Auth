package com.apjake.data.requests

import com.apjake.data.course.CourseType
import com.apjake.utils.base.BaseRequest
import com.apjake.utils.extensions.enumContains
import kotlinx.serialization.Serializable

@Serializable
data class CourseRequest(
    val code: String? = null,
    val title: String,
    val description: String,
    val detailHTML: String,
    val posterUrl: String,
    val type: String,
    val price: Long?,
    val categories: List<String>
) : BaseRequest() {
    override val validator = {
        if (title.isBlank() || description.isBlank()
            || detailHTML.isBlank() || posterUrl.isBlank()
            || type.isBlank() || categories.isEmpty()
        ) {
            invalid("Fields shouldn't be empty")
        }
        if (!enumContains<CourseType>(type, true)) {
            invalid("There is no such course type")
        }
    }
}