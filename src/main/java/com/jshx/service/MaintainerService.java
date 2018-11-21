package com.jshx.service;

import com.jshx.domain.MaintainerRepository;
import com.jshx.entity.Maintainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YAO on 2017/6/13.
 */
@Service
public class MaintainerService {

    @Autowired
    MaintainerRepository maintainerRepository;

    public List<Maintainer> findMaintainerList(){

        return maintainerRepository.findAllByDelflag("0");

    }

    public void saveMaintainer(Maintainer maintainer){
        maintainerRepository.save(maintainer);
    }

}
