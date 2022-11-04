package com.apjake.plugins.pipelines

enum class Role(val roleStr: String, val priority: Int): Comparable<Role> {
    Admin("admin", 100),
    Guest("guest", 0),
    Author("author", 2),
    Manager("manager", 4),
    Student("student", 1);

    companion object {
        val courseCreatorRoles = arrayOf(Admin, Manager, Author)
        val managerRoles = arrayOf(Admin, Manager)
        fun getRoleOrNull(value: String?): Role? {
            if (value == null) return null
            val index = Role.values().map { it.roleStr }.indexOfFirst {
                it.contains(value, ignoreCase = true)
            }
            return if (index >= 0) Role.values()[index] else null
        }
    }

}
