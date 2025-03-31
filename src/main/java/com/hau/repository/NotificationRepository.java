package com.hau.repository;

import com.hau.Enum.NotificationStatus;
import com.hau.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n join n.admins a WHERE a.id = :id order by n.createDate desc")
    List<Notification> findByAdmins_Id(@Param("id") Long id);

}
