package org.example.bxbatuz.antifraud.repo;

import org.example.bxbatuz.antifraud.dto.ConcursName;
import org.example.bxbatuz.antifraud.dto.ConcursRes;
import org.example.bxbatuz.antifraud.entity.Concurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcursRepo extends JpaRepository<Concurs,Long> {
    @Query(value = """
            select co.id, co.name, co.description, co.is_active, co.created_at, ad.fullname
            from concurs co join admin_details ad on ad.id = co.admin_id
            where ad.id=:adminId""", nativeQuery = true)
    List<ConcursRes> findAllByNativeSql(@Param("adminId") Long adminId);

    @Query(value = """
            select co.id, co.name, co.description, co.is_active, co.created_at, ad.fullname as admin_name
            from concurs co join admin_details ad on ad.id = co.admin_id""", nativeQuery = true)
    List<ConcursRes> findAllList();

    @Query(value = """
            select id, name from concurs where admin_id=:adminId and is_active = TRUE""", nativeQuery = true)
    List<ConcursName> concursName(@Param("adminId") Long adminId);

    @Query(value = """
            select id, name from concurs where is_active = TRUE""", nativeQuery = true)
    List<ConcursName> concursAllName();
}
