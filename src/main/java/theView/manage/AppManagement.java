package theView.manage;

import theView.manage.windowShowEnt.WindowShowEnt;

/**
 * The AppManagement class manages the different windows of the application.
 * It provides access to the main connection window, the enterprise creation window,
 * the enterprise configuration window, and the enterprise display window.
 */
public class AppManagement {

    private AppWindowConnect appWindowConnect; // The main connection window
    private WindowCreateEnt windowCreateEnt; // The enterprise creation window
    private WindowShowEnt windowShowEnt; // The enterprise display window
    private WindowConfigEnterprise windowConfigEnterprise; // The enterprise configuration window

    /**
     * Constructs an AppManagement instance and initializes the main windows.
     */
    public AppManagement() {
        appWindowConnect = new AppWindowConnect();
        windowCreateEnt = new WindowCreateEnt();
        windowConfigEnterprise = new WindowConfigEnterprise();
    }

    /**
     * Gets the enterprise configuration window.
     *
     * @return the enterprise configuration window
     */
    public WindowConfigEnterprise getWindowConfigEnterprise() {
        return windowConfigEnterprise;
    }

    /**
     * Gets the main connection window.
     *
     * @return the main connection window
     */
    public AppWindowConnect getAppWindowConnect() {
        return appWindowConnect;
    }

    /**
     * Sets the main connection window.
     *
     * @param appWindowConnect the main connection window to set
     */
    public void setAppWindowConnect(AppWindowConnect appWindowConnect) {
        this.appWindowConnect = appWindowConnect;
    }

    /**
     * Gets the enterprise creation window.
     *
     * @return the enterprise creation window
     */
    public WindowCreateEnt getWindowCreateEnt() {
        return windowCreateEnt;
    }

    /**
     * Sets the enterprise creation window.
     *
     * @param windowCreateEnt the enterprise creation window to set
     */
    public void setWindowCreateEnt(WindowCreateEnt windowCreateEnt) {
        this.windowCreateEnt = windowCreateEnt;
    }

    /**
     * Gets the enterprise display window.
     *
     * @return the enterprise display window
     */
    public WindowShowEnt getWindowShowEnt() {
        return windowShowEnt;
    }

    /**
     * Sets the enterprise display window.
     *
     * @param windowShowEnt the enterprise display window to set
     */
    public void setWindowShowEnt(WindowShowEnt windowShowEnt) {
        this.windowShowEnt = windowShowEnt;
    }
}
