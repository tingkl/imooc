package org.seckill.entity;

import java.util.Date;

/**
 * Created by tingkl on 16/7/3.
 */
public class Seckill {
    private long seckillId;
    private String name;
    private int number;
    private Date startTime;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Seckill{" + "seckillId:" + seckillId +
                ",\nname:'" + name + "\'" +
                ",\nnumber:" + number +
                ",\nstartTime:" + startTime +
                ",\nendTime:" + endTime +
                ",\ncreateTime:" + createTime +
                '}';
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date endTime;
    private Date createTime;
}
