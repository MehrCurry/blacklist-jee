/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist;

/**
 *
 * @author michael
 */
public enum ApplicationURL {

    LOCAL("http://localhost:8080/blacklist-jee7/resources/blacklist");

    private String appUrl;

    private ApplicationURL(String url) {
        appUrl = url;
    }

    public String getAppURL() {
        return appUrl;
    }
}
