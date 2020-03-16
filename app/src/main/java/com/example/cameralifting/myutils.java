package com.example.cameralifting;

import com.jaredrummler.android.shell.Shell;
import com.stericson.RootTools.RootTools;

public class myutils {

    public static boolean isMIUI()
    {
        String s = Shell.SH.run("getprop ro.miui.ui.version.code").toString();
        if (s.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static Boolean checkRoot()
    {
        if (RootTools.isRootAvailable())
        {
            if (!RootTools.isAccessGiven())
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;
    }


}
