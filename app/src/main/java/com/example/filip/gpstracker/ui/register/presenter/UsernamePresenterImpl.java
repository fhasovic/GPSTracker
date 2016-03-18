package com.example.filip.gpstracker.ui.register.presenter;

import com.example.filip.gpstracker.api.DataManager;
import com.example.filip.gpstracker.api.DataManagerImpl;
import com.example.filip.gpstracker.api.DatabaseHelperImpl;
import com.example.filip.gpstracker.api.FirebaseHelperImpl;
import com.example.filip.gpstracker.api.RequestResponseListener;
import com.example.filip.gpstracker.ui.register.view.UsernameFragmentView;

/**
 * Created by Filip on 08/03/2016.
 */
public class UsernamePresenterImpl implements UsernamePresenter {
    private final DataManager dataManager;
    private final UsernameFragmentView usernameFragmentView;

    public UsernamePresenterImpl(UsernameFragmentView usernameFragmentView) {
        this.usernameFragmentView = usernameFragmentView;
        this.dataManager = new DataManagerImpl(new DatabaseHelperImpl(), new FirebaseHelperImpl(null, null));
    }

    @Override
    public void checkIfUsernameIsAvailable(String username) {
        dataManager.checkIfUsernameIsAlreadyTaken(username, new RequestResponseListener() {
            @Override
            public void onSuccess() {
                usernameFragmentView.onSuccess();
            }

            @Override
            public void onFailure(Throwable t) {
                if (t != null)
                    t.printStackTrace();
                usernameFragmentView.onFailure();
            }
        });
    }
}
