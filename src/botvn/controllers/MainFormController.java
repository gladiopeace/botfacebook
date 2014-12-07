package botvn.controllers;

import botvn.HttpUtil;
import botvn.JerryUtil;
import botvn.Response;
import botvn.botconfig.BotConfigListener;
import botvn.botconfig.BotConfigLocal;
import botvn.botconfig.BotConfigRequest;
import botvn.botconfig.BotMessageObject;
import botvn.botconfig.BotMessageTemplate;
import botvn.botconfig.BotUrlFormatter;
import botvn.libraries.BotEventListener;
import botvn.libraries.message.BotMessageAds;
import botvn.libraries.search.fanpage.BotFanPage;
import botvn.libraries.BotUtils;
import botvn.libraries.LoggingListener;
import botvn.libraries.LoggingUtils;
import botvn.libraries.LoginInfo;
import botvn.libraries.MessageBox;
import botvn.libraries.like.BotLike;
import botvn.libraries.like.BotLikeListener;
import botvn.libraries.message.BotMessageAdsListener;
import botvn.libraries.search.BotSearch;
import botvn.libraries.search.BotSearchObject;
import botvn.libraries.search.group.BotGroup;
import botvn.libraries.search.group.BotGroupObject;
import botvn.libraries.search.group.IPostCommentListener;
import botvn.libraries.search.group.OnFetchMyGroupsListener;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import jodd.upload.FileUpload;
import jodd.upload.MultipartRequestInputStream;

/**
 *
 * @author vanvo
 */
public class MainFormController extends BaseController implements BotConfigListener {

    /**
     *
     */
    private BotFanPage mBotFanPage;

    /**
     * 
     */
    private BotGroup mBotGroup;
    
    /**
     *
     */
    private BotSearch mSearch;

    /**
     *
     */
    private LoginInfo mLoginInfo;

    /**
     *
     */
    private BotMessageAds mMessage;
    private BotMessageAdsListener mMessageListener;

    /**
     *
     */
    private BotLike mBotLike;

    /**
     *
     * @param parent
     */
    public MainFormController(Component parent) {
        mParentView = parent;
        new BotConfigRequest(this).start();
    }

    @Override
    public void OnInitSuccess() {
        ((BotConfigListener) mParentView).OnInitSuccess();
        LoggingUtils.print("Init success");
        IsConnected = true;
    }

    @Override
    public void OnInitError() {
        ((BotConfigListener) mParentView).OnInitError();
        JOptionPane.showConfirmDialog(mParentView, "Error when init the system, re-open to fix");
        IsConnected = false;
    }

    /**
     *
     * @param listener
     * @param keyword
     */
    public void ScanFanPage(BotEventListener listener, String keyword) {
        if (IsConnected && IsLogged) {
            mBotFanPage = new BotFanPage(listener);
            mBotFanPage.setCookieStore(mResponseUser.getCookieStore());
            mBotFanPage.setKeyword(keyword);
            mBotFanPage.start();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
        }
    }

