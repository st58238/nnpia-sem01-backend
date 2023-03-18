package cz.upce.fei.janacek.lukas.repository

import cz.upce.fei.janacek.lukas.model.User
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository: PagingAndSortingRepository<User, Long> {

}