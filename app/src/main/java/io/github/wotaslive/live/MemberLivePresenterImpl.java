package io.github.wotaslive.live;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.wotaslive.Constants;
import io.github.wotaslive.model.MemberLiveEntity;
import io.github.wotaslive.model.MemberLiveRequestBody;
import io.github.wotaslive.network.Network;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by codeczx on 2017/10/10.
 */

public class MemberLivePresenterImpl implements MemberLiveContract.MemberLivePresenter {

    private static final String TAG = "MemberLivePresenterImpl";
    private Context mContext;
    private MemberLiveContract.MemberLiveView mView;

    public MemberLivePresenterImpl(Context context, MemberLiveContract.MemberLiveView view) {
        mContext = context;
        mView = view;
    }


    @Override
    public void getMemberLive() {
        MemberLiveRequestBody requestBody = new MemberLiveRequestBody(
                0, 0, 0, 0, 20, System.currentTimeMillis());
        Network.getInstance().getSNHApi().getMemberLive(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(memberLiveEntity -> {
                    List<Object> list = new ArrayList<>();
                    MemberLiveEntity.ContentBean contentBean = memberLiveEntity.getContent();
                    list.add(Constants.MEMBER_LIVE_TYPE_LIVE);
                    list.addAll(contentBean.getLiveList());
                    if (contentBean.getLiveList().size() % 2 > 0) {
                        list.add(null);
                    }
                    list.add(Constants.MEMBER_LIVE_TYPE_REVIEW);
                    list.addAll(contentBean.getReviewList());
                    mView.refreshUI(list);
                }, error -> {

                });
    }
}
