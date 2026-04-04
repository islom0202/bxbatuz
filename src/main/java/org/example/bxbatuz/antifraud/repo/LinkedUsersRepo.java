package org.example.bxbatuz.antifraud.repo;
import org.example.bxbatuz.antifraud.dto.LinkedUsersRes;
import org.example.bxbatuz.antifraud.dto.UsersList;
import org.example.bxbatuz.antifraud.entity.LinkedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkedUsersRepo extends JpaRepository<LinkedUsers, Long> {
    @Query(value = """
            select
              ud.id as user_id,
              ud.user_phone,
              lu.user_code,
              ud.user_ip,
              ud.user_device_id,
              ud.is_fraud,
              lu.clicked_at
              from linked_users lu join user_details ud on lu.user_id = ud.id
              where lu.link_id=:linkId""", nativeQuery = true)
    List<UsersList> findLinksAll(@Param("linkId") Long linkId);

    LinkedUsers findByUserId(Long userId);

    @Query(value = """
            select
                          ud.id as user_id,
                          ud.user_phone,
                          co.name as link_name,
                          lu.user_code as code,
                          ud.user_ip,
                          ud.user_device_id,
                          lu.clicked_at as submitted_at,
                          ud.is_fraud
                          from linked_users lu right join user_details ud on lu.user_id = ud.id
                          left join concurs co on co.id = lu.concurs_id
                          where ud.id=:userId""", nativeQuery = true)
    LinkedUsersRes findUser(@Param("userId") Long userId);

}
