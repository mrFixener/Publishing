package com.task.service;

import com.task.model.Reward;

import java.util.List;
import java.util.Optional;

/**
 * Created by Dimon on 26.06.2018.
 */
public interface RewardDAO {
    List<Reward> findByYear(Integer year);

    Reward save(Reward reward);

    Optional<Reward> findById(Long id);

    void delete(Reward reward);
}
