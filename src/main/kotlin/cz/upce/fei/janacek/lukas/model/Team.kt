package cz.upce.fei.janacek.lukas.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "leader_id")
    val leader: User?,
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    val mentor: User?,
    @JsonIgnore
    @Column
    @OneToMany
    val members: Set<User>
)