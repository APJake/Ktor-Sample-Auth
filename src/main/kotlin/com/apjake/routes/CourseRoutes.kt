package com.apjake.routes

import com.apjake.data.course.CourseDataSource
import com.apjake.data.user.UserDataSource
import com.apjake.routes.course.createCourse
import com.apjake.routes.course.deleteCourse
import com.apjake.routes.course.getCourses
import com.apjake.routes.course.getDetailCourse
import io.ktor.server.routing.*

fun Route.configureCourseRoutes(
    courseDataSource: CourseDataSource,
    userDataSource: UserDataSource,
) {
    route("/v1/course") {
        getCourses(courseDataSource)
        createCourse(courseDataSource, userDataSource)
        getDetailCourse(courseDataSource)
        deleteCourse()
    }
}