/*
* Copyright (C) 2023  Applify
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.arr.nautaclear;

import android.app.Activity;
import com.arr.nautaclear.model.MailCount;
import com.arr.nautaclear.utils.EmailsCallback;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class NautaMail {

    private Store store;
    private Properties properties;
    private Session session;
    private Activity mActivity;

    public NautaMail(Activity activity) {
        this.mActivity = activity;
    }

    public long getInboxCount() {
        return getFolderCount("INBOX");
    }

    public long getTrashCount() {
        return getFolderCount("Trash");
    }

    public void obtainsEmail(String email, String password, EmailsCallback callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(
                () -> {
                    MailCount count = new MailCount();
                    try {
                        // obtains(email, password);
                        properties = new Properties();
                        properties.put("mail.imap.host", "imap.nauta.cu");
                        properties.put("mail.imap.port", "143");
                        session = Session.getDefaultInstance(properties);
                        store = session.getStore("imap");
                        store.connect("imap.nauta.cu", email, password);

                        count.inboxCount = getInboxCount();
                        count.trashCount = getTrashCount();
                        mActivity.runOnUiThread(() -> callback.updateUI(count));
                    } catch (AuthenticationFailedException e) {
                        e.printStackTrace();
                        mActivity.runOnUiThread(() -> callback.handleException(e));
                    } catch (Exception e) {
                        e.printStackTrace();
                        mActivity.runOnUiThread(() -> callback.handleException(e));
                    }
                });
    }

    public void deleteEmails(String email, String password, EmailsCallback callback) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(
                () -> {
                    MailCount count = new MailCount();
                    try {
                        properties = new Properties();
                        properties.put("mail.imap.host", "imap.nauta.cu");
                        properties.put("mail.imap.port", "143");
                        session = Session.getDefaultInstance(properties);
                        store = session.getStore("imap");
                        store.connect("imap.nauta.cu", email, password);

                        deleteFolder("INBOX");
                        deleteFolder("Trash");

                        count.inboxCount = getInboxCount();
                        count.trashCount = getTrashCount();
                        mActivity.runOnUiThread(() -> callback.updateUI(count));
                    } catch (AuthenticationFailedException e) {
                        e.printStackTrace();
                        mActivity.runOnUiThread(() -> callback.handleException(e));
                    } catch (Exception e) {
                        e.printStackTrace();
                        mActivity.runOnUiThread(() -> callback.handleException(e));
                    }
                });
    }

    private long getFolderCount(String folderName) {
        Folder folder = null;
        try {
            folder = store.getFolder(folderName);
            folder.open(Folder.READ_ONLY);
            return folder.getMessageCount();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void deleteFolder(String folderName) {
        Folder folder = null;
        try {
            folder = store.getFolder(folderName);
            folder.open(Folder.READ_WRITE);
            Message[] messages = folder.getMessages();
            deleteAllMessage(folder, messages);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllMessage(Folder folder, Message[] messages) throws MessagingException {
        for (Message message : messages) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        folder.expunge();
    }
}
