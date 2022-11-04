package com.apjake.routes

import com.apjake.routes.course.createCourse
import com.apjake.routes.course.deleteCourse
import com.apjake.routes.course.getCourses
import com.apjake.routes.course.getDetailCourse
import io.ktor.server.routing.*

fun Route.configureCourseRoutes() {
    route("/v1/course") {
        getCourses()
        createCourse()
        getDetailCourse()
        deleteCourse()
    }
}