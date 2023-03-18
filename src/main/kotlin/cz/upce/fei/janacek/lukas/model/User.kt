package cz.upce.fei.janacek.lukas.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User (
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val username: String,
    @Column
    val password: String,
    @Column
    val registeredDate: LocalDateTime,
    @Column
    val enabled: Boolean,
    @ManyToOne
    @JoinColumn(name = "team_id")
    val team: Team,
    @ManyToMany(mappedBy = "assignees")
    val roles: Set<Role>
)