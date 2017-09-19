package com.example.greatbook.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.greendao.entity.ContentCommit;

import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.MyPlanDao;
import com.example.greatbook.greendao.MyPlanTemplateDao;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.ContentCommitDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig localGroupDaoConfig;
    private final DaoConfig localRecordDaoConfig;
    private final DaoConfig myPlanDaoConfig;
    private final DaoConfig myPlanTemplateDaoConfig;
    private final DaoConfig essayDaoConfig;
    private final DaoConfig contentCommitDaoConfig;

    private final LocalGroupDao localGroupDao;
    private final LocalRecordDao localRecordDao;
    private final MyPlanDao myPlanDao;
    private final MyPlanTemplateDao myPlanTemplateDao;
    private final EssayDao essayDao;
    private final ContentCommitDao contentCommitDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        localGroupDaoConfig = daoConfigMap.get(LocalGroupDao.class).clone();
        localGroupDaoConfig.initIdentityScope(type);

        localRecordDaoConfig = daoConfigMap.get(LocalRecordDao.class).clone();
        localRecordDaoConfig.initIdentityScope(type);

        myPlanDaoConfig = daoConfigMap.get(MyPlanDao.class).clone();
        myPlanDaoConfig.initIdentityScope(type);

        myPlanTemplateDaoConfig = daoConfigMap.get(MyPlanTemplateDao.class).clone();
        myPlanTemplateDaoConfig.initIdentityScope(type);

        essayDaoConfig = daoConfigMap.get(EssayDao.class).clone();
        essayDaoConfig.initIdentityScope(type);

        contentCommitDaoConfig = daoConfigMap.get(ContentCommitDao.class).clone();
        contentCommitDaoConfig.initIdentityScope(type);

        localGroupDao = new LocalGroupDao(localGroupDaoConfig, this);
        localRecordDao = new LocalRecordDao(localRecordDaoConfig, this);
        myPlanDao = new MyPlanDao(myPlanDaoConfig, this);
        myPlanTemplateDao = new MyPlanTemplateDao(myPlanTemplateDaoConfig, this);
        essayDao = new EssayDao(essayDaoConfig, this);
        contentCommitDao = new ContentCommitDao(contentCommitDaoConfig, this);

        registerDao(LocalGroup.class, localGroupDao);
        registerDao(LocalRecord.class, localRecordDao);
        registerDao(MyPlan.class, myPlanDao);
        registerDao(MyPlanTemplate.class, myPlanTemplateDao);
        registerDao(Essay.class, essayDao);
        registerDao(ContentCommit.class, contentCommitDao);
    }
    
    public void clear() {
        localGroupDaoConfig.getIdentityScope().clear();
        localRecordDaoConfig.getIdentityScope().clear();
        myPlanDaoConfig.getIdentityScope().clear();
        myPlanTemplateDaoConfig.getIdentityScope().clear();
        essayDaoConfig.getIdentityScope().clear();
        contentCommitDaoConfig.getIdentityScope().clear();
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

    public EssayDao getEssayDao() {
        return essayDao;
    }

    public ContentCommitDao getContentCommitDao() {
        return contentCommitDao;
    }

}
