package me.hqythu.ihs.message.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihs.commons.notificationcenter.HSGlobalNotificationCenter;
import com.ihs.commons.notificationcenter.INotificationObserver;
import com.ihs.commons.utils.HSBundle;
import com.ihs.demo.message.FriendManager;

import java.util.ArrayList;

import me.hqythu.ihs.message.R;
import me.hqythu.ihs.message.data.MessageSession;
import me.hqythu.ihs.message.db.SessionDBManager;

/**
 * Created by hqythu on 9/8/2015.
 */
public class MessageSessionFragment extends Fragment {

    private ArrayList<MessageSession> mSessionInfos;
    private RecyclerView mSessionList;
    private MessageSessionAdapter mAdapter;

    public static final String DISPLAY_ALL = "DisplayAll";
    public static final String DISPLAY_ARCHIVED = "DisplayArhived";
    private boolean displayAll, displayArchived;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        displayAll = bundle.getBoolean(DISPLAY_ALL);
        displayArchived = bundle.getBoolean(DISPLAY_ARCHIVED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_message_session, null);

        ArrayList<SessionDBManager.MessageSessionInfo> sessions =
            SessionDBManager.getSessionInfoList(displayAll, displayArchived);
        mSessionInfos = new ArrayList<>();
        for (SessionDBManager.MessageSessionInfo session : sessions) {
            mSessionInfos.add(new MessageSession(session));
        }

        mSessionList = (RecyclerView) view.findViewById(R.id.session_list);
        mSessionList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MessageSessionAdapter(getActivity(), mSessionInfos);
        mSessionList.setAdapter(mAdapter);

        return view;
    }
}