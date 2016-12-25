package jacksonmarkowski.launcher;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InstalledItems {

    private Context context;

    public InstalledItems(Context context) {
        this.context = context;
    }

    public void updateApps() {

        AppDAO appDAO = new AppDAO(context);

        List<App> appsInDb = appDAO.getAllApps();
        List<String> packageNamesInDb = new ArrayList<>();
        for (App app : appsInDb) {
            packageNamesInDb.add(app.getName());
        }

        List<String> instPackageNames = new ArrayList<>();

        //Gets all installed applications on the system
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> appsInfo = pm.getInstalledApplications(0);
        for (ApplicationInfo appInfo : appsInfo) {
            String packageName = appInfo.packageName;

            //Application must have a valid launch intent or else it will be ignored
            if (pm.getLaunchIntentForPackage(packageName) != null) {
                instPackageNames.add(packageName);

                //New application, not in the database, is added
                if (!packageNamesInDb.contains(packageName)) {
                    appDAO.createApp(packageName);
                    Log.d("Add", packageName);
                }
            }
        }

        //Removes apps in the database but no longer installed on the system
        packageNamesInDb.removeAll(instPackageNames);
        for (String packageName : packageNamesInDb) {
            appDAO.deleteApp(packageName);
            Log.d("Remove", packageName);
            //db.removeApplicationFromList(appID);

        }

    }
}
