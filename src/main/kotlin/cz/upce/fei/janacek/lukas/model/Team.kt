package cz.upce.fei.janacek.lukas.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Team (
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val name: String,
    @Column
    val description: String?,
    @ManyToOne
    @JoinColumn(name = "leader_id")
    val leader: User?,
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    val mentor: User?,
    @Column
    @OneToMany
    val members: Set<User>
)