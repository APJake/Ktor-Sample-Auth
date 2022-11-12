package com.apjake.data.course

interface CourseDataSource {
    suspend fun createCourse(course: Course): Boolean
    suspend fun deleteCourseById(id: String): Boolean
    suspend fun updateCourse(course: Course): Boolean
    suspend fun getCourses(search: String, categories: List<String>, page: Int, limit: Int, sortBy: SortBy): List<Course>
    suspend fun getCoursesByAuthorId(authorId: String, page: Int, limit: Int): List<Course>
    suspend fun getCourseDetail(courseId: String): Course?
    suspend fun getCourseByCode(courseCode: String): Course?
}