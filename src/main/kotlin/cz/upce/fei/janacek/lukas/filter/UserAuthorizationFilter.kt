package cz.upce.fei.janacek.lukas.filter

import cz.upce.fei.janacek.lukas.lib.JwtTokenUtil
import cz.upce.fei.janacek.lukas.configuration.SecurityConfiguration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


class UserAuthorizationFilter(
    private val service: UserDetailsService,
    authManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith(SecurityConfiguration.PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header.substring(7))?.also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (!jwtTokenUtil.isTokenValid(token)) return null
        val username = jwtTokenUtil.getUsername(token)
        val user = service.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(user, null, user.authorities) // TODO doplnit granted authority
    }
}