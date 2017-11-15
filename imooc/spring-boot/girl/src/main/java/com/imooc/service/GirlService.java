package com.imooc.service;

import com.imooc.Exception.GirlException;
import com.imooc.domain.Girl;
import com.imooc.enums.ResultEnum;
import com.imooc.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tingkl on 2017/11/15.
 */
@Service
public class GirlService {
    @Autowired
    private GirlRepository girlRepository;
    @Transactional
    // mysql的表需要时innodb才能支持事务，改表为innodb引擎
    public void insertTwo() {
        Girl girlA = new Girl();
        girlA.setCupSize("A");
        girlA.setAge(18);
        girlRepository.save(girlA);
        Girl girlB = new Girl();
        girlB.setCupSize("BB");
        girlB.setAge(19);
        girlRepository.save(girlB);
    }

    public void getAge(Integer id) throws Exception {
        Girl girl = girlRepository.findOne(id);
        Integer age = girl.getAge();
        if (age < 10) {
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        } else if (age > 10 && age < 16) {
            throw new GirlException(ResultEnum.MIDDLE_SCHOOL);
        }
    }

    /*
    * 通过id查询一个女生的信息
    * */
    public Girl findOne(Integer id) {
        return girlRepository.findOne(id);
    }
}
