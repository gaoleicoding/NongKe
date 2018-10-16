package com.nongke.jindao.mpresenter;


import com.nongke.jindao.base.mmodel.ProjectListData;
import com.nongke.jindao.base.mpresenter.BasePresenter;
import com.nongke.jindao.mcontract.ProjectContract;
import com.nongke.jindao.base.thirdframe.rxjava.BaseObserver;

import io.reactivex.Observable;


public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    private int mCurrentPage = 1;

    @Override
    public void getProjectInfo(int page, int cid) {
        Observable observable = mRestService.getProjectListData(page, cid);
        addSubscribe(observable, new BaseObserver<ProjectListData>(true) {
            @Override
            public void onNext(ProjectListData projectListData) {
                mView.showProjectList(projectListData, false);
            }
        });
    }

    @Override
    public void onRefreshMore(int cid) {
        Observable observable = mRestService.getProjectListData(-1, cid);
        addSubscribe(observable, new BaseObserver<ProjectListData>(false) {
            @Override
            public void onNext(ProjectListData projectListData) {
                mView.showProjectList(projectListData, true);
            }
        });
    }

    @Override
    public void onLoadMore(int cid) {
        ++mCurrentPage;
        Observable observable = mRestService.getProjectListData(mCurrentPage, cid);
        addSubscribe(observable, new BaseObserver<ProjectListData>(false) {
            @Override
            public void onNext(ProjectListData projectListData) {
                mView.showProjectList(projectListData, false);
            }
        });
    }


}