    /**
     *
     * @param listener
     * @param keyword
     * @param value
     */
    public void Search(BotEventListener listener, String keyword, int value) {
        if (IsConnected && IsLogged) {
            mSearch = new BotSearch(listener, value);
            mSearch.setCookieStore(mResponseUser.getCookieStore());
            mSearch.setKeyword(keyword);
            mSearch.start();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }
    
    public void SearchGroup(BotEventListener listener, String keyword, int limit){
        if (IsConnected && IsLogged) {
            mBotGroup = new BotGroup(listener);
            mBotGroup.setCookieStore(mLoginCookies);
            mBotGroup.setKeyword(keyword);
            mBotGroup.setLimit(limit);
            mBotGroup.start();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }
    
    /**
     * 
     * @param listener 
     */
    public void JoinGroup(LoggingListener listener){
        mBotGroup.setLog(listener);
        mBotGroup.JoinGroup();
    }
    
    /**
     * 
     * @param listener 
     */
    public void FetchMyGroup(OnFetchMyGroupsListener listener){
        if (IsConnected && IsLogged) {
            mBotGroup = new BotGroup(null);
            mBotGroup.setCookieStore(mLoginCookies);
            mBotGroup.setFetchMyGroupsEvent(listener);
            mBotGroup.ListGroups();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
        
    }

    /**
     *
     * @param email
     * @param password
     * @return true: login success, else false
     * @throws java.io.IOException
     */
    public boolean Login(String email, String password) throws IOException, InterruptedException {
        mResponseUser = BotUtils.loginToFacebook(email, password);
        IsLogged = mResponseUser.getCookies().size() >= 11;
        mLoginCookies = mResponseUser.getCookieStore(); // init first cookie store
        mLoginInfo = new LoginInfo();
        mLoginInfo.fbdtsg = getFBDTSG();
        mLoginInfo.userId = getThisID();
        return IsLogged;
    }

    /**
     *
     */
    public void Logout() {
        mResponseUser = null;
        IsLogged = false;
    }

    /**
     *
     * @return
     */
    public List<BotMessageObject> MessagesToList() {
        return BotMessageTemplate.toList();
    }

    /**
     *
     * @param message
     * @param receiverId
     * @throws java.io.IOException
     */
    public void SendMessage(String message, String receiverId) throws IOException {
        if (IsConnected && IsLogged) {
            if (mMessage == null) {
                mMessage = new BotMessageAds(mLoginInfo);
            }
            mMessage.setMessage(message, receiverId);
            mMessage.start();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }

    /**
     *
     * @param message
     * @param receiverId
     * @param listener
     * @throws IOException
     */
    public void SendMessage(String message, String receiverId, BotMessageAdsListener listener) throws IOException {
        if (IsConnected && IsLogged) {
            if (mMessage == null) {
                mMessage = new BotMessageAds(mLoginInfo);
            }
            mMessage.setBotMessageListener(listener);
            //mMessage.setMessage(message, receiverId);
            mMessage.start();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }

    /**
     *
     * @param messages
     * @param users
     * @throws java.lang.InterruptedException
     */
    public void SendMessageToMultiUser(List<BotMessageObject> messages, List<BotSearchObject> users) throws InterruptedException {
        if (IsConnected && IsLogged) {
            if (mMessage == null) {
                mMessage = new BotMessageAds(mLoginInfo);
            }
            mMessage.setMessages(null, null);
            mMessage.startMultiUser();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }

    /**
     *
     * @param messages
     * @param users
     * @param listener
     * @throws java.lang.InterruptedException
     */
    public void SendMessageToMultiUser(List<BotMessageObject> messages, List<BotSearchObject> users, BotMessageAdsListener listener) throws InterruptedException {
        if (IsConnected && IsLogged) {
            if (mMessage == null) {
                mMessage = new BotMessageAds(mLoginInfo);
                //mMessage.setLoginCookieStore(mLoginCookies);
            }
            mMessage.setBotMessageListener(listener);
            mMessage.setMessages(messages, users);
            mMessage.startMultiUser();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }

    /**
     *
     * @param fanpages
     * @param listener
     */
    public void AutoLike(List<BotSearchObject> fanpages, BotLikeListener listener) {
        LoggingUtils.print(IsConnected + " - " + IsLogged);
        if (IsConnected && IsLogged) {
            if (mBotLike == null) {
                mBotLike = new BotLike(mLoginInfo);
            }
            mBotLike.setLikeListener(listener);
            mBotLike.setListPage(fanpages);
            mBotLike.RunLike();
        } else {
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
        }
    }

    /**
     * Check is any results search are selected before auto actions
     *
     * @param results
     * @return
     */
    public boolean CheckIsResutlsSelected(List<BotSearchObject> results) {
        boolean selected = false;
        for (BotSearchObject obj : results) {
            if (obj.IsSelected) {
                return true;
            }
        }
        return selected;
    }
    
    /**
     * 
     * @param results
     * @return 
     */
    public int countSelected(List<BotSearchObject> results){
        if(results == null) return -1;
        int count = 0;
        for(BotSearchObject obj : results){
            count += obj.IsSelected ? 1 : 0;
        }
        return count;
    }
    
    /**
     * 
     * @param commentTemp
     * @param listener
     */
    public void groupLikePost(final List<BotGroupObject> groups,final String commentTemp, final IPostCommentListener listener){
        if(!(IsConnected && IsLogged)){
            MessageBox.Show(mParentView, "Bạn chưa đăng nhập", "BOTVN", MessageBox.Buttons.OK);
            ((BotConfigListener) mParentView).OnInitError();
            return;
        }
        mBotGroup.LikeComment(groups, commentTemp, listener);
    }
}
