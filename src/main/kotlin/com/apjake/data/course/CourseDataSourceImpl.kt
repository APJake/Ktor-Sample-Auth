package com.apjake.data.course

import com.apjake.data.author.Author
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.regex

class CourseDataSourceImpl(
    db: CoroutineDatabase
) : CourseDataSource {

    private val courses = db.getCollection<Course>()

    override suspend fun createCourse(course: Course): Boolean {
        return courses.insertOne(course).wasAcknowledged()
    }

    override suspend fun deleteCourseById(id: String): Boolean {
        return courses.deleteOneById(ObjectId(id)).wasAcknowledged()
    }

    override suspend fun updateCourse(course: Course): Boolean {
        return courses.updateOneById(course.id, course).wasAcknowledged()
    }

    override suspend fun getCourses(
        search: String,
        categories: List<String>,
        page: Int,
        limit: Int
    ): List<Course> {
        val skip = page * limit
        val filteredCourses = if (categories.isEmpty()) {
            courses.find(
                Course::title.regex(search, "i"),
            )
        } else {
            // change in future for better filter with categories
            // currently the condition of categories is with OR
            courses.find(
                Course::title.regex(search, "i"),
                Course::categories.`in`(categories)
            )
        }
        return filteredCourses.skip(skip).limit(limit).toList()
    }

    override suspend fun getCoursesByAuthorId(
        authorId: String,
        page: Int,
        limit: Int
    ): List<Course> {
        val skip = page * limit
        return courses
            .find(Author::id eq ObjectId(authorId))
            .skip(skip)
            .limit(limit)
            .toList()
    }

    override suspend fun getCourseDetail(courseId: String): Course? {
        return courses.findOneById(ObjectId(courseId))
    }

    override suspend fun getCourseByCode(courseCode: String): Course? {
        return courses.findOne(Course::code eq courseCode)
    }
}