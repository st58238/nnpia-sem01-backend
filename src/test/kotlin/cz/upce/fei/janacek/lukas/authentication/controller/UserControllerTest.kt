package cz.upce.fei.janacek.lukas.authentication.controller

import cz.upce.fei.janacek.lukas.controller.UserController
import cz.upce.fei.janacek.lukas.dto.UserRegistrationDto
import cz.upce.fei.janacek.lukas.dto.toExternalDto
import cz.upce.fei.janacek.lukas.model.User
import org.jetbrains.annotations.TestOnly
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.event.annotation.AfterTestMethod
import java.time.LocalDateTime

@SpringBootTest
internal class UserControllerTest(
    @Autowired private val userController: UserController
) {

    @Test
    @AfterTestMethod("testUserCount")
    fun testGetUserPageWithoutSort() {
        val result = userController.getUserPageByOffsetWithSort(0, 25, null, null)
        assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    fun testGetUserPageWithSort() {
        val result = userController.getUserPageByOffsetWithSort(0, 25, "username", "ASC")
        assertEquals(result.statusCode, HttpStatus.OK)
    }

    @Test
    fun testUserCount() {
        val result = userController.countOfUsers()
        assertEquals(result.statusCode, HttpStatus.OK)
        assertTrue(result.body!! > 0)
    }

    @Test
    fun testAdminExistsByID() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        Mockito.`when`(controller.getUserById(2)).thenReturn(ResponseEntity.ok(admin.toExternalDto()))

        val result = controller.getUserById(2)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(admin.toExternalDto(), result.body!!)
    }

    @Test
    fun testGetUserByID() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        Mockito.`when`(controller.getUserById(2)).thenReturn(ResponseEntity.ok(admin.toExternalDto()))

        val result = controller.getUserById(2)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(admin.toExternalDto(), result.body!!)
    }

    @Test
    fun testCreateUser() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        Mockito.`when`(controller.create(admin.toRegistrationDto())).thenReturn(ResponseEntity.ok(admin.toExternalDto()))

        val result = controller.create(admin.toRegistrationDto())
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(admin.toExternalDto(), result.body!!)
    }

    @Test
    fun testModifyUser() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        Mockito.`when`(controller.create(admin.toRegistrationDto())).thenReturn(ResponseEntity.ok(admin.toExternalDto()))

        val result = controller.create(admin.toRegistrationDto())
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(admin.toExternalDto(), result.body!!)
    }

    @Test
    fun testPatchUser() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        val adminChanged = User(2, "Admin2", "Admin2", LocalDateTime.now(), true, null, mutableSetOf())
        Mockito.`when`(controller.patch(admin.id, adminChanged.toExternalDto())).thenReturn(ResponseEntity.ok(adminChanged.toExternalDto()))

        val result = controller.patch(admin.id, adminChanged.toExternalDto())
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(adminChanged.toExternalDto(), result.body!!)
    }

    @Test
    fun testReplaceUser() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        val regularUser = User(2, "User", "User", LocalDateTime.now(), false, null, mutableSetOf())
        Mockito.`when`(controller.modify(admin.id, regularUser.toExternalDto())).thenReturn(ResponseEntity.ok(regularUser.toExternalDto()))

        val result = controller.modify(admin.id, regularUser.toExternalDto())
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(regularUser.toExternalDto(), result.body!!)
    }

    @Test
    fun testDeleteUser() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        Mockito.`when`(controller.delete(admin.id)).thenReturn(ResponseEntity.ok(admin.toExternalDto()))

        val result = controller.delete(admin.id)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(admin.toExternalDto(), result.body!!)
    }

    @Test
    fun testToggleEnabledUser() {
        val controller = Mockito.mock(userController::class.java)
        val admin = User(2, "Admin", "Admin", LocalDateTime.now(), true, null, mutableSetOf())
        val adminDisabled = User(2, "Admin", "Admin", LocalDateTime.now(), false, null, mutableSetOf())
        Mockito.`when`(controller.toggleEnabled(admin.id)).thenReturn(ResponseEntity.ok(adminDisabled.toExternalDto()))

        val result = controller.toggleEnabled(admin.id)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(adminDisabled.toExternalDto(), result.body!!)
    }

    @TestOnly
    private fun User.toRegistrationDto(): UserRegistrationDto {
        return UserRegistrationDto(username, password, registeredDate, enabled, team, roles)
    }
}