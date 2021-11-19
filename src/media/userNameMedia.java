/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media;

/**
 *
 * @author rifat
 */
public class userNameMedia {

    String staffId;
    String usrName;

    public userNameMedia() {
    }

    public userNameMedia(String Id) {
        this.staffId = Id;
    }

    public userNameMedia(String id, String usrName) {
        this.staffId = id;
        this.usrName = usrName;
    }

    public String getId() {
        return staffId;
    }

    public void setId(String id) {
        this.staffId = id;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

}
