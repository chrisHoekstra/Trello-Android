package com.chrishoekstra.trello.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.chrishoekstra.trello.R;
import com.chrishoekstra.trello.model.TrelloModel;
import com.chrishoekstra.trello.service.TrelloService;
import com.chrishoekstra.trello.vo.*;

public class TrelloController {

    // Singleton stuff
    private static TrelloController controller;

    public static TrelloController getInstance() {
        if (controller == null) {
            controller = new TrelloController();
            controller.mModel = TrelloModel.getInstance();
            controller.mService = new TrelloService();
            controller.addListeners();
        }
        return controller;
    }

    // Variables
    private TrelloModel mModel;
    private TrelloService mService;


    // Public Methods
    public void login(Activity context,String username, String password) {
        new UserLoginTask(context).execute(username, password);
    }

    public void fetchBoard(String mBoardId) {
        new BoardFetchTask().execute(mBoardId);
    }

    public void addCard(Activity context,String boardId, String boardListId, String cardName, int position) {
        AddCardVO addCard = new AddCardVO();

        addCard.method = ApiMethodVO.CREATE;
        addCard.token = mService.getFilteredToken();
        addCard.data.idParents.add(boardListId);
        addCard.data.idParents.add(boardId);
        addCard.data.attrs.name = cardName;
        addCard.data.attrs.pos = position;
        addCard.data.attrs.idBoard = boardId;
        addCard.data.attrs.idList = boardListId;

        new AddCardTask(context).execute(addCard);
    }


    // Private methods
    private void addListeners() {
        mModel.addListener(new TrelloModel.OnLoginCompleteListener() {
            @Override
            public void onLoginCompleteEvent(TrelloModel model) {
                // TODO Auto-generated method stub

            }
        });
    }


    // AsyncTasks
    private class UserLoginTask extends AsyncTask<String, Void, Boolean> {

        Activity context;
        private ProgressDialog waitDialog;

        public UserLoginTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            if (waitDialog == null) {
                waitDialog = new ProgressDialog(context);
                waitDialog.setIndeterminate(true);
            }

            waitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                public void onCancel(DialogInterface dialog) {
                    if (UserLoginTask.this != null) {
                        UserLoginTask.this.cancel(true);
                    }
                    context.finish();
                }
            });
            waitDialog.setTitle(context.getString(R.string.login));
            waitDialog.setMessage(context.getString(R.string.loading));
            waitDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... parameters) {
            Boolean result = false;

            if (mService.login(parameters[0], parameters[1])) {
                AllBoardsResultVO results = mService.getBoardResults();
                if (results != null) {
                    mModel.setAllBoardsResult(results);
                    result = true;
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (waitDialog != null)
                waitDialog.dismiss();
            mModel.isLoginFail = !result;
            mModel.loginComplete();
        }
    }

    private class BoardFetchTask extends AsyncTask<String, Void, BoardResultVO> {

        @Override
        protected BoardResultVO doInBackground(String... parameters) {
            BoardResultVO result = null;

            result = mService.getBoard(parameters[0]);

            return result;
        }

        @Override
        protected void onPostExecute(BoardResultVO result) {
            if (result != null) {
                mModel.boardReceived(result);
            }
        }
    }

    private class AddCardTask extends AsyncTask<AddCardVO, Void, CardVO> {

         Activity context;
        private ProgressDialog waitDialog;

        public AddCardTask(Activity context) {
            this.context = context;
        }

         @Override
        protected void onPreExecute() {
            if (waitDialog == null) {
                waitDialog = new ProgressDialog(context);
                waitDialog.setIndeterminate(true);
            }

            waitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                public void onCancel(DialogInterface dialog) {
                    if (AddCardTask.this != null) {
                        AddCardTask.this.cancel(true);
                    }
                    context.finish();
                }
            });
            waitDialog.setTitle(context.getString(R.string.add_card));
            waitDialog.setMessage(context.getString(R.string.loading));
            waitDialog.show();
        }

        @Override
        protected CardVO doInBackground(AddCardVO... parameters) {
            CardVO result = null;

            result = mService.addCard(parameters[0]);

            return result;
        }

        @Override
        protected void onPostExecute(CardVO result) {
             if (waitDialog != null)
                waitDialog.dismiss();
            if (result != null) {
                mModel.cardAdded(result);
                Toast.makeText(context,R.string.card_added,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
