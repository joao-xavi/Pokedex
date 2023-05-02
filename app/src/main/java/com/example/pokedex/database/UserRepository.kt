package com.example.pokedex.database

class UserRepository(private val userDao: UserDao) {
    suspend fun createUser(user: User) {
        userDao.createUser(user)
    }

    suspend fun login(email: String, password: String): Boolean {
        val user = userDao.getUser(email, password)
        return user != null
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }


}
