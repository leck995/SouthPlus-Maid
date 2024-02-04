package net.southplus.southplusmaid.model;

import java.util.Date;

/**
 * @program: SouthPlusMaid
 * @description:
 * @author: Leck
 * @create: 2024-02-03 23:43
 */
public class BbsTask {
    private String title;
    private Integer sp;
    private String id;
    private BbsTaskType type;
    private Date start;
    private Date end;

    /**
     * @description: 判断当前任务是否可以领取
     * @name: ready
     * @author: Leck
     * @param:
     * @return  boolean
     * @date:   2024/2/4
     */
    public boolean ready(){
        Date date=new Date();
        return start.before(date) && end.after(date) && type==BbsTaskType.READY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSp() {
        return sp;
    }

    public void setSp(Integer sp) {
        this.sp = sp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BbsTaskType getType() {
        return type;
    }

    public void setType(BbsTaskType type) {
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "BbsTask{" +
                "title='" + title + '\'' +
                ", sp=" + sp +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", start=" + start+
                ", end=" + end +
                '}';
    }
}