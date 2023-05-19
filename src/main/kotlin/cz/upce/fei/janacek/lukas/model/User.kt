package cz.upce.fei.janacek.lukas.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity(name = "users")
class User (
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(unique = true)
    private val username: String,
    @Column
    private val password: String,
    @Column
    val registeredDate: LocalDateTime,
    @Column
    val enabled: Boolean,
    @ManyToOne
    @JoinColumn(name = "team_id")
    val team: Team?,
    @ManyToMany(mappedBy = "assignees")
    val roles: Set<Role>
): UserDetails {
    fun cloneWithNewPassword(newPassword: String): User {
        return User(id, username, newPassword, registeredDate, enabled, team, roles)
    }

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = enabled

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(GrantedAuthority { "Login" })
    }

    override fun getPassword(): String = password

}