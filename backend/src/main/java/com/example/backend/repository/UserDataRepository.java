package com.example.backend.repository;

import com.example.backend.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    // 特定のユーザーのデータだけを抽出するメソッド
    List<UserData> findByUserId(String userId);
}