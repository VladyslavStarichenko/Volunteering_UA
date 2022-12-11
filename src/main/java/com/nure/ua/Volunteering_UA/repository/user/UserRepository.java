package com.nure.ua.Volunteering_UA.repository.user;



import com.nure.ua.Volunteering_UA.model.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    Optional<User> findUserByUserName(String name);

    boolean existsByUserName(String userName);

}
