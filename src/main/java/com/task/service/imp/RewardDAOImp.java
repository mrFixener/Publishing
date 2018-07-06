package com.task.service.imp;

import com.task.model.Reward;
import com.task.repository.RewardRepository;
import com.task.service.RewardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Dimon on 26.06.2018.
 */
@Service
@Transactional
public class RewardDAOImp implements RewardDAO {
    @Autowired
    private RewardRepository rewardRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Reward> findByYear(Integer year) {
        return rewardRepository.findByYear(year);
    }

    @Transactional
    @Override
    public Reward save(Reward reward) {
        return rewardRepository.save(reward);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Reward> findById(Long id) {
        return rewardRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(Reward reward) {
        reward.setAuthor(null);
        rewardRepository.save(reward);
        rewardRepository.delete(reward);
    }

}
