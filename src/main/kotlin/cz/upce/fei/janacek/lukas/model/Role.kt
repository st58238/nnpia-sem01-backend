package cz.upce.fei.janacek.lukas.model

import javax.persistence.*

@Entity
class Role (
    @Id
    @Column(name = "role_id")
    val id: Long,
    @Column
    val name: String,
    @Column
    val description: String?,
    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val assignees: Set<User>
)