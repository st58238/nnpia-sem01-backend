package cz.upce.fei.janacek.lukas.authentication.controller

import cz.upce.fei.janacek.lukas.filter.UserAuthenticationFilter
import cz.upce.fei.janacek.lukas.lib.JwtTokenUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.crypto.SecretKey

@SpringBootTest
internal class LoginControllerTest(
    private val wac: WebApplicationContext,
    @Autowired authMan: AuthenticationManager,
    @Autowired key: SecretKey
) {

    companion object {const val USERNAME = "User00"; const val PASSWORD = "User01"}
    private lateinit var mockMvc: MockMvc
    private lateinit var auth: UserAuthenticationFilter
    private lateinit var jwtUtil: JwtTokenUtil

    init {
        val builder = MockMvcBuilders
            .webAppContextSetup(wac)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .dispatchOptions<DefaultMockMvcBuilder>(true)
        mockMvc = builder.build()
        jwtUtil = JwtTokenUtil(key)
        auth = UserAuthenticationFilter(authMan, jwtUtil)
    }

    @Test
    fun testLoginEndpointIsAvailable() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .options("/api/v1/login") // Fascinating, as no /login endpoint actually exists/is defined.
                    .header("Access-Control-Request-Method", "GET")
                    .header("Origin", "http://localhost:5173")
            )
            .andExpect(status().isOk)
    }

    @Test
    fun testLoginFailWith401() {
        val req = MockHttpServletRequest(wac.servletContext)
        req.addHeader("Content-Type", "text/plain")
        req.addHeader("Origin", "http://localhost:5173")
        req.setContent("{\"username\": \"$USERNAME\", \"password\": \"wrongPassword\"}".toByteArray())
        req.method = "POST"
        assertThrows<BadCredentialsException> {
            auth.attemptAuthentication(req, MockHttpServletResponse())
        }
    }

    @Test
    fun testLoginSuccessWith200() {
        val req = MockHttpServletRequest(wac.servletContext)
        req.addHeader("Content-Type", "text/plain")
        req.addHeader("Origin", "http://localhost:5173")
        req.setContent("{\"username\": \"$USERNAME\", \"password\": \"$PASSWORD\"}".toByteArray())
        req.method = "POST"
        val res = MockHttpServletResponse()
        auth.attemptAuthentication(req, res)
        assertEquals(res.status, 200)
    }
}