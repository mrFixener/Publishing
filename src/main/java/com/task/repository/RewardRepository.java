package com.task.repository;

import com.task.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dimon on 26.06.2018.
 */
public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findByYear(Integer year);
}
