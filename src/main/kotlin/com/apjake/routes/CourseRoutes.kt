package com.apjake.routes

import com.apjake.routes.course.*
import io.ktor.server.routing.*

fun Route.configureCourseRoutes() {
    route("/v1/course") {
        getCourses()
        getDetailCourse()
        createCourse()
        deleteCourse()
        updateCourse()
    }
}