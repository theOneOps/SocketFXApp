package theView.manage;

public class AppManagement {

    private AppWindowConnect appWindowConnect;
    private WindowCreateEnt windowCreateEnt;
    private WindowShowEnt windowShowEnt;

    public AppManagement()
    {
        appWindowConnect = new AppWindowConnect();
        windowCreateEnt = new WindowCreateEnt();
        windowCreateEnt = new WindowCreateEnt();
    }

    public AppWindowConnect getAppWindowConnect() {
        return appWindowConnect;
    }

    public void setAppWindowConnect(AppWindowConnect appWindowConnect) {
        this.appWindowConnect = appWindowConnect;
    }

    public WindowCreateEnt getWindowCreateEnt() {
        return windowCreateEnt;
    }

    public void setWindowCreateEnt(WindowCreateEnt windowCreateEnt) {
        this.windowCreateEnt = windowCreateEnt;
    }

    public WindowShowEnt getWindowShowEnt() {
        return windowShowEnt;
    }

    public void setWindowShowEnt(WindowShowEnt windowShowEnt) {
        this.windowShowEnt = windowShowEnt;
    }
}


