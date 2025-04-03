package com.haui.repository;

import com.haui.Enum.DeliveryStatus;
import com.haui.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<BillEntity,Long> {
    @Query("SELECT COUNT(b) FROM BillEntity b WHERE b.deliveryStatus = 'CANCELLED'")
    int countTotalCancelledBill();

    @Query("SELECT COUNT(b) FROM BillEntity b WHERE b.status = 'Đã thanh toán' AND b.deliveryStatus = 'DELIVERED'")
    int countTotalSoldBill();

    Page<BillEntity> findByStatusInAndDeliveryStatusInOrderByCreateDateDesc(Pageable pageable,
                                                                            List<String> statuses,
                                                                            List<DeliveryStatus> deliveryStatus);

    @Query("SELECT b FROM BillEntity b WHERE b.status = 'Đã thanh toán' ORDER BY b.modifiedDate DESC")
    Page<BillEntity> findPaidBills(Pageable pageable);

    Page<BillEntity> findAllByOrderByCreateDateDesc(Pageable pageable);

    Page<BillEntity> findAllByOrderByCreateDateAsc(Pageable pageable);

    @Query("SELECT SUM(b.total) FROM BillEntity b WHERE b.status = 'Đã thanh toán' AND b.deliveryStatus = 'DELIVERED'")
    Double findTotalOfSoldBills();

    @Query("SELECT SUM(b.total) FROM BillEntity b " +
            "WHERE b.status = 'Đã thanh toán' " +
            "AND b.deliveryStatus = 'DELIVERED' " +
            "AND MONTH(b.createDate) = :month " +
            "AND YEAR(b.createDate) = :year")
    Double findTotalOfSoldBillsInMonth(@Param("month") int month, @Param("year") int year);

    Optional<List<BillEntity>> findByUserIdOrderByCreateDateDesc(Long userId);
    Page<BillEntity> findByDisplayNameOrPhoneContainingIgnoreCaseOrderByCreateDateDesc(Pageable pageable, String displayName, String phone);
    Page<BillEntity> findByIdOrderByCreateDateDesc(Pageable pageable, Long id);
    List<BillEntity> findAllByOrderByCreateDateDesc();
}
