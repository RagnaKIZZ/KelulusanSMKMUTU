package ahmedt.kelulusanapp;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Utils {

    public static void getLoading(KProgressHUD hud, String judul, String deskripsi, boolean cancelable){
        if (judul == null){
            judul = "Loading";
        }

        if (deskripsi == null){
            deskripsi = "Please wait";
        }

        hud.setLabel(judul);
        hud.setDimAmount(0.5f);
        hud.setDetailsLabel(deskripsi);
        hud.setCancellable(cancelable);
        hud.setCornerRadius(20);
        hud.show();
    }
}
