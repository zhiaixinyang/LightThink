package com.example.greatbook.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.greendao.entity.MyCollecting;

import com.example.greatbook.greendao.ContentCommitDao;
import com.example.greatbook.greendao.DiarySelfDao;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.MyPlanDao;
import com.example.greatbook.greendao.MyPlanTemplateDao;
import com.example.greatbook.greendao.MyCollectingDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contentCommitDaoConfig;
    private final DaoConfig diarySelfDaoConfig;
    private final DaoConfig essayDaoConfig;
    private final DaoConfig localGroupDaoConfig;
    private final DaoConfig localRecordDaoConfig;
    private final DaoConfig myPlanDaoConfig;
    private final DaoConfig myPlanTemplateDaoConfig;
    private final DaoConfig myCollectingDaoConfig;

    private final ContentCommitDao contentCommitDao;
    private final DiarySelfDao diarySelfDao;
    private final EssayDao essayDao;
    private final LocalGroupDao localGroupDao;
    private final LocalRecordDao localRecordDao;
    private final MyPlanDao myPlanDao;
    private final MyPlanTemplateDao myPlanTemplateDao;
    private final MyCollectingDao myCollectingDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contentCommitDaoConfig = daoConfigMap.get(ContentCommitDao.class).clone();
        contentCommitDaoConfig.initIdentityScope(type);

        diarySelfDaoConfig = daoConfigMap.get(DiarySelfDao.class).clone();
        diarySelfDaoConfig.initIdentityScope(type);

        essayDaoConfig = daoConfigMap.get(EssayDao.class).clone();
        essayDaoConfig.initIdentityScope(type);

        localGroupDaoConfig = daoConfigMap.get(LocalGroupDao.class).clone();
        localGroupDaoConfig.initIdentityScope(type);

        localRecordDaoConfig = daoConfigMap.get(LocalRecordDao.class).clone();
        localRecordDaoConfig.initIdentityScope(type);

        myPlanDaoConfig = daoConfigMap.get(MyPlanDao.class).clone();
        myPlanDaoConfig.initIdentityScope(type);

        myPlanTemplateDaoConfig = daoConfigMap.get(MyPlanTemplateDao.class).clone();
        myPlanTemplateDaoConfig.initIdentityScope(type);

        myCollectingDaoConfig = daoConfigMap.get(MyCollectingDao.class).clone();
        myCollectingDaoConfig.initIdentityScope(type);

        contentCommitDao = new ContentCommitDao(contentCommitDaoConfig, this);
        diarySelfDao = new DiarySelfDao(diarySelfDaoConfig, this);
        essayDao = new EssayDao(essayDaoConfig, this);
        localGroupDao = new LocalGroupDao(localGroupDaoConfig, this);
        localRecordDao = new LocalRecordDao(localRecordDaoConfig, this);
        myPlanDao = new MyPlanDao(myPlanDaoConfig, this);
        myPlanTemplateDao = new MyPlanTemplateDao(myPlanTemplateDaoConfig, this);
        myCollectingDao = new MyCollectingDao(myCollectingDaoConfig, this);

        registerDao(ContentCommit.class, contentCommitDao);
        registerDao(DiarySelf.class, diarySelfDao);
        registerDao(Essay.class, essayDao);
        registerDao(LocalGroup.class, localGroupDao);
        registerDao(LocalRecord.class, localRecordDao);
        registerDao(MyPlan.class, myPlanDao);
        registerDao(MyPlanTemplate.class, myPlanTemplateDao);
        registerDao(MyCollecting.class, myCollectingDao);
    }
    
    public void clear() {
        contentCommitDaoConfig.getIdentityScope().clear();
        diarySelfDaoConfig.getIdentityScope().clear();
        essayDaoConfig.getIdentityScope().clear();
        localGroupDaoConfig.getIdentityScope().clear();
        localRecordDaoConfig.getIdentityScope().clear();
        myPlanDaoConfig.getIdentityScope().clear();
        myPlanTemplateDaoConfig.getIdentityScope().clear();
        myCollectingDaoConfig.getIdentityScope().clear();
    }

    public ContentCommitDao getContentCommitDao() {
        return contentCommitDao;
    }

    public DiarySelfDao getDiarySelfDao() {
        return diarySelfDao;
    }

    public EssayDao getEssayDao() {
        return essayDao;
    }

    public LocalGroupDao getLocalGroupDao() {
        return localGroupDao;
    }

    public LocalRecordDao getLocalRecordDao() {
        return localRecordDao;
    }

    public MyPlanDao getMyPlanDao() {
        return myPlanDao;
    }

    public MyPlanTemplateDao getMyPlanTemplateDao() {
        return myPlanTemplateDao;
    }

    public MyCollectingDao getMyCollectingDao() {
        return myCollectingDao;
    }

}
