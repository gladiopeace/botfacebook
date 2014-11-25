/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package botvn.controllers;

import botvn.Response;
import botvn.botconfig.BotConfigLocal;
import botvn.libraries.BotBase;
import java.awt.Component;

/**
 *
 * @author vanvo
 */
public class BaseController extends BotBase{
    /**
     * Will be save the cookie for remote account
     */
    protected static Response mResponseRemoteAccount = null;
    
    /**
     * Will be save the cookie for user when login
     */
    protected static Response mResponseUser = null;
    
    /**
     * Keep a view parent
     */
    protected Component mParentView;
    
    /**
     * 
     */
    protected boolean IsConnected = false;
    
    /**
     * 
     */
    public boolean IsLogged = false;
    
    /**
     * 
     */
    public BaseController(){
        BotConfigLocal.createFile(BotConfigLocal.getConfigFile());
        BotConfigLocal.createFile(BotConfigLocal.getMessageFile());
    }
}
