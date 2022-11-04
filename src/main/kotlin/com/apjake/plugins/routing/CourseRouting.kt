package com.apjake.plugins.routing

import com.apjake.data.course.CourseDataSource
import com.apjake.data.user.UserDataSource
import com.apjake.routes.course.createCourse
import com.apjake.routes.course.getCourses
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCourseRouting(
    courseDataSource: CourseDataSource,
    userDataSource: UserDataSource,
) {
    routing {
        route("/api/v1/course") {
            getCourses(courseDataSource)
            createCourse(courseDataSource, userDataSource)
        }
    }
}