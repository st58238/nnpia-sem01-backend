package cz.upce.fei.janacek.lukas.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User (
    @Id
    val id: Long,
    @Column
    val username: String,
    @Column
    val password: String,
    @Column
    val registeredDate: LocalDateTime,
    @Column
    val enabled: Boolean
)