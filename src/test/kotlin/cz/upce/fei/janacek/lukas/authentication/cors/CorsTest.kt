package cz.upce.fei.janacek.lukas.authentication.cors

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest
internal class CorsTest (
    wac: WebApplicationContext,
) {

    private lateinit var mockMvc: MockMvc

    init {
        val builder = MockMvcBuilders
            .webAppContextSetup(wac)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .dispatchOptions<DefaultMockMvcBuilder>(true)
        mockMvc = builder.build()
    }

    @Test
    fun testCorsOptionsRequestOk() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .options("/api/v1/users/page/0")
                    .header("Origin", "http://localhost:5173")
                    .header("Access-Control-Request-Method", "GET")
            )
            .andExpect(status().isOk)
            .andExpect(content().string(""))
    }

    @Test
    fun testCorsMethodsAreOk() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .options("/api/v1/users/page/0")
                    .header("Origin", "http://localhost:5173")
                    .header("Access-Control-Request-Method", "GET")
            )
            .andExpect(status().isOk)
            .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS"))
    }

    @Test
    fun testCorsOptionsRequestFailMissingAccessControlRequestMethod() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .options("/api/v1/users/page/0")
                    .header("Origin", "http://localhost:5173")
                    //.header("Access-Control-Request-Method", "GET") // should fail because of this header missing
            )
            .andExpect(status().isForbidden)
    }

    @Test
    fun testCorsGetMissingOriginFail() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get("/api/v1/users/page/0")
                    .header("Access-Control-Request-Method", "GET")
            )
            .andExpect(status().isForbidden)
            .andExpect(content().string(""))
    }


    @Test
    fun testCorsGetForbiddenFail() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get("/api/v1/users/page/0")
                    .header("Access-Control-Request-Method", "GET")
            )
            .andExpect(status().isForbidden)
    }

    @Test
    fun testCorsHeadRequestNotPermitted() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .head("/api/v1/login")
                    .header("Access-Control-Request-Method", "HEAD")
                    .header("Origin", "http://localhost:5173")
            )
            .andExpect(status().isForbidden)
    }

}