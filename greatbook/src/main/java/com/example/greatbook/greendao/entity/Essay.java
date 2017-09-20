package com.example.greatbook.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.DaoException;
import com.example.greatbook.greendao.DaoSession;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.ContentCommitDao;

/**
 * Created by MDove on 17/9/18.
 */
@Entity
public class Essay {
    @Id
    public Long id;
    public String content;
    public String belongId;
    public String belongUserAccount;
    public Date time;
    //外键(一对多)
    @ToMany(referencedJoinProperty = "essayId")
    @OrderBy("time DESC")
    public List<ContentCommit> contentCommits;

    /** Used for active entity operations. */
    @Generated(hash = 1872854044)
    private transient EssayDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    @Generated(hash = 1107247052)
    public Essay(Long id, String content, String belongId,
            String belongUserAccount, Date time) {
        this.id = id;
        this.content = content;
        this.belongId = belongId;
        this.belongUserAccount = belongUserAccount;
        this.time = time;
    }
    @Generated(hash = 2009741210)
    public Essay() {
    }


    public String getBelongId() {
        return this.belongId;
    }
    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBelongUserAccount() {
        return this.belongUserAccount;
    }
    public void setBelongUserAccount(String belongUserAccount) {
        this.belongUserAccount = belongUserAccount;
    }
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 10568812)
    public synchronized void resetContentCommits() {
        contentCommits = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2096570537)
    public List<ContentCommit> getContentCommits() {
        if (contentCommits == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContentCommitDao targetDao = daoSession.getContentCommitDao();
            List<ContentCommit> contentCommitsNew = targetDao._queryEssay_ContentCommits(id);
            synchronized (this) {
                if(contentCommits == null) {
                    contentCommits = contentCommitsNew;
                }
            }
        }
        return contentCommits;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1792737724)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEssayDao() : null;
    }
}
