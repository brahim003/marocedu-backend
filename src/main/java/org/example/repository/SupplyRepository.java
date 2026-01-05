package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.entity.Supply;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    Supply findByName(String name);

    // الطريقة القديمة (للكليان)
    List<Supply> findByLevelSchoolSlugAndLevelSlug(String schoolSlug, String levelSlug);

    // ✅ التعديل هنا: حذف حرف الـ 's' ليصبح 'Level' بدل 'Levels'
    // هذا يطابق الحقل 'private Level level' الموجود في كلاس Supply
    List<Supply> findByLevel_Id(Long levelId);

    // Dashboard count
    Long countByInStock(Boolean inStock);
}